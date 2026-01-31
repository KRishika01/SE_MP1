# 1. Search Operation:

## Overview
This class is the Lucene-based search executor used by Apache Roller to run full-text searches over blog content.

It is responsible for taking a user’s search request and returning matching weblog entries from Roller’s Lucene index.

## Role in Indexing and Search Subsystems:
- Builds a Lucene query from user input

- Applies optional filters (weblog, category, locale)

- Executes the query against the shared Lucene index

- Returns sorted search results

It is a read-only index operation (no indexing or updates).


| Attribute     | Type          | Access               | Description                                       |
| ------------- | ------------- | -------------------- | ------------------------------------------------- |
| logger        | Log           | private static       | Logger used to record search and parsing errors   |
| SEARCH_FIELDS | String[]      | private static final | Lucene fields searched (content, title, comments) |
| SORTER        | Sort          | private static final | Sorts results by published date (descending)      |
| searcher      | IndexSearcher | private              | Executes queries against the Lucene index         |
| searchresults | TopFieldDocs  | private              | Stores search results returned by Lucene          |
| term          | String        | private              | User-provided search query text                   |
| weblogHandle  | String        | private              | Restricts search to a specific weblog             |
| category      | String        | private              | Restricts search to a specific category           |
| locale        | String        | private              | Restricts search to a specific locale             |
| parseError    | String        | private              | Holds query parsing or I/O error message          |

## Methods
### Constructor:
1. `SearchOperation(IndexManager mgr)`
- **Access**: Public
- **Purpose**: Creates new search operation tied to Lucene index Manager
- **Actions**: Casts IndexManager to LuceneIndexManager (for backend access)
- Prepares the operation for execution
---
### Search Parameters:
2. `setTerm(String term)`
- **Access**: Public
- **Purpose**: Sets the search query term.
3. `setWeblogHandle(String weblogHandle)`
- **Access**: Public
- **Purpose**: Restricts the search to a specific weblog.
4. `setCategory(String category)`
- **Access**: Public
- **Purpose**: Restricts the search to a specific category.
5. `setLocale(String locale)`
- **Access**: Public
- **Purpose**: Restricts the search to a specific locale.

---
### Execution:
6. doRun()
- **Access**: Protected (overrides ReadFromIndexOperation)
- **Purpose**: Executes the search against the Lucene index.
**Actions**:

- Obtains a shared IndexReader from the index manager

- Creates a MultiFieldQueryParser with default AND logic

- Builds a Lucene query from the search term

- Adds optional filters for weblog handle, category, and locale

- Executes the query with a maximum of 500 results, sorted by published date

- Stores results in TopFieldDocs

- Captures parse or I/O errors

---
### Results Access
7. getSearcher()
- **Access**: Public
- **Purpose**: Returns the IndexSearcher used for the query.

8. setSearcher(IndexSearcher searcher)
- **Access**: Public
- **Purpose**: Sets a custom IndexSearcher (rarely needed).

9. getResults()
- **Access**: Public
- **Purpose**: Returns the TopFieldDocs containing search results.

10. getResultsCount()
- **Access**: Public
- **Purpose**: Returns the total number of hits for the query, or -1 if search hasn’t run.

11. getParseError()
- **Access**: Public
- **Purpose**: Returns any parsing or I/O errors that occurred during search execution.

---
### Subsystem Interaction:

- IndexManager → Provides the shared Lucene index reader

- Lucene APIs → Builds and executes queries (IndexSearcher, QueryParser, BooleanQuery)

- Domain Objects → Filters results by Weblog, Category, and Locale

- Logging → Captures errors during query execution
---

# FieldConstants
- Access: Public Final
- Purpose: Defines constant field names used in the Lucene index for weblog entries and comments.

