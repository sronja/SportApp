package domain;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

/**
 * Sport-luokan testiluokka
 */
public class SportTest {
    
    Sport sport;
    
    public SportTest() {
    }
    
    @Before
    public void setUp() {
        sport = new Sport("running", 30.0, 5.0, null);
    }
    @Test
    public void notEqualWhenDifferentType() {
        Object object = new Object();
        assertFalse(sport.equals(object));
    
    }
    @Test
    public void returnsCorrectValues() {
        assertEquals("running", sport.getType());
        assertEquals(30.0, sport.getTime(), 0.01);
        assertEquals(5.0, sport.getDistance(), 0.01);
    }
}
