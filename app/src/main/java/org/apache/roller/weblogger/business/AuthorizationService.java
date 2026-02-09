package org.apache.roller.weblogger.business;

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.pojos.RollerPermission;
import org.apache.roller.weblogger.pojos.User;

/**
 * High-level permission check.
 */
public interface AuthorizationService {

    boolean checkPermission(RollerPermission perm, User user)
            throws WebloggerException;
}

