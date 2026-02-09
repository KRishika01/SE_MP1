package org.apache.roller.weblogger.business.jpa;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.WeblogPermissionService;
import org.apache.roller.weblogger.pojos.User;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogPermission;

@Singleton
public class JPAWeblogPermissionService implements WeblogPermissionService {

    private static final Log log = LogFactory.getLog(JPAWeblogPermissionService.class);

    private final JPAPersistenceStrategy strategy;

    @Inject
    public JPAWeblogPermissionService(JPAPersistenceStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public WeblogPermission getWeblogPermission(Weblog weblog, User user)
            throws WebloggerException {

        TypedQuery<WeblogPermission> q =
                strategy.getNamedQuery("WeblogPermission.getByUserName&WeblogId",
                        WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public WeblogPermission getWeblogPermissionIncludingPending(Weblog weblog, User user)
            throws WebloggerException {

        TypedQuery<WeblogPermission> q =
                strategy.getNamedQuery("WeblogPermission.getByUserName&WeblogIdIncludingPending",
                        WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, weblog.getHandle());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public void grantWeblogPermission(Weblog weblog, User user, List<String> actions)
            throws WebloggerException {

        WeblogPermission existing = getWeblogPermissionIncludingPending(weblog, user);
        if (existing == null) {
            WeblogPermission perm = new WeblogPermission(weblog, user, actions);
            perm.setPending(false);
            strategy.store(perm);
        } else {
            existing.setActionsAsList(actions);   // <-- use this
            existing.setPending(false);
            strategy.store(existing);
        }
    }

    @Override
    public void grantWeblogPermissionPending(Weblog weblog, User user, List<String> actions)
            throws WebloggerException {

        WeblogPermission existing = getWeblogPermissionIncludingPending(weblog, user);
        if (existing == null) {
            WeblogPermission perm = new WeblogPermission(weblog, user, actions);
            perm.setPending(true);
            strategy.store(perm);
        } else {
            existing.setActionsAsList(actions);   // <-- and here
            existing.setPending(true);
            strategy.store(existing);
        }
    }



    @Override
    public void confirmWeblogPermission(Weblog weblog, User user)
            throws WebloggerException {

        WeblogPermission perm = getWeblogPermissionIncludingPending(weblog, user);
        if (perm == null || !perm.isPending()) {
            throw new WebloggerException("No pending permission to confirm");
        }
        perm.setPending(false);
        strategy.store(perm);
    }

    @Override
    public void declineWeblogPermission(Weblog weblog, User user)
            throws WebloggerException {

        WeblogPermission perm = getWeblogPermissionIncludingPending(weblog, user);
        if (perm == null || !perm.isPending()) {
            throw new WebloggerException("No pending permission to decline");
        }
        strategy.remove(perm);
    }

    @Override
    public void revokeWeblogPermission(Weblog weblog, User user, List<String> actions)
            throws WebloggerException {

        WeblogPermission perm = getWeblogPermission(weblog, user);
        if (perm == null) {
            return;
        }

        // start from current actions as a list
        List<String> newActions = new ArrayList<>(perm.getActionsAsList());
        newActions.removeAll(actions);

        if (newActions.isEmpty()) {
            // no actions left: remove permission
            strategy.remove(perm);
        } else {
            // update remaining actions
            perm.setActionsAsList(newActions);
            strategy.store(perm);
        }
    }

    @Override
    public List<WeblogPermission> getWeblogPermissions(User user)
            throws WebloggerException {

        TypedQuery<WeblogPermission> q =
                strategy.getNamedQuery("WeblogPermission.getByUserName",
                        WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        return q.getResultList();
    }

    @Override
    public List<WeblogPermission> getPendingWeblogPermissions(User user)
            throws WebloggerException {

        TypedQuery<WeblogPermission> q =
                strategy.getNamedQuery("WeblogPermission.getPendingByUserName",
                        WeblogPermission.class);
        q.setParameter(1, user.getUserName());
        return q.getResultList();
    }

    @Override
    public List<WeblogPermission> getWeblogPermissions(Weblog weblog)
            throws WebloggerException {

        TypedQuery<WeblogPermission> q =
                strategy.getNamedQuery("WeblogPermission.getByWeblogId",
                        WeblogPermission.class);
        q.setParameter(1, weblog.getHandle());
        return q.getResultList();
    }

    @Override
    public List<WeblogPermission> getPendingWeblogPermissions(Weblog weblog)
            throws WebloggerException {

        TypedQuery<WeblogPermission> q =
                strategy.getNamedQuery("WeblogPermission.getPendingByWeblogId",
                        WeblogPermission.class);
        q.setParameter(1, weblog.getHandle());
        return q.getResultList();
    }

    @Override
    public List<WeblogPermission> getWeblogPermissionsIncludingPending(Weblog weblog)
            throws WebloggerException {

        TypedQuery<WeblogPermission> q =
                strategy.getNamedQuery("WeblogPermission.getAllByWeblogId",
                        WeblogPermission.class);
        q.setParameter(1, weblog.getHandle());
        return q.getResultList();
    }
}