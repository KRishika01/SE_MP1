/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.apache.roller.weblogger.business.jpa;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.WebloggerException;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.roller.weblogger.business.WeblogManager;
import org.apache.roller.weblogger.business.Weblogger;
import org.apache.roller.weblogger.pojos.CustomTemplateRendition;
import org.apache.roller.weblogger.pojos.StatCount;
import org.apache.roller.weblogger.pojos.StatCountCountComparator;
import org.apache.roller.weblogger.pojos.ThemeTemplate.ComponentType;
import org.apache.roller.weblogger.pojos.User;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogPermission;
import org.apache.roller.weblogger.pojos.WeblogTemplate;


/*
 * JPAWeblogManagerImpl.java
 * Created on May 31, 2006, 4:08 PM
 */
@com.google.inject.Singleton
public class JPAWeblogManagerImpl implements WeblogManager {
    
    private static final Log log = LogFactory.getLog(JPAWeblogManagerImpl.class);
    
    private static final Comparator<StatCount> STAT_COUNT_COUNT_REVERSE_COMPARATOR =
            Collections.reverseOrder(StatCountCountComparator.getInstance());
    
    private final Weblogger roller;
    private final JPAPersistenceStrategy strategy;

    // New collaborators to improve modularization
    private final WeblogLifecycleService lifecycleService;
    private final WeblogTemplateService templateService;
    
    // cached mapping of weblogHandles -> weblogIds
    private final Map<String, String> weblogHandleToIdMap =
            Collections.synchronizedMap(new HashMap<>());

    @com.google.inject.Inject
    protected JPAWeblogManagerImpl(Weblogger roller, JPAPersistenceStrategy strat) {
        log.debug("Instantiating JPA Weblog Manager");
        this.roller = roller;
        this.strategy = strat;
        this.lifecycleService = new WeblogLifecycleService(roller, strat);
        this.templateService = new WeblogTemplateService(roller, strat);
    }
    
    @Override
    public void release() {}
    
    
    /**
     * Update existing weblog.
     */
    @Override
    public void saveWeblog(Weblog weblog) throws WebloggerException {
        weblog.setLastModified(new java.util.Date());
        strategy.store(weblog);
    }
    
    @Override
    public void removeWeblog(Weblog weblog) throws WebloggerException {
        // remove contents first, then remove weblog
        this.lifecycleService.removeWeblogContents(weblog);
        this.strategy.remove(weblog);
        
        // remove entry from cache mapping
        this.weblogHandleToIdMap.remove(weblog.getHandle());
    }

    /**
     * Store a custom weblog template.
     */
    @Override
    public void saveTemplate(WeblogTemplate template) throws WebloggerException {
        this.templateService.saveTemplate(template);
    }

    @Override
    public void saveTemplateRendition(CustomTemplateRendition rendition) throws WebloggerException {
        this.templateService.saveTemplateRendition(rendition);
    }
    
    @Override
    public void removeTemplate(WeblogTemplate template) throws WebloggerException {
        this.templateService.removeTemplate(template);
    }
    
    @Override
    public void addWeblog(Weblog newWeblog) throws WebloggerException {
        this.strategy.store(newWeblog);
        this.strategy.flush();
        this.lifecycleService.addWeblogContents(newWeblog);
    }
    
    @Override
    public Weblog getWeblog(String id) throws WebloggerException {
        return (Weblog) this.strategy.load(Weblog.class, id);
    }
    
    @Override
    public Weblog getWeblogByHandle(String handle) throws WebloggerException {
        return getWeblogByHandle(handle, Boolean.TRUE);
    }
    
