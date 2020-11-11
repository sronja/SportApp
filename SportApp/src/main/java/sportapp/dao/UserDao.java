
package sportapp.dao;

import java.util.List;
import java.sql.*;
import sportapp.domain.User;

public interface UserDao {
    
    void create(User user) throws SQLException;
    
    void getAll() throws SQLException;

    public User findByUsername(String username, String password) throws SQLException;
}
