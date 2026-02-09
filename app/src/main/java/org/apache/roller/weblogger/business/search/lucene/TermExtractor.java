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
import java.io.StringReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;

/**
 * Utility class for extracting Lucene Terms from text input.
 * 
 * This class extracts the term extraction logic from LuceneIndexManager
 * to follow the Single Responsibility Principle.
 */
public class TermExtractor {

    private static final Log logger = LogFactory.getLog(TermExtractor.class);

    private TermExtractor() {
        // Utility class, prevent instantiation
    }

    /**
     * Extracts a Lucene Term from the input text using the configured analyzer.
     *
     * @param field the field name
     * @param input the input text to analyze
     * @return Term object, or null if extraction fails or input is null
     */
    public static Term getTerm(String field, String input) {
        if (input == null || field == null) {
            return null;
        }

        Analyzer analyzer = AnalyzerFactory.createAnalyzer();
        Term term = null;

        try (TokenStream tokens = analyzer.tokenStream(field, new StringReader(input))) {
            CharTermAttribute termAtt = tokens.addAttribute(CharTermAttribute.class);
            tokens.reset();

            if (tokens.incrementToken()) {
                String termText = termAtt.toString();
                term = new Term(field, termText);
            }
            tokens.end();
        } catch (IOException e) {
            logger.debug("Error extracting term from input: " + input, e);
        }

        return term;
    }
}