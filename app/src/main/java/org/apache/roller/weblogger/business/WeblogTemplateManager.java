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
import org.apache.roller.weblogger.pojos.CustomTemplateRendition;
import org.apache.roller.weblogger.pojos.ThemeTemplate.ComponentType;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogTemplate;

import java.util.List;

/**
 * Manages custom weblog templates and their renditions.
 */
public interface WeblogTemplateManager {

    /**
     * Store a custom weblog template.
     */
    void saveTemplate(WeblogTemplate data) throws WebloggerException;

    /**
     * Remove a custom template.
     */
    void removeTemplate(WeblogTemplate template) throws WebloggerException;

    /**
     * Get a custom template by its id.
     */
    WeblogTemplate getTemplate(String id) throws WebloggerException;

    /**
     * Get a custom template by the action it supports.
     */
    WeblogTemplate getTemplateByAction(Weblog w, ComponentType a) throws WebloggerException;

    /**
     * Get a custom template by its name.
     */
    WeblogTemplate getTemplateByName(Weblog w, String p) throws WebloggerException;

    /**
     * Get a custom template by its link.
     */
    WeblogTemplate getTemplateByLink(Weblog w, String p) throws WebloggerException;

    /**
     * Save a custom template rendition.
     */
    void saveTemplateRendition(CustomTemplateRendition templateCode) throws WebloggerException;

    /**
     * Get all custom templates for a weblog.
     */
    List<WeblogTemplate> getTemplates(Weblog w) throws WebloggerException;

    /**
     * Release any resources held by manager.
     */
    void release();
}
