
package sportapp.dao;

import java.sql.*;
import sportapp.domain.Sport;

public interface SportDao {
    
    void create(Sport sport) throws SQLException;
    
    void getAll() throws SQLException;
}
