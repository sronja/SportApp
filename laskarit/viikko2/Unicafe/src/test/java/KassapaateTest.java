
import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kortti = new Maksukortti(400);
    }
    
    @Test
    public void kassapaatteenRahamaaraOikein() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test
    public void lounaidenMaaraOikein() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void edullinenKateisostoVaihtorahaOikein() {
        assertEquals(10, kassapaate.syoEdullisesti(250));
    }
    @Test
    public void edullinenKateisostoKassarahaOikein() {
        kassapaate.syoEdullisesti(250);
        assertEquals(100240, kassapaate.kassassaRahaa());
    }
    @Test
    public void edullinenKateisostoLounaidenMaaraKasvaa() {
        kassapaate.syoEdullisesti(250);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void maukasKateisostoVaihtorahaOikein() {
        assertEquals(50, kassapaate.syoMaukkaasti(450));
    }
    @Test
    public void maukasKateisostoKassarahaOikein() {
        kassapaate.syoMaukkaasti(450);
        assertEquals(100400, kassapaate.kassassaRahaa());
    }
    @Test
    public void maukasKateisostoLounaidenMaaraKasvaa() {
        kassapaate.syoMaukkaasti(450);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void edullinenKateisostoRahamaaraEiMuutu() {
        kassapaate.syoEdullisesti(100);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test
    public void edullinenKateisostoRahatPalautetaan() {
        assertEquals(100, kassapaate.syoEdullisesti(100));
    }
    @Test
    public void edullinenKateisostoLounaidenMaaraEiMuutu() {
        kassapaate.syoEdullisesti(100);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test 
    public void maukasKateisostoRahamaaraEiMuutu() {
        kassapaate.syoMaukkaasti(100);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test
    public void maukasKateisostoLounaidenMaaraEiMuutu() {
        kassapaate.syoMaukkaasti(100);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void maukasKateisostoRahatPalautetaan() {
        assertEquals(100, kassapaate.syoMaukkaasti(100));
    }
    @Test
    public void edullinenKorttiPalautetaanTrue() {
        assertEquals(true, kassapaate.syoEdullisesti(kortti));
    }
    @Test
    public void edullinenKorttiSummaVeloitetaan() {
        kassapaate.syoEdullisesti(kortti);
        assertEquals(160, kortti.saldo());
    }
    @Test
    public void edullinenKorttiLounaidenMaaraKasvaa() {
        kassapaate.syoEdullisesti(kortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void edullinenKorttiRahamaaraEiMuutu() {
        kassapaate.syoEdullisesti(kortti);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(160, kortti.saldo());
    }
    public void edullinenKorttiLounaidenMaaraEiKasva() {
        kassapaate.syoEdullisesti(kortti);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    public void edullinenKorttiPalautetaanFalse() {
        kassapaate.syoEdullisesti(kortti);
        assertEquals(false, kassapaate.syoEdullisesti(kortti));
    }
    @Test
    public void maukasKorttiPalautetaanTrue() {
        assertEquals(true, kassapaate.syoMaukkaasti(kortti));
    }
    @Test
    public void maukasKorttiSummaVeloitetaan() {
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(0, kortti.saldo());
    }
    @Test
    public void maukasKorttiLounaidenMaaraKasvaa() {
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void maukasKorttiRahamaaraEiMuutu() {
        kassapaate.syoMaukkaasti(kortti);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(0, kortti.saldo());
    }
    @Test
    public void maukasKorttiLounaidenMaaraEiKasva() {
        kassapaate.syoMaukkaasti(kortti);
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void maukasKorttiPalautetaanFalse() {
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(false, kassapaate.syoMaukkaasti(kortti));
    }
    @Test
    public void edullinenKorttiKassarahaEiMuutu() {
        kassapaate.syoEdullisesti(kortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test
    public void maukasKorttiKassarahaEiMuutu() {
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test
    public void kortinSaldoMuuttuuLadatessa() {
        kassapaate.lataaRahaaKortille(kortti, 100);
        assertEquals(500, kortti.saldo());
    }
    @Test
    public void kassarahaKasvaaLadatessa() {
        kassapaate.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, kassapaate.kassassaRahaa());
    }
    @Test
    public void kortinSaldoEiMuutuNegatiivisellaLatauksella() {
        kassapaate.lataaRahaaKortille(kortti, -10);
        assertEquals(400, kortti.saldo());
    }
}
