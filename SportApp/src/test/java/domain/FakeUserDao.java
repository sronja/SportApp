
package domain;

import java.util.ArrayList;
import java.util.List;
import sportapp.dao.UserDao;
import sportapp.domain.User;
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
    
}
