/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
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


package org.apache.roller.weblogger.business;

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.pojos.Weblog;

/**
 * Manages the lifecycle of Weblog objects.
 */
public interface WeblogManager {

    /**
     * Add new website and its default contents.
     */
    void addWeblog(Weblog newWebsite) throws WebloggerException;

    /**
     * Store a single weblog.
     */
    void saveWeblog(Weblog data) throws WebloggerException;

    /**
     * Remove a weblog and all its contents.
     */
    void removeWeblog(Weblog website) throws WebloggerException;

    /**
     * Get a weblog by its ID.
     */
    Weblog getWeblog(String id) throws WebloggerException;

    /**
     * Get a visible weblog by its handle.
     */
    Weblog getWeblogByHandle(String handle) throws WebloggerException;

    /**
     * Get a weblog by handle, with option to include non-visible ones.
     */
    Weblog getWeblogByHandle(String handle, Boolean visible) throws WebloggerException;

    /**
     * Release any resources held by manager.
     */
    void release();
}
