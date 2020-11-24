
package sportapp.dao;


import sportapp.domain.Sport;
import sportapp.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class FileSportDao implements SportDao {
    public List<Sport> sports;
    private String file;
    
    public FileSportDao(String file, UserDao users) throws Exception {
        sports = new ArrayList<>();
        this.file = file;
        
        try {
            Scanner reader = new Scanner(new File(file));
            String[] parts = reader.nextLine().split(",");
            String type = parts[0];
            Double time = Double.parseDouble(parts[1]);
            Double distance = Double.parseDouble(parts[2]);
            User user = users.getAll().stream().filter(us->us.getUsername().equals(parts[3])).findFirst().orElse(null);
            Sport sport = new Sport(type, time, distance, user);
            sports.add(sport);
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
                writer.write(sport.getType() + "," + sport.getTime() + "," + sport.getDistance() + "," + sport.getUser()  + "\n");
            }
        }
    }
    private int newId() {
        return sports.size() + 1;
    }
    
    /**
     * urheilusuorituksen lisääminen listalle
     * @param sport
     * @return sport urheilusuoritus
     * @throws Exception 
     */
    @Override
    public Sport create(Sport sport) throws Exception {
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
     * @param username
     * @throws Exception 
     */
    @Override
    public void delete(String username) throws Exception {
        List removedList = new ArrayList();
        for (Sport sport: sports) {
            if (sport.getUser().equals(username)) {
                removedList.add(sport);
            }
        }   
        sports.removeAll(removedList);
        save();
                
    }
    
}