| Constant       | Type   | Access              | Description                                                       |
| -------------- | ------ | ------------------- | ----------------------------------------------------------------- |
| ANCHOR         | String | public static final | Anchor text or fragment identifier for a weblog entry             |
| UPDATED        | String | public static final | Timestamp of the last update of an entry or comment               |
| ID             | String | public static final | Unique identifier for an indexed object                           |
| USERNAME       | String | public static final | Author username of the entry or comment                           |
| CATEGORY       | String | public static final | Category of the weblog entry                                      |
| TITLE          | String | public static final | Title of the weblog entry                                         |
| PUBLISHED      | String | public static final | Published date of the weblog entry (used for sorting)             |
| CONTENT        | String | public static final | Main content of the weblog entry                                  |
| CONTENT_STORED | String | public static final | Stored content for retrieval without analysis                     |
| C_CONTENT      | String | public static final | Comment content                                                   |
| C_EMAIL        | String | public static final | Email of the commenter                                            |
| C_NAME         | String | public static final | Name of the commenter                                             |
| CONSTANT       | String | public static final | A constant field used for indexing (purpose-specific)             |
| CONSTANT_V     | String | public static final | Value of the constant; must follow analyzer rules or be lowercase |
| WEBSITE_HANDLE | String | public static final | Unique handle for the weblog (used for filtering)                 |
| LOCALE         | String | public static final | Locale of the entry or comment                                    |

These constants are used throughout Roller’s Lucene indexing and search classes.

They provide a consistent schema for storing and querying blog entries, comments, categories, and metadata.


# IndexUtil:
- Access: public final
- Purpose: Provides helper methods for working with lucene indexes in roller

## Role
- Acts as a utility for converting raw input strings into Lucene Term objects that can be used for filtering or querying.

- Ensures that input strings are properly tokenized using Roller’s configured Lucene Analyzer before being turned into terms.

- Helps maintain consistency between search queries and the way content is indexed, especially for filtering by weblog handle, category, or locale.

- Centralized place for any small index-related utilities, keeping search and indexing code clean.

## Method:
| Method                                | Parameters                                                                  | Return | Access        | Description                                                                                                                                                                            |
| ------------------------------------- | --------------------------------------------------------------------------- | ------ | ------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `getTerm(String field, String input)` | `field`: Lucene document field name<br>`input`: text to convert into a term | `Term` | public static | Creates a Lucene `Term` from the first token of the input string. Uses Roller’s configured `Analyzer` to tokenize and normalize the input. Returns `null` if input or field is `null`. |

# SearchResultsModel:
- Extends: `PageModel`
- Access: Public
- Purpose: Represents search results in the Roller UI rendering system
## Role:
- Wraps the results of a Lucene search for display in the web UI.

- Handles mapping results by day, pagination, and category filtering.

- Provides helper methods to access search terms, counts, and errors.

- Works with SearchResultsPager to integrate search results into Roller’s page rendering system.

| Attribute        | Type                               | Access              | Description                                     |
| ---------------- | ---------------------------------- | ------------------- | ----------------------------------------------- |
| RESULTS_PER_PAGE | int                                | public static final | Number of results displayed per page (10)       |
| searchRequest    | WeblogSearchRequest                | package-private     | Original search request object                  |
| urlStrategy      | URLStrategy                        | private             | Handles URL creation for entries and categories |
| results          | Map<Date, Set<WeblogEntryWrapper>> | private             | Search results mapped by day (midnight)         |
| pager            | SearchResultsPager                 | private             | Pager for navigation of search results          |
| hits             | int                                | private             | Total number of hits returned by search         |
| offset           | int                                | private             | Current offset in the search result set         |
| limit            | int                                | private             | Maximum number of results per page              |
| categories       | Set<String>                        | private             | Categories present in the search results        |
| errorMessage     | String                             | private             | Any error message encountered during search     |

