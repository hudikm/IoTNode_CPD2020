## Webová služba

Pojem webová služba môžeme chápať ako komunikačný kanál prostredníctvom ktorého dve elektronické zariadenia medzi sebou komunikujú a to prostredníctvom siete internet známej ako World Wide Web. Taktiež si pod týmto pojmom môžeme predstaviť server, ktorý na určitom porte počúva a prijíma žiadosti o poskytnutie dát vo forme web dokumentov(HTML, JSON, XML, obrázky a pod.) . Pri webových službách je štandardom použitie komunikačného protokolu HTTP, ktorý je založený na spracovaní požiadaviek. 

W3C definuje webovú službu ako softvérový systém navrhnutý na podporu interakcie stroj-stroj, ktorá prebieha cez sieť. Má rozhranie popísané vo formáte, ktorému rozumejú klienti, ktorí chcú danú službu využívať (konkrétne WSDL). Klientské systémy interagujú s webovou službou predpísaným spôsobom  pomocou správ SOAP(Simple Object Access Protocol), ktoré sa zvyčajne prenášajú pomocou protokolu HTTP s využitím XML.

V dnešnej dobe webové služby založené na SOAP už nie sú také dominantné a do popredia sa dostávajú aj iné architektúry na ktorých sú založené webové služby a to hlavne REST architektúra. Existujú aj ďalšie architektúry ako napr. GraphQL, UDDI, XML-RPC ale zatiaľ niesu natoľko štandardizované aby sa využívali vo väčšom merítku.   

S pojmom webová služba sa spája aj pojem API, ktorý môžeme preložiť ako aplikačné rozhranie. Všeobecne aplikačné rozhranie slúži na sprístupnenie aplikácie/knižnice/softvéru inej aplikácii. V prípade webovej služby API tak isto slúži na sprístupnenie funkcionality pre inú aplikáciu/službu ale v tomto prípade výmena informácií musí prebiehať po sieti. Inak povedané API odhaľuje funkčnosť aplikácie a/alebo poskytuje údaje tretím stranám. 

V tejto úlohe budeme používať REST aplikačné rozhranie, ktoré je popísané v nasledujúcich častiach. 

### RESTful

REST (Representational State Transfer) architektúra nie je technológia, ktorú si môžeme kúpiť alebo stiahnuť ako knižnicu a následne použiť v aplikácií. Je to skorej filozofický pohľad určujúci formu akou sa vytvára aplikačné rozhranie, ktoré sprístupňuje dáta a služby.  

Myšlienky a pojmy, ktoré používame na opis systémov „RESTful“, boli predstavené v práci doktora Roya Fieldinga “Architectural Styles and the Design of Network- based Software Architectures”. Tento dokument je prístupný a poskytuje základ pre prax.

Táto architektúra je založená na prenose, sprístupnení, manipulovaní textových dát a keďže využíva HTTP protokol tak je aj bez stavová. Pojmem bez stavová môžeme chápať tak, že server si nevedie žiadne záznamy o tom aké, komu alebo kedy nejaké služby/dáta poskytol. Pri správnej implementácii dané rozhranie má poskytovať jednotné vzájomne kompatibilné rozhranie pre komunikáciu rôznych aplikácií na internete. REST API adresuje jednotlivé zdroje pomocou URL adresy. Schéma URL je definovaná v RFC 1738, ktorý nájdete [tu](ietf.org/rfc/rfc1738.txt) 

Príklad adresovania RESTového zdroja:

```
http://fakelibrary.org/library
```

Táto adresa sa delí na čast, ktorá určuje protokol, aktuálne je to `http` a časť, ktorá určuje umiestnenie zdroja v rámci REST rozhrania `fakelibrary.org/library`. Bez dodatočných informácií nevieme určiť aká služba/zdroj sa pod danou adresou nachádza. 

V REST rozhraní sú definované nad zdrojom tzv. `CRUD` operácie: `Create`(vytvorenie), `Read`(čítanie), `Update`(aktualizácia),`Delete`(vymazanie). Tieto operácie sú implementované pomocou metód HTTP protokolu:

| HTTP Verb | CRUD              | Celá zbierka (napr. `/Zákazníci`)<br />*úvodné číslo je kód odozvy http* | Konkrétna položka (napr. `/Zákazníci/{id}`)                  |
| :-------- | :---------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| POST      | Create            | 201 (Vytvorené), hlavička „Poloha“ s odkazom na / customers / {id} obsahujúci nové ID. | 404 (nenájdené), 409 (konflikt), ak zdroj už existuje.       |
| GET       | Read              | 200 (OK), zoznam zákazníkov. Na navigáciu vo veľkých zoznamoch použite stránkovanie, triedenie a filtrovanie. | 200 (OK), jeden zákazník. 404 (nenájdené), ak ID nebolo nájdené alebo neplatné. |
| PUT       | Update / Nahradiť | 405 (metóda nie je povolená), pokiaľ nechcete aktualizovať / nahradiť všetky prostriedky v celej kolekcii. | 200 (OK) alebo 204 (bez obsahu). 404 (nenájdené), ak ID nebolo nájdené alebo neplatné. |
| PATCH     | Update / Upraviť  | 405 (metóda nie je povolená), pokiaľ nechcete zmeniť samotnú kolekciu. | 200 (OK) alebo 204 (bez obsahu). 404 (nenájdené), ak ID nebolo nájdené alebo neplatné. |
| DELETE    | Delete            | 405 (metóda nie je povolená), pokiaľ nechcete odstrániť celú kolekciu - často nie je žiaduce. | 200 (OK). 404 (nenájdené), ak ID nebolo nájdené alebo neplatné. |

