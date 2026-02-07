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

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.roller.weblogger.config.WebloggerConfig;

/**
 * Factory for creating Lucene Analyzer instances.
 * Handles analyzer instantiation based on configuration.
 * 
 * This class extracts the analyzer creation logic from LuceneIndexManager
 * to follow the Single Responsibility Principle.
 */
public class AnalyzerFactory {

    private static final Log logger = LogFactory.getLog(AnalyzerFactory.class);

    private AnalyzerFactory() {
        // Utility class, prevent instantiation
    }

    /**
     * Creates an analyzer instance based on configuration.
     * Falls back to StandardAnalyzer if configured analyzer fails.
     *
     * @return Analyzer instance
     */
    public static Analyzer createAnalyzer() {
        final String className = WebloggerConfig.getProperty("lucene.analyzer.class");
        
        try {
            final Class<?> clazz = Class.forName(className);
            return (Analyzer) ConstructorUtils.invokeConstructor(clazz, null);
            
        } catch (final ClassNotFoundException e) {
            logger.error("Failed to lookup analyzer class: " + className, e);
            return createDefaultAnalyzer();
            
        } catch (final NoSuchMethodException | InstantiationException | 
                       IllegalAccessException | InvocationTargetException e) {
            logger.error("Failed to instantiate analyzer: " + className, e);
            return createDefaultAnalyzer();
        }
    }

    /**
     * Creates the default StandardAnalyzer.
     *
     * @return StandardAnalyzer instance
     */
    private static Analyzer createDefaultAnalyzer() {
        return new StandardAnalyzer();
    }
}