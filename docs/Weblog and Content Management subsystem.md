# Rishika

**Weblog and Content Management subsystem**

1. **Weblog  (I**dentity & Metadata,Configuration & Settings, Localization & Theme, Associations (Composition / Aggregation) **)**  
   **Attributes**  
   \- id : String   
   \- handle : String   
   \- name : String   
   \- tagline : String   
   \- about : String   
   \- creator : String   
   \- dateCreated : Date   
   \- lastModified : Date  
   \- enableBloggerApi : Boolean  
   \- allowComments : Boolean  
   \- emailComments : Boolean  
   \- moderateComments : Boolean  
   \- defaultAllowComments : Boolean  
   \- defaultCommentDays : int  
   \- entryDisplayCount : int  
   \- visible : Boolean  
   \- active : Boolean  
   \- enableMultiLang : boolean  
   \- showAllLangs : boolean  
   \- analyticsCode : String  
   \- locale : String  
   \- timeZone : String  
   \- editorTheme : String  
   \- editorPage : String  
   \- iconPath : String  
   \- bloggerCategory : WeblogCategory  
   \- weblogCategories : List\<WeblogCategory\>  
   \- bookmarkFolders : List\<WeblogBookmarkFolder\>  
   \- mediaFileDirectories : List\<MediaFileDirectory\>  
   \- initializedPlugins : Map\<String, WeblogEntryPlugin\>  
     
   **Methods**  
   \+ getTheme() : weblogTheme  
   \+ getId() : String  
   \+ setId(String id)  
   \+ getHandle() : String  
   \+ setHandle(String handle)  
   \+ getName() : String  
   \+ setName(String Name)  
   \+ getTagline() : String  
   \+ setTagline(string tagline)  
   \+ getCreator() : User  
   \+ getCreatorUserName() : String  
   \+ setCreatorUserName(String creatorUserName)  
   \+ getEnableBloggerApi() : Boolean  
   \+ setEnableBloggerApi(Boolean enableBloggerApi)  
   \+ getBloggerCategory() : WeblogCategory  
   \+ setBloggerCategory(WeblogCategory bloggerCategory)  
   \+ getEditorPage() : String  
   \+ setEditorPage(String editorPage)  
   \+ getBannedwordslits() : String  
   \+ setBannedwordslist(String bannedwordslist)  
   \+ getAllowComments() : Boolean  
   \+ setAllowComments(Boolean allowComments)  
   \+ getDefaultAllowComments() : Boolean  
   \+ setDefaultAllowComments(Boolean defaultAllowComments)  
   \+ getDefaultCommentDays() : int  
   \+ setDefaultCommentDays(int defaultCommentDays)  
   \+ getModerateComments() : Boolean  
   \+ setModerateComments(Boolean moderateComments)  
   \+ getEmailComments() : Boolean  
   \+ setEmailComments(Boolean emailComments)  
   \+ getEmailAddress() : String  
   \+ setEmailAddress(String emailAddress)  
   \+ getEditorTheme() : String  
   \+ setEditorTheme(String editorTheme)  
   \+ getLocale() : String  
   \+ setLocale(String locale)  
   \+ getTimeZone() : String  
   \+ setTimeZone(String timeZone)  
   \+ getDateCreated() : Date  
   \+ setDateCreated(final Date date)  
   \+ getDefaultPlugins : String  
   \+ setDefaultPlugins(String string)  
   \+ setData(Weblog other)  
   \+ getLocaleInstance() : Locale  
   \+ getTimeZoneInstance() : TimeZone  
   \+ hasUserPermission(User user, String action) : boolean   
   \+ hasUserPermissions(User user, List\<String\> actions) : boolean  
   \+ getEntryDisplayCount() : int  
   \+ setEntryDisplayCount(int entryDisplayCount)  
   \+ getVisible() : Boolean  
   \+ setVisible(Boolean visible)  
   \+ getActive() : Boolean  
   \+ setActive(Boolean Active)  
   \+ getCommentModerationRequired() : boolean  
   \+ setCommentModerationRequired(boolean modRequired)  
   \+ getLastModified() : Date  
   \+ setLastModified(Date lastModified)  
   \+ isEnableMultiLang() : boolean  
   \+ setEnableMultiLang(boolean enableMultiLang)  
   \+ isShowAllLangs() : boolean  
   \+ setShowAllLangs(boolean showAllLangs)  
   \+ getURL() : String  
   \+ getAbsoluteURL() : String  
   \+ getIconPath() : String  
   \+ setIconPath(String iconPath)  
   \+ getAnalyticsCode() : String  
   \+ setAnalyticsCode(String analyticsCode)  
   \+ getAbout() : String  
   \+ setAbout(String about)  
   \+ getInitializedPlugins() : Map\<String, WeblogEntryPlugin\>  
   \+ getWeblogEntry(String anchor) : WeblogEntry  
   \+ getWeblogCategory(String categoryName) : weblogCategory  
   \+ getRecentWeblogEntries(String cat, int length) : List\<WeblogEntry\>  
   \+ getRecentWeblogEntriesByTag(String tag, int length) : List\<WeblogEntry\>  
   \+ getRecentComments(int length) : List\<WeblogEntryComment\>  
   \+ getBookmarkFolder(String folderName) : weblogBookmarkFolder  
   \+ getTodaysHits() : int  
   \+ getPopularTags(int sinceDays, int length) : List\<TagStat\>  
   \+ getCommentCount() : long  
   \+ getEntryCount() : long  
   \+ addCategory(weblogCategory category)  
   \+ getWeblogCategories() : List\<WeblogCategory\>  
   \+ setWeblogCategories(List\<WeblogCategory\> cats)  
   \+ hasCategory(String name) : boolean  
   \+ getBookmarkFolders() : List\<WeblogBookmarkFolder\>

	\+ setBookmarkFolders(List\<WeblogBookmarkFolder\> bookmarkFolders)  
	\+ getMediaFileDirectories :List\<MediaFileDirectory\>  
	\+ setMediaFileDirectories(List\<MediaFileDirectory\> mediaFileDirectories)  
	\+ addBookmarkFolder(WeblogBookmarkFolder folder)  
	\+ hasBookmarkFolder(String name) : boolean  
	\+ hasMediaFileDirectory(String name) : boolean  
	\+ getMediaFileDirectory(String name) : MediaFileDirectory

	**Role**

* The Weblog class represents a blog/website entity in Apache Roller.  
* It is the central domain model that holds configuration, metadata, and  
  associations for a single blog.  
* This class acts as the **root aggregate** for all blog-related content and is frequently used by service-layer managers during rendering, content fetching, and permission checking.  
    
  **Interaction with Other Classes     (need to confirm)**  
  1\. WeblogEntry  \- one to many  
  2\. WeblogCategory \- One to many  
  3\. WeblogEntryManager  \- One-to-Many  
  4\. **ThemeManager**  
  **5\. PluginManager**  
  6\. UserManager  
  7\. WeblogBookmarkFolder \- One-to-Many (Weblog owns bookmark folders)  
  8\. MediaFileDirectory \- One-to-Many (Weblog owns media directories)  
  9\. User \- Many-to-One (Many weblogs can have same creator)  
  10\. WeblogPermission \- One-to-Many (Weblog has many permission assignments)   
  11\. WeblogTheme \- Many-to-One (Many weblogs can use same theme)   
  12\. WeblogEntryComment  (**INDIRECT**)  
  13\. WeblogEntryTag \- One-to-Many (Weblog owns tags)  
  14\. WeblogEntryPlugin

2. **User**  
   **Attributes**

	\- id : String  
\- userName : String  
\- password : String  
\- openIdUrl : String  
\- screenName : String  
\- fullName : String  
\- emailAddress : String  
\- dateCreated : Date  
\- locale : String  
\- timeZone : String  
\- enabled : Boolean  
\- activationCode : String

**Methods**  
\+ getId() : String  
\+ setId(id : String) : void

\+ getUserName() : String  
\+ setUserName(userName : String) : void  
\+ getPassword() : String  
\+ setPassword(password : String) : void  
\+ resetPassword(newPassword : String) : void  
\+ getOpenIdUrl() : String  
\+ setOpenIdUrl(openIdUrl : String) : void  
\+ getScreenName() : String  
\+ setScreenName(screenName : String) : void  
\+ getFullName() : String  
\+ setFullName(fullName : String) : void  
\+ getEmailAddress() : String  
\+ setEmailAddress(email : String) : void  
\+ getDateCreated() : Date  
\+ setDateCreated(final Date date)  
\+ getLocale() : String  
\+ setLocale(locale : String) : void  
\+ getTimeZone() : String  
\+ setTimeZone(timeZone : String) : void  
\+ getEnabled() : Boolean  
\+ setEnabled(enabled : Boolean) : void  
\+ getActivationCode() : String  
\+ setActivationCode(code : String) : void  
\+ hasGlobalPermission(action : String) : boolean  
\+ hasGlobalPermissions(actions : List\<String\>) : boolean

**Role**

* User is a domain entity representing a registered user of the blogging system.  
    
  **Interaction with other parts of subsystem**  
  1\. UserManager \- Uses  
  2\. Weblog \- One to Many  
  3\. **WeblogPermission** \- Many to Many (User has permissions on multiple weblogs)  
  4\. GlobalPermission \-   
  5\. WeblogEntry \- One-to-Many (User creates multiple entries)  
    
    
    
    
    
  




3. **WeblogTheme (**Inherited from Theme **Interface)**  
   **Attributes**  
   \# weblog : Weblog  
     
   **Methods**  
   \+ getWeblog() : Weblog  
     
   **Role**  
* WeblogTheme represents a theme bound to a specific weblog and is a core abstraction used during rendering.  
* This class is central to the rendering pipeline and is used whenever weblog content is transformed into HTML output.

	**Interaction with Other classes**  
	1\. Weblog \- One to One  
	2\. Theme (**Interface**)

4. **WeblogTemplate (**Inherited from ThemeTemplate **Interface)**  
   **Attributes**  
   \- id : String  
   \- action : ComponentType  
   \- name : String  
   \- description : String  
   \- link : String  
   \- lastModified : Date  
   \- hidden : boolean  
   \- navbar : boolean  
   \- outputContentType : String  
   \- weblog : Weblog  
   \- templateRenditions : List\<CustomTemplateRendition\>  
     
   **Methods**  
   \+ getId() : String  
   \+ setId(String id)  
   \+ getWeblog() : Weblog  
   \+ setWeblog(Weblog website)  
   \+ getAction() : ComponentType  
   \+ setAction(ComponentType action)  
   \+ getName() : String  
   \+ setName(String name)  
   \+ getDescription() : String  
   \+ setDescription(String description)  
   \+ getLink() : Strong  
   \+ setLink(STring link)  
   \+ getLastModified() : Date  
   \+ setLastModified(final Date newtime)  
   \+ isNavbar() : boolean  
   \+ setNavbar(boolean navbar)  
   \+ isHiddent() : boolean  
   \+ setHidden(boolean isHidden)  
   \+ getOutputContentType() : String  
   \+ setOutputContentType(String outputContentType)  
   \+ isRequired() : boolean  
   \+ isCustom() : boolean  
   \+ getTemplateRenditions() : List\<CustomTemplateRendition\>  
   \+ setTemplateRenditions(List\<CustomTemplateRendition\> templateRenditions)  
   \+ getTemplateRendition(CustomTemplateRendition.RenditionType desiredType) : CustomTemplateRendition  
   \+ addTemplateRendition(CustomTemplateRendition newRendition)  
   \+ hasTemplateRendition(customTemplateRendition proposed) : boolean  
     
   **Role**  
* WeblogTemplate represents a single user-defined template page belonging to a specific weblog.  
* It defines how weblog content is rendered into output (HTML, RSS, etc.).  
    
  **Interaction with other parts of the subsystem**  
  1\. Weblog \- (One weblog can have many weblogTemplate objects and Each weblogTemplate belongs to exactly one weblog) (**WHICH RELATION**)  
  2\. CustomTemplateRendition  
  3\. ThemeTemplate (**Interface**)  
    
5. **WeblogBookmark**

	**Attributes**  
	\- id : String  
\- name : String  
\- description : String  
\- url : String  
\- priority : Integer  
\- image : String  
\- feedUrl : String  
\- folder : WeblogBookmarkFolder

**Methods**  
	\+ getId() : String  
\+ setId(id : String)  
\+ getName() : String  
\+ setName(name : String)  
\+ getDescription() : String  
\+ setDescription(description : String)  
\+ getUrl() : String  
\+ setUrl(url : String)  
\+ getPriority() : Integer  
\+ setPriority(priority : Integer)  
\+ getImage() : String  
\+ setImage(image : String)  
\+ getFeedUrl() : String  
\+ setFeedUrl(feedUrl : String)  
\+ calculatePriority() : void  
\+ getFolder() : WeblogBookmarkFolder  
\+ setFolder(folder : WeblogBookmarkFolder)  
\+ getWebsite() : Weblog  
\+ compareTo(o : WeblogBookmark) : int

**Role**

* WeblogBookmark models a single bookmark (favorite link) stored in a weblog’s bookmark collection.  
* It's used to manage and display links to other websites, blogs, or resources that a blog owner wants to recommend or showcase. 

	**Interaction with other parts of the subsystem**  
	1\. WeblogBookmarkFolder \- Many to one (Many books belong to one folder)  
	2\. Weblog \- Many to one (**INDIRECT**)  
	3\. BookmarkManager (**INTERFACE**)

6. **WeblogBookmarkFolder**  
   **Attributes**  
   \- id : String  
   \- name : String  
   \- weblog : Weblog  
   \- bookmarks : List\<WeblogBookmark\>  
     
   **Methods**  
   \+ getId() : String  
   \+ setId(String id)  
   \+ getName() : String  
   \+ setName(String name)  
   \+ getWeblog() : Weblog  
   \+ setWeblog(Weblog website)  
   \+ getBookmarks() : List\<WeblogBookmark\>  
   \+ setBookmarks(List\<WeblogBookmark\> bookmarks)  
   \+ addBookmark(WeblogBookmark bookmark)  
   \+ hasBookmarkOfName(String bookmarkName) : boolean  
   \+ retrieveBookmarks() : List\<WeblogBookmark\>  
   \+ compareTo(WeblogBookmarkFolder other) : int  
     
   **Role**  
* WeblogBookmarkFolder represents a folder of bookmarks inside a Weblog in Apache Roller.  
* It acts as a categorization mechanism for grouping related links together.

	**Interaction with other parts of the subsystem**  
	1\. WeblogBookmark \- One folder has many bookmarks and each bookmark belongs to one folder.  
	2\. Weblog \- One weblog can have many BookmarkFolders and Each BookmarkFolder belongs to exactly one weblog.  
	3\. BookmarkManager (**INTERFACE**)**.**

7. **WeblogEntry**

	**Attributes (**FOCUS ON THESE ATTRIBUTES ONLY \- GPT**)**  
	\- id : String  
	\- title : String  
	\- link : String  
	\- summary : String  
	\- text : String  
	\- contentType : String  
\- contentSrc : String  
\-  anchor : String  
\- pubTime : Timestamp  
\- updateTime : Timestamp  
\- plugins : String  
\- allowComments : Boolean  
\- commentDays : Integer  
\- rightToLeft : Boolean  
\- pinnedToMain : Boolean  
\- status : PubStatus  
\- locale : String  
\- creatorUserName : String  
\- searchDescription : String  
\- refreshAggregates : Boolean  
\- website : Weblog  
\- category : WeblogCategory  
\- attest : Set\<WeblogEntryAttribute\>  
\- tagSet : Set\<WeblogEntryTag\>  
\- removedTags : Set\<WeblogEntryTag\>  
\- addedTags : Set\<WeblogEntryTag\>

**Methods**  
\+ setData(WeblogEntry other)  
\+ getId() : String  
\+ setId(String id)  
\+ getCategory() : WeblogCategory  
\+ setCategory(WeblogCategory cat)  
\+ getCategories() : List\<WeblogCategory\>  
\+ getWebsite() : Weblog  
\+ setWebsite(Weblog website)  
\+ getCreator() : User  
\+ getCreatorUserName() : String  
\+ setCreatorUserName(String creatorUserName)  
\+ getTitle() : String  
\+ setTitle(String title)  
\+ getSummary() : String  
\+ setSummary(String summary)  
\+ getSearchDescription() : String  
\+ setSearchDescription(String searchDescription)  
\+ getText() : String  
\+ setText(String text)  
\+ getContentType() : String  
\+ setContentType(String contentType)  
\+ getContentSrc() : String  
\+ setContentSrc(String contentSrc)  
\+ getAnchor() : String  
\+ setAnchor(String anchor)  
\+ getEntryAttributes() : Set\<WeblogEntryAttribute\>  
\+ setEntryAttributes(Set\<WeblogEntryAttribute\> atts)  
\+ findEntryAttribute(String name) : String  
\+ putEntryAttribute(String name, String value)  
\+ getPubTime() : Timestamp  
\+ setPubTime(Timestamp pubTime)  
\+ getUpdateTime() : Timestamp  
	\+ setUpdateTime(Timestamp updateTime)  
	\+ getStatus() : PubStatus  
	\+ setStatus(PubStatus status)  
	\+ getLink() : String  
	\+ setLink(String link)  
	\+ getPlugins() : String  
	\+ setPlugins(String string)  
	\+ getAllowComments() : Boolean  
	\+ setAllowComments(Boolean allowComments)  
	\+ getCommentDays() : Integer  
	\+ setCommentDays(Integer commentDays)  
	\+ getRightToLeft() : Boolean  
	\+ setRightToLeft(Boolean rightToLeft)  
	\+ getPinnedToMain() : Boolean  
	\+ setPinnedToMain(Boolean pinnedToMain)  
	\+ getLocale() : String  
	\+ setLocale(String locale)  
	\+ getTags() : Set\<WeblogEntryTag\>  
	\+ setTags(Set\<WeblogEntryTag\> tagSet)  
	\+ addTag(String name)  
	\+ getAddedTags() : Set\<WeblogEntryTag\>  
	\+ getRemovedTags() : Set\<WeblogEntryTag\>  
	\+ getTagsAsString() : String  
	\+ setTagsAsString(String tags)  
	\+ getCommentsStillAllowed() : boolean  
	\+ setCommentsStillAllowed(boolean ignored)  
	\+ formatPubTime(String pattern) : String  
	\+ formatUpdateTime(String pattern) : String  
	\+ getComments() : List\<WeblogEntryComment\>  
	\+ getComments(boolean ignoreSpam, boolean approvedOnly) : List\<WeblogEntryComment\>  
	\+ getCommentCount() : int  
	\+ getPermalink() : String  
	\+ getPermaLink() : String  
	\+ getCommentsLink() : String  
	\+ getDisplayTitle() : String  
\+ createAnchor() : String  
\+ createAnchorBase() : String  
\+ setPermalink(String string)  
\+ setPermaLink(String string)  
\+ setDisplayTitle(String string)  
\+ getPluginsList() : List\<String\>  
\+ isDraft() : boolean  
\+ isPending() : boolean  
\+ isPublished() : boolean  
\+ getTransformedText() : String  
\+ getTransformedSummary() : String  
\+ hasWritePermissions(User user) : boolean  
\+ render(String str) : String  
\+ displayContent(String readMoreLink) : String  
\+ getDisplayContent() : String  
\+ getRefreshAggregates() : Boolean  
\+ setRefreshAggregates(Boolean refreshAggregates)

**Role**

* WeblogEntry represents a single blog post in Apache Roller.  
    
  **Interaction with other classes in the subsystem**  
  1\. Weblog \- Each entry belongs to exactly one weblog.  (Weblog 1 \-------- 0..\* WeblogEntry)

	2\. WeblogCategory \- Each entry belongs to one category. (WeblogCategory 1    \-------- 0..\* WeblogEntry)  
	3\. User \- (User 1 \-------- 0..\* WeblogEntry)  
	4\. WeblogEntryTag \- Each entry can have multiple tags. (WeblogEntry 1 \-------- 0..\* WeblogEntryTag)  
	5\. WeblogEntryComment (WeblogEntry 1 \-------- 0..\* WeblogEntryComment)  
	6\. WeblogEntryAttribute (WeblogEntry 1 \-------- 0..\* WeblogEntryAttribute)  
	7\. PubStatus (ENUM)  
	8\. WeblogEntryManager  
	9\. WeblogEntryPlugin

