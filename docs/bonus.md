## Dobrovoľná úloha

Vypracovaním dobrovoľnej úlohy je možné zlepšiť známku z úlohy 1.

**Zadanie úlohy**

Vyriešením prvej časti zadania, sme vytvorili jednoduchého HTTP klienta, ktorý pristupuje k REST rozhraniu webovej služby a sťahuje požadované dáta. V tomto prípade prístup na webovú službu nebol nijako zabezpečený a hocikto môže sťahovať ľubovolné dáta.  V reálnom svete len málo služieb je takto otvorených  a väčšina web služieb je zabezpečená nejakým bezpečnostným mechanizmom. Naša *WeatherStation* webová služba má aj zabezpečenú čast označenú ako `Auth` . Autorizácia prístupu je vytvorená pomocou tzv, Token-u, ktorý musí byť súčasťou každej požiadavky na server. Získať token je možné cez vytvorený URl zdroj `createJWT`, ktorý je zabezpečený pomocou `Basic access authentication` , čo dovoľuje iba  autorizovanému užívateľovi získať potrebný Tóken. 

Vašou úlohou je implementovať zvyšné metódy `Auth` v triede `WeatherStationService`:

``` java
public interface WeatherStationService {

    // ... getToken(authorization, claims); Používa sa http metóda POST na odoslanie údajov claims

    // ... getStationLocationsAuth(authorization);
    
    // ... getCurrentWeatherAuth(authorization, station);

    // ... getCurrentWeatherAuth(authorization, station, fields);

    // ... getHistoryWeatherAuth(authorization, station, from, to);

    // ... getHistoryWeatherAuth(authorization, station, from, to, fields);
}
```

