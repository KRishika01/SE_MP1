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
package org.apache.roller.weblogger.business;

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.jpa.JPAPersistenceStrategy;
import org.apache.roller.weblogger.business.pings.AutoPingManager;
import org.apache.roller.weblogger.business.pings.PingQueueManager;
import org.apache.roller.weblogger.business.pings.PingTargetManager;
import org.apache.roller.weblogger.config.WebloggerConfig;
import org.apache.roller.weblogger.pojos.*;

import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the contents of a weblog, such as categories, bookmarks, and permissions.
 * This class isolates the complex logic of populating and clearing a weblog's associated data.
 */
@com.google.inject.Singleton
public class WeblogContentManager {

    private final Weblogger roller;
    private final JPAPersistenceStrategy strategy;

    @com.google.inject.Inject
    protected WeblogContentManager(Weblogger roller, JPAPersistenceStrategy strategy) {
        this.roller = roller;
        this.strategy = strategy;
    }

    /**
     * Set up the initial contents for a new weblog.
     * This includes default permissions, categories, bookmarks, and ping targets.
     */
    public void addWeblogContents(Weblog newWeblog) throws WebloggerException {
        // Grant weblog creator ADMIN permission
        List<String> actions = new ArrayList<>();
        actions.add(WeblogPermission.ADMIN);
        roller.getPermissionManager().grantWeblogPermission(newWeblog, newWeblog.getCreator(), actions);

        // Add default categories
        String cats = WebloggerConfig.getProperty("newuser.categories");
        WeblogCategory firstCat = null;
        if (cats != null) {
            // isBlank() is from Java 11, Roller may be on an older version
            // Using trim().isEmpty() is safer for older Java versions.
            String[] splitcats = cats.split(",");
            for (String split : splitcats) {
                if (split.trim().isEmpty()) {
                    continue;
                }
                WeblogCategory c = new WeblogCategory(newWeblog, split, null, null);
                if (firstCat == null) {
                    firstCat = c;
                }
                strategy.store(c);
            }
        }

        if (firstCat != null) {
            newWeblog.setBloggerCategory(firstCat);
            strategy.store(newWeblog);
        }

        // Add default bookmarks
        WeblogBookmarkFolder defaultFolder = new WeblogBookmarkFolder("default", newWeblog);
        strategy.store(defaultFolder);

        String blogroll = WebloggerConfig.getProperty("newuser.blogroll");
        if (blogroll != null) {
            String[] splitroll = blogroll.split(",");
            for (String splitItem : splitroll) {
                String[] rollitems = splitItem.split("\\|");
                if (rollitems.length > 1) {
                    WeblogBookmark b = new WeblogBookmark(defaultFolder, rollitems[0], "", rollitems[1].trim(), null, null);
                    strategy.store(b);
                }
            }
        }

        roller.getMediaFileManager().createDefaultMediaFileDirectory(newWeblog);

        strategy.flush();

        // Add auto-enabled ping targets
        PingTargetManager pingTargetMgr = roller.getPingTargetManager();
        AutoPingManager autoPingMgr = roller.getAutopingManager();

        for (PingTarget pingTarget : pingTargetMgr.getCommonPingTargets()) {
            if (pingTarget.isAutoEnabled()) {
                AutoPing autoPing = new AutoPing(null, pingTarget, newWeblog);
                autoPingMgr.saveAutoPing(autoPing);
            }
        }
    }

    /**
     * Remove all contents associated with a weblog.
     * This is a prerequisite to deleting the weblog itself.
     */
    public void removeWeblogContents(Weblog weblog) throws WebloggerException {
        UserManager        umgr = roller.getUserManager();
        PermissionManager  pmgr = roller.getPermissionManager();
        WeblogEntryManager emgr = roller.getWeblogEntryManager();
        BookmarkManager    bmgr = roller.getBookmarkManager();
        AutoPingManager    autoPingMgr = roller.getAutopingManager();
        PingQueueManager   pingQueueMgr = roller.getPingQueueManager();
        MediaFileManager   mfmgr = roller.getMediaFileManager();

        // CORRECT DELETION ORDER
        // 1. Remove tags, which depend on weblog entries.
        emgr.removeWeblogEntryTags(weblog);

        // 2. Remove the weblog entries themselves. This must happen before categories.
        TypedQuery<WeblogEntry> entryQuery = strategy.getNamedQuery("WeblogEntry.getByWebsite", WeblogEntry.class);
        entryQuery.setParameter(1, weblog);
        for (WeblogEntry entry : entryQuery.getResultList()) {
            emgr.removeWeblogEntry(entry);
        }
        
        // 3. Flush the session to ensure entries are deleted from the database
        //    before we proceed to delete their dependencies.
        strategy.flush();

        // 4. Now that entries are gone, it's safe to remove the categories.
        emgr.removeWeblogCategories(weblog);

        // 5. Remove other associated objects that do not have FK constraints on entries.
        pingQueueMgr.removeQueueEntries(weblog);
        autoPingMgr.removeAutoPings(autoPingMgr.getAutoPingsByWebsite(weblog));

        // Remove templates
        TypedQuery<WeblogTemplate> templateQuery = strategy.getNamedQuery("WeblogTemplate.getByWeblog", WeblogTemplate.class);
        templateQuery.setParameter(1, weblog);
        for (WeblogTemplate template : templateQuery.getResultList()) {
            strategy.remove(template);
        }

        // remove folders (including bookmarks)
        bmgr.removeAllFolders(weblog);

        // remove media files and directories
        mfmgr.removeAllFiles(weblog);

        // remove permissions
        for (WeblogPermission perm : pmgr.getWeblogPermissions(weblog)) {
            pmgr.revokeWeblogPermission(perm.getWeblog(), perm.getUser(), WeblogPermission.ALL_ACTIONS);
        }
        
        // Final flush to make sure all of the above is committed before returning.
        strategy.flush();
    }
}