8. **WeblogEntryAttribute**  
   **Attributes**  
   \- id : String  
   \- entry : WeblogEntry  
   \- name : String  
   \- value : String

	  
	**Methods**  
	\+ getId() : String  
	\+ setId(String Id)  
	\+ getEntry() : WeblogEntry  
	\+ setEntry(WeblogEntry entry)  
	\+ getName() : String  
	\+ setName(String name)  
	\+ getValue() : String  
	\+ setValue(String value)  
	\+ compareTo(WeblogEntryAttribute att)

	**Role**

* WeblogEntryAttribute represents a custom name–value metadata pair attached to a WeblogEntry

	  
	**Interaction with other parts of the subsystem**  
	1\. WeblogEntry 

* One weblogEntry has many attributes  
* Each attribute has exactly one entry.  
* String ownership (Composition)  
* WeblogEntry 1 ◆────── 0..\* WeblogEntryAttribute


9. **WeblogEntrySearchCriteria**  
   **Attributes**  
   \- weblog : Weblog  
   \- user : User  
   \- startDate : Date  
   \- endDate : Date  
   \- catName : String  
   \- tags : List\<String\>  
   \- status : PubStatus  
   \- text : String  
   \- sortBy : SortBy  
   \- sortOrder : SortOrder  
   \- locale : String  
     
   **Methods**  
   \+ getWeblog() : Weblog   
   \+ setWeblog(Weblog weblog)  
   \+ getUser() : User  
   \+ setUser(User user)  
   \+ getStartDate() : Date  
   \+ setStartDate(Date startDate)  
   \+ getEndDate() : Date  
   \+ setEndDate(Date endDate)  
   \+ getCatName() : String  
   \+ setCatName(String catName)  
   \+ getTags() : List\<String\>  
   \+ setTags(List\<String\> tags)  
   \+ getStatus() : PubStatus  
   \+ setStatus(PubStatus status)  
   \+ getText() : String  
   \+ setText(String text)  
   \+ getSortBy() : SortBy  
   \+ setSortBy(SortBy sortBy)  
   \+ getSortOrder() : SortOrder  
   \+ setSortOrder(SortOrder sortOrder)  
   \+ getLocale() : String  
   \+ setLocale(String locale)

**Role**

*  WeblogEntrySearchCriteria is used to collect all conditions needed to search weblog entries

	**Interaction with other parts of the subsystem (NEED TO CHECK THE CARDINALITY)**  
	1\. Weblog  (0..1)  
	2\. User (0..1)  
	3\. SortBy (ENUM)  
	4\. SortOrder (ENUM)  
	5\. PubStatus (ENUM)

10. **WeblogEntryTag**  
    **Attributes**  
    \- log : Log  
    \- id : String  
    \- website: Weblog  
    \- userName : String  
    \- name : String  
    \- time : Timestamp

	  
	**Methods**  
	\+ getId() : String  
\+ setId(String id)  
	\+ getWeblog() : Weblog  
	\+ setWeblog(Weblog website)  
	\+ getWeblogEntry() : WeblogEntry  
	\+ setWeblogEntry(WeblogEntry data)  
	\+ getUser() : User  
	\+ getCreatorUserName() : String  
	\+ setCreatorUserName(String userName)  
	\+ getName() : String  
	\+ setName(String name)  
	\+ getTime() : Timestamp  
	\+ setTime(Timestamp tagTime)  
	  
	**Role:**

* WeblogEntryTag represents a *tag (keyword)* assigned to a specific weblog entry.  
* It links a WeblogEntry, the Weblog, and the creator user, and stores when the tag was created.  
* Its main role is to support tag-based organization, searching, and filtering of blog entries.

	**Interaction with other parts of the subsystem**  
	1\. Weblog  
	2\. WeblogEntry (One weblog entry can have many tags and each tag is associated with exactly one weblog).  
	3\. User (One tag is created by one user) {Multiplicity \- 1} (**INDIRECT**).

11. **WeblogEntryTagAggregate**  
    **Attributes**  
    \- Id : String  
    \-  name : String  
    \- Website : Weblog  
    \- lastUsed : Timestamp  
    \- total : int  
      
    **Methods**  
    \+ getId() : String  
    \+ setId(String id)  
    \+ getName() : String  
    \+ setName(String name)  
    \+ getLastUsed() : Timestamp  
    \+ setLastUsed(Timestamp lastUsed)  
    \+ getTotal() : int  
    \+ setTotal(int total)  
      
    **Role**  
* It represents aggregated tag statistics at the weblog level—for each tag, it stores how many times it is used (total) and when it was last used.  
* This is mainly used for tag efficient tag listing without scanning individual entries.

	  
	**Interaction with other parts of the subsystem**  
	1\. WeblogEntryTag  
	2\. Weblog \- (Each aggregate belongs to one weblog)  
	

12. **WeblogEntryComment**  
    **Attributes**  
    \- id : String  
    \- name : String  
    \- email : String  
    \- url : String  
    \- content : String  
    \- postTime : Timestamp  
    \- status : ApprovalStatus  
    \- notify : Boolean  
    \- remoteHost : String  
    \- referrer : String  
    \- userAgent : STring  
    \- plugins : String  
    \- contentType : String  
    \- weblogEntry : WeblogEntry  
      
    **Methods**  
    \+ getId() : String  
    \+ setId(String id)  
    \+ getName() : String  
    \+ setName(String name)  
    \+ getEmail() : String  
    \+ setEmail(String email)  
    \+ getWeblogEntry() : WeblogEntry  
    \+ setWeblogEntry(WeblogEntry entry)  
    \+ getUrl() : String  
    \+ setUrl(String url)  
    \+ getContent() : String  
    \+ setContent(String content)  
    \+ getPostTime() : Timestamp  
    \+ setPostTime(Timestamp postTime)  
    \+ getStatus() : ApprovalStatus  
    \+ setStatus(ApprovalStatus status)  
    \+ getNotify() : Boolean  
    \+ setNotify(Boolean notify)  
    \+ getRemoteHost() : String  
    \+ setRemoteHost(STring remoteHost)  
    \+ getReferrer() : String  
    \+ setReferrer(String referrer)  
    \+ getUserAgent() : String  
    \+ setUserAgent(String userAgent)  
    \+ getPlugins() : String  
    \+ setPlugins(String plugins)  
    \+ getContentType() : String  
    \+ setContentType(String ContentType)  
    \+ getPending() : Boolean  
    \+ getApproved() : Boolean  
    \+ getTimestamp() : String  
      
    **Role**  
* It represents a user comment on a weblog entry, storing the comment’s content, author details, posting time, and moderation status (approved, pending, spam).

	**Interaction with other parts of the subsystem**

	1\. WeblogEntry \- (Each weblogEntry has many comments and each comment belongs to exactly one webLogEntry).

	2\. ApprovalStatus (**ENUM**) \- {APPROVED, DISAPPROVED, SPAM, PENDING}

13. **WeblogCategory**  
    **Attributes**  
    \- id : String  
    \- name : String  
    \- description : String  
    \- image : String  
    \- position : int  
    \- weblog : Weblog  
      
    **Methods**  
    \+ calculatePosition() : void  
    \+ getId() : String  
    \+ setId(String id)  
    \+ getName() : String  
    \+ setName(String name)  
    \+ getDescription() : String  
    \+ setDescription(String description)  
    \+ getPosition() : int  
    \+ setPosition(int position)  
    \+ setImage(String image)  
    \+ getImage() : String  
    \+ getWeblog() : Weblog  
    \+ setWeblog(Weblog weblog)  
    \+ retrieveWeblogEntries(boolean publishedOnly) : List\<WeblogEntry\>  
    \+ isInUse() : boolean  
      
    **Role**  
* It represents a classification/grouping mechanism for weblog entries, allowing blog posts to be organized, ordered, and displayed under meaningful categories.  
    
  **Interactions with other parts of the subsystem**  
  1\. Weblog : Each category belongs to exactly one weblog; a weblog can have many categories.  
  2\. WeblogEntry : Entries are associated with categories; categories retrieve entries using WeblogEntryManager and WeblogEntrySearchCriteria.  
    
    
14. **MailProvider**  
    **Attributes**  
    \- session : Session  
    \- type : ConfigurationType  
    \- mailHostName : String  
    \- mailPort : int  
    \- mailUsername : String  
    \- mailPassword : String  
      
    **Methods**  
    \+ getSession() : Session  
    \+ getTransport() : Transport  
      
    **Role**  
* MailProvider centralizes and manages email configuration and connectivity for Roller, creating and validating JavaMail Session and Transport objects used to send emails.

	  
	**Interaction with other parts of the subsystem (NEED TO CONFIRM)**  
	1\. 

15. **RendererManager**  
    **Attributes**  
    \- rendererFactories : List\<RendererFactory\>  
      
    **Methods**  
    \+ getRenderer(Template template, MobileDeviceRepository.DeviceType deviceType) : Renderer  
      
    **Role**  
* RendererManager acts as a central dispatcher that selects the appropriate Renderer for a given Template and device type, enabling rendering mechanism.

	  
	**Interaction with other parts of the subsystem**  
	1\. Renderer (**INTERFACE**)  
2\. Template  
3\. MobileDeviceRepository

16. **WeblogManager (Interface)**  
    Attributes  
     None. This is an interface.  
    Methods  
     The WeblogManager defines operations for managing weblogs and their templates, including:  
* addWeblog(newWebsite : Weblog) : void

* saveWeblog(data : Weblog) : void

* removeWeblog(website : Weblog) : void

* getWeblog(id : String) : Weblog

* getWeblogByHandle(handle : String) : Weblog

* getWeblogByHandle(handle : String, enabled : Boolean) : Weblog

* getWeblogs(enabled : Boolean, active : Boolean, startDate : Date, endDate : Date, offset : int, length : int) : List\<Weblog\>

* getUserWeblogs(user : User, enabledOnly : boolean) : List\<Weblog\>

* getWeblogUsers(weblog : Weblog, enabledOnly : boolean) : List\<User\>

* getMostCommentedWeblogs(startDate : Date, endDate : Date, offset : int, length : int) : List\<StatCount\>

* getWeblogHandleLetterMap() : Map\<String, Long\>

* getWeblogsByLetter(letter : char, offset : int, length : int) : List\<Weblog\>

* saveTemplate(data : WeblogTemplate) : void

* removeTemplate(template : WeblogTemplate) : void

* getTemplate(id : String) : WeblogTemplate

* getTemplateByAction(w : Weblog, a : ComponentType) : WeblogTemplate

* getTemplateByName(w : Weblog, p : String) : WeblogTemplate

* getTemplateByLink(w : Weblog, p : String) : WeblogTemplate

* saveTemplateRendition(templateCode : CustomTemplateRendition) : void

* getTemplates(w : Weblog) : List\<WeblogTemplate\>

* getWeblogCount() : long

* release() : void

  Role  
   The WeblogManager interface acts as the business service layer for weblog and template management. It defines the contract for creating, retrieving, updating, and deleting Weblog entities. It also supports querying weblogs by different criteria such as user ownership, activity state, and comment statistics. In addition, it manages the persistence and retrieval of WeblogTemplate objects and their custom renditions.  
  Interactions with other parts of the subsystem  
* Weblog (Class)  
   Relationship: Dependency  
   Cardinality: One WeblogManager handles many Weblog instances  
   Reason: Weblog objects are passed to the manager for creation, update, deletion, and are returned by query operations.

* User (Class)  
   Relationship: Dependency  
   Cardinality: One WeblogManager interacts with many User instances  
   Reason: The manager retrieves weblogs owned by a user and users associated with a weblog.

* WeblogTemplate (Class)  
   Relationship: Dependency  
   Cardinality: One WeblogManager handles many WeblogTemplate instances  
   Reason: The manager is responsible for saving, removing, and retrieving templates linked to weblogs.

* CustomTemplateRendition (Class)  
   Relationship: Dependency  
   Cardinality: One WeblogManager handles many CustomTemplateRendition instances  
   Reason: The manager persists specific code renditions for custom templates.

* StatCount (Class)  
   Relationship: Dependency  
   Cardinality: One WeblogManager produces many StatCount objects  
   Reason: StatCount is used as a data transfer object when returning weblog statistics such as most commented weblogs.

* ComponentType (Enum)  
   Relationship: Dependency  
   Cardinality: One-to-one per method call  
   Reason: ComponentType is used to identify and retrieve specific template types based on their action (for example, weblog page or permalink).  
    
17. **UserManager (interface)**  
    Attributes  
    None (This is an Interface).  
    Methods

    \+ addUser(newUser : User) : void  
    \+ saveUser(user : User) : void  
    \+ removeUser(user : User) : void  
    \+ getUserCount() : long  
    \+ getUserByActivationCode(activationCode : String) : User  
    \+ getUser(id : String) : User  
    \+ getUserByUserName(userName : String) : User  
    \+ getUserByUserName(userName : String, enabled : Boolean) : User  
    \+ getUserByOpenIdUrl(openIdUrl : String) : User  
    \+ getUsers(enabled : Boolean, startDate : Date, endDate : Date, offset : int, length : int) : List\<User\>  
    \+ getUsersStartingWith(startsWith : String, enabled : Boolean, offset : int, length : int) : List\<User\>  
    \+ getUserNameLetterMap() : Map\<String, Long\>  
    \+ getUsersByLetter(letter : char, offset : int, length : int) : List\<User\>  
    \+ checkPermission(perm : RollerPermission, user : User) : boolean  
    \+ grantWeblogPermission(weblog : Weblog, user : User, actions : List\<String\>) : void  
    \+ grantWeblogPermissionPending(weblog : Weblog, user : User, actions : List\<String\>) : void  
    \+ confirmWeblogPermission(weblog : Weblog, user : User) : void  
    \+ declineWeblogPermission(weblog : Weblog, user : User) : void  
    \+ revokeWeblogPermission(weblog : Weblog, user : User, actions : List\<String\>) : void  
    \+ getWeblogPermissions(user : User) : List\<WeblogPermission\>  
    \+ getPendingWeblogPermissions(user : User) : List\<WeblogPermission\>  
    \+ getWeblogPermissions(weblog : Weblog) : List\<WeblogPermission\>  
    \+ getPendingWeblogPermissions(weblog : Weblog) : List\<WeblogPermission\>  
    \+ getWeblogPermissionsIncludingPending(weblog : Weblog) : List\<WeblogPermission\>  
    \+ getWeblogPermission(weblog : Weblog, user : User) : WeblogPermission  
    \+ getWeblogPermissionIncludingPending(weblog : Weblog, user : User) : WeblogPermission  
    \+ grantRole(roleName : String, user : User) : void  
    \+ revokeRole(roleName : String, user : User) : void  
    \+ hasRole(roleName : String, user : User) : boolean  
    \+ getRoles(user : User) : List\<String\>  
    \+ release() : void

    Role in Subsystem

    The UserManager interface defines the contract for managing the lifecycle of system users and their authorization. In the context of the Weblog and Content Subsystem, it acts as the gatekeeper and relationship manager. It handles the creation and retrieval of User entities (authors) and manages WeblogPermission objects, which link a User to a Weblog with specific rights (e.g., ability to post entries, manage comments). Without this class, the system cannot determine ownership or authorship of content.

      
    Interactions with Other Classes

    User

    Relationship: Dependency  
    Cardinality: 1 to \* (One UserManager manages many Users)  
    Why/Where: Used as a parameter in almost all methods (addUser, saveUser, grantWeblogPermission) and returned by query methods (getUser, getUsers).   
    The manager is responsible for persisting and retrieving User state.  
    

    Weblog

    Relationship: Dependency  
    Cardinality: 1 to \* (One UserManager manages permissions for many Weblogs)  
    Why/Where: Used in permission-related methods (grantWeblogPermission, getWeblogPermissions). The manager associates Users with Weblogs.

      
    WeblogPermission

    Relationship: Dependency (Creates/Returns)  
    Cardinality: 1 to \*  
    Why/Where: Returned by getWeblogPermissions and implicitly created/modified by grantWeblogPermission. This object represents the association data between a User and a Weblog.

      
    RollerPermission

    Relationship: Dependency  
    Cardinality: 1 to 1 (per check)  
    Why/Where: Used in checkPermission(RollerPermission perm, User user) to verify if a user has a specific abstract permission (like Global Admin).  
      
18. **WeblogEntryManager (interface)**  
    No attributes  
    

    ### Methods

* saveWeblogEntry(entry : WeblogEntry) : void  
* removeWeblogEntry(entry : WeblogEntry) : void  
* getWeblogEntry(id : String) : WeblogEntry  
* getWeblogEntryByAnchor(website : Weblog, anchor : String) : WeblogEntry  
* getWeblogEntries(wesc : WeblogEntrySearchCriteria) : List\<WeblogEntry\>  
* getWeblogEntryObjectMap(wesc : WeblogEntrySearchCriteria) : Map\<Date, List\<WeblogEntry\>\>  
* getWeblogEntryStringMap(wesc : WeblogEntrySearchCriteria) : Map\<Date, String\>  
* getMostCommentedWeblogEntries(website : Weblog, startDate : Date, endDate : Date, offset : int, length : int) : List\<StatCount\>  
* getNextEntry(current : WeblogEntry, catName : String, locale : String) : WeblogEntry  
* getPreviousEntry(current : WeblogEntry, catName : String, locale : String) : WeblogEntry  
* getWeblogEntriesPinnedToMain(max : Integer) : List\<WeblogEntry\>  
* removeWeblogEntryAttribute(name : String, entry : WeblogEntry) : void  
* saveWeblogCategory(cat : WeblogCategory) : void  
* removeWeblogCategory(cat : WeblogCategory) : void  
* getWeblogCategory(id : String) : WeblogCategory  
* moveWeblogCategoryContents(srcCat : WeblogCategory, destCat : WeblogCategory) : void  
* getWeblogCategoryByName(website : Weblog, categoryName : String) : WeblogCategory  
* getWeblogCategories(website : Weblog) : List\<WeblogCategory\>  
* saveComment(comment : WeblogEntryComment) : void  
* removeComment(comment : WeblogEntryComment) : void  
* getComment(id : String) : WeblogEntryComment  
* getComments(csc : CommentSearchCriteria) : List\<WeblogEntryComment\>  
* removeMatchingComments(website : Weblog, entry : WeblogEntry, searchString : String, startDate : Date, endDate : Date, status : ApprovalStatus) : int  
* createAnchor(data : WeblogEntry) : String  
* isDuplicateWeblogCategoryName(data : WeblogCategory) : boolean  
* isWeblogCategoryInUse(data : WeblogCategory) : boolean  
* applyCommentDefaultsToEntries(website : Weblog) : void  
* release() : void  
* getPopularTags(website : Weblog, startDate : Date, offset : int, limit : int) : List\<TagStat\>  
* getTags(website : Weblog, sortBy : String, startsWith : String, offset : int, limit : int) : List\<TagStat\>  
* getTagComboExists(tags : List\<String\>, weblog : Weblog) : boolean  
* getHitCount(id : String) : WeblogHitCount  
* getHitCountByWeblog(weblog : Weblog) : WeblogHitCount  
* getHotWeblogs(sinceDays : int, offset : int, length : int) : List\<WeblogHitCount\>  
* saveHitCount(hitCount : WeblogHitCount) : void  
* removeHitCount(hitCount : WeblogHitCount) : void  
* incrementHitCount(weblog : Weblog, amount : int) : void  
* resetAllHitCounts() : void  
* resetHitCount(weblog : Weblog) : void  
* getCommentCount() : long  
* getCommentCount(websiteData : Weblog) : long  
* getEntryCount() : long  
* getEntryCount(websiteData : Weblog) : long

  ### Role

