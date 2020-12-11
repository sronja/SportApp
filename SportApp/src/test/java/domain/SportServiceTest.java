
package domain;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sportapp.domain.Sport;
import sportapp.domain.SportService;
import sportapp.domain.User;

/**
 *
 * SportService-luokan testiluokka
 */
public class SportServiceTest {
    
    SportService sportService;
    FakeSportDao sportDao;
    FakeUserDao userDao;
    
    @Before
    public void setUp() {
        sportDao = new FakeSportDao();
        userDao = new FakeUserDao();
        this.sportService = new SportService(userDao, sportDao);
        User user1 = new User("maijamallikas", "salasana", "Maija", 50, "suomi");
        User user2 = new User("heikki", "salaheikki", "Heikki", 35, "suomi");
        userDao.create(user1);
        userDao.create(user2);
        sportDao.create(new Sport(1, "running", 30.0, 5.0, 140, 8, user1));
        sportDao.create(new Sport(2, "skiing", 50.0, 8.0, 145, 10, user2));
        sportDao.create(new Sport(3, "walking", 60.0, 6.0, 110, 5, user2));
        sportService.login("maijamallikas", "salasana");
    }
    
    @Test
    public void loggedUsersTableDoesnotContaintOthersSports() {
        List<Sport> sports = sportService.getSport();
        assertEquals(1, sports.size());
    }
    
    @Test
    public void listEmptyWhenLoggedOut() {
        sportService.logout();
        List<Sport> sports = sportService.getSport();
        assertEquals(0, sports.size());
    }
    
    @Test
    public void loggedInIsNullWhenLoggedOut() {
        sportService.logout();
        assertEquals(null, sportService.getLoggedUser());
    }
    
    @Test
    public void loginFailsWhenUserIsNotFound() {
        sportService.logout();
        assertEquals(false, sportService.login("ronja", "salaronja"));
    }
    
    @Test
    public void deletingUserSucceeds() {
        sportService.deleteUser();
        assertEquals(1, userDao.getAll().size());
    }
    
    @Test
    public void deletingUserFails() {
        sportService.logout();
        assertEquals(false, sportService.deleteUser());
    }
    
    @Test
    public void deletingSportsSucceeds() {
        sportService.deleteSports();
        assertEquals(0, sportService.getSport().size());
    }
    
    @Test
    public void deletingSpecificSportSucceeds() {
        sportService.logout();
        sportService.login("heikki", "salaheikki");
        sportService.deleteSport("walking", 60.0, 6.0, 110, 5);
        List<Sport> sports = sportService.getSport();
        assertEquals(1, sports.size());
        assertEquals("skiing", sports.get(0).getType());
    }
    
    @Test
    public void creatingSportSucceeds() {
        sportService.addSport("running", 50.0, 8.0, 155, 9);
        assertEquals(2, sportService.getSport().size());
        assertEquals(155, sportService.getSport().get(1).getHeartrate());
    }
    @Test
    public void creatingUserSucceedsWhenCorrectData() {
        assertEquals(true, sportService.createUser("ronja", "salaronja", "Ronja", 20, "suomi"));
        assertEquals(false, sportService.createUser("maijamallikas", "salasana", "Maija", 50, "suomi"));
    }
    @Test
    public void meanDistanceIsCorrect() {
        sportService.addSport("running", 50.0, 8.0, 155, 9);
        assertEquals(6.5, sportService.countMeanDistance(), 0.01);
        sportService.deleteSports();
        assertEquals(0.0, sportService.countMeanDistance(), 0.01);
    }
    @Test
    public void sumDistanceIsCorrect() {
        sportService.addSport("running", 50.0, 8.0, 155, 9);
        assertEquals(13.0, sportService.countSumDistance(), 0.01);
        sportService.deleteSports();
        assertEquals(0.0, sportService.countSumDistance(), 0.01);
    }
    @Test
    public void sumTimeIsCorrect() {
        sportService.addSport("running", 50.0, 8.0, 155, 9);
        assertEquals(80.0, sportService.countSumTime(), 0.01);
        sportService.deleteSports();
        assertEquals(0.0, sportService.countSumTime(), 0.01);
    }
    
}
