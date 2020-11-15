
package sportapp.domain;

import sportapp.dao.UserDao;
import sportapp.dao.SportDao;
/**
 * Sovelluslogiikasta vastaava luokka
 */
public class SportService {
    private UserDao userDao;
    private SportDao sportDao;
    private User loggedIn;
    
    public SportService (UserDao userDao, SportDao sportDao) {
        this.userDao = userDao;
        this.sportDao = sportDao;
       
    }
    /**
     * sisäänkirjautuminen
     * @param username
     * @return true jos käyttäjätunnus on olemassa, jos ei ole niin false
     */
    
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
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
    
    
}
