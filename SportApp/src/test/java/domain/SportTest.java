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
        sport = new Sport( "running", 30.0, 5.0, null);
    }
    @Test
    public void notEqualWhenDifferentType() {
        Object object = new Object();
        assertFalse(sport.equals(object));
    }
}
