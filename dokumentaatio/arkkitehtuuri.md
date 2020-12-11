# Rakenne

Ohjelmassa on kolme kerrosta: pakkaus *todoapp.ui* sisältää JavaFX:llä toteutetun graafisen käyttöliittymän, *todoapp.domain* ohjelman sovelluslokiigan ja *todoapp.dao* tietojen pysyväistallennukseen tarvittavan koodin.
Seuraava pakkauskaavio esittää koodin pakkausrakennetta: 

![Pakkausrakenne](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/pakkauskaavio.png)

# Käyttöliittymä

Sovelluksessa on yhteensä neljä eri sovellusikkunaa. Ne on toteutettu Scene-olioina, joista vain yksi näkyy kerrallaan eli on sijoitettuna stageen. 
Näkymät ovat:

* kirjautuminen
* uuden käyttäjän luominen
* pääsivu, jossa voi lisätä, katsoa ja poistaa urheilusuorituksia ja tutkia urheilusuoritusten tilastotietoa
* asetussivu

Käyttöliittymä on toteutettu luokkaan [sportapp.ui.SportUi](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/ui/SportUi.java). SportUi ei lähtökohtaisesti toteuta sovelluslogiikkaa, vaan se kutsuu sovelluslogiikasta vastaavan *todoService*-luokan metodeja.


# Sovelluslogiikka

Luokka [User](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/domain/User.java) kuvaa sovelluksen käyttäjiä ja luokka [Sport](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/domain/Sport.java) käyttäjien urheilusuorituksia.

![Pääluokat](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/pääluokat.png)

Toiminnallisuudesta vastaa luokka [SportService](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/domain/SportService.java), joka sisältää koodin käyttöliittymän toiminnoille. Se saa tarvitsemansa tiedon käyttäjistä ja urheilusuorituksista *sportapp.dao*-pakkauksessa sijaitsevien luokkien, jotka toteuttavat rajapinnat *SportDao* ja *UserDao*, kautta. 
Metodeja ovat esimerkiksi:

* boolean createUser(String username, String password, String name, int age, String country)
* boolean addSport(String contentType, double contentTime, double contentDistance, int contentHeartrate, int contentFeeling)
* double countMeanDistance()
* List<Sport> getSport()

SportApp-ohjelman osien suhdetta kuvaava luokka/pakkauskkaavio:

![Sovelluslogiikka](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/sovelluslogiikka.png)

# Tietojen pysyväistallennus

Pakkaus *sportapp.dao* sisältää luokat [FileSportDao](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/dao/FileSportDao.java) ja [FileUserDao](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/dao/FileUserDao.java), jotka sisältävät koodin tiedon pysyväistallennukseen. Sovelluslogiikka käyttää luokkiia rajapintojen *SportDao* ja *UserDao* kautta.

## Tiedostot

Tiedot talletetaan kahteen eri tiedostoon, joiden nimet on määritetty sovelluksen juureen sijoitetussa konfiguraatiotiedostossa [config.properties](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/config.properties).
Käyttäjiin liittyvä tieto talletetaan omaan tiedostoon ja urheilusuorituksiin liittyvä toiseen.

Käyttäjiin liittyvä tieto talletetaan seuraavanalaisesti:

> maijamallikas,salasana,Maija,50,suomi 

> heikki,salaheikki,Heikki,25,ruotsi

Pilkku siis erottaa käyttäjänimen, salasanan, etunimen, iän ja maan toisistaan.

Urheilusuoritukset tallennetaan samalla tavalla:

> 1,juoksu,60.0,10.0,145,10

> 2,hiihto,35.0,5.7,124,7

ja tässä siis pilkku erottaa urheilusuorituksen indeksin, tyypin, ajan, matkan, keskisykkeen ja fiiliksen. 

# Päätoiminnallisuudet

Joitakin ohjelman päätoiminnallisuuksia sekvenssikaavioin esitettynä.

**Käyttäjän kirjautuminen sisään**

Kun käyttäjä syöttää kirjautumissivun syöttökenttiin oikean käyttäjätunnuksen ja salasanan sekä painaa loginButtonia, etenee sovellus seuraavasti: 

![Kirjautuminen](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/loggingIn.png)

Nappia painettaessa tapahtumankäsittelijä kutsuu sovelluslogiikan *sportService* metodia [login](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/domain/SportService.java#L31) ja antaa parametreiksi käyttäjätunnuksen ja salasanan.
SportService selvittää *userDao*-luokan metodin [findByUserNameAndPassword](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/dao/FileUserDao.java#L90) avulla, onko parametreina annetut käyttäjätunnus ja salasana olemassa. 
Jos on, niin kirjautuminen onnistuu ja sivu vaihtuu *sportSceneksi* eli sovelluksen varsinaiseksi päänäkymäksi.

**Uuden käyttäjän luominen**

Kun käyttäjän rekisteröintinäkymässä on syötetty käyttäjätunnus, joka ei ole kenenkään muun käytössä (väh. 5 merkkiä) sekä salasana (väh. 8 merkkiä), etunimi, ikä ja maa oikeassa muodossa, ja käyttäjä painaa nappia *createUserButton* etenee sovellus seuraavasti:

![Rekisteröinti](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/signUp.png)

Nappia painettaessa tapahtumankäsittelijä kutsuu sovelluslogiikan *sportService* metodia [createUser](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/domain/SportService.java#L56) ja antaa parametreiksi käyttäjän syöttämät tiedot. 
*SportService* selvittää *userDao*-luokan metodin [findByUsername](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/dao/FileUserDao.java#L75) avulla onko käyttäjätunnus jo olemassa. Jos ei, niin sovelluslogiikka luo *User*-olion ja kutsuu *userDao*-luokan metodia [create](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/dao/FileUserDao.java#L56), jotta olio voidaan tallentaa.
Tämän jälkeen käyttöliittymä vaihtaa näkymäksi *loginScenen* eli kirjautumissivun.

**Muut toiminnallisuudet**

Sovelluksessa on muitakin toiminnallisuuksia. Ne toimivat samalla periaatteella kuin selitetyt toiminnallisuudet.

