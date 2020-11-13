
package sportapp.dao;


import sportapp.domain.Sport;
import sportapp.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseSportDao implements SportDao {
    public List<Sport> sports;
    private String file;
    
    public DatabaseSportDao(String file, UserDao users) throws Exception {
        sports = new ArrayList<>();
        this.file = file;
        
        try {
            Scanner reader = new Scanner(new File(file));
            String[] parts = reader.nextLine().split(",");
            int id = Integer.parseInt(parts[0]);
            String type = parts[1];
            Double time = Double.parseDouble(parts[2]);
            Double distance = Double.parseDouble(parts[3]);
            User user = users.getAll().stream().filter(us->us.getUsername().equals(parts[4])).findFirst().orElse(null);
            Sport sport = new Sport(id, type, time, distance, user);
            sports.add(sport);
        }
        catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Sport sport: sports) {
                writer.write(sport.getId() + "," + sport.getType() + "," + sport.getTime() + "," + sport.getDistance() + "," + sport.getUser()  + "\n");
            }
        }
    }
    private int newId() {
        return sports.size() + 1;
    }
    
    @Override
    public Sport create(Sport sport) throws Exception{
        sport.setId(newId());
        sports.add(sport);
        save();
        return sport;
    }
    @Override
    public List<Sport> getAll() {
        return sports;
    }
    
}
