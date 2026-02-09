/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
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

package org.apache.roller.weblogger.business.support;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.WeblogEntryManager;
import org.apache.roller.weblogger.business.WebloggerFactory;
import org.apache.roller.weblogger.config.WebloggerRuntimeConfig;
import org.apache.roller.weblogger.pojos.CommentSearchCriteria;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.apache.roller.weblogger.pojos.WeblogEntryComment;

/**
 * Support class for WeblogEntry comment operations.
 * Extracted from WeblogEntry to reduce God Class smell.
 */
public class WeblogEntryCommentSupport {

    private WeblogEntryCommentSupport() {
        // Utility class, prevent instantiation
    }

    /**
     * Get comments for this entry with default settings (ignore spam, approved only).
     * 
     * @param entry The weblog entry
     * @return List of comments
     */
    public static List<WeblogEntryComment> getComments(WeblogEntry entry) {
        return getComments(entry, true, true);
    }

    /**
     * Get comments for this entry with specified filters.
     * 
     * @param entry The weblog entry
     * @param ignoreSpam Whether to ignore spam comments
     * @param approvedOnly Whether to return only approved comments
     * @return List of comments
     */
    public static List<WeblogEntryComment> getComments(WeblogEntry entry, boolean ignoreSpam, boolean approvedOnly) {
        try {
            WeblogEntryManager wmgr = WebloggerFactory.getWeblogger().getWeblogEntryManager();

            CommentSearchCriteria csc = new CommentSearchCriteria();
            csc.setWeblog(entry.getWebsite());
            csc.setEntry(entry);
            csc.setStatus(approvedOnly ? WeblogEntryComment.ApprovalStatus.APPROVED : null);
            return wmgr.getComments(csc);
        } catch (WebloggerException alreadyLogged) {
            // Exception already logged
        }
        
        return Collections.emptyList();
    }

    /**
     * Get the count of comments for this entry.
     * 
     * @param entry The weblog entry
     * @return Number of comments
     */
    public static int getCommentCount(WeblogEntry entry) {
        return getComments(entry).size();
    }

    /**
     * True if comments are still allowed on this entry considering the
     * allowComments and commentDays fields as well as the website and 
     * site-wide configs.
     * 
     * @param entry The weblog entry
     * @return true if comments are still allowed
     */
    public static boolean getCommentsStillAllowed(WeblogEntry entry) {
        if (!WebloggerRuntimeConfig.getBooleanProperty("users.comments.enabled")) {
            return false;
        }
        if (entry.getWebsite().getAllowComments() != null && !entry.getWebsite().getAllowComments()) {
            return false;
        }
        if (entry.getCommentSettings().getAllowComments() != null && !entry.getCommentSettings().getAllowComments()) {
            return false;
        }
        boolean ret = false;
        if (entry.getCommentSettings().getCommentDays() == null || entry.getCommentSettings().getCommentDays() == 0) {
            ret = true;
        } else {
            // we want to use pubtime for calculating when comments expire, but
            // if pubtime isn't set (like for drafts) then just use updatetime
            Date inPubTime = entry.getPubTime();
            if (inPubTime == null) {
                inPubTime = entry.getUpdateTime();
            }
            
            Calendar expireCal = Calendar.getInstance(
                    entry.getWebsite().getLocaleInstance());
            expireCal.setTime(inPubTime);
            expireCal.add(Calendar.DATE, entry.getCommentSettings().getCommentDays());
            Date expireDay = expireCal.getTime();
            Date today = new Date();
            if (today.before(expireDay)) {
                ret = true;
            }
        }
        return ret;
    }
}
