package org.apache.roller.weblogger.business.jpa;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.MediaFileManager;
import org.apache.roller.weblogger.business.UserManager;
import org.apache.roller.weblogger.business.WeblogEntryManager;
import org.apache.roller.weblogger.business.Weblogger;
import org.apache.roller.weblogger.business.WebloggerFactory;
import org.apache.roller.weblogger.business.pings.AutoPingManager;
import org.apache.roller.weblogger.business.pings.PingTargetManager;
import org.apache.roller.weblogger.config.WebloggerConfig;
import org.apache.roller.weblogger.pojos.AutoPing;
import org.apache.roller.weblogger.pojos.PingQueueEntry;
import org.apache.roller.weblogger.pojos.PingTarget;
import org.apache.roller.weblogger.pojos.TagStat;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogBookmark;
import org.apache.roller.weblogger.pojos.WeblogBookmarkFolder;
import org.apache.roller.weblogger.pojos.WeblogCategory;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.apache.roller.weblogger.pojos.WeblogEntryTag;
import org.apache.roller.weblogger.pojos.WeblogEntryTagAggregate;
import org.apache.roller.weblogger.pojos.WeblogPermission;
import org.apache.roller.weblogger.pojos.WeblogTemplate;

class WeblogLifecycleService {

    private static final Log log = LogFactory.getLog(WeblogLifecycleService.class);

    private final Weblogger roller;
    private final JPAPersistenceStrategy strategy;

    WeblogLifecycleService(Weblogger roller, JPAPersistenceStrategy strategy) {
        this.roller = roller;
        this.strategy = strategy;
    }

    void addWeblogContents(Weblog newWeblog) throws WebloggerException {
        // grant weblog creator ADMIN permission
        List<String> actions = new ArrayList<>();
        actions.add(WeblogPermission.ADMIN);
        roller.getUserManager().grantWeblogPermission(
                newWeblog, newWeblog.getCreator(), actions);

        String cats = WebloggerConfig.getProperty("newuser.categories");
        WeblogCategory firstCat = null;
        if (cats != null) {
            String[] splitcats = cats.split(",");
            for (String split : splitcats) {
                if (split.isBlank()) {
                    continue;
                }
                WeblogCategory c = new WeblogCategory(
                        newWeblog,
                        split,
                        null,
                        null);
                if (firstCat == null) {
                    firstCat = c;
                }
                this.strategy.store(c);
            }
        }

        // Use first category as default for Blogger API
        if (firstCat != null) {
            newWeblog.setBloggerCategory(firstCat);
        }

        this.strategy.store(newWeblog);

        // add default bookmarks
        WeblogBookmarkFolder defaultFolder = new WeblogBookmarkFolder(
                "default", newWeblog);
        this.strategy.store(defaultFolder);

        String blogroll = WebloggerConfig.getProperty("newuser.blogroll");
        if (blogroll != null) {
            String[] splitroll = blogroll.split(",");
            for (String splitItem : splitroll) {
                String[] rollitems = splitItem.split("\\|");
                if (rollitems.length > 1) {
                    WeblogBookmark b = new WeblogBookmark(
                            defaultFolder,
                            rollitems[0],
                            "",
                            rollitems[1].trim(),
                            null,
                            null);
                    this.strategy.store(b);
                }
            }
        }

        roller.getMediaFileManager().createDefaultMediaFileDirectory(newWeblog);

        // flush so that all data up to this point can be available in db
        this.strategy.flush();

        // add any auto enabled ping targets
        PingTargetManager pingTargetMgr = roller.getPingTargetManager();
        AutoPingManager autoPingMgr = roller.getAutopingManager();

        for (PingTarget pingTarget : pingTargetMgr.getCommonPingTargets()) {
            if (pingTarget.isAutoEnabled()) {
                AutoPing autoPing = new AutoPing(
                        null, pingTarget, newWeblog);
                autoPingMgr.saveAutoPing(autoPing);
            }
        }
    }