The WeblogEntryManager acts as the business service interface for the content subsystem. It abstracts the persistence layer logic required to manage the core artifacts of a blog: entries (posts), categories (organization), comments (user interaction), tags (metadata), and hit counts (analytics). It ensures data integrity (e.g., checking for duplicate categories) and provides complex retrieval methods (e.g., searching entries by criteria, getting popular tags).

### Interactions with other parts of the subsystem

WeblogEntry

* Relationship: Dependency  
* Cardinality: 1 Manager uses 0..\* WeblogEntries  
* Reason: The manager is responsible for saving, removing, and retrieving WeblogEntry objects. It is the primary CRUD interface for entries.

Weblog

* Relationship: Dependency  
* Cardinality: 1 Manager uses 0..\* Weblogs  
* Reason: Most operations are scoped to a specific Weblog (e.g., getting categories for a weblog, getting entries for a weblog). The manager uses the Weblog object to filter queries.

WeblogCategory

* Relationship: Dependency  
* Cardinality: 1 Manager uses 0..\* WeblogCategories  
* Reason: The manager handles the creation, removal, and retrieval of categories, as well as moving contents between them.

WeblogEntryComment

* Relationship: Dependency  
* Cardinality: 1 Manager uses 0..\* WeblogEntryComments  
* Reason: The manager handles the persistence and moderation (removal) of comments associated with entries.

WeblogEntrySearchCriteria

* Relationship: Dependency  
* Cardinality: 1 Manager uses 0..\* Criteria objects  
* Reason: This class is used as a parameter object to encapsulate complex filtering options (date range, tags, status) when retrieving lists of entries.

CommentSearchCriteria

* Relationship: Dependency  
* Cardinality: 1 Manager uses 0..\* Criteria objects  
* Reason: Used as a parameter object to encapsulate filtering options when retrieving lists of comments.

WeblogHitCount (not in this subsystem in any part)

* Relationship: Dependency  
* Cardinality: 1 Manager uses 0..\* WeblogHitCounts  
* Reason: The manager updates and retrieves hit count statistics for weblogs.

TagStat

* Relationship: Dependency  
* Cardinality: 1 Manager produces 0..\* TagStats  
* Reason: The manager calculates and returns tag usage statistics (popular tags) as TagStat objects.

StatCount (not in this subsystem in any part)

* Relationship: Dependency  
* Cardinality: 1 Manager produces 0..\* StatCounts  
* Reason: The manager calculates statistics (e.g., most commented entries) and returns them as StatCount objects.  
    
19. **Weblogger**  
    **(Interface)**

    ### Methods

* \+ getUserManager() : UserManager  
* \+ getBookmarkManager() : BookmarkManager  
* \+ getOAuthManager() : OAuthManager  
* \+ getWeblogManager() : WeblogManager  
* \+ getWeblogEntryManager() : WeblogEntryManager  
* \+ getAutopingManager() : AutoPingManager  
* \+ getPingTargetManager() : PingTargetManager  
* \+ getPingQueueManager() : PingQueueManager  
* \+ getPropertiesManager() : PropertiesManager  
* \+ getThreadManager() : ThreadManager  
* \+ getIndexManager() : IndexManager  
* \+ getThemeManager() : ThemeManager  
* \+ getPluginManager() : PluginManager  
* \+ getMediaFileManager() : MediaFileManager  
* \+ getFileContentManager() : FileContentManager  
* \+ getUrlStrategy() : URLStrategy  
* \+ flush() : void  
* \+ release() : void  
* \+ initialize() : void  
* \+ shutdown() : void  
* \+ getVersion() : String  
* \+ getRevision() : String  
* \+ getBuildTime() : String  
* \+ getBuildUser() : String  
* \+ getFeedFetcher() : FeedFetcher  
* \+ getPlanetManager() : PlanetManager  
* \+ getPlanetURLStrategy() : PlanetURLStrategy

  ### Role

  The Weblogger interface acts as the Facade and Service Locator for the entire Weblogger business tier. It aggregates all specific domain managers (such as WeblogManager, WeblogEntryManager, MediaFileManager) and provides a single, unified entry point for the application to access these services. It is responsible for the initialization, lifecycle management, and resource release of the business layer. In the context of the "Weblog and Content Subsystem," it is the primary interface through which the subsystem's components are retrieved and utilized.

  ### Interactions

  * WeblogManager  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the WeblogManager, which handles the creation and management of weblogs (blogs).  
  * WeblogEntryManager  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the WeblogEntryManager, which handles blog entries, comments, and categories.  
  * ThemeManager  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the ThemeManager, which is responsible for the rendering engine's themes and templates.  
  * FeedFetcher (not in this subsystem)  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the FeedFetcher, used for content fetching in the Planet aggregator subsystem.  
  * MediaFileManager  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the MediaFileManager, which manages media uploads and directories.  
  * UserManager (not in this subsystem)  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the UserManager for handling user accounts and permissions.  
  * BookmarkManager (not in this subsystem)  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the BookmarkManager for managing blogrolls/bookmarks.  
  * PluginManager  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the PluginManager for managing content plugins.  
  * IndexManager (not in this subsystem)  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the IndexManager for search indexing.  
  * FileContentManager (not in this subsystem)  
    * Cardinality: 1..1  
    * Relationship: Association / Dependency  
    * Reason: The Weblogger provides access to the FileContentManager for low-level file storage operations.  
        
20.  **Tagstat**  
    Attributes  
* \- serialVersionUID : long  
* \- name : String  
* \- count : int  
* \- intensity : int

  ### Methods

* \+ TagStat()  
* \+ getName() : String  
* \+ setName(String name) : void  
* \+ getCount() : int  
* \+ setCount(int count) : void  
* \+ toString() : String  
* \+ getIntensity() : int  
* \+ setIntensity(int intensity) : void

  ### Role

  The TagStat class acts as a Data Transfer Object (DTO) or a value object representing the usage statistics of a specific tag within a weblog or across the entire site.

* It encapsulates the tag's name.  
* It holds the count (frequency) of how many times the tag has been used on blog entries.  
* It holds an intensity value, which is typically calculated by the business logic (Manager) to determine the visual weight (e.g., font size) of the tag when rendered in a UI "Tag Cloud."

  ### Interactions with other parts of the subsystem

1. WeblogEntryManager  
   * Cardinality: 1 (Manager) to 0..\* (TagStat)  
   * Relationship: Dependency (Creation / Return Type)  
   * Why/Where: The WeblogEntryManager is the service interface responsible for calculating tag statistics. Methods like getPopularTags() and getTags() in the manager query the database and populate TagStat objects to return to the controller or view layer. TagStat does not call the Manager; the Manager creates TagStat.  
2. java.io.Serializable  
   * Cardinality: 1  
   * Relationship: Realization (Implementation)  
   * Why/Where: TagStat implements this interface to allow the object to be serialized, which is useful for caching tag clouds or passing the object between network layers.

**22\. PluginManager (interface)**

Methods

* hasPagePlugins() : boolean  
* getWeblogEntryPlugins(website : Weblog) : Map\<String, WeblogEntryPlugin\>  
* applyWeblogEntryPlugins(pagePlugins : Map\<String, WeblogEntryPlugin\>, entry : WeblogEntry, str : String) : String  
* getCommentPlugins() : List  
* applyCommentPlugins(comment : WeblogEntryComment, text : String) : String  
* release() : void  
  Role  
  The PluginManager interface defines the contract for managing the lifecycle and application of content plugins within the Weblogger system. It acts as a bridge between the core content objects (Weblogs, Entries, Comments) and the extensible plugin architecture. Its primary role is to retrieve configured plugins and apply them to text content, transforming raw input into render-ready HTML (e.g., converting line breaks, processing smileys, or sanitizing malicious code).

Interactions with other parts of the subsystem  
Weblog

* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in the getWeblogEntryPlugins method. The Weblog object is passed as a parameter to identify which website context the plugins are being retrieved for, allowing for site-specific plugin initialization.  
  WeblogEntryPlugin  
* Type: Interface  
* Relationship: Dependency  
* Cardinality: 0..\*  
* Interaction Detail: Interacts in getWeblogEntryPlugins (as a return value) and applyWeblogEntryPlugins (as a parameter). This interface represents the actual plugin units that the manager orchestrates to transform entry text.  
  WeblogEntry  
* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in the applyWeblogEntryPlugins method. The WeblogEntry is passed to the manager so it can be forwarded to plugins, providing them with necessary context (like the entry's timestamp or locale) during the rendering process.  
  WeblogEntryCommentPlugin  
* Type: Interface  
* Relationship: Dependency  
* Cardinality: 0..\*  
* Interaction Detail: Interacts in the getCommentPlugins method. The manager returns a list of these interfaces, which represent the plugins capable of processing comment text.  
  WeblogEntryComment  
* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in the applyCommentPlugins method. The WeblogEntryComment object is passed to the manager to determine which plugins apply to the specific comment and to provide context for the transformation.

**23\. PluginManagerImpl**

Attributes (private)

* log : Log  
* mPagePlugins : Map\<String, Class\<? extends WeblogEntryPlugin\>\>  
* commentPlugins : List\<WeblogEntryCommentPlugin\>  
  Methods  
* PluginManagerImpl()  
* hasPagePlugins() : boolean  
* getWeblogEntryPlugins(website : Weblog) : Map\<String, WeblogEntryPlugin\>  
* applyWeblogEntryPlugins(pagePlugins : Map\<String, WeblogEntryPlugin\>, entry : WeblogEntry, str : String) : String  
* getCommentPlugins() : List\<WeblogEntryCommentPlugin\>  
* applyCommentPlugins(comment : WeblogEntryComment, text : String) : String  
* loadPagePluginClasses() : void \-\> **private**  
* loadCommentPlugins() : void \-\> **private**  
* release() : void  
  Role  
  PluginManagerImpl is the concrete implementation of the PluginManager interface within the Weblog and Content Subsystem. Its primary role is to act as the registry and execution engine for content transformers. It loads plugin configurations (both for blog entries and comments) from the system properties, instantiates them using reflection, and orchestrates their application to text content. It ensures that raw text from entries or comments is processed through the configured pipeline of plugins (e.g., formatting, syntax highlighting) and sanitized before being rendered to the user.  
   Interactions with other parts of the subsystem

  PluginManager  
* Type: Interface  
* Relationship: Realization  
* Cardinality: 1  
* Interaction Detail: PluginManagerImpl implements the PluginManager interface. It provides the concrete logic for the methods defined in the contract, ensuring the subsystem can interact with plugins without knowing the implementation details.

  WeblogEntryPlugin

* Type: Interface  
* Relationship: Association  
* Cardinality: 0..\*  
* Interaction Detail: The class maintains a static map (mPagePlugins) containing the *classes* of available entry plugins. In getWeblogEntryPlugins, it instantiates these plugins using reflection. In applyWeblogEntryPlugins, it iterates through specific plugin instances to transform the entry text.

  WeblogEntryCommentPlugin

* Type: Interface  
* Relationship: Aggregation  
* Cardinality: 0..\*  
* Interaction Detail: The class maintains a list (commentPlugins) of instantiated comment plugin objects. It iterates over this list in applyCommentPlugins to process comment text, allowing features like spam filtering or text formatting on comments.

  Weblog

* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in the getWeblogEntryPlugins method. The Weblog object is passed to the init() method of each WeblogEntryPlugin so that plugins can be initialized with settings specific to that particular blog instance.

  WeblogEntry

* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in the applyWeblogEntryPlugins method. The WeblogEntry is passed to the plugins to provide context (such as the entry's timestamp or locale) needed during the rendering of the text.

  WeblogEntryComment

* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in the applyCommentPlugins method. The WeblogEntryComment object is checked to see if it has specific plugins enabled (via comment.getPlugins()), and it provides the context for the plugin execution.

  WebloggerConfig

* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in loadPagePluginClasses. The manager uses this configuration class to retrieve the property plugins.page, which contains the class names of the plugins that need to be loaded.

  HTMLSanitizer

* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Interaction Detail: Interacts in applyWeblogEntryPlugins. After all plugins have processed the text, the manager calls HTMLSanitizer.conditionallySanitize to ensure the final output is safe and free of malicious HTML tags.


**24\. WeblogEntryPlugin (interface)**

Methods  
\+ getName() : String 

\+ getDescription() : String 

\+ init(weblog : Weblog) : void 

\+ render(entry : WeblogEntry, str : String) : String

Role  
The WeblogEntryPlugin interface defines the contract for plugins that perform text transformations on weblog entries. It acts as a hook in the rendering pipeline, allowing the system to modify the content of a blog post (summary or body) dynamically. This is used for features like converting line breaks to HTML, parsing wiki syntax, handling emoticons, or applying other formatting rules before the content is served to the reader.

Interactions with other parts of the subsystem

* Weblog  
  * Exact Relationship: Dependency  
  * Cardinality: 1 (passed as a parameter)  
  * Why/Where it interacts: The init method accepts a Weblog object. This allows the plugin to initialize itself with context specific to the weblog being rendered (for example, checking weblog-specific plugin settings).  
* WeblogEntry  
  * Exact Relationship: Dependency  
  * Cardinality: 1 (passed as a parameter)  
  * Why/Where it interacts: The render method accepts a WeblogEntry object. The plugin uses this to access details about the specific entry being processed while transforming the text string.  
* WebloggerException (doubt if to be added in this subsystem)  
  * Exact Relationship: Dependency  
  * Cardinality: 0..1 (thrown)  
  * Why/Where it interacts: The init method declares that it throws this exception if initialization fails.

**25\. WeblogEntryCommentPlugin (interface)**

Methods

* getId() : String  
* getName() : String  
* getDescription() : String  
* render(comment : WeblogEntryComment, str : String) : String  
  Role  
  This interface defines the contract for plugins that manipulate, transform, or validate the text of a weblog entry comment. It serves as an abstraction layer allowing the system to apply various processors (such as spam filters, HTML sanitizers, or text formatters) to comments dynamically without modifying the core comment logic.  
  Interactions with other parts of the subsystem  
  WeblogEntryComment  
* Cardinality: 1 (Plugin) processes 1 (Comment) per method call.  
* Relationship: Dependency (Usage).  
* Why/Where: The interaction occurs in the render method. The WeblogEntryComment object is passed as a parameter (comment), allowing the plugin to inspect the comment's metadata (like author, email, or existing body) to decide how to transform the input string.

**26\. CommentSearchCriteria**

Attributes  
Filter Criteria

* \- weblog : Weblog  
* \- entry : WeblogEntry  
* \- searchText : String  
* \- startDate : Date  
* \- endDate : Date  
* \- status : ApprovalStatus

  Sorting and Pagination

* \- reverseChrono : boolean  
* \- offset : int  
* \- maxResults : int  
    
  Methods  
* \+ getWeblog() : Weblog  
* \+ setWeblog(weblog : Weblog) : void  
* \+ getEntry() : WeblogEntry  
* \+ setEntry(entry : WeblogEntry) : void  
* \+ getSearchText() : String  
* \+ setSearchText(searchText : String) : void  
* \+ getStartDate() : Date  
* \+ setStartDate(startDate : Date) : void  
* \+ getEndDate() : Date  
* \+ setEndDate(endDate : Date) : void  
* \+ getStatus() : ApprovalStatus  
* \+ setStatus(status : ApprovalStatus) : void  
* \+ isReverseChrono() : boolean  
* \+ setReverseChrono(reverseChrono : boolean) : void  
* \+ getOffset() : int  
* \+ setOffset(offset : int) : void  
* \+ getMaxResults() : int  
* \+ setMaxResults(maxResults : int) : void  
  Role  
  This class functions as a Search Specification or Parameter Object. Its primary role is to decouple the method signatures of the business layer from the specific criteria used to query comments. Instead of passing 9 different arguments to a search method (which would be messy and hard to maintain), the system passes a single instance of CommentSearchCriteria. It allows the application to filter comments based on the blog they belong to, the specific entry, the content text, date ranges, or approval status, while also handling pagination (offset/limit) and sorting.  
  Interactions with other parts of the subsystem  
  1\. Class: Weblog  
* Relationship: Association (Unidirectional)  
* Cardinality: 0..1 (Optional)  
* Why/Where it interacts: The criteria object holds a reference to a Weblog object. This is used to restrict the search results to comments that belong only to that specific weblog. If null, the search applies to all weblogs.  
  2\. Class: WeblogEntry  
* Relationship: Association (Unidirectional)  
* Cardinality: 0..1 (Optional)  
* Why/Where it interacts: It holds a reference to a WeblogEntry. This is used to narrow down the search to comments associated with a specific blog post/entry.  
  3\. Class: WeblogEntryComment.ApprovalStatus  
* Relationship: Dependency  
* Cardinality: 0..1 (Optional)  
* Why/Where it interacts: It uses this Enum to filter comments based on their workflow state (e.g., PENDING, APPROVED, SPAM). This allows the system to fetch only approved comments for public display or pending comments for moderation.  
  4\. Class: Date (java.util.Date)  
* Relationship: Dependency  
* Cardinality: 0..1 (Optional for both start and end)  
* Why/Where it interacts: Used to define the temporal boundaries (startDate and endDate) for the search query.

**27\. GlobalCommentManagement**

Attributes

* \- log : Log  
* \- COUNT : int \= 30  
* \- bean : GlobalCommentManagementBean  
* \- pager : CommentsPager  
* \- firstComment : WeblogEntryComment  
* \- lastComment : WeblogEntryComment  
* \- bulkDeleteCount : int \= 0  
* \- httpMethod : String \= "GET"  
  Methods  
* \+ GlobalCommentManagement()  
* \+ requiredGlobalPermissionActions() : List\<String\>  
* \+ isWeblogRequired() : boolean  
* \+ loadComments() : void  
* \- buildBaseUrl() : String  
* \+ execute() : String  
* \+ query() : String  
* \+ delete() : String  
* \+ update() : String  
* \+ getCommentStatusOptions() : List\<KeyValueObject\>  
* \+ getBean() : GlobalCommentManagementBean  
* \+ setBean(bean : GlobalCommentManagementBean) : void  
* \+ getBulkDeleteCount() : int  
* \+ setBulkDeleteCount(bulkDeleteCount : int) : void  
* \+ getFirstComment() : WeblogEntryComment  
* \+ setFirstComment(firstComment : WeblogEntryComment) : void  
* \+ getLastComment() : WeblogEntryComment  
* \+ setLastComment(lastComment : WeblogEntryComment) : void  
* \+ getPager() : CommentsPager  
* \+ setPager(pager : CommentsPager) : void  
* \+ setServletRequest(req : HttpServletRequest) : void  
  Role  
  This class serves as the Controller (Struts2 Action) for the global administration of weblog comments. It allows administrators to search, view, approve, mark as spam, or delete comments across the entire system. It acts as the bridge between the administrative user interface and the backend business logic (WeblogEntryManager) that persists comment data.  
  Interactions with other parts of the subsystem  
* UIAction  
  * Type: Class  
  * Relationship: Inheritance (Generalization)  
  * Cardinality: 1  
  * Reason: GlobalCommentManagement extends UIAction to inherit standard Struts2 action capabilities like menu handling and title setting.  
* ServletRequestAware  
  * Type: Interface  
  * Relationship: Realization  
  * Cardinality: 1  
  * Reason: Implemented to obtain access to the HttpServletRequest object, specifically to determine the HTTP method (GET/POST).  
* GlobalCommentManagementBean  
  * Type: Class  
  * Relationship: Association (Composition)  
  * Cardinality: 1  
  * Reason: Used as a data transfer object (DTO) to bind form data (search criteria, checkboxes) from the UI to the Action.  
* WeblogEntryManager  
  * Type: Interface/Class  
  * Relationship: Dependency  
  * Cardinality: 1  
  * Reason: The core business service used to query comments (getComments), remove comments (removeMatchingComments, removeComment), and save status changes (saveComment).  
* WeblogEntryComment  
  * Type: Class  
  * Relationship: Association  
  * Cardinality: 0..\*  
  * Reason: Represents the actual comment entities being managed. The action holds references to the first and last comments for display logic and processes lists of them during updates.  
* CommentsPager  
  * Type: Class  
  * Relationship: Association  
  * Cardinality: 0..1  
  * Reason: A helper class instantiated to handle the pagination logic for the list of comments displayed to the user.  
* WebloggerFactory  
  * Type: Class  
  * Relationship: Dependency  
  * Cardinality: 1  
  * Reason: Used statically to retrieve the WeblogEntryManager instance and the UrlStrategy.  
* CommentSearchCriteria  
  * Type: Class  
  * Relationship: Dependency  
  * Cardinality: 1  
  * Reason: A parameter object instantiated to encapsulate search filters (date range, status, text) when calling WeblogEntryManager.  
* Weblog  
  * Type: Class  
  * Relationship: Dependency  
  * Cardinality: 0..\*  
  * Reason: Accessed via comments to identify which weblogs need their cache invalidated after a comment update or deletion.  
* CacheManager  
  * Type: Class  
  * Relationship: Dependency  
  * Cardinality: 1  
  * Reason: Used statically to invalidate the cache for specific weblogs when their comments are modified.

**28\. CommentAuthenticator (interface)**

Methods

* \+ getHtml(request : HttpServletRequest) : String  
* \+ authenticate(request : HttpServletRequest) : boolean  
  Role  
  This interface defines the contract for comment authentication plugins within the subsystem. It acts as a strategy pattern, allowing the weblog engine to plug in different mechanisms (such as Math CAPTCHA, LDAP, or simple pass-through) to verify a user's identity or humanity before a comment is accepted. It is responsible for generating the necessary HTML for the authentication challenge and for verifying the user's response during the comment submission process.  
  Interactions with other parts of the subsystem  
* HttpServletRequest  
  * Relationship: Dependency  
  * Cardinality: 1..1 (Passed as an argument to methods)  
  * Reason: The interface relies on the standard Java EE HttpServletRequest to access form parameters, session attributes, and locale information required to generate the authentication HTML widget or verify the authentication credentials provided by the user.  
* DefaultCommentAuthenticator  
  * Relationship: Realization  
  * Cardinality: 1..1  
  * Reason: This class implements the CommentAuthenticator interface to provide the default behavior (usually a no-op or simple pass-through) when no specific authentication plugin is configured.  
* MathCommentAuthenticator  
  * Relationship: Realization  
  * Cardinality: 1..1  
  * Reason: This class implements the CommentAuthenticator interface to provide a specific implementation that challenges the user with a simple math problem to prevent spam bots.  
* LdapCommentAuthenticator  
  * Relationship: Realization  
  * Cardinality: 1..1  
  * Reason: This class implements the CommentAuthenticator interface to provide an implementation that authenticates the commenter against an external LDAP directory service.

**29\. CommentValidator (interface)**

Methods  
getName() : String 

validate(WeblogEntryComment comment, RollerMessages messages) : int

Role  
It defines the contract for comment validation plugins within the subsystem. Implementations of this interface are used to inspect WeblogEntryComment objects for validity (e.g., checking for spam, banned words, or excessive links) and report errors via RollerMessages. It allows the comment validation logic to be extensible and pluggable.

Interactions with other parts of the subsystem

* WeblogEntryComment  
  * Relationship: Dependency  
  * Cardinality: 1  
  * Reason: It is passed as an argument to the validate method. The validator inspects the comment's content, author, and URL to determine if it meets validation criteria.  
* RollerMessages  
  * Relationship: Dependency  
  * Cardinality: 1  
  * Reason: It is passed as an argument to the validate method. The validator adds error messages to this object if the comment fails validation.  
* CommentValidationManager  
  * Relationship: Association (Aggregation)  
  * Cardinality: 1  
  * Reason: The CommentValidationManager manages the lifecycle of CommentValidator instances. It iterates through a list of configured validators and invokes their validate methods to process a comment.

**30\. CommentValidationManager**

Attributes

* \- log : Log  
* \- validators : List\<CommentValidator\>  
  Methods  
* \+ CommentValidationManager()  
* \+ addCommentValidator(val : CommentValidator) : void  
* \+ validateComment(comment : WeblogEntryComment, messages : RollerMessages) : int  
  Role in Subsystem  
  The CommentValidationManager acts as the central orchestrator for comment validation. Its primary role is to manage a collection of pluggable CommentValidator implementations (such as spam checkers, size limiters, or banned word checkers). When a new comment is submitted, this manager iterates through all configured validators to assess the comment's validity, aggregating confidence scores and collecting error messages.  
  Interactions with Other Classes  
1. Class: CommentValidator  
   * Exact Relationship: Aggregation  
   * Cardinality: 1 (Manager) to 0..\* (Validators)  
   * Interaction: The manager maintains a list of CommentValidator instances. In the validateComment method, it iterates through this list, invoking the validate method on each validator to check the comment.  
2. Class: WeblogEntryComment  
   * Exact Relationship: Dependency  
   * Cardinality: 1 (Manager) to \* (Comments)  
   * Interaction: The WeblogEntryComment object is passed as a parameter to the validateComment method. The manager and its validators inspect the state of this object (content, author, etc.) to determine validity.  
3. Class: RollerMessages  
   * Exact Relationship: Dependency  
   * Cardinality: 1 (Manager) to \* (Messages)  
   * Interaction: This object is passed as a parameter to validateComment. If any validator finds an issue with the comment, it adds specific error messages to this RollerMessages object for UI feedback.  
4. Class: Reflection  
   * Exact Relationship: Dependency  
   * Cardinality: 1 (Manager) to 1 (Utility)  
   * Interaction: The manager uses the Reflection utility class in its constructor to dynamically instantiate CommentValidator objects based on class names defined in the configuration properties.

**31.Renderer**

Methods

* \+ render(model : Map\<String, Object\>, writer : Writer) : void  
  Role  
  The Renderer interface acts as an abstraction layer for the rendering mechanism in the Weblogger subsystem. Its primary role is to take a data model (a map of objects) and a template, and generate the final output (typically writing to a Writer). By defining this interface, the system allows for pluggable rendering technologies (such as Velocity or other template engines) without tightly coupling the core logic to a specific implementation.  
  Interactions with other parts of the subsystem  
* PageServlet  
  * Relationship: Dependency  
  * Cardinality: 1 PageServlet uses 1 Renderer (per request).  
  * Reason: PageServlet (and other servlets like FeedServlet) is the consumer of this interface. It retrieves a specific Renderer instance and calls the render() method to generate the HTTP response content for a weblog page.  
* RendererManager  
  * Relationship: Dependency  
  * Cardinality: 1 RendererManager manages/returns 1 Renderer.  
  * Reason: The RendererManager is responsible for locating the appropriate Renderer for a given template and device type. It returns an object implementing the Renderer interface to the caller.  
* RendererFactory  
  * Relationship: Dependency (Creation)  
  * Cardinality: 1 RendererFactory creates \* Renderer.  
  * Reason: Implementations of RendererFactory are responsible for instantiating specific implementations of the Renderer interface.  
* RenderingException  
  * Relationship: Dependency (Throws)  
  * Cardinality: N/A  
  * Reason: The render method declares that it throws RenderingException if an error occurs during the rendering process, enforcing error handling in interacting classes.

**32\. VelocityRenderer**

Attributes

\- log : Log {static, readOnly}

\- renderTemplate : Template {readOnly}

\- deviceType : MobileDeviceRepository.DeviceType {readOnly}

\- velocityTemplate : org.apache.velocity.Template

\- velocityDecorator : org.apache.velocity.Template

\- velocityException : Exception

Methods

\+ VelocityRenderer(template : Template, deviceType : MobileDeviceRepository.DeviceType)

\+ render(model : Map\<String, Object\>, out : Writer) : void

\- renderException(model : Map\<String, Object\>, out : Writer, template : String) : void

Role  
The VelocityRenderer class acts as the concrete implementation of the rendering engine specifically for Velocity templates within the Weblog and Content Subsystem. Its primary role is to take a generic Template object and a data model, and use the Apache Velocity engine to merge them, producing the final textual output (typically HTML) to a Writer. It handles the complexity of looking up the correct template rendition based on the device type, managing decorators, and rendering specific error pages if the Velocity engine encounters exceptions.

Interactions with other parts of the subsystem

* Renderer (Interface)  
  * Exact Relationship: Realization  
  * Cardinality: 1  
  * Reason: VelocityRenderer implements the Renderer interface, fulfilling the contract for rendering content in the system.  
* Template (Interface)  
  * Exact Relationship: Association  
  * Cardinality: 1  
  * Reason: The renderer holds a direct reference to the Template object (renderTemplate) it is responsible for rendering.  
  * RollerVelocity

**33\. TemplateRendition**

Attributes  
*(Note: As an interface, these are properties implied by the accessor methods. The inner Enums defined within the file also contain attributes.)*

TemplateRendition (Interface Properties)

* template : String  
* templateLanguage : TemplateLanguage  
* type : RenditionType  
  TemplateLanguage (Inner Enum)  
* readableName : String  
  Methods  
  TemplateRendition  
* \+ getTemplate() : String  
* \+ getTemplateLanguage() : TemplateLanguage  
* \+ getType() : RenditionType  
* \+ setTemplate(template : String) : void  
* \+ setTemplateLanguage(templateLanguage : TemplateLanguage) : void  
* \+ setType(type : RenditionType) : void  
  TemplateLanguage (Inner Enum)  
* \+ getReadableName() : String  
  Role  
  The TemplateRendition interface represents a specific version of a weblog template tailored for a particular context. Its primary role is to encapsulate the actual template code (source) along with metadata describing its language (e.g., Velocity) and its intended target device or output format (defined by RenditionType, such as STANDARD or MOBILE). This allows the rendering engine to dynamically select and render the appropriate template based on the user's device or request context.

	**Interactions:**

	Only enums relations, no specific relationships from this

**34\. CustomTemplateRendition**

Attributes  
Identification

\- {static} serialVersionUID : long

\- id : String

Associations

\- weblogTemplate : WeblogTemplate

Content & Metadata

\- template : String

\- type : RenditionType

\- templateLanguage : TemplateLanguage

Methods

\+ CustomTemplateRendition(template : WeblogTemplate, type : RenditionType)

\+ CustomTemplateRendition()

\+ getWeblogTemplate() : WeblogTemplate

\+ setWeblogTemplate(weblogTemplate : WeblogTemplate) : void

\+ getId() : String

\+ setId(id : String) : void

\+ getTemplate() : String

\+ setTemplate(template : String) : void

\+ getType() : RenditionType

\+ setType(type : RenditionType) : void

\+ toString() : String

\+ equals(other : Object) : boolean

\+ hashCode() : int

\+ getTemplateLanguage() : TemplateLanguage

\+ setTemplateLanguage(templateLanguage : TemplateLanguage) : void

Role in Subsystem  
The CustomTemplateRendition class represents a specific implementation (rendition) of a weblog template. In the rendering engine, a single "Template" (like "Weblog Standard") might need different code depending on the device accessing it (e.g., a Standard version vs. a Mobile version) or the language used. This class stores that specific template code string and links it to the parent WeblogTemplate. It interacts with the rest of the subsystem by providing the raw content that the template engine parses to generate HTML for the user.

Interactions with other parts of the subsystem

* WeblogTemplate  
  * Type: Class  
    * Relationship: Association (Bidirectional / Aggregation)  
    * Cardinality: Many-to-One (CustomTemplateRendition \* –- 1 WeblogTemplate)  
    * Why/Where it interacts:  
      * Why: A rendition cannot exist in isolation; it is a specific version of a parent WeblogTemplate.  
      * How: The constructor CustomTemplateRendition(WeblogTemplate template, ...) takes the parent as an argument and immediately calls weblogTemplate.addTemplateRendition(this), linking the two together. The weblogTemplate field stores this reference.  
  * RenditionType  
    * Type: Enum (or Class acting as Type)  
    * Relationship: Association  
    * Cardinality: Many-to-One (CustomTemplateRendition \* – 1 RenditionType)  
    * Why/Where it interacts:  
      * Why: To classify what device or context this specific template code is meant for (e.g., STANDARD, MOBILE).  
      * How: It is passed in the constructor and stored in the type field. The rendering engine checks this to decide which rendition to load for a specific user agent.  
  * TemplateLanguage  
    * Type: Enum (or Class acting as Type)  
    * Relationship: Association  
    * Cardinality: Many-to-One (CustomTemplateRendition \* – 1 TemplateLanguage)  
    * Why/Where it interacts:  
      * Why: To identify the syntax of the code stored in the template string (e.g., Velocity, Freemarker).  
      * How: Stored in the templateLanguage field. This helps the rendering engine choose the correct parser.  
  * TemplateRendition  
    * Type: Interface  
    * Relationship: Realization (Implementation)  
    * Cardinality: N/A (Inheritance)  
    * Why/Where it interacts:  
      * Why: This class implements the TemplateRendition interface.  
      * How: It provides concrete implementations for the methods defined in the interface, ensuring it adheres to the contract expected by the rendering system.

**35\. PageModel**

Attributes

* Private (-)  
  * log: Log (static)  
  * pageRequest: WeblogPageRequest  
  * urlStrategy: URLStrategy  
  * commentForm: WeblogEntryCommentForm  
  * requestParameters: Map\<String, String\[\]\>  
  * weblog: Weblog  
  * deviceType: DeviceType

  Methods

* PageModel()  
* getModelName(): String  
* init(initData: Map\<String, Object\>): void  
* getLocale(): String  
* getWeblog(): WeblogWrapper  
* isPermalink(): boolean  
* isSearchResults(): boolean  
* getWeblogEntry(): WeblogEntryWrapper  
* getWeblogPage(): ThemeTemplateWrapper  
* getWeblogCategory(): WeblogCategoryWrapper  
* getTags(): List\<String\>  
* getDeviceType(): String  
* getWeblogEntriesPager(): WeblogEntriesPager  
* getWeblogEntriesPager(catArgument: String): WeblogEntriesPager  
* getWeblogEntriesPagerByTag(tagArgument: String): WeblogEntriesPager  
* getCommentForm(): WeblogEntryCommentForm  
* getRequestParameter(paramName: String): String

  (All public except)

* getWeblogEntriesPager(catArgument: String, tagArgument: String): WeblogEntriesPager \-\> **private**  
  Role in Subsystem  
  The PageModel class is a crucial component in the content rendering process. Its primary role is to act as a data bridge between the incoming web request and the view template (e.g., a JSP or Velocity template). It gathers all necessary data for a specific page request-such as the weblog itself, the specific entry or category being viewed, date information, and request parameters and exposes this data to the rendering engine through a clean and organized API. It encapsulates the logic for determining what content to display (e.g., a single post, a page of posts for a specific day, or the latest posts).  
  Interactions with Other Classes  
  PageModel interacts with several other classes to fulfill its role.  
* Model:  
  * Relationship: Realization (Implements)  
  * Cardinality: PageModel implements one Model interface.  
  * Reason: It adheres to the Model contract, which requires getModelName() and init() methods. This allows the rendering framework to handle it polymorphically.  
* WeblogPageRequest:  
  * Relationship: Association  
  * Cardinality: PageModel has exactly one WeblogPageRequest. (1)  
  * Reason: The init() method receives and stores a WeblogPageRequest. This object contains all the parsed details from the incoming URL, such as the weblog handle, entry anchor, date, category, and tags, which PageModel uses extensively to fetch the correct content.  
* URLStrategy:  
  * Relationship: Association  
  * Cardinality: PageModel has exactly one URLStrategy. (1)  
  * Reason: It uses the URLStrategy to generate correct, site-specific URLs for various entities (like weblogs, entries, and categories) when they are wrapped. This is essential for creating links within the rendered page.  
* Weblog:  
  * Relationship: Association  
  * Cardinality: PageModel is associated with exactly one Weblog. (1)  
  * Reason: The PageModel is built to render a page for a specific Weblog. It holds a reference to the Weblog object to access its properties and associated content.  
* WeblogEntriesPager (and its subclasses):  
  * Relationship: Dependency  
  * Cardinality: PageModel creates and returns one WeblogEntriesPager. (1)  
  * Reason: In the getWeblogEntriesPager methods, PageModel acts as a factory. It analyzes the request details (permalink, date, etc.) to decide which specific pager implementation to instantiate (WeblogEntriesPermalinkPager, WeblogEntriesDayPager, etc.) and returns it. The view then uses this pager to iterate through and display weblog entries.  
* WeblogWrapper, WeblogEntryWrapper, WeblogCategoryWrapper, ThemeTemplateWrapper:  
  * Relationship: Dependency  
  * Cardinality: PageModel creates and returns many of these wrappers. (\*)  
  * Reason: PageModel does not return the raw data objects (POJOs) directly. Instead, it wraps them (e.g., Weblog is wrapped in WeblogWrapper) before returning them from methods like getWeblog() and getWeblogEntry(). This is an example of the Decorator pattern, where the wrapper adds new functionality—specifically, the ability to generate its own URL via the URLStrategy.  
* WeblogEntryCommentForm:  
  * Relationship: Association  
  * Cardinality: PageModel can have zero or one WeblogEntryCommentForm. (0..1)  
  * Reason: If a user submits a comment, the init() method receives a WeblogEntryCommentForm object containing the submitted data (and any validation errors). PageModel then makes this form available to the template so the form can be re-displayed with the user's data.


**36\. WeblogPageRequest**

Attributes  
Static Attributes

\- log : Log

\- PAGE\_SERVLET : String

Lightweight Attributes (URL Parsing Results)

\- context : String

\- weblogAnchor : String

\- weblogPageName : String

\- weblogCategoryName : String

\- weblogDate : String

\- tags : List\<String\>

\- pageNum : int

\- customParams : Map\<String, String\[\]\>

Heavyweight Attributes (Domain Objects)

\- weblogEntry : WeblogEntry

\- weblogPage : ThemeTemplate

\- weblogCategory : WeblogCategory

Page Hits (State Flags)

\- websitePageHit : boolean

\- otherPageHit : boolean

Methods

\+ WeblogPageRequest()

\+ WeblogPageRequest(request : HttpServletRequest)

\~ isValidDestination(servlet : String) : boolean

\- isValidDateString(dateString : String) : boolean

\+ getContext() : String

\+ setContext(context : String) : void

\+ getWeblogAnchor() : String

\+ setWeblogAnchor(weblogAnchor : String) : void

\+ getWeblogPageName() : String

\+ setWeblogPageName(weblogPage : String) : void

\+ getWeblogCategoryName() : String

\+ setWeblogCategoryName(weblogCategory : String) : void

\+ getWeblogDate() : String

\+ setWeblogDate(weblogDate : String) : void

\+ getPageNum() : int

\+ setPageNum(pageNum : int) : void

\+ getCustomParams() : Map\<String, String\[\]\>

\+ setCustomParams(customParams : Map\<String, String\[\]\>) : void

\+ getTags() : List\<String\>

\+ setTags(tags : List\<String\>) : void

\+ getWeblogEntry() : WeblogEntry

\+ setWeblogEntry(weblogEntry : WeblogEntry) : void

\+ getWeblogPage() : ThemeTemplate

\+ setWeblogPage(weblogPage : WeblogTemplate) : void

\+ getWeblogCategory() : WeblogCategory

\+ setWeblogCategory(weblogCategory : WeblogCategory) : void

\+ isWebsitePageHit() : boolean

\+ setWebsitePageHit(websitePageHit : boolean) : void

\+ isOtherPageHit() : boolean

\+ setOtherPageHit(otherPageHit : boolean) : void

Role  
This class acts as a Request Parser and Context Provider for the rendering engine. It translates a raw HttpServletRequest and its URL path (e.g., /entry/my-post, /date/20230101) into a structured object containing the specific domain objects (Entry, Category, Page) required to render the view. It encapsulates the logic for decoding URL structures and lazy-loading the corresponding database entities.

Interactions with other parts of the subsystem

WeblogRequest (doubts)

* Cardinality: 1 to 1 (Inheritance)  
  * Relationship: Inheritance (Generalization)  
  * Why/Where: WeblogPageRequest extends WeblogRequest. It relies on the parent class to parse the initial weblog handle and locale from the request before performing its own specific parsing for page details.  
    HttpServletRequest  
  * Cardinality: 1 to 1  
  * Relationship: Dependency  
  * Why/Where: Passed into the constructor. The class extracts the servlet path, path info, and query parameters from this standard Java EE object to populate its own attributes.  
    WeblogEntry  
  * Cardinality: 0..1  
  * Relationship: Association (Aggregation)  
  * Why/Where: Represents the specific blog post requested by the URL (if any). The class lazy-loads this object using the WeblogEntryManager when getWeblogEntry() is called.  
    ThemeTemplate (doubt)  
  * Cardinality: 0..1  
  * Relationship: Association (Aggregation)  
  * Why/Where: Represents the specific design template requested (e.g., a custom page). The class retrieves this from the Weblog's theme when getWeblogPage() is called.  
    WeblogCategory  
  * Cardinality: 0..1  
  * Relationship: Association (Aggregation)  
  * Why/Where: Represents the category of posts requested. The class lazy-loads this object using the WeblogEntryManager when getWeblogCategory() is called.  
    WeblogEntryManager  
  * Cardinality: 0..1  
  * Relationship: Dependency  
  * Why/Where: This is the service interface used inside getWeblogEntry() and getWeblogCategory() to fetch the actual data objects from the backend based on the parsed names/anchors.  
    WebloggerFactory  
  * Cardinality: 0..1  
  * Relationship: Dependency  
  * Why/Where: Used statically to obtain the instance of WeblogEntryManager.

**37\. WeblogFeedRequest**

Attributes  
Static Attributes

\- {static} log : Log

\- {static} FEED\_SERVLET : String

Lightweight Attributes

\- type : String

\- format : String

\- weblogCategoryName : String

\- tags : List\<String\>

\- page : int

\- excerpts : boolean

\- term : String

Heavyweight Attributes

\- weblogCategory : WeblogCategory

Methods

\+ WeblogFeedRequest()

\+ WeblogFeedRequest(request : HttpServletRequest)

\+ getType() : String

\+ setType(type : String) : void

\+ getFormat() : String

\+ setFormat(format : String) : void

\+ getWeblogCategoryName() : String

\+ setWeblogCategoryName(weblogCategory : String) : void

\+ getTags() : List\<String\>

\+ setTags(tags : List\<String\>) : void

\+ isExcerpts() : boolean

\+ setExcerpts(excerpts : boolean) : void

\+ getWeblogCategory() : WeblogCategory

\+ setWeblogCategory(weblogCategory : WeblogCategory) : void

\+ getPage() : int

\+ setPage(page : int) : void

\+ getTerm() : String

\+ setTerm(query : String) : void

Role  
The WeblogFeedRequest class acts as a specialized request parser and context holder for Roller weblog feeds (e.g., RSS, Atom). Its primary role is to interpret the URL structure (e.g., /roller-ui/rendering/feed/handle/type/format) and query parameters (like categories, tags, or search terms) to determine exactly what subset of weblog data is being requested. It encapsulates this criteria so the rendering engine can fetch the appropriate entries.

Interactions with other parts of the subsystem  
WeblogRequest (doubts)

* Type: Class  
* Relationship: Generalization (Inheritance)  
* Cardinality: 1  
* Why/Where: WeblogFeedRequest extends WeblogRequest. It inherits the base functionality for identifying the specific weblog (handle) and locale from the request, adding specific logic for feed-related parameters.

  WeblogCategory

* Type: Class  
* Relationship: Association  
* Cardinality: 0..1  
* Why/Where: The request may optionally filter the feed by a specific category. The weblogCategory attribute holds the reference to the WeblogCategory object corresponding to the weblogCategoryName parsed from the URL.

  WeblogEntryManager

* Type: Interface  
* Relationship: Dependency  
* Cardinality: 1  
* Why/Where: Used inside the getWeblogCategory() method. The class interacts with this manager to retrieve the actual WeblogCategory object from the database using the category name and the associated weblog.

  HttpServletRequest

* Type: Interface  
* Relationship: Dependency  
* Cardinality: 1  
* Why/Where: Passed to the constructor. The class parses the ServletPath, PathInfo, and request parameters from this object to populate its own attributes.

  WebloggerFactory

* Type: Class  
* Relationship: Dependency  
* Cardinality: 1  
* Why/Where: Used statically in getWeblogCategory() to obtain the entry point for the Roller backend (specifically to get the WeblogEntryManager).


**38.WeblogFeedRequest**

Attributes

* log : Log \-\> private  
* SEARCH\_SERVLET : String-\> public  
* query : String-\> private  
* pageNum : int-\> private  
* weblogCategoryName : String-\> private  
* weblogCategory : WeblogCategory-\> private  
  Methods (all public)  
* WeblogSearchRequest()  
* WeblogSearchRequest(request : HttpServletRequest)  
* getQuery() : String  
* setQuery(query : String) : void  
* getPageNum() : int  
* setPageNum(pageNum : int) : void  
* getWeblogCategoryName() : String  
* setWeblogCategoryName(weblogCategory : String) : void  
* getWeblogCategory() : WeblogCategory  
* setWeblogCategory(weblogCategory : WeblogCategory) : void  
  Role  
  The WeblogSearchRequest class acts as a specialized data transfer object and request parser for the weblog search functionality. Its primary role is to validate that an incoming HTTP request is intended for the search servlet and to extract relevant search parameters (such as the search query q, the page number page, and the category filter cat). It encapsulates this logic so that the rendering engine can easily access the search criteria without parsing the raw HttpServletRequest again.  
  Interactions with other parts of the subsystem

  WeblogRequest (Class) (doubt)  
* Cardinality: 1  
* Relationship: Inheritance (Generalization)  
* Interaction: WeblogSearchRequest extends WeblogRequest. It relies on the parent class to parse the common elements of a weblog request, such as the weblog handle and locale, via the super(request) call in the constructor.

  HttpServletRequest (Interface)

* Cardinality: 1  
* Relationship: Dependency  
* Interaction: This interface is passed to the constructor. The class uses it to retrieve the servlet path and extract query parameters (q, page, cat) to populate its attributes.

  WeblogCategory (Class)

* Cardinality: 0..1  
* Relationship: Association  
* Interaction: The class maintains a reference to a WeblogCategory object. This represents the specific category context in which the search is being performed. It is lazily loaded in getWeblogCategory().

  WeblogEntryManager (Interface)

* Cardinality: 1  
* Relationship: Dependency  
* Interaction: This manager is used within the getWeblogCategory() method. The class calls getWeblogCategoryByName on the manager to retrieve the actual WeblogCategory entity from the database using the parsed category name.

  WebloggerFactory (Class)

* Cardinality: 1  
* Relationship: Dependency  
* Interaction: The class uses WebloggerFactory.getWeblogger() in getWeblogCategory() to obtain the Weblogger instance, which in turn provides access to the WeblogEntryManager.

  URLUtilities (Class)

* Cardinality: 1  
* Relationship: Dependency  
* Interaction: The class uses the static method URLUtilities.decode() in the constructor to properly decode the cat (category) parameter from the URL.

  Log (Interface)

* Cardinality: 1  
* Relationship: Association  
* Interaction: A static Log instance is used throughout the class to log debug messages during parsing and error messages if category lookup fails.

  InvalidRequestException (Class)

* Cardinality: 0..\*  
* Relationship: Dependency  
* Interaction: This exception is thrown by the constructor if the incoming request is not bound for the correct search servlet or if the path info is invalid.

**39\. Pager (interface)**  
Methods:  
\+ getHomeLink(): String  
\+ getHomeName(): String  
\+ getNextLink(): String  
\+ getNextName(): String  
\+ getPrevLink(): String  
\+ getPrevName(): String  
\+ getItems(): List\<T\>

Role in Subsystem

The Pager\<T\> interface establishes a generic and reusable contract for pagination within the Roller Weblogger application. Its primary role is to provide a standardized way for the rendering engine to handle collections of objects (like weblog entries, comments, users, etc.) that are too large to be displayed on a single page. It decouples the presentation layer (the templates) from the business logic required to fetch subsets of data and create navigation links, ensuring a consistent user experience for pagination across different types of content.

Interactions with other parts of the subsystem

* AbstractPager  
  * Relationship: Implementation  
  * Cardinality: 1..1 (The AbstractPager class implements the Pager interface).  
  * Interaction: AbstractPager provides a foundational, partial implementation of the Pager interface. It handles the common logic for creating "Home," "Next," and "Previous" links based on a page number. Concrete pager classes extend AbstractPager to inherit this common functionality and avoid code duplication.  
* Concrete Pager Classes (e.g., CommentsPager, UsersPager, WeblogsPager, WeblogEntriesListPager)  
  * Relationship: Implementation (via AbstractPager)  
  * Cardinality: 1..\* (Many concrete classes implement the Pager contract by extending AbstractPager).  
  * Interaction: These classes provide specific implementations for fetching a particular type of data (e.g., UsersPager fetches User objects). They implement the getItems() method to retrieve the relevant data for the current page and rely on the inherited methods from AbstractPager for link generation.  
* Rendering Engine (e.g., PageServlet, Velocity/JSP Templates)  
  * Relationship: Association / Dependency  
  * Cardinality: 1..\* (A single Pager instance is used by the rendering components for a specific request).  
  * Interaction: The rendering engine uses a Pager object to display a list of items and the navigation controls. In templates (like .vm files), developers access methods like pager.getItems() to loop through the content for the current page and pager.getNextLink() or pager.getPrevLink() to create the "Next" and "Previous" buttons for the user.

**40\. WeblogEntriesPager (interface)**

Methods

* getEntries() : Map\<Date, ? extends Collection\>  
* getHomeLink() : String  
* getHomeName() : String  
* getNextLink() : String  
* getNextName() : String  
* getPrevLink() : String  
* getPrevName() : String  
* getNextCollectionLink() : String  
* getNextCollectionName() : String  
* getPrevCollectionLink() : String  
* getPrevCollectionName() : String  
  Role  
  The WeblogEntriesPager interface acts as a contract for the rendering mechanism to navigate and display lists of weblog entries. It abstracts the logic required to paginate through content (e.g., "Next Page", "Previous Page") and navigate between logical collections (e.g., "Next Month", "Previous Day"). It ensures that the rendering layer receives entries grouped by date, facilitating the display of chronological blog posts regardless of the underlying data source (search results, category archives, or main weblog feeds).  
  Interactions with other parts of the subsystem

  WeblogEntryWrapper  
* Type: Class  
* Relationship: Dependency  
* Cardinality: 0..\*  
* Reason: The pager is designed specifically to manage and return collections of WeblogEntryWrapper objects via the getEntries() method for display in templates.

  Date

* Type: Class  
* Relationship: Dependency  
* Cardinality: 0..\*  
* Reason: Used as the key in the map returned by getEntries() to organize the wrapped entries chronologically (e.g., grouping posts by day).

**41\. WelogEntriesPermalinkPager**

    // Attributes

    \- static final Log log

    \- WeblogEntry currEntry

    \- WeblogEntry nextEntry

    \- WeblogEntry prevEntry

    \- Map\<Date, List\<WeblogEntryWrapper\>\> entries

    // Methods

    \+ WeblogEntriesPermalinkPager(URLStrategy strat, Weblog weblog, String locale, String pageLink, String entryAnchor, String dateString, String catName, List\<String\> tags, int page)

    \+ getEntries() : Map\<Date, List\<WeblogEntryWrapper\>\>

    \+ getHomeLink() : String

    \+ getHomeName() : String

    \+ getNextLink() : String

    \+ getNextName() : String

    \+ getPrevLink() : String

    \+ getPrevName() : String

    \- getNextEntry() : WeblogEntry

    \- getPrevEntry() : WeblogEntry

	Role

	The WeblogEntriesPermalinkPager class is responsible for managing the display of a single weblog entry, typically when a user accesses an entry directly via its permalink. It fetches the specific entry identified by its anchor and provides navigation links to the chronologically next and previous published entries within the same weblog. This class is a specialized pager that focuses on individual entry views, contrasting with pagers that might display lists of entries (e.g., by month, day, or latest). It ensures that the content for a permalink view is correctly retrieved, wrapped for presentation, and that contextual navigation is available.

Interactions with Other Classes

* AbstractWeblogEntriesPager  
  * Relationship: Inheritance. WeblogEntriesPermalinkPager extends AbstractWeblogEntriesPager.  
  * Cardinality: WeblogEntriesPermalinkPager is a specialized type of AbstractWeblogEntriesPager.  
  * Why/Where it interacts: It inherits common functionalities for weblog entry pagers, such as URL creation logic (createURL), internationalization utilities (messageUtils), and common constructor parameters (strat, weblog, locale, pageLink, entryAnchor, dateString, catName, tags, page). This promotes code reuse and maintains a consistent structure for all weblog entry pagers.  
* WeblogEntry  
  * Relationship: Association. The WeblogEntriesPermalinkPager holds references to currEntry, nextEntry, and prevEntry of type WeblogEntry.  
  * Cardinality: 1 (for currEntry), 0..1 (for nextEntry and prevEntry).  
  * Why/Where it interacts: This class's primary function is to display and navigate between WeblogEntry objects. It fetches the current entry, and then determines the next and previous entries based on the current one's publication time and category. It relies on WeblogEntry properties like anchor, pubTime, status, and title.  
* WeblogEntryWrapper  
  * Relationship: Association. The entries map within the pager stores List\<WeblogEntryWrapper\>.  
  * Cardinality: 0..\* WeblogEntryWrapper objects are contained within the entries map.  
  * Why/Where it interacts: WeblogEntryWrapper is used to encapsulate WeblogEntry objects, providing a layer that adds presentation-specific methods and access to the URLStrategy without modifying the core WeblogEntry POJO. The pager wraps the raw WeblogEntry objects before making them available for rendering.  
* Weblog  
  * Relationship: Association. The Weblog object is passed in the constructor and stored as weblog.  
  * Cardinality: 1\.  
  * Why/Where it interacts: The pager operates within the context of a specific weblog. It needs the Weblog object to retrieve entries (via WeblogEntryManager), determine the weblog's locale and timezone, and construct URLs specific to that weblog.  
* URLStrategy  
  * Relationship: Association. The URLStrategy object is passed in the constructor and stored as urlStrategy.  
  * Cardinality: 1\.  
  * Why/Where it interacts: It is used to generate consistent and correct URLs for the home link, and the next/previous entry links, ensuring that navigation within the weblog is properly handled.  
* WebloggerFactory  
  * Relationship: Dependency (static method calls).  
  * Cardinality: N/A (static factory).  
  * Why/Where it interacts: It's used to obtain the central Weblogger instance, which acts as a facade to access various business managers, including the WeblogEntryManager.  
* Weblogger  
  * Relationship: Dependency (obtained from WebloggerFactory).  
  * Cardinality: 1 (instance).  
  * Why/Where it interacts: Provides access to the WeblogEntryManager to perform operations like fetching weblog entries.  
* WeblogEntryManager  
  * Relationship: Dependency (obtained from Weblogger).  
  * Cardinality: 1 (instance).  
  * Why/Where it interacts: This manager is crucial for fetching the WeblogEntry by its anchor (getWeblogEntryByAnchor) and for retrieving the chronologically getNextEntry and getPreviousEntry.  
* Log (from Apache Commons Logging)  
  * Relationship: Dependency.  
  * Cardinality: 1 (static instance).  
  * Why/Where it interacts: Used for logging errors that occur during entry fetching or other operations within the pager, aiding in debugging and monitoring.  
* Utilities  
  * Relationship: Dependency (static method call).  
  * Cardinality: N/A (static utility).  
  * Why/Where it interacts: Used for utility functions such as truncateNicely to format the titles of next/previous entries for display in navigation links, ensuring they fit well within the UI

**42\. WeblogEntriesListPager**  
**Attributes**  
\- locale : String  
\- sinceDays : int  
\- length : int  
\- queryWeblog : Weblog  
\- queryUser : User  
\- queryCat : String  
\- queryTags : List\<String\>  
\- entries : List\<WeblogEntryWrapper\>  
\- more : boolean  
\- lastUpdated : Date

**Methods**  
\+ getItems() : List\<WeblogEntryWrapper\>  
\+ hasMoreItems() : boolean  
\+ getLastUpdated() : Date

**Role**

* WeblogEntriesListPager is a UI-layer pagination component responsible for fetching, filtering, and paginating published weblog entries and preparing them for rendering in views (pages, feeds, archives).


	  
	**Interaction with other parts of the subsystem (NEED TO CONFIRM)**  
	1\. WeblogEntryManager  
	2\. WeblogEntrySearchCriteria  
	3\. WeblogEntry  
	4\. WeblogEntryWrapper  
	**5\. Weblog**  
	**6\. User**  
**43\. RollerVelocity**  
     	 **Attributes**  
	\- VELOCITY\_CONFIG : String  
	\- velocityEngine : VelocityEngine  
	  
	**Methods**  
	\+ getEngine() : VelocityEngine  
	\+ getTemplate(String name) : Template  
	\+ getTemplate(String name, MobileDeviceRepository.DeviceType deviceType) : Template  
	\+ getTemplate(String name, String encoding) : Template  
	\+ getTemplate(String name, MobileDeviceRepository.DeviceType deviceType, String encoding) : Template  
	  
	**Role**

* The RollerVelocity class is a Velocity template engine provider and configuration manager for Apache Roller.  
*  It acts as a singleton wrapper around Apache Velocity, providing centralized initialization, configuration, and access to the Velocity rendering engine.   
* This is a critical infrastructure component within the Rendering Engine subsystem that enables template-based content rendering.

	**Interaction with other parts of the subsystem**

	1\. Template  
	2\. WebloggerConfig	  
	3\. RollerContext  
	4\. MobileDeviceRepository	

**44\. ThemeManager (INTERFACE)**  
	**Methods**  
	\+ getTheme(String id) : SharedTheme  
	\+ getTheme(Weblog weblog) : WeblogTheme  
	\+ getEnabledThemesList() : List\<SharedTheme\>  
	\+ importTheme(Weblog website, SharedTheme theme,boolean skipStylesheet)	  
	\+ reLoadThemeFromDisk(String reloadTheme) : boolean  
	

	**Role**

* The ThemeManager interface defines the business layer contract for managing weblog themes in Apache Roller.   
* It provides operations for retrieving, importing, and managing both shared and custom themes, acting as the central theme management authority within the Weblog and Content Subsystem.   
* This interface abstracts theme operations from their implementation details.  
  **Interaction with other parts of the subsystem**  
  1\. WeblogTheme  
  2\. SharedTheme  
  3\. Weblog

**45\.** 

# Anagha

**6\. Tagging and categorization**

1. **WeblogEntry**  
2. **Weblog**  
     
3. **WeblogCategory**  
   **Attributes:**  
     
   Database/ID  
   \- id: String

   Content/Display Info  
   \- name: String  
   \- description: String  
   \- image: String  
   \- position: int

   Associations  
   \- weblog: Weblog

   Static/Constant  
   \+ serialVersionUID: long  
   

	**Methods:**  
	  
Constructors  
\+ WeblogCategory()  
\+ WeblogCategory(weblog: Weblog, name: String, description: String, image: String)

Business/Utility Methods  
\+ calculatePosition(): void  
	\+ retrieveWeblogEntries(publishedOnly: boolean): List\<WeblogEntry\> throws WebloggerException  
	\+ isInUse(): boolean

Object Overrides  
	\+ toString(): String  
	\+ equals(other: Object): boolean  
	\+ hashCode(): int  
	\+ compareTo(other: WeblogCategory): int

Getters / Setters  
	\+ getId(): String  
	\+ setId(id: String): void  
	\+ getName(): String  
\+ setName(name: String): void  
	\+ getDescription(): String  
	\+ setDescription(description: String): void  
	\+ getPosition(): int  
	\+ setPosition(position: int): void  
	\+ getImage(): String  
	\+ setImage(image: String): void  
	\+ getWeblog(): Weblog  
	\+ setWeblog(weblog: Weblog): void  
	  
**Role:**  
WeblogCategory represents a category for blog entries. It helps organize content within a weblog, allows retrieval of weblog entries by category, and tracks display order and visual representation (image). It is key for content classification and rendering in the weblog.

**Classes it interacts with:**

* Weblog

  * Type: Class

  * Relationship: Association (composition-like, as a category belongs to exactly one weblog)

  * Cardinality: WeblogCategory "1" \-- "1" Weblog

  * Why/Where it interacts: WeblogCategory needs the parent Weblog to calculate position, add itself to the weblog's list of categories, and retrieve entries.

* WeblogEntry

  * Type: Class

  * Relationship: Dependency

  * Cardinality: WeblogCategory uses List\<WeblogEntry\>

  * Why/Where it interacts: For retrieving entries in this category through retrieveWeblogEntries().

* WebloggerFactory 

  * Type: Classes

  * Relationship: Dependency

  * Cardinality: WeblogCategory calls methods, no direct ownership

  * Why/Where it interacts: Used internally to fetch entries and check if the category is in use.

* Utilities

  * Type: Class (utility)

  * Relationship: Dependency

  * Cardinality: Used in setName() and setDescription()

  * Why/Where it interacts: Removes HTML from input strings for safe storage/display.  
      
4. **WeblogEntryTag**

	**Attributes:**

Static / Logging

\- log: Log

Static / Constant

\+ serialVersionUID: long

Database / Identification

\- id: String

Associations / Links

\- website: Weblog

\- weblogEntry: WeblogEntry

User / Creator Info

\- userName: String

Tag Info / Content

\- name: String

\- time: Timestamp

**Methods:**

Constructors

\+ WeblogEntryTag()

\+ WeblogEntryTag(id: String, website: Weblog, weblogEntry: WeblogEntry, user: User, name: String, time: Timestamp)

Getters / Setters

\+ getId(): String

\+ setId(id: String): void

\+ getWeblog(): Weblog

\+ setWeblog(website: Weblog): void

\+ getWeblogEntry(): WeblogEntry

\+ setWeblogEntry(data: WeblogEntry): void

\+ getUser(): User

\+ getCreatorUserName(): String

\+ setCreatorUserName(userName: String): void

\+ getName(): String

\+ setName(name: String): void

\+ getTime(): Timestamp

\+ setTime(tagTime: Timestamp): void

Object Overrides / Utility

\+ toString(): String

\+ equals(other: Object): boolean

\+ hashCode(): int

**Role:**  
 WeblogEntryTag represents a tag for a weblog entry, allowing entries to be classified, filtered, and searched by keywords. It supports features like content organization, tag-based navigation, and analytics. Each tag belongs to a single blog entry and records the user who created it and the time it was added.

**Classes it interacts with:**

* Weblog

  * Type: Class

  * Relationship: Association (composition-like, each tag is linked to a specific weblog)

  * Cardinality: WeblogEntryTag "1" \-- "1" Weblog

  * Why / Where it interacts: Provides the blog context for the tag; needed when fetching or filtering tags for a specific weblog.

* WeblogEntry

  * Type: Class

  * Relationship: Association (composition-like, each tag belongs to a single entry)

  * Cardinality: WeblogEntryTag "1" \-- "1" WeblogEntry

  * Why / Where it interacts: Tags are directly linked to entries; used in retrieval, display, and filtering of tagged content.

* User

  * Type: Class

  * Relationship: Dependency

  * Cardinality: Each tag stores userName and can fetch User object

  * Why / Where it interacts: Records which user created the tag.

* WebloggerFactory

  * Type: Class

  * Relationship: Dependency

  * Cardinality: Used internally to fetch User object

  * Why / Where it interacts: Helps resolve userName to full User object for further operations.

* Log / LogFactory

  * Type: Class

  * Relationship: Dependency

  * Cardinality: Static logging instance

  * Why / Where it interacts: Used to log errors, e.g., if fetching User fails.

5. **WeblogEntryTagAggregate**  
   **Attributes:**  
     
   Static / Constant

   \+ serialVersionUID: long

   Database / Identification

   \- id: String

   Content / Tag Info

   \- name: String  
   \- total: int  
   \- lastUsed: Timestamp

   Associations / Links

   \- website: Weblog

	**Methods:**

Constructors  
\+ WeblogEntryTagAggregate()  
\+ WeblogEntryTagAggregate(id: String, website: Weblog, name: String, total: int)	

Getters / Setters  
\+ getId(): String  
\+ setId(id: String): void  
\+ getWeblog(): Weblog  
\+ setWeblog(website: Weblog): void  
	\+ getName(): String  
	\+ setName(name: String): void  
	\+ getTotal(): int  
\+ setTotal(total: int): void  
\+ getLastUsed(): Timestamp  
\+ setLastUsed(lastUsed: Timestamp): void

Object Overrides / Utility  
\+ toString(): String  
\+ equals(other: Object): boolean  
\+ hashCode(): int

**Role:**  
 WeblogEntryTagAggregate is an aggregate class for tags, storing summary statistics about tag usage in a weblog. It helps in generating analytics, tag clouds, or popular tags lists. It does not manage individual entries directly but complements WeblogEntryTag by providing aggregated information for the blog.

**Classes it interacts with and relationships:**

1. Weblog

   * Type: Class

   * Relationship: Association (each tag aggregate belongs to a single weblog)

   * Cardinality: WeblogEntryTagAggregate "1" \-- "1" Weblog

   * Why / Where it interacts: The aggregate is scoped to a particular weblog, so it stores a reference to its weblog for filtering and aggregation purposes.

**7\. Media management**

1. **MediaFile**   
   

     **Attributes:**

// Identifiers / basic metadata

\- id: String

\- name: String

\- description: String

\- copyrightText: String

// Flags / booleans

\- isSharedForGallery: Boolean

// Size / dimensions

\- length: long

\- width: int

\- height: int

\- thumbnailHeight: int

\- thumbnailWidth: int

// Content / type / path

\- contentType: String

\- originalPath: String

// Timestamps / audit

\- dateUploaded: Timestamp

\- lastUpdated: Timestamp

// Ownership / creator / associations

\- creatorUserName: String

\- weblog: Weblog

\- directory: MediaFileDirectory

// Content storage / streams

\- is: InputStream

\- content: FileContent

\- thumbnail: FileContent

// Tag management

\- tagSet: Set

\- removedTags: Set

\- addedTags: Set

**Methods:**

\+ MediaFile()  
\+ getId(): String  
\+ setId(String): void  
\+ getName(): String  
\+ setName(String): void  
\+ getDescription(): String  
\+ setDescription(String): void  
\+ getCopyrightText(): String  
\+ setCopyrightText(String): void  
\+ getSharedForGallery(): Boolean  
\+ setSharedForGallery(Boolean): void  
\+ getLength(): long  
\+ setLength(long): void  
\+ getDateUploaded(): Timestamp  
\+ setDateUploaded(Timestamp): void  
\+ getLastModified(): long  
\+ getLastUpdated(): Timestamp  
\+ setLastUpdated(Timestamp): void  
\+ getDirectory(): MediaFileDirectory  
\+ setDirectory(MediaFileDirectory): void  
\+ getTags(): Set  
\- setTags(Set): void  
\+ addTag(String): void  
\+ onRemoveTag(String): void  
\+ getAddedTags(): Set  
\+ getRemovedTags(): Set  
\+ updateTags(List): void  
\+ getTagsAsString(): String  
\+ setTagsAsString(String): void  
\+ getContentType(): String  
\+ setContentType(String): void  
\+ getPath(): String  
\+ getInputStream(): InputStream  
\+ setInputStream(InputStream): void  
\+ setContent(FileContent): void  
\+ isImageFile(): boolean  
\+ getPermalink(): String  
\+ getThumbnailURL(): String  
\+ getCreatorUserName(): String  
\+ setCreatorUserName(String): void  
\+ getCreator(): User  
\+ getOriginalPath(): String  
\+ setOriginalPath(String): void  
\+ getWeblog(): Weblog  
\+ setWeblog(Weblog): void  
\+ getWidth(): int  
\+ setWidth(int): void  
\+ getHeight(): int  
\+ setHeight(int): void  
\+ getThumbnailInputStream(): InputStream  
\+ setThumbnailContent(FileContent): void  
\+ getThumbnailHeight(): int  
\+ getThumbnailWidth(): int  
\- figureThumbnailSize(): void  
\+ toString(): String  
\+ equals(Object): boolean  
\+ hashCode(): int

**Role**

MediaFile represents an uploaded media asset (image, document, theme resource, or migrated file) associated with a weblog. It encapsulates metadata such as name, description, creator, timestamps, content type, size, and dimensions, along with storage references (directory and FileContent objects for primary and thumbnail data) and tag-related state required by the media subsystem. As a lightweight domain object, it serves managers, rendering logic, and URL strategies by providing a consistent reference to media without containing storage implementation details.

**How it interacts with other classes:**

**MediaFileDirectory (pojos)**

* Relationship: Aggregation (MediaFile references a directory, directory is a standalone object shared across files)  
* Cardinality: MediaFile 1 → MediaFileDirectory 1  
* Where/why: field private MediaFileDirectory directory; methods getDirectory(), setDirectory(...), getPath() (calls getDirectory().getName()). Directory provides the logical namespace/path for the file.

**FileContent (pojos)**

* Relationship: Aggregation (MediaFile holds references to content objects that store bytes; not created by MediaFile)  
* Cardinality: MediaFile 1 → FileContent 0..1 (content) and MediaFile 1 → FileContent 0..1 (thumbnail)  
* Where/why: fields private FileContent content, private FileContent thumbnail; methods setContent(...), setThumbnailContent(...), getInputStream() (delegates to content.getInputStream()), getThumbnailInputStream() (delegates to thumbnail.getInputStream()). FileContent is the backing byte store for the file and thumbnail.

**MediaFileTag (pojos)**

* Relationship: Composition (MediaFile logically owns its tag objects in tagSet)  
* Cardinality: MediaFile 1 → MediaFileTag 0..\*  
* Where/why: field private Set\<MediaFileTag\> tagSet; methods getTags(), addTag(...) (creates new MediaFileTag(name, this)), setTagsAsString(...), updateTags(...), getTagsAsString(), setTags(...). Tags are created/managed by the MediaFile and persisted via the manager.

**MediaFileManager (business)**

* Relationship: Dependency (MediaFile uses MediaFileManager services at runtime)  
* Cardinality: MediaFile 1 → MediaFileManager 1 (service)  
* Where/why: updateTags(...) calls WebloggerFactory.getWeblogger().getMediaFileManager() then removeMediaFileTag(...); figureThumbnailSize() reads constants MediaFileManager.MAX\_WIDTH/MAX\_HEIGHT. MediaFile relies on the manager for tag removal and manager constants.

**Weblog (pojos)**

* Relationship: Aggregation / contextual association (MediaFile references the owning weblog)  
* Cardinality: MediaFile 1 → Weblog 0..1  
* Where/why: field private Weblog weblog; methods getWeblog(), setWeblog(...), addTag(...) (uses getWeblog().getLocaleInstance()), getPermalink(), getThumbnailURL() (passed to UrlStrategy). Weblog supplies locale for tag normalization and context for URL generation.

**User (via UserManager)**

* Relationship: Dependency (MediaFile resolves a User through UserManager; does not hold a direct User reference)  
* Cardinality: MediaFile 1 → User 0..1  
* Where/why: method getCreator() calls WebloggerFactory.getWeblogger().getUserManager().getUserByUserName(getCreatorUserName()). MediaFile stores creator username and lazily resolves User when needed.

**WebloggerFactory (business)**

* Relationship: Dependency (singleton factory used to obtain managers and url strategy)  
* Cardinality: MediaFile 1 → WebloggerFactory 1 (singleton)  
* Where/why: calls like WebloggerFactory.getWeblogger() appear in getPermalink(), getThumbnailURL(), getCreator(), and updateTags() to obtain managers and UrlStrategy. MediaFile depends on WebloggerFactory to reach infrastructure services.

**UrlStrategy (returned by WebloggerFactory.getWeblogger().getUrlStrategy())**

* Relationship: Dependency  
* Cardinality: MediaFile 1 → UrlStrategy 1 (service)  
* Where/why: getPermalink() and getThumbnailURL() call getUrlStrategy().getMediaFileURL(...) and getMediaFileThumbnailURL(...) to build public URLs for rendering.

**Utilities**

* Relationship: Dependency (utility helper class)  
* Cardinality: MediaFile 1 → Utilities 1 (static utility)  
* Where/why: addTag(...) and updateTags(...) use Utilities.normalizeTag(...); setTagsAsString(...) uses Utilities.splitStringAsTags(...). Utilities provides tag parsing/normalization logic.

**MediaFileType (enum)**

* Relationship: Dependency (type-check helper)  
* Cardinality: MediaFile 1 → MediaFileType 1  
* Where/why: isImageFile() compares getContentType() with MediaFileType.IMAGE.getContentTypePrefix() to decide image behavior.

**UUIDGenerator**

* Relationship: Dependency (used at construction for id generation)  
* Cardinality: MediaFile 1 → UUIDGenerator 1 (utility)  
* Where/why: field initialization private String id \= UUIDGenerator.generateUUID(); provides a default id for the POJO.

2.  **MediaFileComparator**

**Attributes:**  
		enum MediaFileComparatorType { NAME, TYPE, DATE\_UPLOADED }

* MediaFileComparatorType type

**Methods:**

* MediaFileComparator(MediaFileComparatorType type)  
* int compare(MediaFile file1, MediaFile file2)  
  **Role:**  
    
  MediaFileComparator is a lightweight utility comparator that orders or presents media files according to a chosen attribute (name, content type, or upload date). It is part of media management and is used wherever collections of org.apache.roller.weblogger.pojos.MediaFile must be sorted before being returned to callers or rendered.

**Interactions with other classes:**

* MediaFile  
  Path: org/apache/roller/weblogger/pojos/MediaFile.java  
  Exact relationship: Dependency (uses)  
  Cardinality: MediaFileComparator \-\> MediaFile \= 2  
  Why/where: The comparator directly accesses MediaFile getters such as getName(), getContentType(), and getDateUploaded() inside compare(...) to compute ordering. It provides the comparison criteria whenever two MediaFile objects must be ordered.


* MediaFileManager  
  Path: org/apache/roller/weblogger/business/MediaFileManager.java  
  Exact relationship: Dependency (uses)  
  Cardinality: MediaFileComparator 0..\* — MediaFileManager 0..\*  
  Why/where: Implementations of MediaFileManager call sorting routines when returning lists of media files (for example, listMediaFiles()). They depend on MediaFileComparator to ensure consistent ordering before handing results to callers or views.


* FileContentManager  
  Path: org/apache/roller/weblogger/business/FileContentManager.java  
  Exact relationship: Dependency (uses)  
  Cardinality: MediaFileComparator 0..\* — FileContentManager 0..\*  
  Why/where: When FileContentManager builds lists of file contents or search results for presentation, it may use the comparator to sort MediaFile results, such as grouping by content type or ordering by most recent upload.


* MediaFileDirectory  
  Path: org/apache/roller/weblogger/pojos/MediaFileDirectory.java  
  Exact relationship: Dependency (uses)  
  Cardinality: MediaFileComparator 0..\* — MediaFileDirectory 0..\*  
  Why/where: Directory and listing objects hold collections of MediaFile instances. When returning or rendering directory contents, code sorts the contained MediaFile list using MediaFileComparator to present files in the requested order.


* JPAMediaFileManagerImpl  
  Path: org/apache/roller/weblogger/business/jpa/JPAMediaFileManagerImpl.java  
  Exact relationship: Dependency (uses)  
  Cardinality: MediaFileComparator 0..\* — JPAMediaFileManagerImpl 0..\*  
  Why/where: This concrete MediaFileManager implementation fetches data from persistence and then sorts results in memory before returning them. It depends on MediaFileComparator to apply uniform comparison logic based on name, type, or upload date.

3. **MediaFileDirectory**  
   **Attributes:**

    \- id: String

    \- name: String

    \- description: String

    \- weblog: Weblog

    \- mediaFiles: Set\<MediaFile\>

	**Methods**:

    \+ MediaFileDirectory()

    \+ MediaFileDirectory(weblog: Weblog, name: String, desc: String)

    \+ isEmpty(): boolean

    \+ getId(): String

    \+ setId(id: String): void

    \+ getName(): String

    \+ setName(name: String): void

    \+ getDescription(): String

    \+ setDescription(description: String): void

    \+ getWeblog(): Weblog

    \+ setWeblog(weblog: Weblog): void

    \+ getMediaFiles(): Set\<MediaFile\>

    \+ setMediaFiles(mediaFiles: Set\<MediaFile\>): void

    \+ hasMediaFile(name: String): boolean

    \+ getMediaFile(name: String): MediaFile

    \+ equals(other: Object): boolean

    \+ hashCode(): int

	**Role:**

Role: represents a named folder/bucket of media files belonging to a single weblog. It organizes media content (images, documents, etc.) so the rendering layer, upload handlers, and management APIs can find, list, and validate files associated with a weblog.

Responsibilities: stores metadata (id, name, description), maintains a reference to the owning weblog, and holds the collection of contained MediaFile objects; provides simple helper operations such as checking whether a file exists, looking up a file by name, and determining whether the folder is empty.

Where used: media upload and download workflows, media listing in the admin UI, template rendering (resolving media paths through Weblog/MediaFile), and persistence layers or managers responsible for creating, updating, and organizing media files and folders.

**How it interacts with other classes:**

* Weblog

Symbol & file: org.apache.roller.weblogger.pojos.Weblog — Weblog.java

Exact relationship: composition (Weblog composes MediaFileDirectory)

Cardinality: Weblog 1 — 0..\* MediaFileDirectory. Each MediaFileDirectory belongs to exactly one Weblog, while a Weblog may have zero or many media directories.

Why/where it interacts: MediaFileDirectory stores a reference to its owning Weblog, and its constructor adds itself to weblog.getMediaFileDirectories(). Weblog uses these directories to enumerate media folders for rendering, admin UI listing, and MediaFileManager operations (for example, createMediaFile flows from weblog to selecting a directory). This relationship enables templates and managers to locate directories for a specific weblog and enforce per-weblog scoping.

* MediaFile

Symbol & file: org.apache.roller.weblogger.pojos.MediaFile — MediaFile.java

Exact relationship: composition (MediaFileDirectory composes MediaFile)

Cardinality: MediaFileDirectory 0..\* — 1 MediaFile. A directory may contain many media files, and each MediaFile belongs to exactly one directory.

Why/where it interacts: MediaFileDirectory holds a Set of MediaFile objects and provides helper methods such as hasMediaFile() and getMediaFile(), which are used during upload validation and lookups. MediaFile maintains a reference to its directory through setDirectory()/getDirectory() and uses directory.getName() when computing its path (for example in MediaFile.getPath()). MediaFileManager create and update operations work with both objects together.

* MediaFileManager

Symbol & file: org.apache.roller.weblogger.business.MediaFileManager — MediaFileManager.java

Exact relationship: association (service usage)

Cardinality: MediaFileManager 1 — 0..\* MediaFileDirectory. The manager operates on many directories and media files across different weblogs.

Why/where it interacts: MediaFileManager implementations such as JPAMediaFileManagerImpl create and update media files and may initialize directories during setup or migration. The manager uses directory metadata and methods like getMediaFiles() and hasMediaFile() to validate uploads, persist changes, and generate media URLs (often via URLStrategy).

4. **MediaFileDirectoryComparator**  
   **Attriutes:**  
   	@startuml  
   package org.apache.roller.weblogger.pojos {  
     class MediaFileDirectoryComparator {  
       \+enum DirectoryComparatorType { NAME }  
       \~DirectoryComparatorType type  
       \+MediaFileDirectoryComparator(DirectoryComparatorType type)  
       \+int compare(MediaFileDirectory dir1, MediaFileDirectory dir2)  
     }  
     MediaFileDirectoryComparator ..|\> java.util.Comparator  
   }  
   @enduml

	**Role:**

	Acts as a sorting utility that provides a name-based comparator for media file directories, allowing collections of MediaFileDirectory objects to be ordered predictably. It does not store any domain state and exists purely to supply comparison behavior when directory lists need to be displayed, ordered, or processed by managers or the user interface.

**How it interacts with other classes:**

* MediaFileDirectory  
  Symbol/file: org.apache.roller.weblogger.pojos.MediaFileDirectory  
  Exact relationship: dependency (uses)  
  Cardinality: MediaFileDirectoryComparator to MediaFileDirectory is 0..\*. The comparator can be applied to any number of MediaFileDirectory instances, comparing two at a time.  
  Why/where it interacts: The comparator depends on MediaFileDirectory in its compare method signature and calls getName() to decide ordering. Any sorting of directory collections returned by weblogs or managers passes MediaFileDirectory instances to this comparator.


* Weblog  
  Symbol/file: org.apache.roller.weblogger.pojos.Weblog  
  Exact relationship: dependency  
  Cardinality: Weblog to MediaFileDirectoryComparator is 0..1. A Weblog or its helper code may optionally instantiate or use this comparator when ordering directories.  
  Why/where it interacts: Weblog exposes getMediaFileDirectories() as a collection. When this collection must be displayed or processed in a predictable order, such as in the admin UI, API output, or export/import flows, calling code uses MediaFileDirectoryComparator to sort directories by name.


* MediaFile  
  Symbol/file: org.apache.roller.weblogger.pojos.MediaFile  
  Exact relationship: indirect association through MediaFileDirectory  
  Cardinality: MediaFileDirectory to MediaFile is 0..\*. A directory contains many media files.  
  Why/where it interacts: The comparator does not reference MediaFile directly, but sorting MediaFileDirectory objects affects the order in which groups of MediaFile instances are presented or processed, for example in UI listings or feed generation.


5. **MediaFileFilter.java**

	@startuml

class MediaFileFilter {

    ' enums

    \+enum SizeFilterType { GT, GTE, EQ, LT, LTE }

    \+enum MediaFileOrder { NAME, DATE\_UPLOADED, TYPE }

    ' attributes (package visibility)

    \~name : String

    \~type : MediaFileType

    \~size : long

    \~sizeFilterType : SizeFilterType

    \~tags : List\<String\>

    \~order : MediaFileOrder

    \~startIndex : int \= \-1

    \~length : int

    ' methods (public)

    \+getName() : String

    \+setName(name : String) : void

    \+getType() : MediaFileType

    \+setType(type : MediaFileType) : void

    \+getTags() : List\<String\>

    \+setTags(tags : List\<String\>) : void

    \+getSize() : long

    \+setSize(size : long) : void

    \+getSizeFilterType() : SizeFilterType

    \+setSizeFilterType(sizeFilterType : SizeFilterType) : void

    \+getStartIndex() : int

    \+setStartIndex(startIndex : int) : void

    \+getLength() : int

    \+setLength(length : int) : void

    \+getOrder() : MediaFileOrder

    \+setOrder(order : MediaFileOrder) : void

}

@enduml

**Role**  
 MediaFileFilter is a simple search-criteria DTO used to express filtering, sorting, and paging options when querying media files (uploads) belonging to weblogs. It does not implement any logic itself. Instead, it carries parameters that are consumed by media-management services or managers, which perform the actual search and retrieval.

**Classes it interacts with**

* org.apache.roller.weblogger.business.MediaFileManager  
   Relationship type: Dependency / Association (method parameter)  
   Cardinality: MediaFileManager (1) uses MediaFileFilter (0..1) per query call  
   Why / Where: MediaFileFilter is passed to MediaFileManager search or list methods to constrain results based on type, tags, size, ordering, and paging. The manager applies the logic and returns MediaFile objects.

* org.apache.roller.weblogger.business.jpa.JPAMediaFileManagerImpl  
   Relationship type: Dependency  
   Cardinality: JPAMediaFileManagerImpl (1) depends on MediaFileFilter (0..1)  
   Why / Where: The JPA-based implementation reads values from MediaFileFilter to build JPA or SQL query criteria, apply ordering and pagination, and return matching MediaFile entities.

* org.apache.roller.weblogger.pojos.MediaFile  
   Relationship type: Association (filter to result type)  
   Cardinality: MediaFileFilter (1) filters MediaFile (0..\*)  
   Why / Where: MediaFileFilter defines constraints that are used to retrieve zero or more MediaFile instances. Filter fields correspond to MediaFile attributes such as type, tags, size, and metadata.

* org.apache.roller.weblogger.pojos.MediaFileType  
   Relationship type: Attribute association  
   Cardinality: MediaFileFilter (1) has MediaFileType (0..1)  
   Why / Where: The `type` attribute restricts searches to specific categories of media files such as images, audio, or video.

* org.apache.roller.weblogger.pojos.MediaFileDirectory  
   Relationship type: Indirect association  
   Cardinality: MediaFileFilter (1) results relate to MediaFileDirectory (0..\*)  
   Why / Where: Although MediaFileFilter does not directly store directory information, returned MediaFile objects include directory or path details. Query implementations may implicitly apply directory-based constraints or ordering.

* org.apache.roller.weblogger.business.FileContentManager  
   Relationship type: Indirect dependency  
   Cardinality: MediaFileManager implementation (1) uses FileContentManager (1)  
   Why / Where: When media content is requested along with metadata, manager implementations may invoke FileContentManager to load file bytes after MediaFileFilter-based queries resolve matching media records.

* org.apache.roller.weblogger.pojos.Weblog  
   Relationship type: Contextual association  
   Cardinality: Weblog (1) scopes MediaFileFilter (0..\*)  
   Why / Where: Media searches are typically scoped to a single weblog. While MediaFileFilter itself does not contain a weblog reference, search APIs usually accept both a Weblog and a MediaFileFilter so that filtering occurs within the weblog’s media space.

6. **MediaFileTag.java**  
   class MediaFileTag \<\<Serializable\>\> {  
     \- serialVersionUID : long {static, final}  
     \- id : String  
     \- name : String  
     \- mediaFile : MediaFile  
     
     \+ MediaFileTag()  
     \+ MediaFileTag(name : String, mediaFile : MediaFile)  
     \+ getId() : String  
     \+ setId(id : String) : void  
     \+ getName() : String  
     \+ setName(name : String) : void  
     \+ getMediaFile() : MediaFile  
     \+ setMediaFile(mediaFile : MediaFile) : void  
     \+ toString() : String  
     \+ equals(other : Object) : boolean  
     \+ hashCode() : int  
   }   
     
   **Role:**

Represents a tag applied to a media file as metadata. It provides a unique identity (id), the tag value (name), and a reference back to the owning MediaFile. It is used for searching and querying media files, displaying tags in pages and feeds, and enabling tag based filtering of media during rendering and feed generation.

**Classes it interacts with** 

* org.apache.roller.weblogger.pojos.MediaFile  
  Relationship: Composition (MediaFile composes MediaFileTag)  
  Cardinality: MediaFile 1 — \* MediaFileTag (one MediaFile owns zero or more MediaFileTag)  
  Why / where: MediaFile maintains a collection of tags, for example a Set of MediaFileTag. Tags are created, updated, and removed through MediaFile methods such as updateTags, setTags, and getTags. MediaFileTag stores a back reference to its owning MediaFile. This models true ownership, as tags do not exist independently of a media file in the codebase.  
* org.apache.roller.util.UUIDGenerator  
  Relationship: Dependency (uses)  
  Cardinality: MediaFileTag class 1 — 0..\* UUIDGenerator usage  
  Why / where: MediaFileTag uses UUIDGenerator.generateUUID to initialize its identifier. This is a utility dependency only and does not represent a persistent or structural association.  
* org.apache.roller.weblogger.business.MediaFileManager  
  Relationship: Dependency (uses), indirect association  
  Cardinality: MediaFileManager 1 — \* MediaFileTag (indirect, via MediaFile)  
  Why / where: MediaFileManager implementations create, update, and delete media files. Tag creation, persistence, and removal occur as part of the MediaFile lifecycle. The manager interacts with tags only through MediaFile objects.  
* org.apache.roller.weblogger.business.jpa.JPAMediaFileManagerImpl  
  Relationship: Dependency (uses)  
  Cardinality: JPAMediaFileManagerImpl 1 — \* MediaFileTag (indirect, via MediaFile)  
  Why / where: The JPA persistence layer loads and stores MediaFile entities along with their associated tags. When a MediaFile is retrieved, its tag set is also available to the application as part of the entity state.  
* org.apache.roller.weblogger.pojos.MediaFileDirectory  
  Relationship: Indirect association  
  Cardinality: MediaFileDirectory 1 — \* MediaFile, MediaFile 1 — \* MediaFileTag  
  Why / where: MediaFileDirectory contains MediaFile objects, and each MediaFile owns its tags. This directory to file to tag containment chain is used in upload interfaces, media browsing, and feed or resource generation.

7. **MediaFileType**

	enum MediaFileType {

    // enum constants (public static final)

    \+ AUDIO

    \+ VIDEO

    \+ IMAGE

    \+ OTHERS

    \-- Attributes \--

    \~ contentTypePrefix : String

    \~ id                : String

    \~ description       : String

    \-- Methods \--

    \- MediaFileType(id: String, desc: String, prefix: String)  // private constructor

    \+ getContentTypePrefix() : String

    \+ getId() : String

    \+ getDesc() : String

}

**Role**: 

simple domain enum that categorizes media files into Audio, Video, Image, or Others and stores a content type prefix used for type classification and routing, such as deciding image specific processing or thumbnail logic. It is a value or domain type used by media POJOs and managers, not a manager or service itself.

**How it interacts with other classes** 

* org.apache.roller.weblogger.pojos.MediaFile  
  Relationship: Association (aggregation). MediaFile references a media type or category.  
  Cardinality: MediaFile many to MediaFileType one (many MediaFile instances classify to a single MediaFileType value).  
  Why or where: MediaFile objects are classified by type for user interface display, validation, and media specific processing decisions such as checking whether a file is an image. MediaFileType provides canonical category metadata such as prefix, identifier, or description that is used by MediaFile logic.  
* org.apache.roller.weblogger.pojos.MediaFileDirectory  
  Relationship: Dependency (uses), indirect. A directory contains MediaFile instances which are classified by MediaFileType.  
  Cardinality: MediaFileDirectory one to MediaFileType zero or more (indirect, via the MediaFile objects it contains).  
  Why or where: Directories group media files, and MediaFileType is used when listing or serving resources, for example to filter images versus other file types.  
* org.apache.roller.weblogger.business.MediaFileManager  
  Relationship: Dependency (uses). Manager logic classifies files and performs type specific operations.  
  Cardinality: MediaFileManager one to MediaFileType zero or more (manager handles many files categorized by type).  
  Why or where: The manager uses MediaFileType to choose processing paths such as thumbnail generation for images, upload validation, or content handling decisions.  
* org.apache.roller.weblogger.business.JPAMediaFileManagerImpl  
  Relationship: Dependency (uses). Concrete implementation performs database and file content operations and uses the enum for decisions.  
  Cardinality: JPAMediaFileManagerImpl one to MediaFileType zero or more.  
  Why or where: Implementation code that loads MediaFile entities and their content may branch based on MediaFileType for thumbnailing, content loading, or content saving behavior.  
* org.apache.roller.weblogger.business.FileContentManager  
  Relationship: Dependency (uses). FileContentManager stores and loads raw file content and may use MediaFileType for handling decisions.  
  Cardinality: FileContentManager one to MediaFileType zero or more.  
  Why or where: When saving or validating uploaded content, the MediaFileType or its content type prefix may be checked or used to decide storage location or processing, such as treating images differently from other media.  
* org.apache.roller.weblogger.webservices.atomprotocol.RollerAtomService  
  Relationship: Dependency (uses). Atom service exposes upload and media collections and uses media type information.  
  Cardinality: RollerAtomService one to MediaFileType zero or more.  
  Why or where: When exposing media upload collections, the service may advertise acceptable content types or categorize collections. MediaFileType provides the content type prefix used to populate accept headers or collection metadata.

8. **MediaFileManager (interface)**  
   interface MediaFileManager {  
     \+ static final int MAX\_WIDTH \= 120  
     \+ static final int MAX\_HEIGHT \= 120  
     
     \+ void initialize()  
     \+ void release()  
     
     \+ void createMediaFile(Weblog weblog, MediaFile mediaFile, RollerMessages errors) throws WebloggerException  
     \+ void createThemeMediaFile(Weblog weblog, MediaFile mediaFile, RollerMessages errors) throws WebloggerException  
     
     \+ void updateMediaFile(Weblog weblog, MediaFile mediaFile) throws WebloggerException  
     \+ void updateMediaFile(Weblog website, MediaFile mf, java.io.InputStream fis) throws WebloggerException  
     
     \+ MediaFile getMediaFile(String id) throws WebloggerException  
     \+ MediaFile getMediaFile(String id, boolean includeContent) throws WebloggerException  
     
     \+ void removeMediaFile(Weblog weblog, MediaFile mediaFile) throws WebloggerException  
     
     \+ java.util.List\<MediaFile\> searchMediaFiles(Weblog weblog, MediaFileFilter filter) throws WebloggerException  
     
     \+ MediaFileDirectory createDefaultMediaFileDirectory(Weblog weblog) throws WebloggerException  
     \+ void createMediaFileDirectory(MediaFileDirectory directory) throws WebloggerException  
     \+ MediaFileDirectory createMediaFileDirectory(Weblog weblog, String name) throws WebloggerException  
     
     \+ MediaFileDirectory getMediaFileDirectory(String id) throws WebloggerException  
     \+ MediaFileDirectory getMediaFileDirectoryByName(Weblog weblog, String name) throws WebloggerException  
     
     \+ MediaFile getMediaFileByPath(Weblog weblog, String path) throws WebloggerException  
     \+ MediaFile getMediaFileByOriginalPath(Weblog weblog, String origpath) throws WebloggerException  
     
     \+ java.util.List\<MediaFileDirectory\> getMediaFileDirectories(Weblog weblog) throws WebloggerException  
     \+ MediaFileDirectory getDefaultMediaFileDirectory(Weblog weblog) throws WebloggerException  
     
     \+ void moveMediaFiles(java.util.Collection\<MediaFile\> mediaFiles, MediaFileDirectory directory) throws WebloggerException  
     \+ void moveMediaFile(MediaFile mediaFile, MediaFileDirectory directory) throws WebloggerException  
     
     \+ java.util.List\<MediaFile\> fetchRecentPublicMediaFiles(int length) throws WebloggerException  
     
     \+ void removeAllFiles(Weblog website) throws WebloggerException  
     
     \+ void removeMediaFileDirectory(MediaFileDirectory mediaFileDir) throws WebloggerException  
     
     \+ void removeMediaFileTag(String name, MediaFile entry) throws WebloggerException  
   }  
     
     
   **Role**:   
   Interface that defines the media management API used by the content subsystem. Its responsibilities include lifecycle operations such as initialize and release, creating, updating, and deleting media metadata and content, managing directories, searching and retrieving media files, moving files between directories, cleaning up all media for a weblog, and returning recent public media files. It separates higher level rendering and weblog entry logic from storage and binary content concerns.  
   How it integrates: rendering code and feed or page models request media metadata and content through this interface. Concrete implementations, such as JPA backed managers, handle persistence and delegate binary storage responsibilities to a FileContentManager.

	**Interactions:**

* org.apache.roller.weblogger.pojos.MediaFile  
  Relationship: Association (uses). MediaFile appears as a parameter and return type in manager methods.  
  Cardinality: MediaFileManager interacts with zero or more MediaFile objects.  
  Why or where: MediaFile instances represent media metadata that is created, updated, retrieved, or returned as search results by the manager.  
  Type: class.  
* org.apache.roller.weblogger.pojos.MediaFileDirectory  
  Relationship: Association (uses). Used as a parameter and return type for directory related operations.  
  Cardinality: MediaFileManager interacts with zero or more MediaFileDirectory objects.  
  Why or where: Directories group media files, and the manager is responsible for creating, listing, retrieving, and deleting directories, as well as moving files between them.  
  Type: class.  
* org.apache.roller.weblogger.pojos.Weblog  
  Relationship: Dependency (method parameter). Operations are scoped to a weblog.  
  Cardinality: One Weblog is used per operation from the MediaFileManager perspective.  
  Why or where: Methods require a Weblog to scope media operations such as creating files, listing directories, or removing all files for a weblog.  
  Type: class.  
* org.apache.roller.weblogger.pojos.MediaFileFilter  
  Relationship: Dependency (parameter). Used to pass search criteria.  
  Cardinality: Zero or one MediaFileFilter supplied per search operation.  
  Why or where: MediaFileFilter describes filtering, sorting, and paging criteria passed to searchMediaFiles.  
  Type: class.  
* org.apache.roller.weblogger.util.RollerMessages  
  Relationship: Dependency (parameter). Used for reporting validation or user facing errors.  
  Cardinality: Zero or one RollerMessages instance passed to create operations.  
  Why or where: Create and update methods accept RollerMessages to collect validation messages or errors for presentation to the user.  
  Type: class.  
* org.apache.roller.weblogger.business.FileContentManager  
  Relationship: Usage or aggregation at the implementation level. MediaFileManager implementations delegate binary content handling to this component.  
  Cardinality: One FileContentManager is typically used by a MediaFileManager implementation.  
  Why or where: Reading and writing binary content such as uploaded files and thumbnails is handled by FileContentManager, while MediaFileManager coordinates metadata and lifecycle operations.  
  Type: interface.  
* org.apache.roller.weblogger.business.jpa.JPAMediaFileManagerImpl  
  Relationship: Realization. This class implements the MediaFileManager interface.  
  Cardinality: One or more implementations may realize the interface; at runtime typically a single implementation is used.  
  Why or where: The implementation provides persistence, database interaction, transaction handling, and integration with FileContentManager.  
  Type: class.  
* org.apache.roller.weblogger.business.WebloggerFactory  
  Relationship: Association and lookup. MediaFileManager is obtained through the Weblogger API.  
  Cardinality: One Weblogger exposes one MediaFileManager instance.  
  Why or where: Rendering code, actions, and other consumers obtain the MediaFileManager via WebloggerFactory.getWeblogger().getMediaFileManager().  
  Type: Weblogger is an interface; WebloggerFactory is a factory class.

9. **JPAMediaFileManagerImpl.java**  
   **Attributes**  
   \- roller : org.apache.roller.weblogger.business.Weblogger  
   \- strategy : JPAPersistenceStrategy  
   \- static final log : org.apache.commons.logging.Log  
   \- public static final MIGRATION\_STATUS\_FILENAME : String  
   **Methods**  
   \+ \<\<constructor\>\> protected JPAMediaFileManagerImpl(roller : org.apache.roller.weblogger.business.Weblogger, persistenceStrategy : JPAPersistenceStrategy)  
   \+ initialize() : void  
   \+ release() : void  
   \+ moveMediaFiles(mediaFiles : java.util.Collection\<org.apache.roller.weblogger.pojos.MediaFile\>, targetDirectory : org.apache.roller.weblogger.pojos.MediaFileDirectory) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ moveMediaFile(mediaFile : org.apache.roller.weblogger.pojos.MediaFile, targetDirectory : org.apache.roller.weblogger.pojos.MediaFileDirectory) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ createMediaFileDirectory(directory : org.apache.roller.weblogger.pojos.MediaFileDirectory) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ createMediaFileDirectory(weblog : org.apache.roller.weblogger.pojos.Weblog, requestedName : String) : org.apache.roller.weblogger.pojos.MediaFileDirectory throws org.apache.roller.weblogger.WebloggerException  
   \+ createDefaultMediaFileDirectory(weblog : org.apache.roller.weblogger.pojos.Weblog) : org.apache.roller.weblogger.pojos.MediaFileDirectory throws org.apache.roller.weblogger.WebloggerException  
   \+ createMediaFile(weblog : org.apache.roller.weblogger.pojos.Weblog, mediaFile : org.apache.roller.weblogger.pojos.MediaFile, errors : org.apache.roller.weblogger.util.RollerMessages) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ createThemeMediaFile(weblog : org.apache.roller.weblogger.pojos.Weblog, mediaFile : org.apache.roller.weblogger.pojos.MediaFile, errors : org.apache.roller.weblogger.util.RollerMessages) : void throws org.apache.roller.weblogger.WebloggerException  
   \- updateThumbnail(mediaFile : org.apache.roller.weblogger.pojos.MediaFile) : void  
   \+ updateMediaFile(weblog : org.apache.roller.weblogger.pojos.Weblog, mediaFile : org.apache.roller.weblogger.pojos.MediaFile) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ updateMediaFile(weblog : org.apache.roller.weblogger.pojos.Weblog, mediaFile : org.apache.roller.weblogger.pojos.MediaFile, is : java.io.InputStream) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ getMediaFile(id : String) : org.apache.roller.weblogger.pojos.MediaFile throws org.apache.roller.weblogger.WebloggerException  
   \+ getMediaFile(id : String, includeContent : boolean) : org.apache.roller.weblogger.pojos.MediaFile throws org.apache.roller.weblogger.WebloggerException  
   \+ getMediaFileDirectoryByName(weblog : org.apache.roller.weblogger.pojos.Weblog, name : String) : org.apache.roller.weblogger.pojos.MediaFileDirectory throws org.apache.roller.weblogger.WebloggerException  
   \+ getMediaFileByPath(weblog : org.apache.roller.weblogger.pojos.Weblog, path : String) : org.apache.roller.weblogger.pojos.MediaFile throws org.apache.roller.weblogger.WebloggerException  
   \+ getMediaFileByOriginalPath(weblog : org.apache.roller.weblogger.pojos.Weblog, origpath : String) : org.apache.roller.weblogger.pojos.MediaFile throws org.apache.roller.weblogger.WebloggerException  
   \+ getMediaFileDirectory(id : String) : org.apache.roller.weblogger.pojos.MediaFileDirectory throws org.apache.roller.weblogger.WebloggerException  
   \+ getDefaultMediaFileDirectory(weblog : org.apache.roller.weblogger.pojos.Weblog) : org.apache.roller.weblogger.pojos.MediaFileDirectory throws org.apache.roller.weblogger.WebloggerException  
   \+ getMediaFileDirectories(weblog : org.apache.roller.weblogger.pojos.Weblog) : java.util.List\<org.apache.roller.weblogger.pojos.MediaFileDirectory\> throws org.apache.roller.weblogger.WebloggerException  
   \+ removeMediaFile(weblog : org.apache.roller.weblogger.pojos.Weblog, mediaFile : org.apache.roller.weblogger.pojos.MediaFile) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ fetchRecentPublicMediaFiles(length : int) : java.util.List\<org.apache.roller.weblogger.pojos.MediaFile\> throws org.apache.roller.weblogger.WebloggerException  
   \+ searchMediaFiles(weblog : org.apache.roller.weblogger.pojos.Weblog, filter : org.apache.roller.weblogger.pojos.MediaFileFilter) : java.util.List\<org.apache.roller.weblogger.pojos.MediaFile\> throws org.apache.roller.weblogger.WebloggerException  
   \+ isFileStorageUpgradeRequired() : boolean  
   \+ upgradeFileStorage() : java.util.List\<String\>  
   \- upgradeUploadsDir(weblog : org.apache.roller.weblogger.pojos.Weblog, user : org.apache.roller.weblogger.pojos.User, oldDir : java.io.File, newDir : org.apache.roller.weblogger.pojos.MediaFileDirectory) : void  
   \+ removeAllFiles(website : org.apache.roller.weblogger.pojos.Weblog) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ removeMediaFileDirectory(dir : org.apache.roller.weblogger.pojos.MediaFileDirectory) : void throws org.apache.roller.weblogger.WebloggerException  
   \+ removeMediaFileTag(name : String, entry : org.apache.roller.weblogger.pojos.MediaFile) :

	

	**Role:**

	JPAMediaFileManagerImpl implements media storage and lifecycle management for uploaded media. It creates and manages media database records, delegates binary content storage to FileContentManager, generates thumbnails, migrates legacy filesystem uploads into the new storage system, and removes media when required. It acts as an adapter and service in the content subsystem, connecting persistent entities such as MediaFile, MediaFileDirectory, and tags to the low level file store and the weblog domain represented by Weblog.

**Classes it interacts with** 

* org.apache.roller.weblogger.business.Weblogger  
  Relationship: Association (constructor injected dependency).  
  Cardinality: One instance.  
  Rationale: Used as a service locator to access other managers such as WeblogManager and FileContentManager. It provides runtime services rather than storage behavior.  
  Type: interface.  
* JPAPersistenceStrategy  
  Relationship: Association (composition like injected dependency).  
  Cardinality: One instance.  
  Rationale: Used for database operations such as loading, storing, removing, and querying persistent entities through a persistence strategy.  
  Type: class.  
* org.apache.roller.weblogger.business.FileContentManager  
  Relationship: Dependency (uses or association).  
  Cardinality: Zero or more uses, typically per file operation.  
  Rationale: Responsible for saving, loading, and deleting the actual binary file content. JPAMediaFileManagerImpl delegates all content input and output operations to this component.  
  Type: interface.  
* org.apache.roller.weblogger.business.WeblogManager  
  Relationship: Dependency (uses).  
  Cardinality: One instance obtained from Weblogger.  
  Rationale: Used to update weblog last modified timestamps, retrieve weblog users during migration, and persist weblog related changes.  
  Type: interface.  
* org.apache.roller.weblogger.pojos.MediaFile  
  Relationship: Association (manipulates entity).  
  Cardinality: Zero or more MediaFile entities.  
  Rationale: Primary domain entity managed by this class, including creation, update, retrieval, and deletion of media files.  
  Type: class.  
* org.apache.roller.weblogger.pojos.MediaFileDirectory  
  Relationship: Association (manipulates entity).  
  Cardinality: Zero or more directories.  
  Rationale: Directories act as containers for media files and are created, modified, and used as parent structures for organizing uploads.  
  Type: class.  
* org.apache.roller.weblogger.pojos.MediaFileTag  
  Relationship: Association (aggregation).  
  Cardinality: Zero or more tags associated with media files.  
  Rationale: Tags are iterated over and removed as part of media or tag management operations.  
  Type: class.  
* org.apache.roller.weblogger.pojos.MediaFileFilter  
  Relationship: Dependency (used as parameter).  
  Cardinality: Zero or one filter per search operation.  
  Rationale: Provides search criteria for building dynamic media queries.  
  Type: class.  
* org.apache.roller.weblogger.pojos.Weblog  
  Relationship: Association (used as context).  
  Cardinality: One weblog per operation.  
  Rationale: Media files belong to a weblog, and operations are scoped to a specific weblog instance.  
  Type: class.  
* org.apache.roller.weblogger.util.RollerMessages  
  Relationship: Dependency (utility).  
  Cardinality: Zero or more uses.  
  Rationale: Used to collect validation and feedback messages during media creation or migration operations.  
  Type: class.  
* org.apache.roller.weblogger.business.WebloggerFactory  
  Relationship: Dependency (static access).  
  Cardinality: One factory reference.  
  Rationale: Used to obtain a FileContentManager when an injected Weblogger reference is not available.  
  Type: class.  
* org.apache.roller.weblogger.pojos.MediaFileType  
  Relationship: Dependency (uses).  
  Cardinality: Zero or one per operation.  
  Rationale: Used to map media file types to content type prefixes when building or executing queries.  
  Type: enum or class.

10. **FileContentManager**  
    interface FileContentManager {  
        \-- Attributes \--  
        // none  
      
        \-- Methods \--  
        \+ FileContent getFileContent(Weblog weblog, String fileId)   
          throws FileNotFoundException, FilePathException  
      
        \+ void saveFileContent(Weblog weblog, String fileId, InputStream is)   
          throws FileNotFoundException, FilePathException, FileIOException  
      
        \+ void deleteFile(Weblog weblog, String fileId)   
          throws FileNotFoundException, FilePathException, FileIOException  
      
        \+ void deleteAllFiles(Weblog weblog)   
          throws FileIOException  
      
        \+ boolean overQuota(Weblog weblog)  
      
        \+ boolean canSave(Weblog weblog, String fileName, String contentType, long size, RollerMessages messages)  
      
        \+ void release()  
    }  
    **Role:**

	Defines the contract for saving, reading, and deleting raw file contents stored for a weblog uploads area. It encapsulates filesystem or storage related concerns such as reading, writing, deleting files, quota checks, and validation.

**How it interacts with other classes:**

* org.apache.roller.weblogger.pojos.Weblog  
  Relationship: association, used as a method parameter.  
  Cardinality: FileContentManager uses one Weblog per operation call.  
  Why or where: Every FileContentManager method takes a Weblog parameter to locate the correct uploads area or tenant.  
  Type: interface.  
* org.apache.roller.weblogger.pojos.FileContent  
  Relationship: association, returned object.  
  Cardinality: FileContentManager returns one FileContent per call.  
  Why or where: The getFileContent method returns a FileContent instance that represents the stored binary content and related metadata.  
  Type: class.  
* org.apache.roller.weblogger.util.RollerMessages  
  Relationship: dependency, used as a method parameter.  
  Cardinality: Zero or one RollerMessages instance per call.  
  Why or where: The canSave method populates RollerMessages with validation errors such as filename issues, content type problems, or quota violations.  
  Type: class.  
* org.apache.roller.weblogger.business.MediaFileManager  
  Relationship: association, client and provider relationship.  
  Cardinality: MediaFileManager implementations depend on one FileContentManager instance.  
  Why or where: Higher level media operations such as creating, updating, or retrieving media files delegate reading and writing of file bytes to FileContentManager. For example, JPAMediaFileManagerImpl obtains a FileContentManager to load or save content.  
  Type: MediaFileManager is an interface.  
* org.apache.roller.weblogger.pojos.MediaFile  
  Relationship: association.  
  Cardinality: FileContentManager interacts with zero or more MediaFile objects.  
  Why or where: MediaFile objects receive FileContent after FileContentManager returns it from getFileContent, and saveFileContent stores content for a given MediaFile.  
  Type: class.  
* org.apache.roller.weblogger.business.FileContentManagerImpl  
  Relationship: realization, implementation of the interface.  
  Cardinality: One or more implementations may exist.  
  Why or where: FileContentManagerImpl is a concrete class that performs the actual input and output operations such as filesystem access, path resolution, and thumbnail handling.  
  Type: class.  
* org.apache.roller.weblogger.business.WeblogEntryManager  
  Relationship: indirect association.  
  Cardinality: Zero or one dependency.  
  Why or where: Higher level operations that render weblog entries or handle embedded media may indirectly rely on FileContentManager through MediaFileManager when media content is needed.  
  Type: interface.

11. **JPAFileContentManagerImpl**  
    class JPAMediaFileManagerImpl {  
        \- final Weblogger roller  
        \- final JPAPersistenceStrategy strategy  
        \- static final Log log  
        \+ static final String MIGRATION\_STATUS\_FILENAME  
        \+ \<\<constructor\>\> protected JPAMediaFileManagerImpl(Weblogger roller, JPAPersistenceStrategy persistenceStrategy)  
        \+ void initialize()  
        \+ void release()  
        \+ void moveMediaFiles(Collection\<MediaFile\> mediaFiles, MediaFileDirectory targetDirectory) throws WebloggerException  
        \+ void moveMediaFile(MediaFile mediaFile, MediaFileDirectory targetDirectory) throws WebloggerException  
        \+ void createMediaFileDirectory(MediaFileDirectory directory) throws WebloggerException  
        \+ MediaFileDirectory createMediaFileDirectory(Weblog weblog, String requestedName) throws WebloggerException  
        \+ MediaFileDirectory createDefaultMediaFileDirectory(Weblog weblog) throws WebloggerException  
        \+ void createMediaFile(Weblog weblog, MediaFile mediaFile, RollerMessages errors) throws WebloggerException  
        \+ void createThemeMediaFile(Weblog weblog, MediaFile mediaFile, RollerMessages errors) throws WebloggerException  
        \- void updateThumbnail(MediaFile mediaFile)  
        \+ void updateMediaFile(Weblog weblog, MediaFile mediaFile) throws WebloggerException  
        \+ void updateMediaFile(Weblog weblog, MediaFile mediaFile, InputStream is) throws WebloggerException  
        \+ MediaFile getMediaFile(String id) throws WebloggerException  
        \+ MediaFile getMediaFile(String id, boolean includeContent) throws WebloggerException  
        \+ MediaFileDirectory getMediaFileDirectoryByName(Weblog weblog, String name) throws WebloggerException  
        \+ MediaFile getMediaFileByPath(Weblog weblog, String path) throws WebloggerException  
        \+ MediaFile getMediaFileByOriginalPath(Weblog weblog, String origpath) throws WebloggerException  
        \+ MediaFileDirectory getMediaFileDirectory(String id) throws WebloggerException  
        \+ MediaFileDirectory getDefaultMediaFileDirectory(Weblog weblog) throws WebloggerException  
        \+ List\<MediaFileDirectory\> getMediaFileDirectories(Weblog weblog) throws WebloggerException  
        \+ void removeMediaFile(Weblog weblog, MediaFile mediaFile) throws WebloggerException  
        \+ List\<MediaFile\> fetchRecentPublicMediaFiles(int length) throws WebloggerException  
        \+ List\<MediaFile\> searchMediaFiles(Weblog weblog, MediaFileFilter filter) throws WebloggerException  
        \+ boolean isFileStorageUpgradeRequired()  
        \+ List\<String\> upgradeFileStorage()  
        \- void upgradeUploadsDir(Weblog weblog, User user, File oldDir, MediaFileDirectory newDir)  
        \+ void removeAllFiles(Weblog website) throws WebloggerException  
        \+ void removeMediaFileDirectory(MediaFileDirectory dir) throws WebloggerException  
        \+ void removeMediaFileTag(String name, MediaFile entry) throws WebloggerException  
    }  
    

	**Role:**

Implements media storage and management for weblogs, including creating, reading, updating, and deleting media files and directories, generating thumbnails, migrating legacy filesystem uploads, and searching and retrieving media files. It provides services used by the content rendering layer for media referenced by posts and by the administrative user interface for managing uploads.

**How it interacts with other classes:**

* Weblogger  
  Type: class, application facade.  
  Relationship: association, field reference.  
  Cardinality: one.  
  Why or where: Used to obtain other managers such as FileContentManager and WeblogManager, to call roller.flush, and to access configuration. Acts as the central application context.  
* JPAPersistenceStrategy  
  Type: class, persistence helper.  
  Relationship: association, field reference.  
  Cardinality: one.  
  Why or where: Used for all database operations including store, load, remove, named queries, dynamic queries, and refresh.  
* MediaFileManager  
  Type: interface.  
  Relationship: realization, implemented by this class.  
  Cardinality: one.  
  Why or where: JPAMediaFileManagerImpl is the concrete implementation of the MediaFileManager interface exposed to the application.  
* FileContentManager  
  Type: interface or manager class.  
  Relationship: dependency, uses.  
  Cardinality: one, obtained from Weblogger or WebloggerFactory.  
  Why or where: Used to store, retrieve, and delete actual binary file contents, providing a separation between media metadata and file blobs.  
* WeblogManager  
  Type: interface or manager class.  
  Relationship: dependency, uses.  
  Cardinality: one, obtained from Weblogger.  
  Why or where: Used to update weblog last modified state and to look up weblogs and users during migration.  
* MediaFile  
  Type: class, entity.  
  Relationship: association, manipulates instances.  
  Cardinality: zero or more.  
  Why or where: Primary domain entity being created, updated, moved, removed, searched, and associated with thumbnails or content.  
* MediaFileDirectory  
  Type: class, entity.  
  Relationship: association, manipulates instances.  
  Cardinality: one or more.  
  Why or where: Organizes media files into directories and is used for directory lookup, creation, and migration.  
* FileContent  
  Type: class, content wrapper.  
  Relationship: dependency, used.  
  Cardinality: zero or one per media file when content is included.  
  Why or where: Used to attach binary content to a MediaFile when loading with includeContent enabled.  
* MediaFileFilter, MediaFileTag, MediaFileType  
  Type: classes, enums, or POJOs.  
  Relationship: dependency or association.  
  Cardinality: MediaFileFilter zero or one as search input, MediaFileTag zero or more as tags on a media file, MediaFileType zero or one as filter or classification criteria.  
  Why or where: MediaFileFilter is used in search operations, MediaFileTag is manipulated during tag removal, and MediaFileType is used for classification and filtering.  
* User and Weblog  
  Type: classes, entities.  
  Relationship: association, used during migration and creation.  
  Cardinality: User zero or more, Weblog one per operation.  
  Why or where: Migration sets creator information from users and associates media files and directories with a specific weblog.  
* RollerMessages  
  Type: class.  
  Relationship: dependency, used as an output parameter.  
  Cardinality: zero or one per create operation.  
  Why or where: Used to collect validation messages and feedback during creation or migration.  
* Utilities  
  Type: utility class.  
  Relationship: dependency, static usage.  
  Cardinality: not applicable.  
  Why or where: Used to derive content type information from filenames.  
* java.io.File, java.util.Properties, java.io.InputStream, java.sql.Timestamp  
  Type: JDK classes.  
  Relationship: usage.  
  Cardinality: varies by method.  
  Why or where: Used for filesystem migration, input and output streams, configuration handling, and timestamping last modified data.  
    
12. **FileContent**

	class FileContent {

    \- resourceFile: java.io.File

    \- fileId: java.lang.String

    \- weblog: org.apache.roller.weblogger.pojos.Weblog

    \+ FileContent(weblog: org.apache.roller.weblogger.pojos.Weblog, fileId: java.lang.String, file: java.io.File)

    \+ getWeblog(): org.apache.roller.weblogger.pojos.Weblog

    \+ getName(): java.lang.String

    \+ getFileId(): java.lang.String

    \+ getLastModified(): long

    \+ getLength(): long

    \+ getInputStream(): java.io.InputStream

}

**Role:**

FileContent encapsulates a filesystem-backed media/resource attached to a weblog. It provides metadata (name, length, lastModified), the owning weblog reference (for scoping/permission/path resolution) and an InputStream factory to read the binary content. It is used by resource managers/DAOs, rendering or download controllers, and any content-fetching logic that serves media for weblog entries.

**Classes it interacts with**

* org.apache.roller.weblogger.pojos.Weblog  
  * Type: class  
  * Relationship: Aggregation (many FileContent aggregated by one Weblog)  
  * Cardinality: Weblog 1 \<-- \* FileContent (each FileContent references exactly one Weblog; a Weblog can own many FileContent)  
  * Why/where: FileContent.getWeblog() returns owner weblog used for scoping, permissions, URL/path resolution, and to associate media with a weblog.  
* java.io.File  
  * Type: class (JDK)  
  * Relationship: Composition (FileContent owns the backing File instance)  
  * Cardinality: FileContent 1 \--\> 1 java.io.File  
  * Why/where: resourceFile is the physical backing file; all metadata and stream creation derive from it.  
* java.io.InputStream  
  * Type: abstract class/interface in JDK (java.io.InputStream is an abstract class)  
  * Relationship: Dependency (usage)  
  * Cardinality: FileContent depends on InputStream (method-level)  
  * Why/where: getInputStream() returns an InputStream to consumers that read the file content.  
* java.io.FileInputStream  
  * Type: class  
  * Relationship: Dependency (usage)  
  * Cardinality: FileContent depends on FileInputStream when creating streams  
  * Why/where: getInputStream() uses new FileInputStream(resourceFile) to implement content access.  
* java.io.FileNotFoundException / java.lang.RuntimeException  
  * Type: classes (exceptions)  
  * Relationship: Dependency (exception handling)  
  * Cardinality: method-level dependency  
  * Why/where: getInputStream() catches FileNotFoundException and rethrows as RuntimeException to signal error creating stream. give plain text