## Methods:
| Method                                                                                   | Parameters         | Return                            | Access  | Description                                                                                          |
| ---------------------------------------------------------------------------------------- | ------------------ | --------------------------------- | ------- | ---------------------------------------------------------------------------------------------------- |
| `init(Map<String,Object> initData)`                                                      | `initData`         | void                              | public  | Initializes the model, performs search if query is present, maps results by date, and sets up pager. |
| `addEntryToResults(Map<Date,Set<WeblogEntryWrapper>> results, WeblogEntryWrapper entry)` | `results`, `entry` | void                              | private | Adds a single entry to the results map, grouping by day and avoiding duplicates using a `Set`.       |
| `isSearchResults()`                                                                      | -                  | boolean                           | public  | Returns `true` to indicate this model represents search results.                                     |
| `getWeblogEntriesPager()`                                                                | -                  | WeblogEntriesPager                | public  | Returns the pager for search results.                                                                |
| `getWeblogEntriesPager(String category)`                                                 | `category`         | WeblogEntriesPager                | public  | Returns the pager for a specific category (always returns search pager).                             |
| `getTerm()`                                                                              | -                  | String                            | public  | Returns the search query term, XML-escaped for safe display.                                         |
| `getRawTerm()`                                                                           | -                  | String                            | public  | Returns the raw search query term (not escaped).                                                     |
| `getHits()`                                                                              | -                  | int                               | public  | Returns total number of search hits.                                                                 |
| `getOffset()`                                                                            | -                  | int                               | public  | Returns current offset in results.                                                                   |
| `getLimit()`                                                                             | -                  | int                               | public  | Returns maximum results per page.                                                                    |
| `getResults()`                                                                           | -                  | Map<Date,Set<WeblogEntryWrapper>> | public  | Returns the results mapped by date.                                                                  |
| `getCategories()`                                                                        | -                  | Set<String>                       | public  | Returns categories present in the results.                                                           |
| `getErrorMessage()`                                                                      | -                  | String                            | public  | Returns any error message from the search.                                                           |
| `getWeblogCategoryName()`                                                                | -                  | String                            | public  | Returns the weblog category name used in the search request.                                         |
| `getWeblogCategory()`                                                                    | -                  | WeblogCategoryWrapper             | public  | Returns a wrapped `WeblogCategory` object if present in the search request.                          |

    
    
## Subsystem Interaction:
    
IndexManager → Executes the search and retrieves results.

WeblogEntryWrapper → Wraps entries for display, sorted by publication time.

SearchResultsPager → Handles pagination and navigation of results.

URLStrategy → Generates URLs for entries, categories, and search navigation.

DateUtil → Normalizes dates to midnight for grouping results.

Error Handling → Captures exceptions from search operations and exposes messages via getErrorMessage().

Behavior:

Maps search results by publication date (descending).

Filters out entries not yet published.

Supports both paginated search results and category-specific filtering.
    
# SearchResultsPager:
Implements WeblogEntriesPager
    Access: Public
    Purpose: Provides pagination and navigation for weblog search results.
## Role:
- Wraps search results and exposes them to the rendering system with next/previous navigation links.

- Integrates with URLStrategy to generate URLs for entries, next/previous pages, and home links.

- Handles i18n messages for UI text such as “next”, “previous”, and “home”.

- Works with SearchResultsModel to paginate search results grouped by day (date).

    | Attribute    | Type                               | Access        | Description                                                    |
| ------------ | ---------------------------------- | ------------- | -------------------------------------------------------------- |
| messageUtils | I18nMessages                       | final         | Utility for internationalized messages (next, previous, home)  |
| urlStrategy  | URLStrategy                        | final         | Used to generate URLs for navigation and entries               |
| entries      | Map<Date, Set<WeblogEntryWrapper>> | private final | Search results grouped by date                                 |
| weblog       | Weblog                             | private final | Weblog associated with the search results                      |
| locale       | String                             | private final | Locale for the search and navigation links                     |
| query        | String                             | private final | Search query term                                              |
| category     | String                             | private final | Category filter applied to the search                          |
| page         | int                                | private final | Current page number in the search results                      |
| moreResults  | boolean                            | private final | Indicates if there are more results available beyond this page |

    | Method                                                                                                                              | Parameters                                  | Return                             | Access | Description                                                                                    |
