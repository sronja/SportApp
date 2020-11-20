
package sportapp.dao;

import sportapp.domain.User;
import sportapp.domain.Sport;
import java.util.List;

public interface SportDao {
    
    Sport create(Sport sport) throws Exception;
    
    public List<Sport> getAll();
}
