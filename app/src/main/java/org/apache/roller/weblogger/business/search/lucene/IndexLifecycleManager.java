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

package org.apache.roller.weblogger.business.search.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.FSDirectory;
import org.apache.roller.weblogger.business.InitializationException;

/**
 * Manages the lifecycle of the search index including initialization,
 * consistency checking, and cleanup.
 * 
 * This class extracts lifecycle management from LuceneIndexManager
 * to follow the Single Responsibility Principle.
 */
public class IndexLifecycleManager {

    private static final Log logger = LogFactory.getLog(IndexLifecycleManager.class);

    private final String indexDir;
    private final File indexConsistencyMarker;
    private boolean inconsistentAtStartup = false;

    /**
     * Creates a new lifecycle manager for the specified index directory.
     *
     * @param indexDir the directory containing the search index
     */
    public IndexLifecycleManager(String indexDir) {
        this.indexDir = indexDir;
        this.indexConsistencyMarker = new File(
            indexDir + File.separator + ".index-inconsistent");
    }

    /**
     * Initializes the index, checking for consistency and creating
     * directory if needed.
     *
     * @throws InitializationException if initialization fails
     */
    public void initialize() throws InitializationException {
        // Check for consistency marker from previous unclean shutdown
        if (indexConsistencyMarker.exists()) {
            logger.debug("Index inconsistent: marker exists");
            inconsistentAtStartup = true;
            deleteIndex();
        } else {
            try {
                File makeIndexDir = new File(indexDir);
                if (!makeIndexDir.exists()) {
                    makeIndexDir.mkdirs();
                    inconsistentAtStartup = true;
                    logger.debug("Index inconsistent: new directory created");
                }
                indexConsistencyMarker.createNewFile();
            } catch (IOException e) {
                logger.error("Failed to create index directory or consistency marker", e);
                throw new InitializationException("Index initialization failed", e);
            }
        }
    }

    /**
     * Cleans up resources and removes consistency marker.
     */
    public void shutdown() {
        if (indexConsistencyMarker.exists()) {
            boolean deleted = indexConsistencyMarker.delete();
            if (!deleted) {
                logger.warn("Failed to delete index consistency marker");
            }
        }
    }

    /**
     * Checks if the index was inconsistent at startup.
     *
     * @return true if index was inconsistent, false otherwise
     */
    public boolean isInconsistentAtStartup() {
        return inconsistentAtStartup;
    }

    /**
     * Deletes all files in the index directory.
     */
    public void deleteIndex() {
        try (FSDirectory directory = FSDirectory.open(Path.of(indexDir))) {
            String[] files = directory.listAll();
            for (String file : files) {
                try {
                    Files.delete(Path.of(indexDir, file));
                } catch (IOException ex) {
                    logger.warn("Failed to delete index file: " + file, ex);
                }
            }
            logger.info("Index deleted successfully");
        } catch (IOException ex) {
            logger.error("Problem accessing index directory during deletion", ex);
        }
    }

    /**
     * Marks the index as inconsistent (for use during initialization).
     */
    public void markInconsistent() {
        inconsistentAtStartup = true;
    }
}