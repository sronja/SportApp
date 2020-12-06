
package sportapp.domain;

import java.util.List;
import java.util.stream.Collectors;
import sportapp.dao.UserDao;
import sportapp.dao.SportDao;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

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
     * @param username käyttäjänimi
     * @param password salasana
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
     * @param username käyttäjänimi
     * @param password salasana
     * @param name etunimi
     * @param age ikä
     * @param country maa
     * @return true jos käyttäjän rekisteröinti onnistuu, jos ei niin false
     */
    public boolean createUser(String username, String password, String name, int age, String country) {
        if (userDao.findByUsername(username) == null) {
            User user = new User(username, password, name, age, country);
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
     * @param contentHeartrate lisättävän urheiusuorituksen keskimääräinen syke
     * @param contentFeeling lisättävän urheiusuorituksen aikainen fiilis
     * @return true, jos lisäys onnistuu, muuten false
     */
    public boolean addSport(String contentType, double contentTime, double contentDistance, int contentHeartrate, int contentFeeling) {
        Sport sport = new Sport(contentType, contentTime, contentDistance, contentHeartrate, contentFeeling, loggedIn);
        try {
            sportDao.create(sport);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * kirjautuneen käyttäjän lisäämien urheilusuoritusten hakeminen tiedostosta
     * @return kirjautuneen käyttäjän lisäämät urheilusuoritukset
     */
    public List<Sport> getSport() {
        return sportDao.getAll()
                .stream()
                .filter(u -> u.getUser().equals(loggedIn))
                .collect(Collectors.toList());
    }
    
    /**
     * kirjautunut käyttäjä
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
    
    /**
     * kirjautuneen käyttäjän urheilusuoritusten poistaminen
     * @return true jos onnistuu, jos ei niin false
     */
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
    
    /**
     * kirjautuneen käyttäjän tietyn urheilusuorituksen poistaminen
     * @param type
     * @param time
     * @param distance
     * @param heartrate
     * @param feeling
     * @return true jos onnistuu, muuten false
     */
    public boolean deleteSport(String type, double time, double distance, int heartrate, int feeling) {
        try {
            if (userDao.findByUsername(loggedIn.getUsername()) != null) {
                sportDao.deleteSpecific(type, time, distance, heartrate, feeling, loggedIn.getUsername());
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    return false;
}      
    
    /**
     * käyttäjän lisäämien urheilusuoritusten keskimääräisen matkan laskeminen
     * @return matkan keskiarvo
     */
    public double countMeanDistance() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        if (getSport().isEmpty()) {
            return 0.0;
        }
        for (Sport sport: getSport()) {
            stats.addValue(sport.getDistance());
        }
        return stats.getMean();
        
    }
    
    /**
     * käyttäjän lisäämien urheilusuoritusten matkan yhteispituuden laskeminen
     * @return matkojen summa
     */
    public double countSumDistance() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Sport sport: getSport()) {
            if (getSport().size() < 1) {
                return 0.0;
            }
            stats.addValue(sport.getDistance());
        }
        return stats.getSum();
    }
    
    /**
     * käyttäjän lisäämiin urheilusuorituksiin käytetyn ajan summan laskeminen
     * @return aikojen summa
     */
    public double countSumTime() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Sport sport: getSport()) {
            if (getSport().size() < 1) {
                return 0.0;
            }
            stats.addValue(sport.getTime());
        }
        return stats.getSum();
    }
    
}
