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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.roller.util.DateUtil;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.WebloggerFactory;
import org.apache.roller.weblogger.config.WebloggerConfig;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.apache.roller.weblogger.util.Utilities;

/**
 * Support class for WeblogEntry permalink operations.
 * Extracted from WeblogEntry to reduce God Class smell.
 */
public class WeblogEntryPermalinkSupport {

    private static final char TITLE_SEPARATOR =
        WebloggerConfig.getBooleanProperty("weblogentry.title.useUnderscoreSeparator") ? '_' : '-';

    private WeblogEntryPermalinkSupport() {
        // Utility class, prevent instantiation
    }

    /**
     * Returns absolute entry permalink.
     * 
     * @param entry The weblog entry
     * @return Absolute permalink URL
     */
    public static String getPermalink(WeblogEntry entry) {
        return WebloggerFactory.getWeblogger().getUrlStrategy().getWeblogEntryURL(
            entry.getWebsite(), null, entry.getAnchor(), true);
    }

    /**
     * Returns entry permalink, relative to Roller context.
     * 
     * @param entry The weblog entry
     * @return Relative permalink URL
     * @deprecated Use getPermalink() instead.
     */
    @Deprecated
    public static String getPermaLink(WeblogEntry entry) {
        String lAnchor = URLEncoder.encode(entry.getAnchor(), StandardCharsets.UTF_8);
        return "/" + entry.getWebsite().getHandle() + "/entry/" + lAnchor;
    }

    /**
     * Get relative URL to comments page.
     * 
     * @param entry The weblog entry
     * @return Comments link URL
     * @deprecated Use commentLink() instead
     */
    @Deprecated
    public static String getCommentsLink(WeblogEntry entry) {
        return getPermaLink(entry) + "#comments";
    }

    /**
     * Create anchor for weblog entry, based on title or text.
     * 
     * @param entry The weblog entry
     * @return Generated anchor
     * @throws WebloggerException if there's an error creating the anchor
     */
    public static String createAnchor(WeblogEntry entry) throws WebloggerException {
        return WebloggerFactory.getWeblogger().getWeblogEntryManager().createAnchor(entry);
    }

    /**
     * Create anchor base for weblog entry, based on title or text.
     * 
     * @param entry The weblog entry
     * @return Generated anchor base
     */
    public static String createAnchorBase(WeblogEntry entry) {
        // Use title (minus non-alphanumeric characters)
        String base = null;
        if (!StringUtils.isEmpty(entry.getTitle())) {
            base = Utilities.replaceNonAlphanumeric(entry.getTitle(), ' ').trim();    
        }
        // If we still have no base, then try text (minus non-alphanumerics)
        if (StringUtils.isEmpty(base) && !StringUtils.isEmpty(entry.getText())) {
            base = Utilities.replaceNonAlphanumeric(entry.getText(), ' ').trim();  
        }
        
        if (!StringUtils.isEmpty(base)) {
            
            // Use only the first 4 words
            StringTokenizer toker = new StringTokenizer(base);
            String tmp = null;
            int count = 0;
            while (toker.hasMoreTokens() && count < 5) {
                String s = toker.nextToken();
                s = s.toLowerCase();
                tmp = (tmp == null) ? s : tmp + TITLE_SEPARATOR + s;
                count++;
            }
            base = tmp;
        }
        // No title or text, so instead we will use the items date
        // in YYYYMMDD format as the base anchor
        else {
            base = DateUtil.format8chars(entry.getPubTime());
        }
        
        return base;
    }
}
