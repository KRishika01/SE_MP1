// /*
//  * Licensed to the Apache Software Foundation (ASF) under one or more
//  * contributor license agreements.  The ASF licenses this file to You
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

// import java.io.IOException;
// import java.sql.Timestamp;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import java.util.Set;
// import java.util.TreeSet;
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
// import org.apache.lucene.document.Document;
// import org.apache.lucene.search.ScoreDoc;
// import org.apache.roller.weblogger.WebloggerException;
// import org.apache.roller.weblogger.business.URLStrategy;
// import org.apache.roller.weblogger.business.WeblogEntryManager;
// import org.apache.roller.weblogger.business.Weblogger;
// import org.apache.roller.weblogger.business.WebloggerFactory;
// import org.apache.roller.weblogger.business.search.SearchResultList;
// import org.apache.roller.weblogger.pojos.WeblogEntry;
// import org.apache.roller.weblogger.pojos.wrapper.WeblogEntryWrapper;

// /**
//  * Converts Lucene search hits to WeblogEntry objects.
//  * 
//  * This class extracts result conversion logic from LuceneIndexManager
//  * to follow the Single Responsibility Principle.
//  */
// public class SearchResultConverter {

//     private static final Log logger = LogFactory.getLog(SearchResultConverter.class);

//     /**
//      * Converts Lucene search hits to a paginated list of weblog entries.
//      *
//      * @param hits array of search results
//      * @param search the search operation containing the searcher
//      * @param pageNum the page number (0-indexed)
//      * @param entryCount number of entries per page
//      * @param weblogHandle the weblog handle (for filtering)
//      * @param websiteSpecificSearch true if searching within a specific weblog
//      * @param urlStrategy the URL strategy for wrapping entries
//      * @return SearchResultList containing entries and metadata
//      * @throws WebloggerException if conversion fails
//      */
//     public SearchResultList convertHitsToEntryList(
//         ScoreDoc[] hits,
//         SearchOperation search,
//         int pageNum,
//         int entryCount,
//         String weblogHandle,
//         boolean websiteSpecificSearch,
//         URLStrategy urlStrategy) throws WebloggerException {

//         List<WeblogEntryWrapper> results = new ArrayList<>();

//         // Calculate pagination bounds
//         int offset = calculateOffset(pageNum, entryCount, hits.length);
//         int limit = calculateLimit(offset, entryCount, hits.length);

//         try {
//             Set<String> categories = extractCategories(
//                 hits, 
//                 search, 
//                 offset, 
//                 limit, 
//                 weblogHandle, 
//                 websiteSpecificSearch);

//             List<WeblogEntryWrapper> entries = extractEntries(
//                 hits, 
//                 search, 
//                 offset, 
//                 limit, 
//                 urlStrategy);

//             results.addAll(entries);

//             return new SearchResultList(results, categories, limit, offset);

//         } catch (IOException e) {
//             logger.error("Error converting search hits to entries", e);
//             throw new WebloggerException("Failed to convert search results", e);
//         }
//     }

//     /**
//      * Calculates the offset for pagination.
//      */
//     private int calculateOffset(int pageNum, int entryCount, int totalHits) {
//         int offset = pageNum * entryCount;
//         if (offset >= totalHits) {
//             offset = 0;
//         }
//         return offset;
//     }

//     /**
//      * Calculates the limit for pagination.
//      */
//     private int calculateLimit(int offset, int entryCount, int totalHits) {
//         int limit = entryCount;
//         if (offset + limit > totalHits) {
//             limit = totalHits - offset;
//         }
//         return limit;
//     }

//     /**
//      * Extracts unique categories from search results.
//      */
//     private Set<String> extractCategories(
//         ScoreDoc[] hits,
//         SearchOperation search,
//         int offset,
//         int limit,
//         String weblogHandle,
//         boolean websiteSpecificSearch) throws IOException {

//         TreeSet<String> categorySet = new TreeSet<>();

//         for (int i = offset; i < offset + limit; i++) {
//             Document doc = search.getSearcher().doc(hits[i].doc);
//             String handle = doc.getField(FieldConstants.WEBSITE_HANDLE).stringValue();

//             if (!(websiteSpecificSearch && handle.equals(weblogHandle))
//                 && doc.getField(FieldConstants.CATEGORY) != null) {
//                 categorySet.add(doc.getField(FieldConstants.CATEGORY).stringValue());
//             }
//         }

//         return categorySet;
//     }

//     /**
//      * Extracts weblog entries from search results.
//      */
//     private List<WeblogEntryWrapper> extractEntries(
//         ScoreDoc[] hits,
//         SearchOperation search,
//         int offset,
//         int limit,
//         URLStrategy urlStrategy) throws IOException, WebloggerException {

//         List<WeblogEntryWrapper> entries = new ArrayList<>();
//         Weblogger roller = WebloggerFactory.getWeblogger();
//         WeblogEntryManager weblogMgr = roller.getWeblogEntryManager();
//         Timestamp now = new Timestamp(new Date().getTime());

//         for (int i = offset; i < offset + limit; i++) {
//             Document doc = search.getSearcher().doc(hits[i].doc);
//             String entryId = doc.getField(FieldConstants.ID).stringValue();
//             WeblogEntry entry = weblogMgr.getWeblogEntry(entryId);

