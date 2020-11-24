
package domain;

import java.util.ArrayList;
import java.util.List;
import dao.UserDao;
/**
 *
 * @author Ronja
 */
public class FakeUserDao implements UserDao {
    List<User> users = new ArrayList<>();

    public FakeUserDao() {
        users.add(new User("maijamallikas"));
    }
    @Override
    public User create(User user) throws Exception {
        users.add(user);
        return(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User findByUsername(String username) {
        return users.stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        for (User user: users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
            
        }
        return null;
    }
    @Override
    public User delete(String username) throws Exception{
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                users.remove(user);
                return user;
            }
        }
    return null;
    }
}

