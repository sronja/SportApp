
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
import java.util.List;
import org.junit.Before;
import sportapp.dao.FileUserDao;
import sportapp.dao.UserDao;

/**
 *
 * FileUserDao-luokan testiluokka
 */
public class FileUserDaoTest {
    
    @Rule
    public TemporaryFolder temporaryTestFolder = new TemporaryFolder();
    
    File userFile;
    UserDao dao;
    
    @Before
    public void setUp() throws Exception {
        userFile = temporaryTestFolder.newFile("usersTestFile.txt");
        
        try (FileWriter testFile = new FileWriter(userFile.getAbsolutePath())) {
            testFile.write("maijamallikas,salasana,maija,15,suomi\n");
        }
        dao = new FileUserDao(userFile.getAbsolutePath());
    }
    
    @Test
    public void existingUserIsFound() throws Exception {
        User u = dao.findByUsername("maijamallikas");
        assertEquals("maijamallikas", u.getUsername());
        
    }
    @Test
    public void existingUserAndPasswordIsFound() throws Exception {
        User u = dao.findByUsernameAndPassword("maijamallikas", "salasana");
        assertEquals("maijamallikas", u.getUsername());
        assertEquals("salasana", u.getPassword());
    }
    @Test 
    public void deletingUserSucceeds() throws Exception {
        dao.delete("maijamallikas");
        User u = dao.findByUsername("maijamallikas");
        assertEquals(null, u);
    }
    @Test
    public void readingFromListSucceeds() throws Exception {
        dao.create(new User("heikki", "salaheikki", "Heikki", 50, "ruotsi"));
        dao.create(new User("jaakko", "salajaakko", "Jaakko", 25, "suomi"));
        List<User> users = dao.getAll();
        assertEquals(3, users.size());
        User user = users.get(2);
        assertEquals("jaakko", user.getUsername());
        assertEquals("salajaakko", user.getPassword());
        assertEquals("Jaakko", user.getName());
        assertNotEquals(50, user.getAge());
        assertNotEquals("ruotsi", user.getCountry());
    }
}
