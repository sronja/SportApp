# Vaatimusmäärittely
## Sovelluksen tarkoitus

Sovellus on tarkoitettu liikuntasuoritusten tallentamiseen, seuraamiseen ja tarkastelemiseen. Sovellusta voi käyttää useampi rekisteröitynyt käyttäjä, joilla on pääsy omaan liikuntasuoritusdataan.

## Käyttäjät

Ainakin aluksi sovelluksella on vain yksi käyttäjärooli eli *normaali käyttäjä*. Sovellukseen saatetaan luoda myös *erikoiskäyttäjä* eli käyttäjä, joka esimerkiksi voisi maksaa sovelluksen käytöstä ja saa pääsyn joihinkin sovelluksen ominaisuuksiin.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

* käyttäjä voi luoda järjestelmään käyttäjätunnuksen ja salasanan 
  * käyttäjätunnuksen täytyy olla uniikki ja vähintään 5 merkkiä pitkä
  * salasanan täytyy olla vähintään 8 merkkiä pitkä 
  * lisäksi käyttäjän tulee syöttää järjestelmään etunimi, ikä ja maa 
* käyttäjä voi kirjautua järjestelmään 
  * voimassaoleva käyttäjätunnus ja salasana kirjoitetaan kirjautumislomakkeelle oikeisiin kohtiin 
  * jos käyttäjää ei ole olemassa tai salasana on väärin, niin järjestelmä ilmoittaa siitä 

### Kirjautumisen jälkeen

* käyttäjä näkee lisäämänsä liikuntasuoritukset 
* käyttäjä voi lisätä uuden liikuntasuorituksen 
  * vain käyttäjä näkee lisäämänsä liikuntasuorituksen 
  * liikuntasuoritukseen kuuluvat tiedot: laji, matka, aika, keskisyke ja fiilis
* käyttäjä voi kirjautua ulos järjestelmästä 
* yksittäisen liikuntasuorituksen poistaminen
* käyttäjän lisäämien kaikkien liikuntasuoritusten poistaminen
* yhteenvedon näkeminen liikuntasuorituksista 
  * käytetty aika, kuljettu matka, kuljetun matkan keskipituus
* käyttäjätunnuksen ja siihen liittyvien tietojen poistaminen 

### Jatkokehitysideoita

Perusversion jälkeen järjestelmään voidaan laajentaa esimerkiksi seuraavilla toiminnallisuuksilla

* liikuntasuorituksiin liittyvän yhteenvedon laajentaminen
  * esim. kuinka monta kertaa harrastettu tiettyä lajia, kuinka monta urheilusuoritusta on yhteensä lisätty
  * yhteenvedon esittäminen taulukkomuodossa
* lisätyn urheilusuorituksen muokkaaminen
* käyttäjän tietojen muokkaaminen
* käyttäjät voivat pyytää toisia käyttäjiä kavereikseen
* kaverikäyttäjät voivat tarkistella toistensa urheilusuorituksia