| ----------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- | ---------------------------------- | ------ | ---------------------------------------------------------------------------------------------- |
| `SearchResultsPager(URLStrategy strat, WeblogSearchRequest searchRequest, Map<Date,Set<WeblogEntryWrapper>> entries, boolean more)` | `strat`, `searchRequest`, `entries`, `more` | -                                  | public | Constructor that initializes pager with search results, request info, URLs, and i18n messages. |
| `getEntries()`                                                                                                                      | -                                           | Map<Date, Set<WeblogEntryWrapper>> | public | Returns the search results grouped by date.                                                    |
| `getHomeLink()`                                                                                                                     | -                                           | String                             | public | Returns the weblog home URL.                                                                   |
| `getHomeName()`                                                                                                                     | -                                           | String                             | public | Returns the localized label for “home”.                                                        |
| `getNextLink()`                                                                                                                     | -                                           | String                             | public | Returns the URL for the next page of search results, if any.                                   |
| `getNextName()`                                                                                                                     | -                                           | String                             | public | Returns the localized label for “next”.                                                        |
| `getPrevLink()`                                                                                                                     | -                                           | String                             | public | Returns the URL for the previous page of search results, if any.                               |
| `getPrevName()`                                                                                                                     | -                                           | String                             | public | Returns the localized label for “previous”.                                                    |
| `getNextCollectionLink()`                                                                                                           | -                                           | String                             | public | Returns null (not used in search results).                                                     |
| `getNextCollectionName()`                                                                                                           | -                                           | String                             | public | Returns null (not used in search results).                                                     |
| `getPrevCollectionLink()`                                                                                                           | -                                           | String                             | public | Returns null (not used in search results).                                                     |
| `getPrevCollectionName()`                                                                                                           | -                                           | String                             | public | Returns null (not used in search results).                                                     |

Subsystem Interaction

- URLStrategy → Builds URLs for home, next/previous search pages.

- I18nMessages → Provides localized strings for pager navigation links.

- WeblogEntryWrapper → Wraps weblog entries displayed on a page.

- Map<Date, Set<WeblogEntryWrapper>> → Groups entries by day (used by SearchResultsModel).

- Handles search result pagination logic while integrating with Roller’s UI rendering framework.****


# SearchResultsFeedPager:
Extends: AbstractPager<WeblogEntryWrapper>
Access: Public
Purpose: Provides pagination and URL generation specifically for search result feeds (Atom/RSS).

## Role:
Wraps search result entries for feed output.

Generates next/previous URLs, home link, and feeds links with category, term, or tag filters.

Integrates with URLStrategy and URLUtilities to ensure proper URL encoding.

Handles i18n messages for feed navigation.

Works closely with SearchResultsFeedModel to paginate search result feeds.

## Attributes:
| Attribute    | Type                     | Access        | Description                                                         |
| ------------ | ------------------------ | ------------- | ------------------------------------------------------------------- |
| messageUtils | I18nMessages             | private       | Utility for localized messages (home, etc.)                         |
| entries      | List<WeblogEntryWrapper> | private final | Search result entries for the feed                                  |
| weblog       | Weblog                   | private final | Weblog associated with the feed search                              |
| moreResults  | boolean                  | private final | Indicates if there are more results beyond this page                |
| feedRequest  | WeblogFeedRequest        | private final | Original feed request containing term, category, tags, locale, etc. |
| url          | String                   | private final | Base URL used for constructing pager links                          |

