package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void rahanLataaminenToimii() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(10);
        assertEquals("saldo: 0.0", kortti.toString());
    }
    @Test
    public void saldoSailyyJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(100);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void otaRahaaPalauttaaTrue() {
        assertTrue(true==kortti.otaRahaa(10));
    }
    @Test
    public void otaRahaaPalauttaaFalse() {
        assertTrue(false==kortti.otaRahaa(100));
    }
    @Test
    public void saldoPalauttaaOikeanSumman() {
        assertEquals(10, kortti.saldo());
    }
}
