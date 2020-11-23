
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sportapp.domain.User;

/**
 * User-luokan testiluokka
 */
public class UserTest {
    
    User user;
    
    public UserTest() {
    }
    
    @Before
    public void setUp() {
        user = new User("maijamallikas", "salasana");
    }
    @Test
    public void equalWhenSameUsernameAndPassword() {
        User user2 = new User("maijamallikas", "salasana");
        assertTrue(user.equals(user2));
    }
    @Test
    public void notEqualWhenDifferentUsername() {
        User user2 = new User("mattimajava", "salasana");
        assertFalse(user.equals(user2));
    }
    @Test
    public void notEqualWhenDifferentType() {
        Object object = new Object();
        assertFalse(user.equals(object));
    }
}
