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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.apache.roller.weblogger.pojos.WeblogEntryTag;
import org.apache.roller.weblogger.pojos.WeblogEntryTagComparator;
import org.apache.roller.weblogger.util.Utilities;

/**
 * Support class for WeblogEntry tag operations.
 * Extracted from WeblogEntry to reduce God Class smell.
 */
public class WeblogEntryTagSupport {

    private WeblogEntryTagSupport() {
        // Utility class, prevent instantiation
    }

    /**
     * Add a tag to the entry.
     * Roller lowercases all tags based on locale because there's not a 1:1 mapping
     * between uppercase/lowercase characters across all languages.
     * 
     * @param entry The weblog entry
     * @param name The tag name to add
     * @throws WebloggerException if there's an error adding the tag
     */
    public static void addTag(WeblogEntry entry, String name) throws WebloggerException {
        Locale localeObject = entry.getWebsite() != null ? entry.getWebsite().getLocaleInstance() : Locale.getDefault();
        name = Utilities.normalizeTag(name, localeObject);
        if (name.length() == 0) {
            return;
        }
        
        for (WeblogEntryTag tag : entry.getTags()) {
            if (tag.getName().equals(name)) {
                return;
            }
        }

        WeblogEntryTag tag = new WeblogEntryTag();
        tag.setName(name);
        tag.setCreatorUserName(entry.getCreatorUserName());
        tag.setWeblog(entry.getWebsite());
        tag.setWeblogEntry(entry);
        tag.setTime(entry.getUpdateTime());
        entry.getTags().add(tag);
        
        entry.getAddedTags().add(tag);
    }

    /**
     * Get tags as a space-separated string.
     * 
     * @param entry The weblog entry
     * @return Space-separated tag names
     */
    public static String getTagsAsString(WeblogEntry entry) {
        StringBuilder sb = new StringBuilder();
        // Sort by name
        Set<WeblogEntryTag> tmp = new TreeSet<>(new WeblogEntryTagComparator());
        tmp.addAll(entry.getTags());
        for (WeblogEntryTag entryTag : tmp) {
            sb.append(entryTag.getName()).append(" ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * Set tags from a space-separated string.
     * 
     * @param entry The weblog entry
     * @param tags Space-separated tag names
     * @throws WebloggerException if there's an error setting tags
     */
    public static void setTagsAsString(WeblogEntry entry, String tags) throws WebloggerException {
        if (StringUtils.isEmpty(tags)) {
            entry.getRemovedTags().addAll(entry.getTags());
            entry.getTags().clear();
            return;
        }

        List<String> updatedTags = Utilities.splitStringAsTags(tags);
        Set<String> newTags = new HashSet<>(updatedTags.size());
        Locale localeObject = entry.getWebsite() != null ? entry.getWebsite().getLocaleInstance() : Locale.getDefault();

        for (String name : updatedTags) {
            newTags.add(Utilities.normalizeTag(name, localeObject));
        }

        // remove old ones no longer passed.
        for (Iterator<WeblogEntryTag> it = entry.getTags().iterator(); it.hasNext();) {
            WeblogEntryTag tag = it.next();
            if (!newTags.contains(tag.getName())) {
                // tag no longer listed in UI, needs removal from DB
                entry.getRemovedTags().add(tag);
                it.remove();
            } else {
                // already in persisted set, therefore isn't new
                newTags.remove(tag.getName());
            }
        }

        for (String newTag : newTags) {
            addTag(entry, newTag);
        }
    }
}
