
package sportapp.domain;

/**
 * Järjestelmän käyttäjää kuvaava luokka
 */
public class User {
    private String username;
    private String password;
    
    public User(String username) {
        this.username = username;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if (this.username.equals(other.username) && this.password == other.password) {
            return true;
        }
        return false;
    }
    
}
