# Sovelluslogiikka

SportApp-ohjelman osien suhdetta kuvaava luokka/pakkauskkaavio:

![Sovelluslogiikka](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/sovelluslogiikka.png)

# Päätoiminnallisuudet

Joitakin ohjelman päätoiminnallisuuksia sekvenssikaavioin esitettynä.

**Käyttäjän kirjautuminen sisään**

Kun käyttäjä syöttää kirjautumissivun syöttökenttiin oikean käyttäjätunnuksen ja salasanan sekä painaa loginButtonia, etenee sovellus seuraavasti: 

![Kirjautuminen](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/loggingIn.png)

Nappia painettaessa tapahtumankäsittelijä kutsuu sovelluslogiikan *sportService* metodia login ja antaa parametreiksi käyttäjätunnuksen ja salasanan.
SportService selvittää *userDao*-luokan metodin findByUserNameAndPassword onko parametreina annetut käyttäjätunnus ja salasana olemassa. 
Jos on, niin kirjautuminen onnistuu ja sivu vaihtuu *sportSceneksi* eli sovelluksen varsinaiseksi päänäkymäksi.

**Uuden käyttäjän luominen**

Kun käyttäjän rekisteröintinäkymässä on syötetty käyttäjätunnus, joka ei ole kenenkään muun käytössä (väh. 5 merkkiä) sekä salasana (väh. 8 merkkiä), etunimi, ikä ja maa oikeassa muodossa, ja käyttäjä painaa nappia *createUserButton* etenee sovellus seuraavasti:

![Rekisteröinti](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/signUp.png)

Nappia painettaessa tapahtumankäsittelijä kutsuu sovelluslogiikan *sportService* metodia *createUser* ja antaa parametreiksi käyttäjän syöttämät tiedot. 
*SportService* selvittää *userDao*-luokan metodin *findByUsername* avulla onko käyttäjätunnus jo olemassa. Jos ei, niin sovelluslogiikka luo *User*-olion ja kutsuu *userDao*-luokna metodia *create*, jotta olio voidaan tallentaa.
Tämän jälkeen käyttöliittymä vaihtaa näkymäksi *loginScenen* eli kirjautumissivun.

**Muut toiminnallisuudet**

Sovelluksessa on muitakin toiminnallisuuksia. Ne toimivat samalla periaatteella kuin selitetyt toiminnallisuudet.

