
package sportapp.dao;


import java.sql.*;
import java.util.List;
import sportapp.domain.User;

/**
 *
 * @author Ronja
 */

public class DatabaseUserDao implements UserDao {


    @Override
    public void create(User user) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:user.db");
        PreparedStatement p = connection.prepareStatement("INSERT INTO User"
            + " (username, password)"
            + " VALUES (?, ?)");
        p.setString(1, user.getUsername());
        p.setString(2, user.getPassword());
        
        p.executeUpdate();
        p.close();
        connection.close();
    }

    @Override
    public void getAll() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:user.db");
        PreparedStatement ps = connection.prepareStatement("SELECT username FROM User");
        ResultSet r = ps.executeQuery();
        while (r.next()) {
            System.out.println("Username: " + r.getString("username"));
        }
        
    }
    @Override
    public User findByUsername(String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:user.db");
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        
        ResultSet r = ps.executeQuery();
        if (!r.next()) {
            return null;
        } else {
            User user = new User(r.getString(username), r.getString(password));
            
            connection.close();
            ps.close();
            r.close();
            
            return user;
        }
    } 
}
