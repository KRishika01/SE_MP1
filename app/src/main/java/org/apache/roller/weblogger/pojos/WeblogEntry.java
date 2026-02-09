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

package org.apache.roller.weblogger.pojos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.util.RollerConstants;
import org.apache.roller.util.UUIDGenerator;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.PermissionManager;
import org.apache.roller.weblogger.business.UserManager;
import org.apache.roller.weblogger.business.WebloggerFactory;
import org.apache.roller.weblogger.config.WebloggerConfig;
import org.apache.roller.weblogger.util.Utilities;

/**
 * Represents a Weblog Entry.
 */
public class WeblogEntry implements Serializable {
    private static final Log mLogger = LogFactory.getFactory().getInstance(WeblogEntry.class);
    
    public static final long serialVersionUID = 2341505386843044125L;

    public enum PubStatus {DRAFT, PUBLISHED, PENDING, SCHEDULED}



    // Simple properies
    private String    id            = UUIDGenerator.generateUUID();
    private String    title         = null;
    private String    link          = null;
    private String    summary       = null;
    private String    text          = null;
    private String    contentType   = null;
    private String    contentSrc    = null;
    private String    anchor        = null;
    private String    plugins       = null;
    private Boolean   rightToLeft   = Boolean.FALSE;
    private Boolean   pinnedToMain  = Boolean.FALSE;
    private String    locale        = null;
    private String    creatorUserName = null;      
    private String    searchDescription = null;

    // Extracted settings objects
    private WeblogEntryPublicationSettings publicationSettings = new WeblogEntryPublicationSettings();
    private WeblogEntryCommentSettings commentSettings = new WeblogEntryCommentSettings();

    // set to true when switching between pending/draft/scheduled and published
    // either the aggregate table needs the entry's tags added (for published)
    // or subtracted (anything else)
    private Boolean   refreshAggregates = Boolean.FALSE;

    // Associated objects
    private Weblog        website  = null;
    private WeblogCategory category = null;
    
    // Collection of name/value entry attributes
    private Set<WeblogEntryAttribute> attSet = new TreeSet<>();
    
    private Set<WeblogEntryTag> tagSet = new HashSet<>();
    private Set<WeblogEntryTag> removedTags = new HashSet<>();
    private Set<WeblogEntryTag> addedTags = new HashSet<>();
    
    //----------------------------------------------------------- Construction
    
    public WeblogEntry() {
    }
    
    public WeblogEntry(
            String id,
            WeblogCategory category,
            Weblog website,
            User creator,
            String title,
            String link,
            String text,
            String anchor,
            Timestamp pubTime,
            Timestamp updateTime,
            PubStatus status) {
        //this.id = id;
        this.category = category;
        this.website = website;
        this.creatorUserName = creator.getUserName();
        this.title = title;
        this.link = link;
        this.text = text;
        this.anchor = anchor;
        this.publicationSettings.setPubTime(pubTime);
        this.publicationSettings.setUpdateTime(updateTime);
        this.publicationSettings.setStatus(status);
    }
    
    public WeblogEntry(WeblogEntry otherData) {
        this.setData(otherData);
    }
    
    //---------------------------------------------------------- Initializaion
    
    /**
     * Set bean properties based on other bean.
     */
    public void setData(WeblogEntry other) {
        
        this.setId(other.getId());
        this.setCategory(other.getCategory());
        this.setWebsite(other.getWebsite());
        this.setCreatorUserName(other.getCreatorUserName());
        this.setTitle(other.getTitle());
        this.setLink(other.getLink());
        this.setText(other.getText());
        this.setSummary(other.getSummary());
        this.setSearchDescription(other.getSearchDescription());
        this.setAnchor(other.getAnchor());
        this.setPubTime(other.getPubTime());
        this.setUpdateTime(other.getUpdateTime());
        this.setStatus(other.getStatus());
        this.setPlugins(other.getPlugins());
        this.setAllowComments(other.getAllowComments());
        this.setCommentDays(other.getCommentDays());
        this.setRightToLeft(other.getRightToLeft());
        this.setPinnedToMain(other.getPinnedToMain());
        this.setLocale(other.getLocale());
    }
    
