# SportApp

SportApp-sovelluksen päätarkoituksena on mahdollistaa käyttäjille tapa merkitä liikuntasuorituksensa muistiin. 
Ensimmäisellä kerralla sovellukseen tulee rekisteröityä keksimällä käyttäjänimi ja salasana. Sovellusta voi käyttää useampi rekisteröitynyt käyttäjä. 

Sovellus on tehty harjoitustyönä Helsingin yliopiston Tietojenkäsittelytieteen Ohjelmistotekniikka-kurssille.

## Dokumentaatio 

[Vaatimusmäärittely](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/maarittelydokumentti.md)

[Työaikakirjanpito](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/arkkitehtuuri.md)

## Releaset


## Komentorivitoiminnot

# Testaus

Testit suoritetaan seuraavalla komennolla

> mvn test

Testikattavuusraportti saadan luotua seuraavalla komennolla

> mvn test jacoco report

Kattavuusraporttia voi tutkia avaamalla selaimella tiedosto *target/site/jacoco/index.html*

# Suoritettava jarin generointi

Komennolla

> mvn package

saadan generoitua jar-tiedosto *SportApp-1.0-SNAPSHOT.jar* *target*-hakemistoon

# Checkstyle

Tiedoston [checkstyle.xml](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/checkstyle.xml) määrittämät tarkistukset saadaan suoritettua komennolla

> mvn jxr:jxr checkstyle:checkstyle

Mahdollisia virheilmoituksia voi tutkia avaamalla selaimella tiedosto *target/site/checkstyle.html*

