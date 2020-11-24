
package sportapp.domain;

import java.util.List;
import java.util.stream.Collectors;
import sportapp.dao.UserDao;
import sportapp.dao.SportDao;
/**
 * Sovelluslogiikasta vastaava luokka
 */
public class SportService {
    private UserDao userDao;
    private SportDao sportDao;
    private User loggedIn;
    
    public SportService(UserDao userDao, SportDao sportDao) {
        this.userDao = userDao;
        this.sportDao = sportDao;
       
    }
    /**
     * sisäänkirjautuminen
     * @param username
     * @param password
     * @return true jos käyttäjätunnus on olemassa, jos ei ole niin false
     */
    
    public boolean login(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }
    /**
     * uloskirjautuminen
     */
    
    public void logout() {
        loggedIn = null;
    }
    /**
     * uuden käyttäjän rekisteröinti
     * @param username käyttäjätunnus
     * 
     * @return true jos käyttäjän rekisteröinti onnistuu, jos ei niin false
     */
    
    public boolean createUser(String username, String password) {
        if (userDao.findByUsername(username) == null) {
            User user = new User(username, password);
            try {
                userDao.create(user);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }
    /**
     * Uuden urheilusuorituksen lisääminen käyttäjälle
     * @param contentType lisättävän urheilusuorituksen laji
     * @param contentTime lisättävän urheilusuorituksen aika
     * @param contentDistance lisättävän urheilusuorituksen matka
     */
    public boolean addSport(String contentType, double contentTime, double contentDistance) {
        Sport sport = new Sport(contentType, contentTime, contentDistance, loggedIn);
        try {
            sportDao.create(sport);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Sport> getSport() {
        return sportDao.getAll()
                .stream()
                .filter(u -> u.getUser().equals(loggedIn))
                .collect(Collectors.toList());
    }
    /**
     * kirjautunut käyttäjä
     * 
     * @return kirjautunut käyttäjä
     */
    
    public User getLoggedUser() {
        return loggedIn;
    }
    /**
     * kirjautuneen käyttäjän poistaminen
     * @return true jos käyttäjän poistaminen onnistuu, false jos ei
     */
    public boolean deleteUser() {
        try {
            if (userDao.findByUsername(loggedIn.getUsername()) != null) {
                userDao.delete(loggedIn.getUsername());
                sportDao.delete(loggedIn.getUsername());
                
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean deleteSports() {
        try {
            if (userDao.findByUsername(loggedIn.getUsername()) != null) {
                sportDao.delete(loggedIn.getUsername());
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
