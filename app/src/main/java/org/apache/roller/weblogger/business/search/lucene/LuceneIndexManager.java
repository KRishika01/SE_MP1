package org.apache.roller.weblogger.business.search.lucene;

import java.io.File;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.InitializationException;
import org.apache.roller.weblogger.business.URLStrategy;
import org.apache.roller.weblogger.business.Weblogger;
import org.apache.roller.weblogger.business.search.IndexManager;
import org.apache.roller.weblogger.business.search.SearchResultList;
import org.apache.roller.weblogger.config.WebloggerConfig;
import org.apache.roller.weblogger.config.WebloggerRuntimeConfig;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogEntry;

/**
 * Lucene implementation of IndexManager. This is the central entry point into
 * the Lucene searching API.
 * 
 * Refactored to delegate responsibilities to specialized components while
 * maintaining backward compatibility with existing tests and API consumers.
 * 
 * Now implements IndexResourceProvider to break cyclic dependency with IndexOperation.
 * 
 * Components:
 * - IndexLifecycleManager: handles initialization, consistency checks, shutdown
 * - IndexDirectoryManager: manages Lucene directories and index creation
 * - IndexReaderManager: manages shared IndexReader instances
 * - SearchResultConverter: converts Lucene hits to WeblogEntry objects
 * - AnalyzerFactory: creates Analyzer instances
 * - TermExtractor: extracts Terms from text
 * 
 * @author Mindaugas Idzelis (min@idzelis.com)
 * @author mraible (formatting and making indexDir configurable)
 */
@com.google.inject.Singleton
public class LuceneIndexManager implements IndexManager, IndexResourceProvider {

    private static final Log logger = LogFactory.getLog(LuceneIndexManager.class);

    private final Weblogger roller;
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    
    // Delegated components (internal implementation details)
    private final IndexLifecycleManager lifecycleManager;
    private final IndexDirectoryManager directoryManager;
    private final IndexReaderManager readerManager;
    private final SearchResultConverter resultConverter;

    private boolean searchEnabled = true;

