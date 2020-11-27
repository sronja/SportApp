
package sportapp.domain;

/**
 * Järjestelmän käyttäjää kuvaava luokka
 */
public class User {
    private String username;
    private String password;
    private String name;
    private int age;
    private String country;
    
    public User(String username) {
        this.username = username;
    }
    public User(String username, String password, String name, int age, String country) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.country = country;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getCountry() {
        return country;
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
        if (this.username.equals(other.username) && this.password.equals(other.password)) {
            return true;
        }
        return false;
    }
    
}

