# Sovelluslogiikka

SportApp-ohjelman osien suhdetta kuvaava luokka/pakkauskkaavio:

![Sovelluslogiikka](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/sovelluslogiikka.png)

# Päätoiminnallisuudet

Joitakin ohjelman päätoiminnallisuuksia sekvenssikaavioin esitettynä.

**käyttäjän kirjautuminen sisään**

Kun käyttäjä syöttää kirjautumissivun syöttökenttiin oikean käyttäjätunnuksen ja salasanan sekä painaa loginButtonia, etenee sovellus seuraavasti: 

![Kirjautuminen](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/loggingIn.png)

Nappia painettaessa tapahtumankäsittelijä kutsuu sovelluslogiikan *sportService* metodia login ja antaa parametreiksi käyttäjätunnuksen ja salasanan.
SportService selvittää *userDao*-luokan metodin findByUserNameAndPassword onko parametreina annetut käyttäjätunnus ja salasana olemassa. 
Jos on, niin kirjautuminen onnistuu ja sivu vaihtuu *sportSceneksi* eli sovelluksen varsinaiseksi päänäkymäksi.
