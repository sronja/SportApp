
package sportapp.dao;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import sportapp.domain.User;

/**
 *
 * @author Ronja
 */

public class DatabaseUserDao implements UserDao {
    private List<User> users;
    private String file;

    public DatabaseUserDao(String file) throws Exception {
        users = new ArrayList<>();
            this.file = file;
            try {
                Scanner reader = new Scanner(new File(file));
                while (reader.hasNextLine()) {
                    User u = new User(reader.nextLine());
                    users.add(u);
                }
            } catch (Exception e) {
                FileWriter writer = new FileWriter(new File(file));
                writer.close();
            }
    
        }
        
        private void save() throws Exception {
            try (FileWriter writer = new FileWriter(new File(file))) {
                for (User user : users) {
                    writer.write(user.getUsername()+ "\n");
                }
            }
        } 
        @Override
        public User create (User user) throws Exception {
            users.add(user);
            save();
            return user;
        }
        @Override
        public List<User> getAll() {
            return users;
        
        
    }
    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(someuser->someuser.getUsername()
                .equals(username))
                .findFirst()
                .orElse(null);
        
        }
    
}
