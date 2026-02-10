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

import java.util.concurrent.locks.ReadWriteLock;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;

/**
 * Provides access to shared index resources.
 * 
 * This interface breaks the cyclic dependency between IndexOperation 
 * and LuceneIndexManager by allowing operations to depend on an 
 * abstraction rather than the concrete manager class.
 * 
 * Following the Dependency Inversion Principle:
 * - High-level operations (IndexOperation) depend on abstractions
 * - Low-level implementation (LuceneIndexManager) implements the abstraction
 */
public interface IndexResourceProvider {
    
    /**
     * Gets the read-write lock for thread-safe operations.
     * 
     * @return ReadWriteLock instance for coordinating index access
     */
    ReadWriteLock getReadWriteLock();
    
    /**
     * Gets the shared index reader.
     * 
     * @return IndexReader instance for reading from the index
     */
    IndexReader getSharedIndexReader();
    
    /**
     * Gets the index directory.
     * 
     * @return Directory instance representing the index location
     */
    Directory getIndexDirectory();
    
    /**
     * Resets the shared reader, forcing it to be recreated.
     * Should be called after write operations to ensure readers see updates.
     */
    void resetSharedReader();
    
    /**
     * Gets the analyzer used for text tokenization.
     * 
     * @return Analyzer instance
     */
    Analyzer getAnalyzer();
    
    /**
     * Creates a Term object with the given field and value.
     * Returns null if value is null or empty.
     * 
     * @param field the field name
     * @param value the field value
     * @return Term object or null
     */
    Term getTerm(String field, String value);
}