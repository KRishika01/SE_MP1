package org.apache.roller.weblogger.business.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.Weblogger;
import org.apache.roller.weblogger.pojos.CustomTemplateRendition;
import org.apache.roller.weblogger.pojos.ThemeTemplate.ComponentType;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogTemplate;

class WeblogTemplateService {

    private static final Log log = LogFactory.getLog(WeblogTemplateService.class);

    private final Weblogger roller;
    private final JPAPersistenceStrategy strategy;

    WeblogTemplateService(Weblogger roller, JPAPersistenceStrategy strategy) {
        this.roller = roller;
        this.strategy = strategy;
    }

    void saveTemplate(WeblogTemplate template) throws WebloggerException {
        if (template == null) {
            throw new WebloggerException("Template cannot be null");
        }
        this.strategy.store(template);
        // update weblog last modified date. date updated by saveWeblog()
        roller.getWeblogManager().saveWeblog(template.getWeblog());
    }

    void saveTemplateRendition(CustomTemplateRendition rendition) throws WebloggerException {
        if (rendition == null) {
            throw new WebloggerException("Template rendition cannot be null");
        }
        this.strategy.store(rendition);
        // update weblog last modified date. date updated by saveWeblog()
        roller.getWeblogManager().saveWeblog(
                rendition.getWeblogTemplate().getWeblog());
    }

    void removeTemplate(WeblogTemplate template) throws WebloggerException {
        if (template == null) {
            throw new WebloggerException("Template cannot be null");
        }
        this.strategy.remove(template);
        // update weblog last modified date. date updated by saveWeblog()
        roller.getWeblogManager().saveWeblog(template.getWeblog());
    }

    WeblogTemplate getTemplate(String id) throws WebloggerException {
        // Don't hit database for templates stored on disk
        if (id != null && id.endsWith(".vm")) {
            return null;
        }
        return (WeblogTemplate) this.strategy.load(WeblogTemplate.class, id);
    }

    WeblogTemplate getTemplateByLink(Weblog weblog, String templateLink)
            throws WebloggerException {

        if (weblog == null) {
            throw new WebloggerException("weblog is null");
        }
        if (templateLink == null) {
            throw new WebloggerException("templateLink is null");
        }

        TypedQuery<WeblogTemplate> query = strategy.getNamedQuery(
                "WeblogTemplate.getByWeblog&Link", WeblogTemplate.class);
        query.setParameter(1, weblog);
        query.setParameter(2, templateLink);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.debug("No template found for weblog=" + weblog.getHandle()
                    + ", link=" + templateLink);
            return null;
        }
    }

    WeblogTemplate getTemplateByAction(Weblog weblog, ComponentType action)
            throws WebloggerException {

        if (weblog == null) {
            throw new WebloggerException("weblog is null");
        }
        if (action == null) {
            throw new WebloggerException("Action name is null");
        }

        TypedQuery<WeblogTemplate> query = strategy.getNamedQuery(
                "WeblogTemplate.getByAction", WeblogTemplate.class);
        query.setParameter(1, weblog);
        query.setParameter(2, action);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.debug("No template found for weblog=" + weblog.getHandle()
                    + ", action=" + action);
            return null;
        }
    }

    WeblogTemplate getTemplateByName(Weblog weblog, String templateName)
            throws WebloggerException {

        if (weblog == null) {
            throw new WebloggerException("weblog is null");
        }
        if (templateName == null) {
            throw new WebloggerException("Template name is null");
        }

        TypedQuery<WeblogTemplate> query = strategy.getNamedQuery(
                "WeblogTemplate.getByWeblog&Name", WeblogTemplate.class);
        query.setParameter(1, weblog);
        query.setParameter(2, templateName);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.debug("No template found for weblog=" + weblog.getHandle()
                    + ", name=" + templateName);
            return null;
        }
    }

    List<WeblogTemplate> getTemplates(Weblog weblog) throws WebloggerException {
        if (weblog == null) {
            throw new WebloggerException("weblog is null");
        }
        TypedQuery<WeblogTemplate> q = strategy.getNamedQuery(
                "WeblogTemplate.getByWeblogOrderByName", WeblogTemplate.class);
        q.setParameter(1, weblog);
        return q.getResultList();
    }
}