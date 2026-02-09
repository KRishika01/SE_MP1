package org.apache.roller.weblogger.business.jpa;

import java.sql.Timestamp;
import java.util.*;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.RoleService;
import org.apache.roller.weblogger.business.UserService;
import org.apache.roller.weblogger.config.WebloggerConfig;
import org.apache.roller.weblogger.pojos.User;

@Singleton
public class JPAUserService implements UserService {

    private static final Log log = LogFactory.getLog(JPAUserService.class);

    private final JPAPersistenceStrategy strategy;
    private final RoleService roleService;

    // cached mapping of userNames -> userIds
    private final Map<String, String> userNameToIdMap =
            Collections.synchronizedMap(new HashMap<>());

    @Inject
    public JPAUserService(JPAPersistenceStrategy strategy, RoleService roleService) {
        this.strategy = strategy;
        this.roleService = roleService;
    }

    // ---------------------------------------------------------- user CRUD ---

    @Override
    public void addUser(User newUser) throws WebloggerException {
        if (newUser == null) {
            throw new IllegalArgumentException("newUser cannot be null");
        }

        // first user admin logic
        boolean firstUserAdmin = WebloggerConfig.getBooleanProperty("users.firstUserAdmin");
        boolean adminUser = false;

        List<User> existingUsers = getUsers(Boolean.TRUE, null, null, 0, 1);
        if (existingUsers.isEmpty() && firstUserAdmin) {
            adminUser = true;
        }

        // check for duplicate username (case-insensitive)
        User byExact = getUserByUserName(newUser.getUserName(), null);
        User byLower = getUserByUserName(newUser.getUserName().toLowerCase(Locale.ENGLISH), null);
        if (byExact != null || byLower != null) {
            throw new WebloggerException("User with username already exists: " + newUser.getUserName());
        }

        strategy.store(newUser);

        // default editor role
        roleService.grantRole("editor", newUser);

        // optional admin role for first user
        if (adminUser) {
            roleService.grantRole("admin", newUser);
        }
    }

    @Override
    public void saveUser(User user) throws WebloggerException {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        strategy.store(user);
    }

    @Override
    public void removeUser(User user) throws WebloggerException {
        if (user == null) {
            return;
        }
        String userName = user.getUserName();
        strategy.remove(user);
        userNameToIdMap.remove(userName);
    }

    @Override
    public long getUserCount() throws WebloggerException {
        TypedQuery<Long> q =
                strategy.getNamedQuery("User.getCountEnabledDistinct", Long.class);
        q.setParameter(1, Boolean.TRUE);
        List<Long> results = q.getResultList();
        return results.isEmpty() ? 0L : results.get(0);
    }

    @Override
    public User getUserByActivationCode(String activationCode) throws WebloggerException {
        if (activationCode == null) {
            return null;
        }
        TypedQuery<User> q =
                strategy.getNamedQuery("User.getUserByActivationCode", User.class);
        q.setParameter(1, activationCode);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ------------------------------------------------------- user queries ---

    @Override
    public User getUser(String id) throws WebloggerException {
        if (id == null) {
            return null;
        }
        return (User) strategy.load(User.class, id);
    }

    @Override
    public User getUserByUserName(String userName) throws WebloggerException {
        return getUserByUserName(userName, Boolean.TRUE);
    }

    @Override
    public User getUserByOpenIdUrl(String openIdUrl) throws WebloggerException {
        if (openIdUrl == null) {
            return null;
        }
        TypedQuery<User> query =
                strategy.getNamedQuery("User.getByOpenIdUrl", User.class);
        query.setParameter(1, openIdUrl);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserByUserName(String userName, Boolean enabled)
            throws WebloggerException {

        if (userName == null) {
            return null;
        }

        // check cache first
        if (userNameToIdMap.containsKey(userName)) {
            String id = userNameToIdMap.get(userName);
            User cached = getUser(id);
            if (cached != null
                    && (enabled == null || Boolean.valueOf(cached.getEnabled()).equals(enabled))) {
                return cached;
            }
        }

        TypedQuery<User> query;
        if (enabled != null) {
            query = strategy.getNamedQuery("User.getByUserName&Enabled", User.class);
            query.setParameter(1, userName);
            query.setParameter(2, enabled);
        } else {
            query = strategy.getNamedQuery("User.getByUserName", User.class);
            query.setParameter(1, userName);
        }

        try {
            User user = query.getSingleResult();
            if (user != null) {
                userNameToIdMap.put(userName, user.getId());
            }
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getUsers(Boolean enabled, Date startDate, Date endDate,
                               int offset, int length) throws WebloggerException {

        TypedQuery<User> query;
        Timestamp end = new Timestamp(
                endDate != null ? endDate.getTime() : new Date().getTime());

        if (enabled != null) {
            query = strategy.getNamedQuery("User.getByEnabled&EndDate", User.class);
            query.setParameter(1, enabled);
            query.setParameter(2, end);
        } else {
            query = strategy.getNamedQuery("User.getByEndDate", User.class);
            query.setParameter(1, end);
        }

        if (offset > 0) {
            query.setFirstResult(offset);
        }
        if (length >= 0) {
            query.setMaxResults(length);
        }
        return query.getResultList();
    }

    @Override
    public List<User> getUsersStartingWith(String startsWith, Boolean enabled,
                                           int offset, int length) throws WebloggerException {

        if (startsWith == null) {
            startsWith = "";
        }

        TypedQuery<User> query;
        if (enabled != null) {
            query = strategy.getNamedQuery("User.getByUserNameOrEmailStartingWith&Enabled", User.class);
            query.setParameter(1, startsWith + "%");
            query.setParameter(2, startsWith + "%");
            query.setParameter(3, enabled);
        } else {
            query = strategy.getNamedQuery("User.getByUserNameOrEmailStartingWith", User.class);
            query.setParameter(1, startsWith + "%");
            query.setParameter(2, startsWith + "%");
        }

        if (offset > 0) {
            query.setFirstResult(offset);
        }
        if (length >= 0) {
            query.setMaxResults(length);
        }
        return query.getResultList();
    }

    @Override
    public Map<String, Long> getUserNameLetterMap() throws WebloggerException {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Map<String, Long> results = new TreeMap<>();

        TypedQuery<Long> query =
                strategy.getNamedQuery("User.getCountByUserNameLike", Long.class);

        for (int i = 0; i < letters.length(); i++) {
            String letter = letters.substring(i, i + 1);
            query.setParameter(1, letter + "%");
            List<Long> list = query.getResultList();
            long count = list.isEmpty() ? 0L : list.get(0);
            results.put(letter, count);
        }
        return results;
    }

    @Override
    public List<User> getUsersByLetter(char letter, int offset, int length)
            throws WebloggerException {

        TypedQuery<User> query =
                strategy.getNamedQuery("User.getByUserNameOrderByUserName", User.class);
        query.setParameter(1, letter + "%");

        if (offset > 0) {
            query.setFirstResult(offset);
        }
        if (length >= 0) {
            query.setMaxResults(length);
        }
        return query.getResultList();
    }
}