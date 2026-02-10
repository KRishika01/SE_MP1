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

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.roller.weblogger.business.search.IndexManager;

/**
 * An operation that searches the index.
 * 
 * @author Mindaugas Idzelis (min@idzelis.com)
 */
public class SearchOperation extends ReadFromIndexOperation {

    private static Log logger = LogFactory.getFactory().getInstance(
            SearchOperation.class);

    private static final String[] SEARCH_FIELDS = new String[] {
        FieldConstants.CONTENT,
        FieldConstants.TITLE,
        FieldConstants.C_CONTENT
    };

    private static final Sort SORTER = new Sort(new SortField(
            FieldConstants.PUBLISHED, SortField.Type.STRING, true));

    private IndexSearcher searcher;
    private TopFieldDocs searchresults;

    private String term;
    private String weblogHandle;
    private String category;
    private String locale;
    private String parseError;

    /**
     * Create a new operation that searches the index.
     * Accepts IndexManager which implements IndexResourceProvider.
     */
    public SearchOperation(IndexManager mgr) {
        // Cast to interface instead of concrete LuceneIndexManager
        super((IndexResourceProvider) mgr);
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public void doRun() {
        final int docLimit = 500;
        searchresults = null;
        searcher = null;

        try {
            // Use interface method to get reader
            IndexReader reader = resourceProvider.getSharedIndexReader();
            searcher = new IndexSearcher(reader);

            // USE INTERFACE METHOD - NOT STATIC CALL
            MultiFieldQueryParser multiParser = new MultiFieldQueryParser(
                    SEARCH_FIELDS, resourceProvider.getAnalyzer());  // ← Changed from LuceneIndexManager.getAnalyzer()

            multiParser.setDefaultOperator(MultiFieldQueryParser.Operator.AND);

            Query query = multiParser.parse(term);

            // USE INTERFACE METHOD - NOT STATIC CALL
            Term handleTerm = resourceProvider.getTerm(FieldConstants.WEBSITE_HANDLE, weblogHandle);  // ← Changed from LuceneIndexManager.getTerm()
            if (handleTerm != null) {
                query = new BooleanQuery.Builder()
                    .add(query, BooleanClause.Occur.MUST)
                    .add(new TermQuery(handleTerm), BooleanClause.Occur.MUST)
                    .build();
            }

            if (category != null) {
                Term catTerm = new Term(FieldConstants.CATEGORY, category.toLowerCase());
                query = new BooleanQuery.Builder()
                    .add(query, BooleanClause.Occur.MUST)
                    .add(new TermQuery(catTerm), BooleanClause.Occur.MUST)
                    .build();
            }

            // USE INTERFACE METHOD - NOT STATIC CALL
            Term localeTerm = resourceProvider.getTerm(FieldConstants.LOCALE, locale);  // ← Changed from LuceneIndexManager.getTerm()
            if (localeTerm != null) {
                query = new BooleanQuery.Builder()
                    .add(query, BooleanClause.Occur.MUST)
                    .add(new TermQuery(localeTerm), BooleanClause.Occur.MUST)
                    .build();
            }

            searchresults = searcher.search(query, docLimit, SORTER);

        } catch (IOException e) {
            logger.error("Error searching index", e);
            parseError = e.getMessage();

        } catch (ParseException e) {
            parseError = e.getMessage();
        }
    }

    public IndexSearcher getSearcher() {
        return searcher;
    }

    public void setSearcher(IndexSearcher searcher) {
        this.searcher = searcher;
    }

    public TopFieldDocs getResults() {
        return searchresults;
    }

    public int getResultsCount() {
        if (searchresults == null) {
            return -1;
        }
        return (int) searchresults.totalHits.value;
    }

    public String getParseError() {
        return parseError;
    }

    public void setWeblogHandle(String weblogHandle) {
        this.weblogHandle = weblogHandle;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}