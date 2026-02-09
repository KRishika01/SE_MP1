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
import org.apache.roller.weblogger.business.RoleService;
import org.apache.roller.weblogger.pojos.User;
import org.apache.roller.weblogger.pojos.UserRole;

@Singleton
public class JPARoleService implements RoleService {

    private static final Log log = LogFactory.getLog(JPARoleService.class);

    private final JPAPersistenceStrategy strategy;

    @Inject
    public JPARoleService(JPAPersistenceStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean hasRole(String roleName, User user) throws WebloggerException {
        if (user == null || roleName == null) {
            return false;
        }
        TypedQuery<Long> q =
                strategy.getNamedQuery("UserRole.hasRole", Long.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, roleName);
        List<Long> result = q.getResultList();
        long count = result.isEmpty() ? 0L : result.get(0);
        return count > 0;
    }

    @Override
    public List<String> getRoles(User user) throws WebloggerException {
        if (user == null) {
            return new ArrayList<>();
        }
        TypedQuery<UserRole> q =
                strategy.getNamedQuery("UserRole.getByUserName", UserRole.class);
        q.setParameter(1, user.getUserName());
        List<UserRole> roles = q.getResultList();
        List<String> names = new ArrayList<>();
        for (UserRole role : roles) {
            names.add(role.getRole());
        }
        return names;
    }

    @Override
    public void grantRole(String roleName, User user) throws WebloggerException {
        if (user == null || roleName == null) {
            return;
        }
        if (hasRole(roleName, user)) {
            return;
        }
        UserRole role = new UserRole();
        role.setUserName(user.getUserName());
        role.setRole(roleName);
        strategy.store(role);
    }

    @Override
    public void revokeRole(String roleName, User user) throws WebloggerException {
        if (user == null || roleName == null) {
            return;
        }
        TypedQuery<UserRole> q =
                strategy.getNamedQuery("UserRole.getByUserName&Role", UserRole.class);
        q.setParameter(1, user.getUserName());
        q.setParameter(2, roleName);
        try {
            UserRole role = q.getSingleResult();
            strategy.remove(role);
        } catch (NoResultException e) {
            // nothing to revoke
        }
    }
}