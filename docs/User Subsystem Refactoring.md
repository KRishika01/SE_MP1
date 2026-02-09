# UserManager Refactoring

We refactored Apache Roller's `UserManager` by applying the Interface Segregation Principle to split one large interface into three focused interfaces .

### Refactoring Overview
- **Original State**: Single `UserManager` interface with 39 methods handling user CRUD, role management, and permission management
- **Refactored State**: Three specialized interfaces
  - `UserManager` (13 methods) - User CRUD operations
  - `RoleManager` (4 methods) - User role assignments  
  - `PermissionManager` (13 methods) - Weblog permission management

---

## Problem
### Initial State: The God Class(Insufficient Modularization)
### Identified Problems

#### 1. **Violation of Single Responsibility Principle (SRP)**
The interface had **three distinct responsibilities**:
- **User Management**: Creating, reading, updating, deleting user accounts
- **Role Management**: Assigning system-wide roles (admin, editor, etc.)
- **Permission Management**: Managing weblog-specific permissions (post, edit, admin within a blog)

**Implication**: Any change to permission logic required modifications to the UserManager interface, affecting all clients that only needed user CRUD operations.

#### 2. **Violation of Interface Segregation Principle (ISP)**
Fat interfaces create unnecessary dependencies, making code harder to understand, test, and maintain.

#### 3. **High Coupling**
All components that needed any user-related functionality were tightly coupled to the entire UserManager interface:

**Implication**: Changes to permission logic could affect components that only deal with user accounts.

#### 4. **Low Cohesion**
Methods within UserManager had weak relationships:
- `addUser()` deals with database user records
- `checkPermission()` deals with authorization logic
- `grantRole()` deals with role assignments

**Implication**: The class has too many responsibilities, making it difficult to understand, test, and modify.

#### 5. **Testing Complexity**
Mock setup becomes unnecessarily complex

#### 6. **Poor Dependency Management**
The implementation (`JPAUserManagerImpl`) had mixed dependencies:

Single class handling three different data models:
    -User entities (user table)
    -UserRole entities (userrole table)
    -WeblogPermission entities (roller_permission table)

**Implication**: Database queries for different concerns were mixed in one class, violating separation of concerns.

---

## Refactoring Strategy

### Why We Chose Interface Segregation

The **Interface Segregation Principle** states:
> "Clients should not be forced to depend on interfaces they do not use."

We identified that `UserManager` could be cleanly separated into three focused interfaces based on:

1. **Data Model Analysis**:
   - `User` entity (user table) → UserManager
   - `UserRole` entity (userrole table) → RoleManager
   - `WeblogPermission` entity (roller_permission table) → PermissionManager

2. **Use Case Analysis**:
   - User registration/login → needs only User operations
   - Blog membership management → needs Permission operations
   - System administration → needs Role operations

3. **Responsibility Analysis**:
   - User lifecycle management → separate concern
   - Authorization (permissions) → separate concern
   - Role assignments → separate concern

### Design Decision: Three Interfaces

#### Interface 1: UserManager (13 methods)
**Responsibility**: User account and CRUD management

**Role**: 
- Focused on `User` entity operations
- No dependencies on permissions or roles
- Clients interested in user data don't need authorization concerns

#### Interface 2: RoleManager (4 methods)
**Responsibility**: System-wide role assignments

**Role**:
- Manages `UserRole` entity (separate table)
- System-level roles (admin, editor) are different from blog-level permissions
- `hasRole()` and `getRoles()` marked deprecated because permission checking should use `PermissionManager.checkPermission()` with `GlobalPermission` objects

**Design Note**: Methods marked `@Deprecated` because:
1. Direct role checking is discouraged in favor of permission-based authorization
2. Apache Roller's architecture uses permission objects (`GlobalPermission`, `WeblogPermission`) for authorization decisions
3. Roles should be an implementation detail, not part of the authorization API

#### Interface 3: PermissionManager (13 methods)
**Responsibility**: Weblog-specific permission management

**Role**:
- Manages `WeblogPermission` entity (separate table)
- Blog-level permissions (POST, EDIT_DRAFT, ADMIN within a specific blog)
- Includes pending permission workflow (invite/accept mechanism)
- Central authorization API for the application

---

## Implementation

### Step 1: Create New Interfaces

1. **`RoleManager.java`** (New Interface)
   - Location: `app/src/main/java/org/apache/roller/weblogger/business/`
   - Methods extracted from UserManager: `grantRole()`, `revokeRole()`, `hasRole()`, `getRoles()`

2. **`PermissionManager.java`** (New Interface)
   - Location: `app/src/main/java/org/apache/roller/weblogger/business/`
   - Methods extracted from UserManager: All 13 permission-related methods

3. **`UserManager.java`** (Modified)
   - Removed 17 methods (4 role + 13 permission)
   - Kept 13 user CRUD methods
   - Added documentation pointing to new interfaces


