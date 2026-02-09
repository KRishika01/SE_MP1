package org.apache.roller.weblogger.business;

import java.util.List;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.pojos.User;

/**
 * Role management for users.
 */
public interface RoleService {

    void grantRole(String roleName, User user) throws WebloggerException;

    void revokeRole(String roleName, User user) throws WebloggerException;

    @Deprecated
    boolean hasRole(String roleName, User user) throws WebloggerException;

    @Deprecated
    List<String> getRoles(User user) throws WebloggerException;
}