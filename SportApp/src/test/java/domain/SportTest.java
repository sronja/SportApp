package domain;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import sportapp.domain.Sport;
import sportapp.domain.User;

/**
 * Sport-luokan testiluokka
 */
public class SportTest {
    
    Sport sport;
    
    public SportTest() {
    }
    
    @Before
    public void setUp() {
        sport = new Sport(1, "running", 30.0, 5.0, null);
    }
    @Test
    public void equalWhenSameId() {
        Sport sport2 = new Sport(1, "running", 30.0, 5.0, null);
        assertTrue(sport.equals(sport2));
    }
    @Test
    public void notEqualWhenDifferentId() {
        Sport sport2 = new Sport (2, "running", 30.0, 5.0, null);
        assertFalse(sport.equals(sport2));
    }
    @Test
    public void notEqualWhenDifferentType() {
        Object object = new Object();
        assertFalse(sport.equals(object));
    }
}
