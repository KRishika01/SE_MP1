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

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;

/**
 * Manages shared IndexReader instances for searching.
 * Provides thread-safe access to readers.
 * 
 * This class extracts reader management from LuceneIndexManager
 * to follow the Single Responsibility Principle.
 */
public class IndexReaderManager {

    private static final Log logger = LogFactory.getLog(IndexReaderManager.class);

    private final IndexDirectoryManager directoryManager;
    private IndexReader sharedReader;

    /**
     * Creates a new reader manager.
     *
     * @param directoryManager the directory manager to use
     */
    public IndexReaderManager(IndexDirectoryManager directoryManager) {
        this.directoryManager = directoryManager;
    }

    /**
     * Initializes the shared reader if index exists.
     *
     * @return true if reader was initialized, false otherwise
     */
    public synchronized boolean initialize() {
        if (!directoryManager.indexExists()) {
            logger.debug("Index does not exist, reader not initialized");
            return false;
        }

        try {
            Directory dir = directoryManager.getIndexDirectory();
            if (dir != null) {
                sharedReader = DirectoryReader.open(dir);
                logger.debug("Shared reader initialized");
                return true;
            }
        } catch (IOException | IllegalArgumentException ex) {
            logger.warn("Failed to open shared reader", ex);
        }
        return false;
    }

    /**
     * Gets the shared IndexReader, creating it if necessary.
     * This method is thread-safe.
     *
     * @return IndexReader instance
     * @throws RuntimeException if reader cannot be created
     */
    public synchronized IndexReader getSharedIndexReader() {
        if (sharedReader == null) {
            try {
                Directory dir = directoryManager.getIndexDirectory();
                if (dir == null) {
                    throw new IOException("Index directory is null");
                }
                sharedReader = DirectoryReader.open(dir);
            } catch (IOException ex) {
                logger.error("Error opening DirectoryReader", ex);
                throw new RuntimeException(ex);
            }
        }
        return sharedReader;
    }

    /**
     * Resets the shared reader, forcing it to be recreated on next access.
     * Use this after index modifications.
     */
    public synchronized void resetSharedReader() {
        if (sharedReader != null) {
            try {
                sharedReader.close();
            } catch (IOException ex) {
                logger.warn("Error closing reader during reset", ex);
            }
        }
        sharedReader = null;
    }

    /**
     * Closes the shared reader and releases resources.
     */
    public synchronized void shutdown() {
        if (sharedReader != null) {
            try {
                sharedReader.close();
                logger.debug("Shared reader closed");
            } catch (IOException ex) {
                logger.error("Unable to close reader during shutdown", ex);
            }
            sharedReader = null;
        }
    }
}