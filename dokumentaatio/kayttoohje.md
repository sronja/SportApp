# Käyttöohje

Lataa ensin tiedosto [sportApp.jar](https://github.com/sronja/ot-harjoitustyo/releases/tag/viikko7)

## Konfigurointi

Jotta ohjelman suorittaminen onnistuisi, tulee sen käynnistyshakemistossa olla konfiguraatiotiedosto *config.properties*. Siinä määritelläät tiedostot, joihin ohjelma tallettaa tietoa käyttäjistä ja urheilusuorituksista.
Tiedoston sisältö on seuraava

> userFile=userFile.txt

> sportFile=sportFile.txt

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla

> java -jar SportApp.jar

## Kirjautuminen

Sovelluksen ensimmäinen näkymä on kirjautumissivu:

![Kirjautuminen](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/kirjautumissivu.png)

Kirjautuminen tapahtuu syöttämällä oikea käyttäjätunnus ja salasana kenttiin, ja painamalla sen jälkeen *login*-nappia.

## Uuden käyttäjän luominen

Kirjautumissivulla olevaa *Sign up*-nappia painaessa avautuu rekisteröitymissivu.
Rekisteröityminen onnistuu syöttämällä vaaditut tiedot kenttiin ja painamalla *create*-nappia. Jos tiedot oli syötetty oikeassa muodossa, ja käyttäjänimi ei ollut entuudestaan käytössä, avautuu kirjautumissivu uudelleen esille.

![Rekisteröityminen](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/rekisteröintisivu.png)

## Urheilusuoritusten lisääminen ja poistaminen

Kirjautumisen jälkeen avautuu pääsivu, jossa voi lisätä ja poistaa urheilusuorituksia sekä tutkia urheilusuorituksia ja niihin liittyvää tilastotietoa.

![Pääsivu](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/pääsivu.png)

Uuden urheilusuorituksen lisääminen onnistuu syöttämällä urheilusuorituksen tiedot oikeassa muodossa syöttökenttiin ja painamalla sitten nappia *add*.
Kaikki urheilusuoritukset voi poistaa painamalla nappia *delete all*.
Yksittäisen urheilusuorituksen voi puolestaan poistaa painamalla ensin hiirellä urheilusuoritusta taulukossa ja sen jälkeen nappia *delete selected*.
*Log out*-nappia painamalla käyttäjä kirjataan ulos sovelluksesta, ja kirjautumissivu tulee esille.
*Settings*-nappia painamalla avautuus asetussivu.

## Käyttäjän poistaminen

Käyttäjän poistaminen onnistuu asetussivulla olevaa *delete*-nappia painamalla.

![Asetussivu](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/asetussivu.png)