//             // Filter out null entries (deleted/inactive) and future posts
//             if (entry != null && entry.getPubTime().before(now)) {
//                 entries.add(WeblogEntryWrapper.wrap(entry, urlStrategy));
//             }
//         }

//         return entries;
//     }
// }

package org.apache.roller.weblogger.business.search.lucene;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.URLStrategy;
import org.apache.roller.weblogger.business.WeblogEntryManager;
import org.apache.roller.weblogger.business.Weblogger;
import org.apache.roller.weblogger.business.WebloggerFactory;
import org.apache.roller.weblogger.business.search.SearchResultList;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.apache.roller.weblogger.pojos.wrapper.WeblogEntryWrapper;

/**
 * Converts Lucene search hits to WeblogEntry objects.
 * 
 * This class extracts result conversion logic from LuceneIndexManager
 * to follow the Single Responsibility Principle.
 */
public class SearchResultConverter {

    private static final Log logger = LogFactory.getLog(SearchResultConverter.class);

    /**
     * Converts Lucene search hits to a paginated list of weblog entries.
     *
     * @param hits array of search results
     * @param searcher the Lucene IndexSearcher to retrieve documents
     * @param pageNum the page number (0-indexed)
     * @param entryCount number of entries per page
     * @param weblogHandle the weblog handle (for filtering)
     * @param websiteSpecificSearch true if searching within a specific weblog
     * @param urlStrategy the URL strategy for wrapping entries
     * @return SearchResultList containing entries and metadata
     * @throws WebloggerException if conversion fails
     */
    public SearchResultList convertHitsToEntryList(
        ScoreDoc[] hits,
        IndexSearcher searcher,  // Changed from SearchOperation
        int pageNum,
        int entryCount,
        String weblogHandle,
        boolean websiteSpecificSearch,
        URLStrategy urlStrategy) throws WebloggerException {

        List<WeblogEntryWrapper> results = new ArrayList<>();

        // Calculate pagination bounds
        int offset = calculateOffset(pageNum, entryCount, hits.length);
        int limit = calculateLimit(offset, entryCount, hits.length);

        try {
            Set<String> categories = extractCategories(
                hits, 
                searcher,  // Changed
                offset, 
                limit, 
                weblogHandle, 
                websiteSpecificSearch);

            List<WeblogEntryWrapper> entries = extractEntries(
                hits, 
                searcher,  // Changed
                offset, 
                limit, 
                urlStrategy);

            results.addAll(entries);

            return new SearchResultList(results, categories, limit, offset);

        } catch (IOException e) {
            logger.error("Error converting search hits to entries", e);
            throw new WebloggerException("Failed to convert search results", e);
        }
    }

    /**
     * Calculates the offset for pagination.
     */
    private int calculateOffset(int pageNum, int entryCount, int totalHits) {
        int offset = pageNum * entryCount;
        if (offset >= totalHits) {
            offset = 0;
        }
        return offset;
    }

    /**
     * Calculates the limit for pagination.
     */
    private int calculateLimit(int offset, int entryCount, int totalHits) {
        int limit = entryCount;
        if (offset + limit > totalHits) {
            limit = totalHits - offset;
        }
        return limit;
    }

    /**
     * Extracts unique categories from search results.
     */
    private Set<String> extractCategories(
        ScoreDoc[] hits,
        IndexSearcher searcher,  // Changed
        int offset,
        int limit,
        String weblogHandle,
        boolean websiteSpecificSearch) throws IOException {

        TreeSet<String> categorySet = new TreeSet<>();

        for (int i = offset; i < offset + limit; i++) {
            Document doc = searcher.doc(hits[i].doc);  // Changed
            String handle = doc.getField(FieldConstants.WEBSITE_HANDLE).stringValue();

            if (!(websiteSpecificSearch && handle.equals(weblogHandle))
                && doc.getField(FieldConstants.CATEGORY) != null) {
                categorySet.add(doc.getField(FieldConstants.CATEGORY).stringValue());
            }
        }

        return categorySet;
    }

    /**
     * Extracts weblog entries from search results.
     */
    private List<WeblogEntryWrapper> extractEntries(
        ScoreDoc[] hits,
        IndexSearcher searcher,  // Changed
        int offset,
        int limit,
        URLStrategy urlStrategy) throws IOException, WebloggerException {

        List<WeblogEntryWrapper> entries = new ArrayList<>();
        Weblogger roller = WebloggerFactory.getWeblogger();
        WeblogEntryManager weblogMgr = roller.getWeblogEntryManager();
        Timestamp now = new Timestamp(new Date().getTime());

        for (int i = offset; i < offset + limit; i++) {
            Document doc = searcher.doc(hits[i].doc);  // Changed
            String entryId = doc.getField(FieldConstants.ID).stringValue();
            WeblogEntry entry = weblogMgr.getWeblogEntry(entryId);

            // Filter out null entries (deleted/inactive) and future posts
            if (entry != null && entry.getPubTime().before(now)) {
                entries.add(WeblogEntryWrapper.wrap(entry, urlStrategy));
            }
        }

        return entries;
    }
}