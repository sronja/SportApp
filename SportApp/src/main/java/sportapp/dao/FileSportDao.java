
package sportapp.dao;

import sportapp.domain.Sport;
import sportapp.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Urheilusuoritusten pysyväistallennus
 * 
 */
public class FileSportDao implements SportDao {
    public List<Sport> sports;
    private String file;
    
    public FileSportDao(String file, UserDao users) throws Exception {
        sports = new ArrayList<>();
        this.file = file;
        
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",");
                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                Double time = Double.parseDouble(parts[2]);
                Double distance = Double.parseDouble(parts[3]);
                Integer heartrate = Integer.parseInt(parts[4]);
                Integer feeling = Integer.parseInt(parts[5]);
                User user = users.getAll().stream().filter(us->us.getUsername().equals(parts[6])).findFirst().orElse(null);
                Sport sport = new Sport(id, type, time, distance, heartrate, feeling, user);
                sports.add(sport);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    /**
     * urheilusuoritusten tallentaminen tiedostoon
     * @throws Exception 
     */
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Sport sport: sports) {
                writer.write(sport.getId() + "," + sport.getType() + "," + sport.getTime() + "," + sport.getDistance() + "," + sport.getHeartrate() + "," + sport.getFeeling() + "," + sport.getUser().getUsername() + "\n");
            }
        }
    }
    /**
     * uuden id:n luominen urheilusuoritukselle
     * @return uusi id (listan koko + 1)
     */
    private int newId() {
        return sports.size() + 1;
    }
    
    /**
     * urheilusuorituksen lisääminen listalle
     * @param sport urheilusuoritus
     * @return sport urheilusuoritus
     * @throws Exception 
     */
    @Override
    public Sport create(Sport sport) throws Exception {
        sport.setId(newId());
        sports.add(sport);
        save();
        return sport;
    }
    
    /**
     * kaikkien käyttäjien urheilusuoritusten hakeminen 
     * @return urheilusuoritukset listana
     */
    @Override
    public List<Sport> getAll() {
        return sports;
    }
    /**
     * poistettavan käyttäjän urheilusuoritusten poistaminen
     * @param username käyttäjänimi
     * @throws Exception 
     */
    @Override
    public void delete(String username) throws Exception {
        List removedList = new ArrayList();
        for (Sport sport: sports) {
            if (sport.getUser().getUsername().equals(username)) {
                removedList.add(sport);
            }
        }   
        sports.removeAll(removedList);
        save();
                
    }
    /**
     * tietyn urheilusuorituksen poistaminen tiedostosta
     * @param type tyyppi
     * @param time aika
     * @param distance matka
     * @param heartrate keskisyke
     * @param feeling fiilis
     * @param username käyttäjänimi
     * @throws Exception 
     */
    @Override
    public void deleteSpecific(String type, double time, double distance, int heartrate, int feeling, String username) throws Exception {
        for (Sport sport: sports) {
            if (sport.getType().equals(type) && sport.getTime() == time && sport.getDistance() == distance && sport.getHeartrate() == heartrate && sport.getFeeling() == feeling && sport.getUser().getUsername().equals(username)) {
                sports.remove(sport);
            }
        }
        save();
    }
}
