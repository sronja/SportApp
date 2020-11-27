
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
        user = new User("maijamallikas", "salasana", "maija", 15, "suomi");
    }
    @Test
    public void equalWhenSameData() {
        User user2 = new User("maijamallikas", "salasana", "maija", 15, "suomi");
        assertTrue(user.equals(user2));
    }
    @Test
    public void notEqualWhenDifferentUsername() {
        User user2 = new User("mattimajava", "salasana", "maija", 15, "suomi");
        assertFalse(user.equals(user2));
    }
    @Test 
    public void notEqualWhenDifferentPassword() {
        User user2 = new User("maijamallikas", "elamaonihanaa", "maija", 15, "suomi");
        assertFalse(user.equals(user2));
    }
    @Test
    public void notEqualWhenDifferentType() {
        Object object = new Object();
        assertFalse(user.equals(object));
    }
}