## Methods:
| Method                                                                                                                                                  | Parameters                                          | Return                   | Access    | Description                                                                                                    |
| ------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------- | ------------------------ | --------- | -------------------------------------------------------------------------------------------------------------- |
| `SearchResultsFeedPager(URLStrategy strat, String baseUrl, int pageNum, WeblogFeedRequest feedRequest, List<WeblogEntryWrapper> entries, boolean more)` | strat, baseUrl, pageNum, feedRequest, entries, more | -                        | public    | Constructor initializes pager with feed entries, request info, base URL, i18n messages, and more-results flag. |
| `getItems()`                                                                                                                                            | -                                                   | List<WeblogEntryWrapper> | public    | Returns the list of entries in the feed page.                                                                  |
| `hasMoreItems()`                                                                                                                                        | -                                                   | boolean                  | public    | Returns true if there are more items beyond the current feed page.                                             |
| `getHomeLink()`                                                                                                                                         | -                                                   | String                   | public    | Returns the URL to the weblog home page.                                                                       |
| `getHomeName()`                                                                                                                                         | -                                                   | String                   | public    | Returns localized string for “home” link.                                                                      |
| `createURL(String url, Map<String,String> params)`                                                                                                      | url, params                                         | String                   | protected | Builds a URL for the feed page including category, term, tags, and excerpts parameters.                        |
| `getUrl()`                                                                                                                                              | -                                                   | String                   | public    | Returns the fully constructed URL for this feed page.                                                          |

## Subsystem Interaction

- URLStrategy → Generates base URLs for weblog and feed.

- URLUtilities → Encodes query parameters (term, category, tags).

- I18nMessages → Localized messages for feed navigation (“home”).

- WeblogEntryWrapper → Feed entries returned by search.

- WeblogFeedRequest → Holds feed-specific search info (term, category, tags, excerpts).

- Supports feed-specific pagination logic with proper URL encoding for search parameters.

# OpenSearchServlet:
Extends HttpServlet
    Access: Public
    Purpose: Generates an OpenSearch descriptor XML for Roller’s search functionality.

Role in Roller:

- Exposes Roller weblogs’ search functionality in a standard OpenSearch format, which allows external clients or browsers to integrate Roller search.

- Supports site-wide search or specific weblog search based on URL.

- Returns search feed (Atom) and search page (HTML) URLs in the descriptor.

- Handles site-wide or weblog-specific metadata like name, description, and contact.
## Attributes:
    This servlet does not define additional instance fields. It relies on local variables and injected services from WebloggerFactory and WebloggerRuntimeConfig
## Methods:
    | Method                                                            | Parameters        | Return | Access | Description                                                                                                                                                                        |
| ----------------------------------------------------------------- | ----------------- | ------ | ------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `doGet(HttpServletRequest request, HttpServletResponse response)` | request, response | void   | public | Handles HTTP GET requests. Generates and writes OpenSearch descriptor XML to the response. Determines weblog context, retrieves URLs, site or weblog metadata, and writes the XML. |
Subsystem Interaction

- WebloggerFactory → access to WeblogManager and URLStrategy.

- WebloggerRuntimeConfig → site-wide metadata and frontpage weblog handle.

- Utilities.stringToStringArray → splits URL path segments.

- escapeXml11 → ensures XML-safe output.

- HttpServletResponse / HttpServletRequest → standard servlet request/response.

# SearchServlet:

Extends HttpServlet
    Access: Public
    Purpose: Handles search queries for weblogs and renders search result pages.

## Role:

- Parses incoming HTTP requests to validate and construct WeblogSearchRequest.

- Manages theme reloading in development mode.

- Determines locale and selects the appropriate theme template for rendering search results.

- Loads models for the search page using ModelLoader.

- Uses Renderer to generate the HTML output for search results.

- Supports both site-wide weblogs and specific weblog searches.

| Attribute          | Type    | Access               | Description                                                    |
| ------------------ | ------- | -------------------- | -------------------------------------------------------------- |
| `themeReload`      | Boolean | private              | Indicates whether to reload weblog themes in development mode. |
| `log`              | Log     | private static final | Logger instance for logging servlet operations.                |
| `serialVersionUID` | long    | private static final | Serializable UID for servlet class versioning.                 |