Pre vypracovanie je potrebné naštudovať dokumentáciu k [Retrofit](https://square.github.io/retrofit/), kde sa dozviete ako sa dá upravovať hlavička http požiadavky.

!!! note "Unit testy"
	Pre overenie správnej funkcionality HTTP klienta, môžete povoliť kompiláciu unit testu `Auth` a spustiť daný test.

 

## REST rozhranie webovej služby

- Získanie prístupového tókenu, ktorý je potrebný na prístup k zabezpečeným zdrojom. Prístup k tomuto zdroju je zabezpečený pomocou [Basic autenticikačného mechanizmu](https://en.wikipedia.org/wiki/Basic_access_authentication). Prístupové údaje: `admin:heslo`

    `POST /apikey/createjwt`

    **Odosielané dáta sú vo forme formulárových dát (FORM ENCODED).**

- Tieto adresy majú rovnakú funkcionalitu ako `/weather` adresy ale prístup k nim je zabezpečený [JWT](https://jwt.io/) tokénom, ktorý treba pribaliť do hlavičky požiadavky `Authorization: ` `GET /weatherAuth/locations`
    `GET /weatherAuth/{station}/current`
    `GET /weatherAuth/{station}/history`

- Podrobnejšie informácie nájdete v sekcii [REST dokumentácie](./RESTapi/) alebo v interaktívnej swagger dokumentácii, ktorá je súčasťou *WeatherStation* služby `http://localhost:9000/swagger`.  

### Čo je autentifikácia?

Autentifikácia je akt, ktorým sa potvrdzuje, že používatelia sú tí za ktorých sa vydávajú. Meno a heslo sú najbežnejším autentifikačným faktorom - ak používateľ zadá správne heslo, systém predpokladá, že jeho totožnosť je platná a poskytne prístup.

### Čo je autorizácia?
Autorizácia v zabezpečení systému je proces, ktorý dáva užívateľovi povolenie na prístup ku konkrétnemu prostriedku alebo funkcii. Tento výraz sa často používa zameniteľne s kontrolou prístupu alebo oprávnením klienta. Dobrým príkladom je udelenie povolenia niekomu na stiahnutie konkrétneho súboru na serveri alebo poskytnutie administratívneho prístupu k dátam jednotlivých používateľov. V bezpečných prostrediach musí autorizácia vždy nasledovať po autentifikácii - používatelia by mali najprv dokázať, že ich totožnosť je pravá, skôr ako im systém poskytne prístup k požadovaným zdrojom.

### Basic access authentication

Je jednoduchý autorizačný mechanizmus používaný na webových serveroch. Webový server vyzve pomocou protokolu HTTP pristupujúceho klienta (typicky webový prehliadač), aby poslal v rámci svojej požiadavky tiež identifikačné informácie (tj. Meno a heslo). V praxi tento mechanizmus už moc nepoužíva ale pre svoju jednoduchosť bol zvolený v tejto úlohe.

Basic auth sa odosiela v http hlavičke `Authorization` a **služba WeatherStation vyžaduje aby mal nasledujúci tvar:**  `Authorization: Basic <base64 zakódované meno:heslo>`

**Postup autorizácie zo stany klienta**

Ak chce užívateľský agent poslať na server autentifikačné údaje, môže použiť pole *Autorizácia* .

Hlavička ``Authorization`sa zostavuje takto: 

1. Používateľské meno a heslo sa skombinujú použitím dvojbodky (:). To znamená, že samotné používateľské meno nemôže obsahovať dvojbodku.(admin:heslo)
2. Znaková sada, ktorá sa má použiť na toto kódovanie, je štandardne nešpecifikovaná, pokiaľ je kompatibilná s US-ASCII, ale server môže navrhnúť použitie UTF-8 odoslaním parametra *charset*.
3. Výsledný reťazec je kódovaný pomocou  [Base64](https://en.wikipedia.org/wiki/Base64) .
4. Autorizačná metóda („Basic“) a medzera  sa potom vložia pred kódovaný reťazec.

Napríklad, ak užívateľské meno je `Aladdin` a heslo `OpenSesame` , potom je hodnotou poľa základné kódovanie `Aladin:SezamOtvorSa` alebo `QWxhZGluOlNlemFtT3R2b3JTYQ==`. Potom hlavička ``Authorization` vyzerá takto:

```
Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l
```

### Ako funguje autentifikácia založená na tokene

Token sú dáta, ktoré sa v kombinácii so správnym systémom používajú na zabezpečenie prístupu používateľov k systémom a aplikáciám. Pri autentifikácii založenej na tokenoch sa používajú tokeny na zabezpečenie overenia **každej požiadavky** na server - podobne ako heslá umožňujú používateľom prihlásiť sa do služby.

V digitálnom prostredí moderné webové aplikácie zvyčajne používajú na autentifikáciu svojich používateľov webové tokeny JSON (JWT). JWT sú kódované ako objekty JSON a fungujú v rámci otvoreného štandardu na bezpečnú výmenu informácií medzi stranami.

**JWT token**

V skutočnosti môže JWT obsahovať akýkoľvek typ údajov, čo sa často využíva v spojení s **OAuth** bezpečnostnou schémou. Pri prístupovom tokene JWT je potrebné oveľa menej vyhľadávaní v databáze, pričom táto skutočnosť neohrozuje bezpečnosť.

Vo všeobecnosti JWT tokeny bývajú dlhšie ako iné typy prístupových tokenov ale vrámci dnešný prenosový kapacít sa dajú považovať za kompaktné (hoci to záleží na tom, koľko údajov v nich ukladáte):
`eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJob21lcGFnZSI6ImZyaS51bml6YS5zayIsInRhZ2xpbmUiOiJGcmkgaXMgdGhlIGJlc3QiLCJtZXNzYWdlIjoiR29vZCBqb2IhIFlvdSdyZSBhIG1hc3RlciBkZWNvZGVyISJ9.nXDGMIC4D7_EMM7t33-IwhdPMZAiPiYA1Zb9BqbcROI`. Na stránke [JWT](https://jwt.io/) si môžete overiť obsah tokenu.

To že nemusíme tak často vyhľadávať údaje v databáze je z dôvodu, že samotný JWT obsahuje kódované dáta vo formáte BASE64, ktoré sa vyžadujú na určenie identity a rozsahu prístupu. JWT obsahuje aj digitálny podpis vypočítaný na základe údajov. Pomocou podpisu vieme overiť platnosť obsahu a tento výpočet je omnoho efektívnejší ako vyhľadávanie tokenu v databáze, aby sa určilo, komu patrí a či je platný.

Tokeny JWT by sa mali odovzdať v http hlavičke `Authorization` a **služba WeatherStation vyžaduje aby mal nasledujúci tvar:** 

``` http
Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJob21lcGFnZSI6ImZyaS51bml6YS5zayIsInRhZ2xpbmUiOiJGcmkgaXMgdGhlIGJlc3QiLCJtZXNzYWdlIjoiR29vZCBqb2IhIFlvdSdyZSBhIG1hc3RlciBkZWNvZGVyISJ9.nXDGMIC4D7_EMM7t33-IwhdPMZAiPiYA1Zb9BqbcROI
```

!!! note "Bearer"
	Vo veľa systémoch sa pred samotným tokenom musí použiť klúčové slovíčko `Bearer` a hlavička vyzerá nasledovne `Authorization: Bearer <token>` 

Nevýhodou prístupových tokenov JWT je, že ich nemožno odvolať. Z tohto dôvodu sa používajú JWT v kombinácii s obnovovacími tokenmi a obmedzuje sa platnosti JWT. Pri každom volaní API sa musí skontrolovať podpis JWT a ubezpečiť sa, že token  neexpiroval.