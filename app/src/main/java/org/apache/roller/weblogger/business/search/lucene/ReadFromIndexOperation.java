// /*
//  * Licensed to the Apache Software Foundation (ASF) under one or more
//  *  contributor license agreements.  The ASF licenses this file to You
//  * under the Apache License, Version 2.0 (the "License"); you may not
//  * use this file except in compliance with the License.
//  * You may obtain a copy of the License at
//  *
//  *     http://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.  For additional information regarding
//  * copyright in this work, please see the NOTICE file in the top level
//  * directory of this distribution.
//  */
// package org.apache.roller.weblogger.business.search.lucene;

// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;

// /**
//  * @author aim4min
//  */
// public abstract class ReadFromIndexOperation extends IndexOperation {
//     public ReadFromIndexOperation(LuceneIndexManager mgr) {
//         super(mgr);
//     }
    
//     private static Log logger = LogFactory.getFactory().getInstance(
//             ReadFromIndexOperation.class);
    
//     @Override
//     public final void run() {
//         try {
//             manager.getReadWriteLock().readLock().lock();
//             doRun();

//         } catch (Exception e) {
//             logger.error("Error acquiring read lock on index", e);
//         } finally {
//             manager.getReadWriteLock().readLock().unlock();
//         }
//     }
    
// }
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

package org.apache.roller.weblogger.business.search.lucene;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An index operation that reads from the index.
 * Uses read lock to allow concurrent reads.
 */
public abstract class ReadFromIndexOperation extends IndexOperation {
    
    private static Log logger = LogFactory.getFactory().getInstance(
            ReadFromIndexOperation.class);
    
    /**
     * Constructor accepting the resource provider interface.
     * 
     * @param resourceProvider provider of index resources
     */
    public ReadFromIndexOperation(IndexResourceProvider resourceProvider) {
        super(resourceProvider);
    }
    
    @Override
    public final void run() {
        try {
            // Use interface method to get read lock
            resourceProvider.getReadWriteLock().readLock().lock();
            doRun();

        } catch (Exception e) {
            logger.error("Error acquiring read lock on index", e);
        } finally {
            resourceProvider.getReadWriteLock().readLock().unlock();
        }
    }
}