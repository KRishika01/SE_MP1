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
import java.nio.file.Path;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.miscellaneous.LimitTokenCountAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Manages Lucene Directory instances and index creation.
 * 
 * This class extracts directory management from LuceneIndexManager
 * to follow the Single Responsibility Principle.
 */
public class IndexDirectoryManager {

    private static final Log logger = LogFactory.getLog(IndexDirectoryManager.class);
    private static final int MAX_TOKEN_COUNT = 128;

    private final String indexDir;

    /**
     * Creates a new directory manager for the specified index directory.
     *
     * @param indexDir the directory path
     */
    public IndexDirectoryManager(String indexDir) {
        this.indexDir = indexDir;
    }

    /**
     * Gets the Lucene Directory instance for the index.
     *
     * @return Directory instance, or null if error occurs
     */
    public Directory getIndexDirectory() {
        try {
            return FSDirectory.open(Path.of(indexDir));
        } catch (IOException e) {
            logger.error("Problem accessing index directory: " + indexDir, e);
            return null;
        }
    }

    /**
     * Checks if an index exists in the directory.
     *
     * @return true if index exists, false otherwise
     */
    public boolean indexExists() {
        try {
            Directory dir = getIndexDirectory();
            if (dir == null) {
                return false;
            }
            return DirectoryReader.indexExists(dir);
        } catch (IOException e) {
            logger.error("Problem checking if index exists", e);
            return false;
        }
    }

    /**
     * Creates a new empty index in the directory.
     *
     * @param dir the directory to create the index in
     */
    public void createIndex(Directory dir) {
        IndexWriter writer = null;
        try {
            IndexWriterConfig config = new IndexWriterConfig(
                new LimitTokenCountAnalyzer(
                    AnalyzerFactory.createAnalyzer(), 
                    MAX_TOKEN_COUNT));

            writer = new IndexWriter(dir, config);
            logger.info("Index created successfully");

        } catch (IOException e) {
            logger.error("Error creating index", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    logger.warn("Unable to close IndexWriter", ex);
                }
            }
        }
    }

    /**
     * Gets the index directory path.
     *
     * @return index directory path
     */
    public String getIndexDir() {
        return indexDir;
    }
}