| Method                                                            | Parameters        | Return | Access | Description                                                                                                                                                                                                                                            |
| ----------------------------------------------------------------- | ----------------- | ------ | ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `init(ServletConfig servletConfig)`                               | servletConfig     | void   | public | Initializes the servlet. Sets `themeReload` flag and logs initialization.                                                                                                                                                                              |
| `doGet(HttpServletRequest request, HttpServletResponse response)` | request, response | void   | public | Handles **HTTP GET** requests for search. Steps include: parsing `WeblogSearchRequest`, validating weblog, reloading theme (dev mode), setting locale, loading rendering model, selecting `Renderer`, generating output, and writing to HTTP response. |


    
Subsystem Interaction

- WebloggerFactory → Access to ThemeManager, URLStrategy.

- WebloggerRuntimeConfig → Determines site-wide weblogs and default settings.

- WebloggerConfig → Retrieves servlet configuration properties like searchModels.

- ThemeManager → Reloads weblog themes from disk in development mode.

- ModelLoader → Loads search page models for rendering.

- RendererManager / Renderer → Renders HTML content from models and templates.

- Caching → Uses SiteWideCache, WeblogPageCache, and CachedContent.

- Utilities → Misc helper methods, logging, and constants.

# WeblogSearchRequest:
Extends WeblogRequest
Access: Public
Purpose: Represents a search request for a weblog. Encapsulates query parameters, category filtering, and pagination for weblog search pages.

## Role:

Parses HTTP request parameters to extract search query (q), page number (page), and category (cat).

Provides accessors for search-specific attributes.

Lazy-loads the WeblogCategory object if the category name is specified.

Serves as a data model for SearchServlet, SearchResultsModel, and related rendering components.
## Attributes:
    | Attribute            | Type           | Access              | Description                                                                |
| -------------------- | -------------- | ------------------- | -------------------------------------------------------------------------- |
| `log`                | Log            | private static      | Logger instance for the class.                                             |
| `SEARCH_SERVLET`     | String         | public static final | Expected servlet path: `/roller-ui/rendering/search`.                      |
| `query`              | String         | private             | Search query string (from parameter `q`).                                  |
| `pageNum`            | int            | private             | Pagination index (from parameter `page`). Defaults to 0.                   |
| `weblogCategoryName` | String         | private             | Category filter (from parameter `cat`).                                    |
| `weblogCategory`     | WeblogCategory | private             | Lazy-loaded `WeblogCategory` object corresponding to `weblogCategoryName`. |

## Methods:
    | Method                                             | Parameters     | Return         | Access | Description                                                                                                                                                               |
| -------------------------------------------------- | -------------- | -------------- | ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `WeblogSearchRequest()`                            | —              | —              | public | Default constructor.                                                                                                                                                      |
| `WeblogSearchRequest(HttpServletRequest request)`  | request        | —              | public | Constructs a search request from an HTTP request. Parses parameters `q`, `page`, `cat` and validates servlet path. Throws `InvalidRequestException` for invalid requests. |
| `getQuery()`                                       | —              | String         | public | Returns the search query string.                                                                                                                                          |
| `setQuery(String query)`                           | query          | void           | public | Sets the search query string.                                                                                                                                             |
| `getPageNum()`                                     | —              | int            | public | Returns the current page number.                                                                                                                                          |
| `setPageNum(int pageNum)`                          | pageNum        | void           | public | Sets the page number.                                                                                                                                                     |
| `getWeblogCategoryName()`                          | —              | String         | public | Returns the category name filter.                                                                                                                                         |
| `setWeblogCategoryName(String weblogCategory)`     | weblogCategory | void           | public | Sets the category name filter.                                                                                                                                            |
| `getWeblogCategory()`                              | —              | WeblogCategory | public | Lazy-loads and returns the `WeblogCategory` object corresponding to `weblogCategoryName`. Caches result.                                                                  |
| `setWeblogCategory(WeblogCategory weblogCategory)` | weblogCategory | void           | public | Sets the `WeblogCategory` object.                                                                                                                                         |