Existujú aj ďalšie metódy v HTTP tie sa ale tak často v REST rozhraní nepoužívajú. Keďže REST rozhranie nieje úplne štandardizované, tak vzťah medzi HTTP metódami a CRUD operáciami nieje vždy taký ako je znázornený v tabuľke.

Dátová reprezentácia zdroja môže byť rôzna, najčastejšie sa používa JSON a XML ale môže to byť ľubovoľná reprezentácia ako napr. TEXT, HTML, JPG, PDF a pod. V HTTP protokole definujeme dátovú reprezentáciu pomocou MIME typov, ktoré sú zapísané v hlavičke HTTP paketu. 

Kombináciou URL dresy, ktorú používame na určenie adresy zdroja a HTTP metód, ktoré definujú čo s daným zdrojom chceme vykonať a MIME typov v HTTP hlavičke, presne definujeme jednotlivé zdroje RESTového rozhrania. 

> **Príklad komunikácie cez REST rozhranie**
>
> ![](diagrams/out/REST_example.svg)
> Komunikácie prebieha cez HTTP protokol, ktorý je bez stavový protokol typu Klient/Sever 

### Stavové kódy HTTP

**Na informovanie o výsledku spracovania požiadavky sa používajú stavové kódy HTTP**

Stavové kódy odpovedí sú súčasťou špecifikácie HTTP. Existuje ich veľa a vieme s nimi opísať najbežnejšie situácie.  Na to aby RESTful služby obsahovali špecifikáciu HTTP, by mali webové rozhrania API vracať príslušné stavové kódy HTTP. Napríklad, keď sa zdroj úspešne vytvorí (napr. požiadavka POST), API by malo vrátiť stavový kód 201.  Tu je k dispozícii zoznam platných [stavových kódov HTTP](https://www.restapitutorial.com/httpstatuscodes.html), v ktorých sú uvedené podrobné opisy každého z nich.

Najčastejšie používané kódy

- `200 OK`

    Všeobecný stavový kód úspechu. Toto je najbežnejší kód. Používa sa na označenie úspešnej operácie.

- `201 CREATED - VYTVORENÉ`

    Došlo k úspešnému vytvoreniu (prostredníctvom POST alebo PUT). Nastavte hlavičku odpovedi tak, aby obsahovala odkaz na novovytvorený prostriedok (na POST).

- `204 NO CONTENT - BEZ OBSAHU`

    Označuje úspech, ale v tele odpovede sa nenachádza nič, čo sa často používa na operácie DELETE a PUT.

- `400 BAD REQUEST - ZLÁ POŽIADAVKA`

    Všeobecná chyba pri vybavovaní žiadosti by spôsobila neplatný stav. Príklady overenia domény, chýbajúce údaje atď.

- `401 UNAUTHORIZED`

    Chybová odpoveď pre chýbajúci alebo neplatný autentifikačný token.

- `403 FORBIDDEN - ZAKÁZANÉ`

    Kód chyby, keď používateľ nie je oprávnený na vykonanie operácie alebo zdroj nie je k dispozícii z nejakého dôvodu (napr. Časové obmedzenia atď.).

- `404 NOT FOUND - NENÁJDENÉ`

    Používa sa, keď sa požadovaný prostriedok nenájde, či už neexistuje, alebo ak existuje číslo 401 alebo 403, ktoré chce služba z bezpečnostných dôvodov maskovať.

- `405 METHOD NOT ALLOWED - NEPOVOLENÁ METÓDA`

    Používa sa na označenie, že požadovaná adresa URL existuje, ale požadovaná metóda HTTP nie je použiteľná. Napríklad `POST /users/12345` kde API nepodporuje vytváranie zdrojov týmto spôsobom (s poskytnutým ID). Pri návrate čísla 405 musí byť nastavená hlavička HTTP, ktorá definuje podporované metódy HTTP. V predchádzajúcom prípade by hlavička vyzerala takto: `„Allow: GET, PUT, DELETE“`

- `409 CONFLICT`

    Príkladmi sú duplicitné položky, napríklad pokus o vytvorenie dvoch zákazníkov s rovnakými informáciami a odstránenie koreňových objektov, keď nie je podporované kaskádové odstraňovanie.

- `500 INTERNAL SERVER ERROR - INTERNÁ CHYBA NA SERVERI`

    Tento kód nikdy nevracať úmyselne. Väčšinou ho vráti nezachytená výnimka.

### Pomenovanie zdrojov

Okrem správneho používania HTTP metód a návratových hodnôt  je pomenovanie zdrojov pravdepodobne najdôležitejšou a najvýznamnejšou problematikou, ktorú treba adresovať pri návrhu zrozumiteľného a  používateľsky intuitívneho  REST rozhrania. Pri správnom pomenovaní zdrojov REST rozhrania by používateľ mal byť schopný z názvoslovia vydedukovať čo ktorý zdroj obsahuje alebo ako sa používa. 

Vo finálnej podobe RESTové rozhranie je súbor URL adries na zdroje, ktoré sú väčšinou prezentované vo forme JSON/XML. Každý zdroj má svoju vlastnú URL adresu, ktorá je rozšírená o HTTP metódy ktoré definujú operáciu aká sa má vykonať nad daným zdrojom. 

Pri pomenovaní zdrojov systému treba používať podstatné mená a nie slovesá, ktoré by definovali činnosť/akciu (na definovanie činnosti máme HTTP metódy).

Niektoré príklady zdrojov sú:

- Používatelia systému.

- Kurzy, v ktorých je študent zapísaný.

- Časová os príspevkov používateľa.

- Používatelia, ktorí sledujú iného používateľa.

!!! note "Príklad ako by mohlo vyzerať rozhranie objednávkového systému so zákazníkmi"

    - Na vloženie (vytvorenie) nového zákazníka do systému môžeme použiť:
    
        `POST http://www.example.com/customers`
    
    - Na získanie údajov o zákazníkovi s ID # 33245:
    
        `GET http://www.example.com/customers/33245` Rovnaká URL by sa použila pre PUT a DELETE, na aktualizáciu a odstránenie zákazníka.
    
    - URL adresy o produktoch
    
        - Vytvorenie nového produktu
    
            `POST http://www.example.com/products`
    
        - Čítanie, úprava, mazanie produktu na základe ID produktu # 66432
    
            `GET|PUT|DELETE http://www.example.com/products/66432`
    
    - Objednávky - pri objednávkach by sme mohli postupovať podobne ako v prechádzajúci prípadoch ale vieme, že objednávky sú späté s konkrétnym zákazníkom a teda dáva zmysel ich hierarchicky priradiť k zákazníkovi. 
        - Vytvorenie novej objednávky pre zákazník ID #33245
    
            `POST http://www.example.com/customers/33245/orders`
    
        - Získanie všetkých objednávok od jedného zákazníka
    
            `GET http://www.example.com/customers/33245/orders`
    
        - Hierarchicky môžeme postupovať ďalej a vytvoriť ďalšie zdroje
            - Pridanie novej položky do existujúcej objednávky zákazníka
    
                `POST http://www.example.com/customers/33245/orders/8769/items`
             Obdobným spôsobom môžeme vytvoriť aj metódu na získanie všetkých položiek objednávky `GET`
    
            - Ak by sme pokračovali rovnakým spôsobom aj ďalej tak prístup k jednotlivým položkám objednávky by mohol byť nasledovný
    
                `GET|PUT|DELETE http://www.example.com/customers/33245/orders/8769/items/1`



Pri REST rozhraní nieje nič striktne definované a záleží vždy od konkrétneho návrhu architektov ako výsledné rozhranie bude vyzerať.

### Príklady REST rozhraní

- [TatraBank API](https://developer.tatrabanka.sk/documentation/accounts/tbaccounts)
- [ebay API](https://developer.ebay.com/api-docs/sell/account/resources/methods)
- [Heureka API](https://sluzby.heureka.sk/napoveda/kosik-api/)

##  JSON

**JavaScript Object Notation** ( *[javascriptový](https://cs.wikipedia.org/wiki/JavaScript) objektový zápis* , **JSON** ) je spôsob zápisu dát (dátový formát) nezávislý na počítačovej platforme, určený pre prenos dát, ktoré môžu byť organizované v poliach alebo zoskupené v objektoch. Vstupom je ľubovoľná dátová štruktúra (číslo, reťazec, boolean, objekt alebo z nich zložené polia), **výstupom je vždy textový reťazec**. Zložitosť hierarchie vstupných premenných nie je teoreticky nijako obmedzená.

```json
  {
  "first name": "John",
  "last name": "Smith",
  "age": 25,
  "address": {
    "street address": "21 2nd Street",
    "city": "New York",
    "state": "NY",
    "postal code": "10021"
  },
  "phone numbers":[{
      "type": "home",
      "number": "212 555-1234"
    },
    {
      "type": "fax",
      "number": "646 555-4567"
    }]
}
```

Ako Java objekt tzv. POJO(Plain old java object) by daný JSON objekt vyzeral nasledovne:

```Java
public class Address {
    public String streetAddress;
    public String city;
    public String state;
    public String postalCode;
}

public class PhoneNumber {
	public String type;
	public String number;
}

public class User {
    public String firstName;
    public String lastName;
    public int age;
    public Address address;
    public List<PhoneNumber> phoneNumbers;
}
```
