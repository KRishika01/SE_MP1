/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.WeblogContentManager;
import org.apache.roller.weblogger.business.WeblogManager;
import org.apache.roller.weblogger.pojos.Weblog;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JPA implementation of the WeblogManager.
 * This class focuses on the lifecycle of Weblog entities and delegates
 * content and query operations to specialized managers.
 */
@com.google.inject.Singleton
public class JPAWeblogManagerImpl implements WeblogManager {

    private static final Log log = LogFactory.getLog(JPAWeblogManagerImpl.class);

    private final JPAPersistenceStrategy strategy;
    private final WeblogContentManager weblogContentManager;

    // A cache for mapping weblog handles to IDs to optimize lookups
    private final Map<String, String> weblogHandleToIdMap = Collections.synchronizedMap(new HashMap<>());

    @com.google.inject.Inject
    protected JPAWeblogManagerImpl(JPAPersistenceStrategy strategy, WeblogContentManager weblogContentManager) {
        log.debug("Instantiating JPA Weblog Manager");
        this.strategy = strategy;
        this.weblogContentManager = weblogContentManager;
    }

    @Override
    public void saveWeblog(Weblog weblog) throws WebloggerException {
        weblog.setLastModified(new java.util.Date());
        strategy.store(weblog);
    }

    @Override
    public void addWeblog(Weblog newWeblog) throws WebloggerException {
        strategy.store(newWeblog);
        strategy.flush(); // Ensure weblog is in the db before adding content
        weblogContentManager.addWeblogContents(newWeblog);
    }

    @Override
    public void removeWeblog(Weblog weblog) throws WebloggerException {
        weblogContentManager.removeWeblogContents(weblog);
        strategy.remove(weblog);
        weblogHandleToIdMap.remove(weblog.getHandle());
    }

    @Override
    public Weblog getWeblog(String id) throws WebloggerException {
        return (Weblog) strategy.load(Weblog.class, id);
    }

    @Override
    public Weblog getWeblogByHandle(String handle) throws WebloggerException {
        return getWeblogByHandle(handle, Boolean.TRUE);
    }

    @Override
    public Weblog getWeblogByHandle(String handle, Boolean visible) throws WebloggerException {
        if (handle == null || !isAlphanumeric(handle)) {
            throw new WebloggerException("Invalid handle: '" + handle + "'");
        }

        String blogId = weblogHandleToIdMap.get(handle);
        if (blogId != null) {
            Weblog weblog = this.getWeblog(blogId);
            if (weblog != null && (visible == null || visible.equals(weblog.getVisible()))) {
                log.debug("weblogHandleToId CACHE HIT - " + handle);
                return weblog;
            } else {
                weblogHandleToIdMap.remove(handle); // Stale cache entry
            }
        }

        TypedQuery<Weblog> query = strategy.getNamedQuery("Weblog.getByHandle", Weblog.class);
        query.setParameter(1, handle);
        Weblog weblog;
        try {
            weblog = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        if (weblog != null) {
            log.debug("weblogHandleToId CACHE MISS - " + handle);
            weblogHandleToIdMap.put(weblog.getHandle(), weblog.getId());
            if (visible == null || visible.equals(weblog.getVisible())) {
                return weblog;
            }
        }
        return null;
    }

    @Override
    public void release() {
        // No implementation needed
    }

    private boolean isAlphanumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetterOrDigit(str.charAt(i)) && str.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }
}
