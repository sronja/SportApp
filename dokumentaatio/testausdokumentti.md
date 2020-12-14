# Testausdokumentti

Ohjelman testauksessa on käytetty JUnitin automatisoituja yksikkö- ja integraatiotestejä. 
Järjestelmätason testit on suoritettu manuaalisesti.

# Yksikkö- ja integraatiotestaus

## Sovelluslogiikka

Sovelluslogiikan ydintestiluokaksi voidaan määritellä [SportServiceTest](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/test/java/domain/SportServiceTest.java), joka sisältää integraatiotestit pakkauksen [sportapp.domain](https://github.com/sronja/ot-harjoitustyo/tree/main/SportApp/src/main/java/sportapp/domain) luokille.
Ydintestiluokan testit on pyritty tekemään niin, että ne mallintavat mahdollisimman hyvin käyttöliittymän [SportService](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/main/java/sportapp/domain/SportService.java)-olion avulla suorittamia toiminnallisuuksia.

Jotta integraatiotestit voivat tallentaa dataa oikean ohjelman tavoin, on niitä varten luotu luokat [FakeSportDao](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/test/java/domain/FakeSportDao.java) ja [FakeUserDao](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/test/java/domain/FakeUserDao.java).

Sovelluslogiikkaa testataan myös yksikkötestein testiluokilla [UserTest](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/test/java/domain/UserTest.java) ja [SportTest](https://github.com/sronja/ot-harjoitustyo/blob/main/SportApp/src/test/java/domain/SportTest.java).

## DAO-luokat

DAO-luokkien testausta varten on testeissä luotu JUnitin TemporaryFolder eli väliaikainen tiedosto.

## Testauskattavuus

Sovelluksen testauksen rivikattavuus on 93% ja haarautumakattavuus 77%. Testikattavuus ei huomioi käyttöliittymäkerrosta.

![Testikattavuus](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kuvat/testauskattavuus.png)

# Järjestelmätestaus

Järjestelmätason testaus on suoritetty manuaalisesti.

## Asennus ja konfigurointi

Sovellus on ladattu ja testattu [käyttöohjeen](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/kayttoohje.md) mukaisesti macOS-ympäristössä.
Testauksessa on huomioitu se, että sovelluksen käynnistyshakemistossa on tiedosto *config.properties*.

Testejä on suoritettu niin, että tiedontallennukseen tarvittavia tiedostoja ei ole olemassa, jolloin sovellus on luonut ne, sekä niin, että tiedostot on jo olemassa.

## Toiminnallisuudet

Testauksessa on käyty läpi kaikki [määrittelydokumentissa](https://github.com/sronja/ot-harjoitustyo/blob/main/dokumentaatio/maarittelydokumentti.md) ja käyttöohjeessa esiintyvät toiminnallisuudet.
Toiminnallisuudet, jotka sisältävät arvojen syöttämistä, on testattu myös virheellisin syöttein: esimerkiksi kenttään, joka vaatii numeron on syötetty merkkijono.


