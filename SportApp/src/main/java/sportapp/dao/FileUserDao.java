
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

public class FileUserDao implements UserDao {
    private List<User> users;
    private String file;

    public FileUserDao(String file) throws Exception {
        users = new ArrayList<>();
        this.file = file;
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",");
                String username = parts[0];
                String password = parts[1];
                String name = parts[2];
                Integer age = Integer.parseInt(parts[3]);
                String country = parts[4];
                
                User user = new User(username, password, name, age, country);
                users.add(user);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    /*
    * käyttäjien tallentaminen tiedostoon
    */
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (User user : users) {
                    writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getAge() + "," + user.getCountry() + "\n");
                
            }
        }
    } 
    /*
    * uuden käyttäjän luominen ja tallentaminen tiedostoon
    */
    @Override
    public User create(User user) throws Exception {
        users.add(user);
        save();
        return user;
    }
    /**
     * käyttäjät listalla
     * @return listalla olevat käyttäjät
     */
    @Override
    public List<User> getAll() {
        return users;
         
    /**
     * oikean käyttäjän löytäminen käyttäjänimen perusteella
     */
    }
    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(someuser->someuser.getUsername()
                .equals(username))
                .findFirst()
                .orElse(null);
        
    }  
    /**
     * oikean käyttäjän löytäminen käyttäjänimen ja salasanan perusteella
     * @param username
     * @param password
     * @return user tai null
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        for (User user: users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
            
        }
        return null;
    }
    /**
     * käyttäjän poistaminen tiedostosta
     * @param username
     * @return käyttäjä, jos poisto onnistuu, muuten null
     * @throws Exception 
     */
    @Override
    public User delete(String username) throws Exception {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                users.remove(user);
                save();
                return user;
                
            }
        }
        return null;
    }
}
