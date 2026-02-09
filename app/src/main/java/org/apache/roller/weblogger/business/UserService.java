package org.apache.roller.weblogger.business;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.pojos.User;

/**
 * User lifecycle and queries.
 */
public interface UserService {

    // user CRUD
    void addUser(User newUser) throws WebloggerException;

    void saveUser(User user) throws WebloggerException;

    void removeUser(User user) throws WebloggerException;

    long getUserCount() throws WebloggerException;

    User getUserByActivationCode(String activationCode) throws WebloggerException;

    // user queries
    User getUser(String id) throws WebloggerException;

    User getUserByUserName(String userName) throws WebloggerException;

    User getUserByUserName(String userName, Boolean enabled) throws WebloggerException;

    User getUserByOpenIdUrl(String openIdUrl) throws WebloggerException;

    List<User> getUsers(
            Boolean enabled,
            Date    startDate,
            Date    endDate,
            int     offset,
            int     length) throws WebloggerException;

    List<User> getUsersStartingWith(String startsWith,
                                    Boolean enabled,
                                    int offset,
                                    int length) throws WebloggerException;

    Map<String, Long> getUserNameLetterMap() throws WebloggerException;

    List<User> getUsersByLetter(char letter, int offset, int length)
            throws WebloggerException;
}