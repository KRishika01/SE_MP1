/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.roller.weblogger.business.jpa;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.UserManager;
import org.apache.roller.weblogger.business.WeblogQueryManager;
import org.apache.roller.weblogger.pojos.*;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.business.Weblogger;


/**
 * JPA implementation of the WeblogQueryManager.
 */
@com.google.inject.Singleton
public class JPAWeblogQueryManagerImpl implements WeblogQueryManager {

    private static final Log log =
            LogFactory.getLog(JPAWeblogQueryManagerImpl.class);

    private final JPAPersistenceStrategy strategy;
    private final Weblogger roller;

    private static final Comparator<StatCount> STAT_COUNT_COUNT_REVERSE_COMPARATOR =
            Collections.reverseOrder(StatCountCountComparator.getInstance());

    @com.google.inject.Inject
    protected JPAWeblogQueryManagerImpl(Weblogger roller,
                                        JPAPersistenceStrategy strat) {
        log.debug("Instantiating JPA Weblog Query Manager");
        this.roller = roller;
        this.strategy = strat;
    }

    @Override
    public List<Weblog> getWeblogs(
            Boolean enabled, Boolean active,
            Date startDate, Date endDate, int offset, int length) throws WebloggerException {
        
        //if (endDate == null) endDate = new Date();
                      
        List<Object> params = new ArrayList<>();
        int size = 0;
        String queryString;
        StringBuilder whereClause = new StringBuilder();
        
        queryString = "SELECT w FROM Weblog w WHERE ";

        if (startDate != null) {
            Timestamp start = new Timestamp(startDate.getTime());
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            params.add(size++, start);
            whereClause.append(" w.dateCreated > ?").append(size);
        }
        if (endDate != null) {
            Timestamp end = new Timestamp(endDate.getTime());
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            params.add(size++, end);
            whereClause.append(" w.dateCreated < ?").append(size);
        }
        if (enabled != null) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            params.add(size++, enabled);
            whereClause.append(" w.visible = ?").append(size);
        }
        if (active != null) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            params.add(size++, active);
            whereClause.append(" w.active = ?").append(size);
        }      
                
        whereClause.append(" ORDER BY w.dateCreated DESC");
        
        TypedQuery<Weblog> query = strategy.getDynamicQuery(queryString + whereClause.toString(), Weblog.class);
        if (offset != 0) {
            query.setFirstResult(offset);
        }
        if (length != -1) {
            query.setMaxResults(length);
        }
        for (int i=0; i<params.size(); i++) {
           query.setParameter(i+1, params.get(i));
        }
        
        return query.getResultList();
    }

    @Override
    public List<User> getWeblogUsers(Weblog weblog, boolean enabledOnly) throws WebloggerException {
        List<User> users = new ArrayList<>();
        List<WeblogPermission> perms = roller.getUserManager().getWeblogPermissions(weblog);
        for (WeblogPermission perm : perms) {
            User user = perm.getUser();
            if (user == null) {
                log.error("ERROR user is null, userName:" + perm.getUserName());
                continue;
            }
            if (!enabledOnly || user.getEnabled()) {
                users.add(user);
            }
        }
        return users;
    }
    
    @Override
    public List<Weblog> getUserWeblogs(User user, boolean enabledOnly) throws WebloggerException {
        List<Weblog> weblogs = new ArrayList<>();
        if (user == null) {
            return weblogs;
        }
        List<WeblogPermission> perms = roller.getUserManager().getWeblogPermissions(user);
        for (WeblogPermission perm : perms) {
            Weblog weblog = perm.getWeblog();
            if ((!enabledOnly || weblog.getVisible()) && BooleanUtils.isTrue(weblog.getActive())) {
                weblogs.add(weblog);
            }
        }
        return weblogs;
    }

    @Override
    public long getWeblogCount() throws WebloggerException {
        List<Long> results = strategy.getNamedQuery(
                "Weblog.getCountAllDistinct", Long.class).getResultList();
        return results.get(0);
    }

    @Override
    public Map<String, Long> getWeblogHandleLetterMap() throws WebloggerException {
        String lc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Map<String, Long> results = new TreeMap<>();
        TypedQuery<Long> query = strategy.getNamedQuery(
                "Weblog.getCountByHandleLike", Long.class);
        for (int i=0; i<26; i++) {
            char currentChar = lc.charAt(i);
            query.setParameter(1, currentChar + "%");
            List<Long> row = query.getResultList();
            Long count = row.get(0);
            results.put(String.valueOf(currentChar), count);
        }
        return results;
    }

    @Override
    public List<Weblog> getWeblogsByLetter(char letter, int offset, int length)
    throws WebloggerException {
        TypedQuery<Weblog> query = strategy.getNamedQuery(
                "Weblog.getByLetterOrderByHandle", Weblog.class);
        query.setParameter(1, letter + "%");
        if (offset != 0) {
            query.setFirstResult(offset);
        }
        if (length != -1) {
            query.setMaxResults(length);
        }
        return query.getResultList();
    }

    @Override
    public List<StatCount> getMostCommentedWeblogs(Date startDate, Date endDate,
            int offset, int length)
            throws WebloggerException {
        
        Query query;
        
        if (endDate == null) {
            endDate = new Date();
        }
        
        if (startDate != null) {
            Timestamp start = new Timestamp(startDate.getTime());
            Timestamp end = new Timestamp(endDate.getTime());
            query = strategy.getNamedQuery(
                    "WeblogEntryComment.getMostCommentedWebsiteByEndDate&StartDate");
            query.setParameter(1, end);
            query.setParameter(2, start);
        } else {
            Timestamp end = new Timestamp(endDate.getTime());
            query = strategy.getNamedQuery(
                    "WeblogEntryComment.getMostCommentedWebsiteByEndDate");
            query.setParameter(1, end);
        }
        if (offset != 0) {
            query.setFirstResult(offset);
        }
        if (length != -1) {
            query.setMaxResults(length);
        }
        List<?> queryResults = query.getResultList();
        List<StatCount> results = new ArrayList<>();
        if (queryResults != null) {
            for (Object obj : queryResults) {
                Object[] row = (Object[]) obj;
                StatCount sc = new StatCount(
                        (String)row[1],                     // weblog id
                        (String)row[2],                     // weblog handle
                        (String)row[3],                     // weblog name
                        "statCount.weblogCommentCountType", // stat type
                        ((Long)row[0]));        // # comments
                sc.setWeblogHandle((String)row[2]);
                results.add(sc);
            }
        }

        // Original query ordered by desc # comments.
        // JPA QL doesn't allow queries to be ordered by aggregates; do it in memory
        results.sort(STAT_COUNT_COUNT_REVERSE_COMPARATOR);
        
        return results;
    }

    @Override
    public void release() {}
}
