package org.apache.roller.weblogger.business.jpa;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.AuthorizationService;
import org.apache.roller.weblogger.business.WeblogPermissionService;
import org.apache.roller.weblogger.pojos.GlobalPermission;
import org.apache.roller.weblogger.pojos.RollerPermission;
import org.apache.roller.weblogger.pojos.User;
import org.apache.roller.weblogger.pojos.WeblogPermission;

@Singleton
public class JPAAuthorizationService implements AuthorizationService {

    private static final Log log = LogFactory.getLog(JPAAuthorizationService.class);

    private final WeblogPermissionService weblogPermissionService;

    @Inject
    public JPAAuthorizationService(WeblogPermissionService weblogPermissionService) {
        this.weblogPermissionService = weblogPermissionService;
    }

    @Override
    public boolean checkPermission(RollerPermission perm, User user)
            throws WebloggerException {

        if (perm == null || user == null) {
            return false;
        }

        // weblog permission check
        if (perm instanceof WeblogPermission) {
            WeblogPermission requested = (WeblogPermission) perm;
            WeblogPermission existing =
                    weblogPermissionService.getWeblogPermission(requested.getWeblog(), user);
            if (existing != null && existing.implies(perm)) {
                return true;
            }
        }

        // global admin check
        GlobalPermission globalPerm = new GlobalPermission(user);
        if (globalPerm.implies(perm)) {
            return true;
        }

        if (log.isDebugEnabled()) {
            log.debug("PERM CHECK FAILED: user " + user.getUserName()
                    + " does not have " + perm.toString());
        }
        return false;
    }
}