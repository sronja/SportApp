
package dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sportapp.domain.User;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.FileWriter;
import org.junit.Before;
import sportapp.dao.FileUserDao;
import sportapp.dao.UserDao;

/**
 *
 * databaseUserDao-luokan testiluokka
 */
public class DatabaseUserDaoTest {
    
    @Rule
    public TemporaryFolder temporaryTestFolder = new TemporaryFolder();
    
    File userFile;
    UserDao dao;
    
    @Before
    public void setUp() throws Exception {
        userFile = temporaryTestFolder.newFile("usersTestFile.txt");
        
        try (FileWriter testFile = new FileWriter(userFile.getAbsolutePath())) {
            testFile.write("maijamallikas\n");
        }
        dao = new FileUserDao(userFile.getAbsolutePath());
    }
    
    @Test
    public void existingUserIsFound() throws Exception {
        User u = dao.findByUsername("maijamallikas");
        assertEquals("maijamallikas", u.getUsername());
        
        
    }
}
