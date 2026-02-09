
package org.apache.roller.weblogger.business;

/**
 * Legacy façade for user, role and permissions management.
 *
 * It now extends finer-grained service interfaces so responsibilities
 * are split into cohesive modules.
 */
public interface UserManager extends
        UserService,
        WeblogPermissionService,
        RoleService,
        AuthorizationService {

    /**
     * Release any resources held by manager.
     */
    void release();
}