    void removeWeblogContents(Weblog weblog) throws WebloggerException {

        UserManager umgr = roller.getUserManager();
        WeblogEntryManager emgr = roller.getWeblogEntryManager();

        // remove tags
        TypedQuery<WeblogEntryTag> tagQuery = strategy.getNamedQuery(
                "WeblogEntryTag.getByWeblog", WeblogEntryTag.class);
        tagQuery.setParameter(1, weblog);
        List<WeblogEntryTag> results = tagQuery.getResultList();

        for (WeblogEntryTag tagData : results) {
            if (tagData.getWeblogEntry() != null) {
                tagData.getWeblogEntry().getTags().remove(tagData);
            }
            this.strategy.remove(tagData);
        }

        // remove site tag aggregates
        List<TagStat> tags = emgr.getTags(weblog, null, null, 0, -1);
        updateTagAggregates(tags);

        // delete all weblog tag aggregates
        Query removeAggs = strategy.getNamedUpdate(
                "WeblogEntryTagAggregate.removeByWeblog");
        removeAggs.setParameter(1, weblog);
        removeAggs.executeUpdate();

        // delete all bad counts
        Query removeCounts = strategy.getNamedUpdate(
                "WeblogEntryTagAggregate.removeByTotalLessEqual");
        removeCounts.setParameter(1, 0);
        removeCounts.executeUpdate();

        // Remove the weblog's ping queue entries
        TypedQuery<PingQueueEntry> q = strategy.getNamedQuery(
                "PingQueueEntry.getByWebsite", PingQueueEntry.class);
        q.setParameter(1, weblog);
        List<PingQueueEntry> queueEntries = q.getResultList();
        for (Object obj : queueEntries) {
            this.strategy.remove(obj);
        }

        // Remove the weblog's auto ping configurations
        AutoPingManager autoPingMgr = roller.getAutopingManager();
        List<AutoPing> autopings = autoPingMgr.getAutoPingsByWebsite(weblog);
        for (AutoPing autoPing : autopings) {
            this.strategy.remove(autoPing);
        }

        // remove associated templates
        TypedQuery<WeblogTemplate> templateQuery = strategy.getNamedQuery(
                "WeblogTemplate.getByWeblog", WeblogTemplate.class);
        templateQuery.setParameter(1, weblog);
        List<WeblogTemplate> templates = templateQuery.getResultList();

        for (WeblogTemplate template : templates) {
            this.strategy.remove(template);
        }

        // remove folders (including bookmarks)
        TypedQuery<WeblogBookmarkFolder> folderQuery = strategy.getNamedQuery(
                "WeblogBookmarkFolder.getByWebsite", WeblogBookmarkFolder.class);
        folderQuery.setParameter(1, weblog);
        List<WeblogBookmarkFolder> folders = folderQuery.getResultList();
        for (WeblogBookmarkFolder wbf : folders) {
            this.strategy.remove(wbf);
        }

        // remove mediafile metadata & uploaded files
        MediaFileManager mfmgr = WebloggerFactory.getWeblogger().getMediaFileManager();
        mfmgr.removeAllFiles(weblog);
        this.strategy.flush();

        // remove entries
        TypedQuery<WeblogEntry> refQuery = strategy.getNamedQuery(
                "WeblogEntry.getByWebsite", WeblogEntry.class);
        refQuery.setParameter(1, weblog);
        List<WeblogEntry> entries = refQuery.getResultList();
        for (WeblogEntry entry : entries) {
            emgr.removeWeblogEntry(entry);
        }
        this.strategy.flush();

        // delete all weblog categories
        Query removeCategories = strategy.getNamedUpdate(
                "WeblogCategory.removeByWeblog");
        removeCategories.setParameter(1, weblog);
        removeCategories.executeUpdate();

        // remove permissions
        for (WeblogPermission perm : umgr.getWeblogPermissions(weblog)) {
            umgr.revokeWeblogPermission(
                    perm.getWeblog(), perm.getUser(), WeblogPermission.ALL_ACTIONS);
        }

        // flush the changes before returning. This is required as there is a
        // circular dependency between WeblogCategory and Weblog
        this.strategy.flush();
    }

    private void updateTagAggregates(List<TagStat> tags) throws WebloggerException {
        for (TagStat stat : tags) {
            TypedQuery<WeblogEntryTagAggregate> query =
                    strategy.getNamedQueryCommitFirst(
                            "WeblogEntryTagAggregate.getByName&WebsiteNullOrderByLastUsedDesc",
                            WeblogEntryTagAggregate.class);
            query.setParameter(1, stat.getName());
            try {
                WeblogEntryTagAggregate agg = query.getSingleResult();
                agg.setTotal(agg.getTotal() - stat.getCount());
            } catch (NoResultException ignored) {
                // nothing to update
            }
        }
    }
}