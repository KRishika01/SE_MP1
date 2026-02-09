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

import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.apache.roller.weblogger.pojos.WeblogEntryAttribute;

/**
 * Support class for WeblogEntry attribute operations.
 * Extracted from WeblogEntry to reduce God Class smell.
 */
public class WeblogEntryAttributeSupport {

    private WeblogEntryAttributeSupport() {
        // Utility class, prevent instantiation
    }

    /**
     * Find an entry attribute by name.
     * 
     * @param entry The weblog entry
     * @param name The attribute name to find
     * @return The attribute value, or null if not found
     */
    public static String findEntryAttribute(WeblogEntry entry, String name) {
        if (entry.getEntryAttributes() != null) {
            for (WeblogEntryAttribute att : entry.getEntryAttributes()) {
                if (name.equals(att.getName())) {
                    return att.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Put an entry attribute, creating or updating as needed.
     * 
     * @param entry The weblog entry
     * @param name The attribute name
     * @param value The attribute value
     */
    public static void putEntryAttribute(WeblogEntry entry, String name, String value) {
        WeblogEntryAttribute att = null;
        for (WeblogEntryAttribute o : entry.getEntryAttributes()) {
            if (name.equals(o.getName())) {
                att = o;
                break;
            }
        }
        if (att == null) {
            att = new WeblogEntryAttribute();
            att.setEntry(entry);
            att.setName(name);
            att.setValue(value);
            entry.getEntryAttributes().add(att);
        } else {
            att.setValue(value);
        }
    }
}
