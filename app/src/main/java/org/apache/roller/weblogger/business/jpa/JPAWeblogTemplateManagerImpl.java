/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with
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

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.WeblogManager;
import org.apache.roller.weblogger.business.WeblogTemplateManager;
import org.apache.roller.weblogger.pojos.CustomTemplateRendition;
import org.apache.roller.weblogger.pojos.ThemeTemplate.ComponentType;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogTemplate;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * JPA implementation of a WeblogTemplateManager.
 */
@com.google.inject.Singleton
public class JPAWeblogTemplateManagerImpl implements WeblogTemplateManager {

    private final WeblogManager weblogManager;
    private final JPAPersistenceStrategy strategy;

    @com.google.inject.Inject
    protected JPAWeblogTemplateManagerImpl(WeblogManager weblogManager, JPAPersistenceStrategy strategy) {
        this.weblogManager = weblogManager;
        this.strategy = strategy;
    }

    @Override
    public void saveTemplate(WeblogTemplate template) throws WebloggerException {
        strategy.store(template);
        weblogManager.saveWeblog(template.getWeblog()); // Update weblog's last modified date
    }

    @Override
    public void removeTemplate(WeblogTemplate template) throws WebloggerException {
        strategy.remove(template);
        weblogManager.saveWeblog(template.getWeblog()); // Update weblog's last modified date
    }

    @Override
    public void saveTemplateRendition(CustomTemplateRendition rendition) throws WebloggerException {
        strategy.store(rendition);
        weblogManager.saveWeblog(rendition.getWeblogTemplate().getWeblog());
    }

    @Override
    public WeblogTemplate getTemplate(String id) throws WebloggerException {
        if (id != null && id.endsWith(".vm")) {
            return null; // Don't hit database for templates stored on disk
        }
        return (WeblogTemplate) strategy.load(WeblogTemplate.class, id);
    }

    @Override
    public WeblogTemplate getTemplateByName(Weblog weblog, String name) throws WebloggerException {
        TypedQuery<WeblogTemplate> q = strategy.getNamedQuery("WeblogTemplate.getByWeblog&Name", WeblogTemplate.class);
        q.setParameter(1, weblog);
        q.setParameter(2, name);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public WeblogTemplate getTemplateByLink(Weblog weblog, String link) throws WebloggerException {
        TypedQuery<WeblogTemplate> q = strategy.getNamedQuery("WeblogTemplate.getByWeblog&Link", WeblogTemplate.class);
        q.setParameter(1, weblog);
        q.setParameter(2, link);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public WeblogTemplate getTemplateByAction(Weblog weblog, ComponentType action) throws WebloggerException {
        TypedQuery<WeblogTemplate> q = strategy.getNamedQuery("WeblogTemplate.getByAction", WeblogTemplate.class);
        q.setParameter(1, weblog);
        q.setParameter(2, action);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<WeblogTemplate> getTemplates(Weblog weblog) throws WebloggerException {
        TypedQuery<WeblogTemplate> q = strategy.getNamedQuery("WeblogTemplate.getByWeblogOrderByName", WeblogTemplate.class);
        q.setParameter(1, weblog);
        return q.getResultList();
    }
    
    @Override
    public void release() {}
}
