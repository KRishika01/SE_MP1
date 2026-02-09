package org.apache.roller.weblogger.business;

import java.util.List;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.pojos.User;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogPermission;

/**
 * Operations around weblog-level permissions.
 */
public interface WeblogPermissionService {

    void grantWeblogPermission(Weblog weblog, User user, List<String> actions)
            throws WebloggerException;

    void grantWeblogPermissionPending(Weblog weblog, User user, List<String> actions)
            throws WebloggerException;

    void confirmWeblogPermission(Weblog weblog, User user)
            throws WebloggerException;

    void declineWeblogPermission(Weblog weblog, User user)
            throws WebloggerException;

    void revokeWeblogPermission(Weblog weblog, User user, List<String> actions)
            throws WebloggerException;

    List<WeblogPermission> getWeblogPermissions(User user)
            throws WebloggerException;

    List<WeblogPermission> getPendingWeblogPermissions(User user)
            throws WebloggerException;

    List<WeblogPermission> getWeblogPermissions(Weblog weblog)
            throws WebloggerException;

    List<WeblogPermission> getPendingWeblogPermissions(Weblog weblog)
            throws WebloggerException;

    List<WeblogPermission> getWeblogPermissionsIncludingPending(Weblog weblog)
            throws WebloggerException;

    WeblogPermission getWeblogPermission(Weblog weblog, User user)
            throws WebloggerException;

    WeblogPermission getWeblogPermissionIncludingPending(Weblog weblog, User user)
            throws WebloggerException;
}