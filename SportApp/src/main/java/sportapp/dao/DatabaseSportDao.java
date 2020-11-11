
package sportapp.dao;

import java.sql.*;
import sportapp.domain.Sport;

/**
 *
 * @author Ronja
 */
public class DatabaseSportDao implements SportDao {

    @Override
    public void create(Sport sport) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sport.db");
        PreparedStatement p = connection.prepareStatement("INSERT INTO Sport"
            + " (id, type, time, distance, user)"
            +" VALUES (?, ?, ?, ?, ?)");
        p.setInt(1, sport.getId());
        p.setString(2, sport.getType());
        p.setDouble(3, sport.getTime());
        p.setDouble(3, sport.getDistance());
        p.setObject(5, sport.getUser());
        
        p.executeUpdate();
        p.close();
        connection.close();
    }
    @Override
    public void getAll() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sport.db");
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM sport");
        ResultSet r = ps.executeQuery();
        if (!r.isBeforeFirst()) {
            System.out.println("You haven't saved any sports yet");
        } else {
            System.out.printf("%-15s %-15s %-15s\n", "type", "time", "distance");
            while(r.next()) {
                System.out.println(r.getString("type") + '\t'+'\t' + r.getDouble("time") + '\t'+'\t' + r.getDouble("distance"));
            }
        }
        
    }
    
}