    /**
     * Creates a new lucene index manager. This should only be created once.
     * Creating the index manager more than once will definitely result in
     * errors. The preferred way of getting an index is through the
     * RollerContext.
     * 
     * @param roller - the weblogger instance
     */
    @com.google.inject.Inject
    protected LuceneIndexManager(Weblogger roller) {
        this.roller = roller;

        // Check config to see if the internal search is enabled
        String enabled = WebloggerConfig.getProperty("search.enabled");
        if ("false".equalsIgnoreCase(enabled)) {
            this.searchEnabled = false;
        }

        // Get index directory from configuration
        String searchIndexDir = WebloggerConfig.getProperty("search.index.dir");
        String indexDir = searchIndexDir.replace('/', File.separatorChar);

        // Initialize delegated components (hidden from public API)
        this.lifecycleManager = new IndexLifecycleManager(indexDir);
        this.directoryManager = new IndexDirectoryManager(indexDir);
        this.readerManager = new IndexReaderManager(directoryManager);
        this.resultConverter = new SearchResultConverter();

        // Log configuration
        logger.info("Search enabled: " + this.searchEnabled);
        logger.info("Index directory: " + indexDir);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void initialize() throws InitializationException {
        // Only initialize the index if search is enabled
        if (!this.searchEnabled) {
            logger.info("Search is disabled, skipping index initialization");
            return;
        }

        // Delegate to lifecycle manager
        lifecycleManager.initialize();

        // Check if index exists and is readable
        if (directoryManager.indexExists()) {
            boolean readerInitialized = readerManager.initialize();
            
            if (!readerInitialized) {
                logger.warn("Failed to initialize index reader, scheduling rebuild");
                lifecycleManager.markInconsistent();
                lifecycleManager.deleteIndex();
            }
        } else {
            logger.debug("Index does not exist, will be created");
            lifecycleManager.markInconsistent();
            lifecycleManager.deleteIndex();
            directoryManager.createIndex(directoryManager.getIndexDirectory());
        }

        // Rebuild index if inconsistent
        if (lifecycleManager.isInconsistentAtStartup()) {
            logger.info("Index was inconsistent. Rebuilding index in the background...");
            try {
                rebuildWeblogIndex();
            } catch (WebloggerException ex) {
                logger.error("ERROR: scheduling re-index operation", ex);
            }
        } else {
            logger.info("Index initialized and ready for use.");
        }
    }

    @Override
    public void rebuildWeblogIndex() throws WebloggerException {
        scheduleIndexOperation(
            new RebuildWebsiteIndexOperation(roller, this, null));
    }

    @Override
    public void rebuildWeblogIndex(Weblog website) throws WebloggerException {
        scheduleIndexOperation(
            new RebuildWebsiteIndexOperation(roller, this, website));
    }

    @Override
    public void removeWeblogIndex(Weblog website) throws WebloggerException {
        scheduleIndexOperation(
            new RemoveWebsiteIndexOperation(roller, this, website));
    }

    @Override
    public void addEntryIndexOperation(WeblogEntry entry) throws WebloggerException {
        scheduleIndexOperation(
            new AddEntryOperation(roller, this, entry));
    }

    @Override
    public void addEntryReIndexOperation(WeblogEntry entry) throws WebloggerException {
        scheduleIndexOperation(
            new ReIndexEntryOperation(roller, this, entry));
    }

    @Override
    public void removeEntryIndexOperation(WeblogEntry entry) throws WebloggerException {
        executeIndexOperationNow(
            new RemoveEntryOperation(roller, this, entry));
    }

    @Override
    public SearchResultList search(
        String term,
        String weblogHandle,
        String category,
        String locale,
        int pageNum,
        int entryCount,
        URLStrategy urlStrategy) throws WebloggerException {

        SearchOperation search = new SearchOperation(this);
        search.setTerm(term);
        
        boolean weblogSpecific = !WebloggerRuntimeConfig.isSiteWideWeblog(weblogHandle);
        if (weblogSpecific) {
            search.setWeblogHandle(weblogHandle);
        }
        if (category != null) {
            search.setCategory(category);
        }
        if (locale != null) {
            search.setLocale(locale);
        }

        executeIndexOperationNow(search);
        
        if (search.getResultsCount() >= 0) {
            TopFieldDocs docs = search.getResults();
            ScoreDoc[] hitsArr = docs.scoreDocs;
            
            // Delegate to result converter (internal implementation)
            return resultConverter.convertHitsToEntryList(
                hitsArr,
                search.getSearcher(),
                pageNum,
                entryCount,
                weblogHandle,
                weblogSpecific,
                urlStrategy);
        }
        
        throw new WebloggerException("Error executing search");
    }

    // ==================== IndexResourceProvider Implementation ====================
    
    /**
     * Gets the read-write lock for thread-safe index operations.
     * Required by IndexResourceProvider interface.
     *
     * @return ReadWriteLock instance
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return rwl;
    }

    /**
     * Resets the shared reader, forcing it to be recreated.
     * Required by IndexResourceProvider interface.
     */
    @Override
    public synchronized void resetSharedReader() {
        readerManager.resetSharedReader();
    }

    /**
     * Gets the shared IndexReader instance.
     * Required by IndexResourceProvider interface.
     *
     * @return IndexReader instance
     */
    @Override
    public synchronized IndexReader getSharedIndexReader() {
        return readerManager.getSharedIndexReader();
    }

    /**
     * Gets the index directory.
     * Required by IndexResourceProvider interface.
     *
     * @return Directory instance
     */
    @Override
    public Directory getIndexDirectory() {
        return directoryManager.getIndexDirectory();
    }

    /**
     * Gets the analyzer for the provider interface.
     * Required by IndexResourceProvider to break cyclic dependency.
     * 
     * @return Analyzer instance
     */
    @Override
    public Analyzer getAnalyzer() {
        return AnalyzerFactory.createAnalyzer();
    }

    /**
     * Creates a Term object with the given field and value.
     * Required by IndexResourceProvider to break cyclic dependency.
     * 
     * @param field the field name
     * @param value the field value
     * @return Term object or null
     */
    @Override
    public Term getTerm(String field, String value) {
        return TermExtractor.getTerm(field, value);
    }
    
    // ==================== End IndexResourceProvider Implementation ====================

    @Override
    public boolean isInconsistentAtStartup() {
        return lifecycleManager.isInconsistentAtStartup();
    }

    /**
     * Schedules an index operation to run in the background.
     */
    private void scheduleIndexOperation(final IndexOperation op) {
        try {
            if (this.searchEnabled) {
                logger.debug("Scheduling index operation: " + op.getClass().getName());
                roller.getThreadManager().executeInBackground(op);
            }
        } catch (InterruptedException e) {
            logger.error("Error scheduling index operation", e);
        }
    }

    /**
     * Executes an index operation immediately in the foreground.
     */
    private void executeIndexOperationNow(final IndexOperation op) {
        try {
            if (this.searchEnabled) {
                logger.debug("Executing index operation now: " + op.getClass().getName());
                roller.getThreadManager().executeInForeground(op);
            }
        } catch (InterruptedException e) {
            logger.error("Error executing index operation", e);
        }
    }

    @Override
    public void release() {
        // No-op: kept for interface compatibility
    }

    @Override
    public void shutdown() {
        lifecycleManager.shutdown();
        readerManager.shutdown();
    }
}