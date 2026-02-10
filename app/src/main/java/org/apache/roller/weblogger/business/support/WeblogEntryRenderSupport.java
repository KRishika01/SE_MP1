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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.business.plugins.entry.WeblogEntryPlugin;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.apache.roller.weblogger.util.HTMLSanitizer;
import org.apache.roller.weblogger.util.I18nMessages;

/**
 * Support class for WeblogEntry rendering operations.
 * Extracted from WeblogEntry to reduce God Class smell.
 */
public class WeblogEntryRenderSupport {

    private static final Log mLogger = LogFactory.getFactory().getInstance(WeblogEntryRenderSupport.class);

    private WeblogEntryRenderSupport() {
        // Utility class, prevent instantiation
    }

    /**
     * Get entry text, transformed by plugins enabled for entry.
     * 
     * @param entry The weblog entry
     * @return Transformed text
     */
    public static String getTransformedText(WeblogEntry entry) {
        return render(entry, entry.getText());
    }

    /**
     * Get entry summary, transformed by plugins enabled for entry.
     * 
     * @param entry The weblog entry
     * @return Transformed summary
     */
    public static String getTransformedSummary(WeblogEntry entry) {
        return render(entry, entry.getSummary());
    }

    /**
     * Get the right transformed display content depending on the situation.
     *
     * If the readMoreLink is specified then we assume the caller wants to
     * prefer summary over content and we include a "Read More" link at the
     * end of the summary if it exists.  Otherwise, if the readMoreLink is
     * empty or null then we assume the caller prefers content over summary.
     * 
     * @param entry The weblog entry
     * @param readMoreLink The read more link URL, or null
     * @return Display content
     */
    public static String displayContent(WeblogEntry entry, String readMoreLink) {
        String displayContent;
        
        if(readMoreLink == null || readMoreLink.isBlank() || "nil".equals(readMoreLink)) {
            
            // no readMore link means permalink, so prefer text over summary
            if(StringUtils.isNotEmpty(entry.getText())) {
                displayContent = getTransformedText(entry);
            } else {
                displayContent = getTransformedSummary(entry);
            }
        } else {
            // not a permalink, so prefer summary over text
            // include a "read more" link if needed
            if(StringUtils.isNotEmpty(entry.getSummary())) {
                displayContent = getTransformedSummary(entry);
                if(StringUtils.isNotEmpty(entry.getText())) {
                    // add read more
                    List<String> args = List.of(readMoreLink);
                    
                    // TODO: we need a more appropriate way to get the view locale here
                    String readMore = I18nMessages.getMessages(entry.getWebsite().getLocaleInstance()).getString("macro.weblog.readMoreLink", args);
                    
                    displayContent += readMore;
                }
            } else {
                displayContent = getTransformedText(entry);
            }
        }
        
        return HTMLSanitizer.conditionallySanitize(displayContent);
    }

    /**
     * Convenience method to transform mPlugins to a List.
     * 
     * @param entry The weblog entry
     * @return List of plugin names
     */
    public static List<String> getPluginsList(WeblogEntry entry) {
        if (entry.getPlugins() != null) {
            return Arrays.asList(StringUtils.split(entry.getPlugins(), ","));
        }
        return Collections.emptyList();
    }

    /**
     * Transform string based on plugins enabled for this weblog entry.
     * 
     * @param entry The weblog entry
     * @param str The string to render
     * @return Rendered string
     */
    private static String render(WeblogEntry entry, String str) {
        String ret = str;
        mLogger.debug("Applying page plugins to string");
        Map<String, WeblogEntryPlugin> inPlugins = entry.getWebsite().getInitializedPlugins();
        if (str != null && inPlugins != null) {
            List<String> entryPlugins = getPluginsList(entry);
            
            // if no Entry plugins, don't bother looping.
            if (entryPlugins != null && !entryPlugins.isEmpty()) {
                
                // now loop over mPagePlugins, matching
                // against Entry plugins (by name):
                // where a match is found render Plugin.
                for (Map.Entry<String, WeblogEntryPlugin> pluginEntry : inPlugins.entrySet()) {
                    if (entryPlugins.contains(pluginEntry.getKey())) {
                        WeblogEntryPlugin pagePlugin = pluginEntry.getValue();
                        try {
                            ret = pagePlugin.render(entry, ret);
                        } catch (Exception e) {
                            mLogger.error("ERROR from plugin: " + pagePlugin.getName(), e);
                        }
                    }
                }
            }
        } 
        return HTMLSanitizer.conditionallySanitize(ret);
    }
}