    //------------------------------------------------------- Good citizenship

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append(getId());
        buf.append(", ").append(this.getAnchor());
        buf.append(", ").append(this.getTitle());
        buf.append(", ").append(this.getPubTime());
        buf.append("}");
        return buf.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WeblogEntry)) {
            return false;
        }
        WeblogEntry o = (WeblogEntry)other;
        return new EqualsBuilder()
            .append(getAnchor(), o.getAnchor()) 
            .append(getWebsite(), o.getWebsite()) 
            .isEquals();
    }
    
    @Override
    public int hashCode() { 
        return new HashCodeBuilder()
            .append(getAnchor())
            .append(getWebsite())
            .toHashCode();
    }
    
   //------------------------------------------------------ Simple properties
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        // Form bean workaround: empty string is never a valid id
        if (id != null && id.isBlank()) {
            return;
        }
        this.id = id;
    }
    
    public WeblogCategory getCategory() {
        return this.category;
    }
    
    public void setCategory(WeblogCategory category) {
        this.category = category;
    }
       
    /**
     * Return collection of WeblogCategory objects of this entry.
     * Added for symmetry with PlanetEntryData object.
     */
    public List<WeblogCategory> getCategories() {
        return List.of(getCategory());
    }
    
    public Weblog getWebsite() {
        return this.website;
    }
    
    public void setWebsite(Weblog website) {
        this.website = website;
    }
    
   
    
    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }   
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get summary for weblog entry (maps to RSS description and Atom summary).
     */
    public String getSummary() {
        return summary;
    }
    
    /**
     * Set summary for weblog entry (maps to RSS description and Atom summary).
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    /**
     * Get search description for weblog entry.
     */
    public String getSearchDescription() {
        return searchDescription;
    }
    
    /**
     * Set search description for weblog entry
     */
    public void setSearchDescription(String searchDescription) {
        this.searchDescription = searchDescription;
    }

    /**
     * Get content text for weblog entry (maps to RSS content:encoded and Atom content).
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * Set content text for weblog entry (maps to RSS content:encoded and Atom content).
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * Get content type (text, html, xhtml or a MIME content type)
     */
    public String getContentType() {
        return contentType;
    }
    
    /**
     * Set content type (text, html, xhtml or a MIME content type)
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    /**
     * Get URL for out-of-line content.
     */
    public String getContentSrc() {
        return contentSrc;
    }
    
    /**
     * Set URL for out-of-line content.
     */
    public void setContentSrc(String contentSrc) {
        this.contentSrc = contentSrc;
    }
    
    public String getAnchor() {
        return this.anchor;
    }
    
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }
    
    //-------------------------------------------------------------------------

    public Set<WeblogEntryAttribute> getEntryAttributes() {
        return attSet;
    }

    public void setEntryAttributes(Set<WeblogEntryAttribute> atts) {
        this.attSet = atts;
    }
    
    public WeblogEntryPublicationSettings getPublicationSettings() {
        return publicationSettings;
    }

    public void setPublicationSettings(WeblogEntryPublicationSettings publicationSettings) {
        this.publicationSettings = publicationSettings;
    }

    public WeblogEntryCommentSettings getCommentSettings() {
        return commentSettings;
    }

    public void setCommentSettings(WeblogEntryCommentSettings commentSettings) {
        this.commentSettings = commentSettings;
    }
    
    /**
     * <p>Publish time is the time that an entry is to be (or was) made available
     * for viewing by newsfeed readers and visitors to the Roller site.</p>
     *
     * <p>Roller stores time using the timeZone of the server itself. When
     * times are displayed  in a user's weblog they must be translated
     * to the user's timeZone.</p>
     *
     * <p>NOTE: Times are stored using the SQL TIMESTAMP datatype, which on
     * MySQL has only a one-second resolution.</p>
     */
    public Timestamp getPubTime() {
        return this.publicationSettings.getPubTime();
    }
    
    public void setPubTime(Timestamp pubTime) {
        this.publicationSettings.setPubTime(pubTime);
    }
    
    /**
     * <p>Update time is the last time that an weblog entry was saved in the
     * Roller weblog editor or via web services API (XML-RPC or Atom).</p>
     *
     * <p>Roller stores time using the timeZone of the server itself. When
     * times are displayed  in a user's weblog they must be translated
     * to the user's timeZone.</p>
     *
     * <p>NOTE: Times are stored using the SQL TIMESTAMP datatype, which on
     * MySQL has only a one-second resolution.</p>
     */
    public Timestamp getUpdateTime() {
        return this.publicationSettings.getUpdateTime();
    }
    
    public void setUpdateTime(Timestamp updateTime) {
        this.publicationSettings.setUpdateTime(updateTime);
    }
    
    public PubStatus getStatus() {
        return this.publicationSettings.getStatus();
    }
    
    public void setStatus(PubStatus status) {
        this.publicationSettings.setStatus(status);
    }
    
    /**
     * Some weblog entries are about one specific link.
     * @return Returns the link.
     */
    public String getLink() {
        return link;
    }
    
    /**
     * @param link The link to set.
     */
    public void setLink(String link) {
        this.link = link;
    }
    
    /**
     * Comma-delimited list of this entry's Plugins.
     */
    public String getPlugins() {
        return plugins;
    }
    
    public void setPlugins(String string) {
        plugins = string;
    }

    /**
     * True if comments are allowed on this weblog entry.
     */
    /**
     * True if comments are allowed on this weblog entry.
     */
    public Boolean getAllowComments() {
        return this.commentSettings.getAllowComments();
    }
    /**
     * True if comments are allowed on this weblog entry.
     */
    public void setAllowComments(Boolean allowComments) {
        this.commentSettings.setAllowComments(allowComments);
    }
    
    /**
     * Number of days after pubTime that comments should be allowed, or 0 for no limit.
     */
    public Integer getCommentDays() {
        return this.commentSettings.getCommentDays();
    }
    /**
     * Number of days after pubTime that comments should be allowed, or 0 for no limit.
     */
    public void setCommentDays(Integer commentDays) {
        this.commentSettings.setCommentDays(commentDays);
    }
    
    /**
     * True if this entry should be rendered right to left.
     */
    public Boolean getRightToLeft() {
        return rightToLeft;
    }
    /**
     * True if this entry should be rendered right to left.
     */
    public void setRightToLeft(Boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
    }
    
    /**
     * True if story should be pinned to the top of the Roller site main blog.
     * @return Returns the pinned.
     */
    public Boolean getPinnedToMain() {
        return pinnedToMain;
    }
    /**
     * True if story should be pinned to the top of the Roller site main blog.
     * @param pinnedToMain The pinned to set.
     */
    public void setPinnedToMain(Boolean pinnedToMain) {
        this.pinnedToMain = pinnedToMain;
    }

    /**
     * The locale string that defines the i18n approach for this entry.
     */
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    public Set<WeblogEntryTag> getTags() {
         return tagSet;
    }

    @SuppressWarnings("unused")
    public void setTags(Set<WeblogEntryTag> tagSet) throws WebloggerException {
         this.tagSet = tagSet;
         this.removedTags = new HashSet<>();
         this.addedTags = new HashSet<>();
    }
     
    public Set<WeblogEntryTag> getAddedTags() {
        return addedTags;
    }
    
    public Set<WeblogEntryTag> getRemovedTags() {
        return removedTags;
    }

    // ------------------------------------------------------------------------
    
    public void setCommentsStillAllowed(boolean ignored) {
        // no-op
    }
    
    
    //------------------------------------------------------------------------
    
    /**
     * Format the publish time of this weblog entry using the specified pattern.
     * See java.text.SimpleDateFormat for more information on this format.
     *
     * @see java.text.SimpleDateFormat
     * @return Publish time formatted according to pattern.
     */
    public String formatPubTime(String pattern) {
        if (pattern == null || getWebsite() == null || getWebsite().getLocaleInstance() == null || getPubTime() == null) {
            mLogger.warn("formatPubTime: pattern, website, locale, or pubTime is null, cannot format date.");
            return "ERROR: formatting date";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern,
                    this.getWebsite().getLocaleInstance());
            
            return format.format(getPubTime());
        } catch (IllegalArgumentException e) {
            mLogger.error("Unexpected exception", e);
        }
        
        return "ERROR: formatting date";
    }
    
    //------------------------------------------------------------------------
    
    /**
     * Format the update time of this weblog entry using the specified pattern.
     * See java.text.SimpleDateFormat for more information on this format.
     *
     * @see java.text.SimpleDateFormat
     * @return Update time formatted according to pattern.
     */
    public String formatUpdateTime(String pattern) {
        if (pattern == null || getUpdateTime() == null) {
            mLogger.warn("formatUpdateTime: pattern or updateTime is null, cannot format date.");
            return "ERROR: formatting date";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            
            return format.format(getUpdateTime());
        } catch (IllegalArgumentException e) {
            mLogger.error("Unexpected exception", e);
        }
        
        return "ERROR: formatting date";
    }
    
    //------------------------------------------------------------------------
    

    
    //------------------------------------------------------------------------
        

    
    /**
     * Return the Title of this post, or the first 255 characters of the
     * entry's text.
     *
     * @return String
     */
    public String getDisplayTitle() {
        if ( getTitle()==null || getTitle().isBlank() ) {
            return StringUtils.left(Utilities.removeHTML(getText()), RollerConstants.TEXTWIDTH_255);
        }
        return Utilities.removeHTML(getTitle());
    }
    
    /**
     * Return RSS 09x style description (escaped HTML version of entry text)
     */
    public String getRss09xDescription() {
        return getRss09xDescription(-1);
    }
    
    /**
     * Return RSS 09x style description (escaped HTML version of entry text)
     */
    public String getRss09xDescription(int maxLength) {
        String ret = StringEscapeUtils.escapeHtml3(getText());
        if (maxLength != -1 && ret.length() > maxLength) {
            ret = ret.substring(0,maxLength-3)+"...";
        }
        return ret;
    }
    

    
    /**
     * A no-op. TODO: fix formbean generation so this is not needed.
     */
    public void setPermalink(String string) {}
    
    /**
     * A no-op. TODO: fix formbean generation so this is not needed.
     */
    public void setPermaLink(String string) {}
    
    /**
     * A no-op.
     * TODO: fix formbean generation so this is not needed.
     * @param string
     */
    public void setDisplayTitle(String string) {
    }
    
    /**
     * A no-op.
     * TODO: fix formbean generation so this is not needed.
     * @param string
     */
    public void setRss09xDescription(String string) {
    }
    
    


    /** Convenience method for checking status */
    public boolean isDraft() {
        return getStatus().equals(PubStatus.DRAFT);
    }

    /** Convenience method for checking status */
    public boolean isPending() {
        return getStatus().equals(PubStatus.PENDING);
    }

    /** Convenience method for checking status */
    public boolean isPublished() {
        return getStatus().equals(PubStatus.PUBLISHED);
    }



    /**
     * Determine if the specified user has permissions to edit this entry.
     */
    public boolean hasWritePermissions(User user) throws WebloggerException {
        
        // global admins can hack whatever they want
        GlobalPermission adminPerm = 
            new GlobalPermission(Collections.singletonList(GlobalPermission.ADMIN));
        boolean hasAdmin = WebloggerFactory.getWeblogger().getPermissionManager()
            .checkPermission(adminPerm, user); 
        if (hasAdmin) {
            return true;
        }
        
        WeblogPermission perm;
        try {
            // if user is an author then post status defaults to PUBLISHED, otherwise PENDING
            PermissionManager pmgr = WebloggerFactory.getWeblogger().getPermissionManager();
            perm = pmgr.getWeblogPermission(getWebsite(), user);
            
        } catch (WebloggerException ex) {
            // security interceptor should ensure this never happens
            mLogger.error("ERROR retrieving user's permission", ex);
            return false;
        }

        boolean author = perm.hasAction(WeblogPermission.POST) || perm.hasAction(WeblogPermission.ADMIN);
        boolean limited = !author && perm.hasAction(WeblogPermission.EDIT_DRAFT);
        
        return author || (limited && (getStatus() == PubStatus.DRAFT || getStatus() == PubStatus.PENDING));
    }
    


    public Boolean getRefreshAggregates() {
        return refreshAggregates;
    }

    public void setRefreshAggregates(Boolean refreshAggregates) {
        this.refreshAggregates = refreshAggregates;
    }

}
