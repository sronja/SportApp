
package sportapp.dao;

import java.util.List;
import java.sql.*;
import sportapp.domain.Sport;

public interface SportDao {
    
    Sport create(Sport sport) throws SQLException;
    
    List<Sport> getAll() throws SQLException;
}