    /**
     * Return weblog specified by handle.
     */
    @Override
    public Weblog getWeblogByHandle(String handle, Boolean visible) throws WebloggerException {
        
        if (handle == null) {
            throw new WebloggerException("Handle cannot be null");
        } else if (!isAlphanumeric(handle)) {
            throw new WebloggerException("Invalid handle: '"+handle+"'");
        }
        
        // check cache first
        // NOTE: if we ever allow changing handles then this needs updating
        String blogID = this.weblogHandleToIdMap.get(handle);
        if(blogID != null) {
            
            Weblog weblog = this.getWeblog(blogID);
            if (weblog != null) {
                // only return weblog if enabled status matches
                if(visible == null || visible.equals(weblog.getVisible())) {
                    log.debug("weblogHandleToId CACHE HIT - "+handle);
                    return weblog;
                }
            } else {
                // mapping hit with lookup miss?  mapping must be old, remove it
                this.weblogHandleToIdMap.remove(handle);
            }
        }
        
        TypedQuery<Weblog> query = strategy.getNamedQuery("Weblog.getByHandle", Weblog.class);
        query.setParameter(1, handle);
        Weblog weblog;
        try {
            weblog = query.getSingleResult();
        } catch (NoResultException e) {
            weblog = null;
        }
        
        // add mapping to cache
        if(weblog != null) {
            log.debug("weblogHandleToId CACHE MISS - "+handle);
            this.weblogHandleToIdMap.put(weblog.getHandle(), weblog.getId());
        }
        
        if(weblog != null &&
                (visible == null || visible.equals(weblog.getVisible()))) {
            return weblog;
        } else {
            return null;
        }
    }
    
    /**
     * Get weblogs of a user
     */
    @Override
    public List<Weblog> getWeblogs(
            Boolean enabled, Boolean active,
            Date startDate, Date endDate, int offset, int length) throws WebloggerException {
        
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
        
        TypedQuery<Weblog> query = strategy.getDynamicQuery(
                queryString + whereClause.toString(), Weblog.class);
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
    public WeblogTemplate getTemplate(String id) throws WebloggerException {
        return this.templateService.getTemplate(id);
    }
    
    /**
     * Use JPA directly because Weblogger's Query API does too much allocation.
     */
    @Override
    public WeblogTemplate getTemplateByLink(Weblog weblog, String templateLink)
    throws WebloggerException {
        return this.templateService.getTemplateByLink(weblog, templateLink);
    }
    
    /**
     * @see org.apache.roller.weblogger.business.WeblogManager#getTemplateByAction(Weblog, ComponentType)
     */
    @Override
    public WeblogTemplate getTemplateByAction(Weblog weblog, ComponentType action)
            throws WebloggerException {
        return this.templateService.getTemplateByAction(weblog, action);
    }
    
    /**
     * @see org.apache.roller.weblogger.business.WeblogManager#getTemplateByName(Weblog, java.lang.String)
     */
    @Override
    public WeblogTemplate getTemplateByName(Weblog weblog, String templateName)
    throws WebloggerException {
        return this.templateService.getTemplateByName(weblog, templateName);
    }

    /**
     * @see org.apache.roller.weblogger.business.WeblogManager#getTemplates(Weblog)
     */
    @Override
    public List<WeblogTemplate> getTemplates(Weblog weblog) throws WebloggerException {
        return this.templateService.getTemplates(weblog);
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
                        ((Long)row[0]));                    // # comments
                sc.setWeblogHandle((String)row[2]);
                results.add(sc);
            }
        }

        // Original query ordered by desc # comments.
        // JPA QL doesn't allow queries to be ordered by aggregates; do it in memory
        results.sort(STAT_COUNT_COUNT_REVERSE_COMPARATOR);
        
        return results;
    }
    
    /**
     * Get count of weblogs, active and inactive
     */
    @Override
    public long getWeblogCount() throws WebloggerException {
        List<Long> results = strategy.getNamedQuery(
                "Weblog.getCountAllDistinct", Long.class).getResultList();
        return results.get(0);
    }

    /**
     * Returns true if alphanumeric or '_'.
     */
    private boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetterOrDigit(str.charAt(i)) && str.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }

}