## NOTE: 
**Critical Design Decision: Why RoleManager Dependency in UserManager?**

The `addUser()` method needs to assign a default role to new users. This is an **intentional coupling** required by Apache Roller's business rules:

1. **First User Rule**: The first registered user becomes an admin
2. **Default Role Rule**: All subsequent users get the "editor" role
3. **Atomic Operation**: User creation and role assignment must happen atomically

**Why This Design Is Correct**:
```java
// WRONG APPROACH: Force clients to remember role assignment
userManager.addUser(newUser);

roleManager.grantRole("editor", newUser); 
// Easy to forget or miss this..!


// CORRECT APPROACH: UserManager handles complete user setup
userManager.addUser(newUser); // Automatically assigns default role
```

So`addUser()` orchestrates multiple operations to ensure business rules of Apache roller are enforced. 

The alternative would require:
- Every caller to know about role assignment
- Duplicate role assignment logic across the codebase
- Risk of users being created without roles

Alternately we can introduce a new service class which is like Facade for these both functionality and in only purpose to coordinate , but we felt it is overengineered.

---

## Analysis

### Quantitative Metrics

#### Files Modified Summary
| Category | Count | Files |
|----------|-------|-------|
| **New Interfaces** | 2 | RoleManager.java, PermissionManager.java |
| **New Implementations** | 2 | JPARoleManagerImpl.java, JPAPermissionManagerImpl.java |
| **Modified Core** | 4 | UserManager.java, JPAUserManagerImpl.java, Weblogger.java, JPAWebloggerModule.java |
| **Modified POJOs** | 3 | User.java, Weblog.java, WeblogEntry.java |
| **Modified Business** | 10 | JPAWeblogQueryManagerImpl.java, WeblogContentManager.java, GlobalPermission.java, etc. |
| **Modified UI/Actions** | 10 | UIAction.java, Members.java, UserEdit.java, MainMenu.java, etc. |
| **Modified Security** | 5 | RoleAssignmentFilter.java, AuthoritiesPopulator.java, UISecurityInterceptor.java, etc. |
| **Modified Services** | 3 | RollerAtomService.java, MenuHelper.java, MenuModel.java |
| **Modified Tests** | 3 | TestUtils.java, PermissionTest.java, UserTest.java |
| **Total** | **42** | **42 files modified across entire codebase** |

#### Dependency Changes

**Before Refactoring (High Coupling):**

The original architecture had a single UserManager interface with 30 methods handling 3 different responsibilities. All client components (UI Actions, Business Services, and Security Filters) depended on this single interface, creating tight coupling. This meant:
- Every component had access to all 30 methods, even if they only needed 3-4 methods
- Changes to any part of UserManager potentially affected all dependent components
- Impossible to use user operations without also depending on permission and role operations
- Testing required mocking the entire 30-method interface

**After Refactoring (Low Coupling):**

The refactored architecture separates concerns into three focused interfaces:
- **UserManager** (13 methods) - User CRUD operations
- **RoleManager** (4 methods) - Role assignments
- **PermissionManager** (13 methods) - Permission management

Now each client component depends only on the specific interface it needs:
- **User Profile Service** → depends only on UserManager
- **User Admin** → depends on both UserManager and RoleManager
- **Blog Members Management** → depends on PermissionManager
- **Security Checks** → depends on PermissionManager

This means each component depends only on the methods it actually uses, reducing coupling and improving maintainability.

---

## Design Principles & Benefits

### 1. Single Responsibility Principle (SRP)

**Before**: UserManager had 3 responsibilities
**After**: Each interface has 1 responsibility

**Benefit**: 
- Changes to permission logic don't require opening UserManager
- Role management changes isolated to RoleManager

### 2. Interface Segregation Principle (ISP)

**Before**: Clients forced to depend on unused methods
**After**: Clients depend only on what they use

**Benefit**:
- Smaller interfaces are easier to implement
- Mock objects simpler (fewer methods to stub)
- Changes to one interface don't force recompilation of unrelated clients

### 3. Open/Closed Principle (OCP)

**Benefit**: Easier to extend without modifying existing code which is easily scalable and extensible.

---

## Test Analysis

### Test Files Modified
1. `TestUtils.java` - revokeWeblogPermission() (1 method)
2. `PermissionTest.java` - All permission methods (29 calls)
3. `UserTest.java` - All role methods (18 calls)

All tests passed(100%) and Manually verified the fuctionality of each in website.

### Key Improvements

1. **We reduced complexity** - 3 focused interfaces instead of 1 complex one
2. **We improved testability** - Smaller interfaces = simpler mocks
3. **We enabled extensibility** - Easy to add new features without breaking existing code
4. **We preserved functionality** - All tests pass, behavior unchanged

**With our implementation What Stayed the Same**:
- Business logic unchanged
- Method parameters unchanged
- Return types unchanged
- Algorithm unchanged

This shows the **core principle** of the refactoring: **Method signatures were preserved**, only the interface hosting them changed.

