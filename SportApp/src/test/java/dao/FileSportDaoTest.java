
package dao;


import java.io.File;
import java.io.FileWriter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import sportapp.dao.FileSportDao;
import sportapp.dao.SportDao;
import domain.FakeUserDao;
import java.util.List;
import sportapp.domain.Sport;
import sportapp.domain.User;


/**
 *
 * FileSportDao-luokan testiluokka
 */
public class FileSportDaoTest {
    
    @Rule
    public TemporaryFolder temporaryTestFolder = new TemporaryFolder();
    
    File sportFile;
    SportDao dao;
    
    @Before
    public void setUp() throws Exception {
        sportFile = temporaryTestFolder.newFile("sportsTestFile.txt");
        FakeUserDao userDao = new FakeUserDao();
        userDao.create(new User("maijamallikas"));
        
        try (FileWriter testFile = new FileWriter(sportFile.getAbsolutePath())) {
            testFile.write("running,30.0,5.0,maijamallikas\n");
        }
        dao = new FileSportDao(sportFile.getAbsolutePath(), userDao);
    }
    @Test
    public void readingFromFileIsCorrect() {
        List<Sport> sports = dao.getAll();
        assertEquals(1, sports.size());
        Sport sport = sports.get(0);
        assertEquals("running", sport.getType());
        assertEquals(30.0, sport.getTime(), 0.01);
        assertEquals(5.0, sport.getDistance(), 0.01);
        assertEquals("maijamallikas", sport.getUser());
    }
    @Test
    public void addedSportsAreListed() throws Exception {
        dao.create(new Sport("skiing", 35.0, 6.0, new User("maijamallikas")));
        
        List<Sport> sports = dao.getAll();
        assertEquals(2, sports.size());
        Sport sport = sports.get(1);
        assertEquals("skiing", sport.getType());
        assertNotEquals("running", sport.getType());
        assertEquals(35.0, sport.getTime(), 0.01);
        assertNotEquals(30.0, sport.getTime(), 0.01);
        assertEquals(6.0, sport.getDistance(), 0.01);
        assertNotEquals(5.0, sport.getDistance(), 0.01);
        assertEquals("maijamallikas", sport.getUser());
    }
    @Test
    public void deletingSportsSucceeds() throws Exception {
        dao.create(new Sport("skiing", 35.0, 6.0, new User("maijamallikas")));
        dao.delete("maijamallikas");
        List<Sport> sports = dao.getAll();
        assertEquals(0, sports.size());
    }
   
}
