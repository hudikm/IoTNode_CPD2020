## Vytvorenie git repozitára pomocou templatu

[En. návod](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/creating-a-repository-from-a-template)

## Klonovanie projektu

Skúška 

## Postup prác

<!--tgen file='/home/martin/IdeaProjects/WeatherStation/IoTNode/out.patch' lang=java tabs t_new="Nové" t_old="Pred úpravou" -->

<!--____tgen step=all template='gen_tags_separate_header'  -->

<!--tgen step=1.0 template='files_list'  noupdate -->

#### Štruktúra projektu

```bash

src
├─ test
│  └─ java
│     └─ sk
│        └─ fri
│           └─ uniza
│              ├─ NoAuth
│              │  └─ WeatherStationTest.java	 # Unit testy pre prvú časť úlohy
│              └─ Auth
│                 └─ WeatherStationAuthTest.java # Unit testy pre bonusovú časť
└─ main
   └─ java
      └─ sk
         └─ fri
            └─ uniza
               ├─ model	# Adresár dátových modelov
               │  ├─ Location.java		 
               │  ├─ Token.java
               │  └─ WeatherData.java
               ├─ api
               │  └─ WeatherStationService.java # Rozhranie REST api klienta
               ├─ App.java		# Hlavná trieda
               └─ IotNode.java  # Implementácia RETROfit klienta
```


<!--end-->

<!--tgen step=1.0 template='mkdocs_header_only' -->
#### 1.0 Importovanie knižnice Retrofit [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/12609459536f00532cd5a8b1f2b59fe5e1dd55b4/)
<!--end-->

Ako prvé do projektu musíme pridať [Retrofit](https://square.github.io/retrofit/) knižnicu na to využijeme systém Maven. Do súbor `pom.xml` pridáme nasledujúce závislosti. 

<!--tgen step=1.0 template='mkdocs_body_only' noupdate -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/12609459536f00532cd5a8b1f2b59fe5e1dd55b4/pom.xml) pom.xml**


``` xml tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14"
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>converter-jackson</artifactId>
      <version>2.8.1</version>
    </dependency>

    <dependency>
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>retrofit</artifactId>
      <version>2.8.1</version>
    </dependency>

    <dependency>
      <groupId>commons-lang</groupId>

```

``` java tab="Pred úpravou" 
      <scope>test</scope>
    </dependency>


    <dependency>
      <groupId>commons-lang</groupId>

```


<!--end-->


<!--tgen step=1.1 template='mkdocs_header_only'  -->
#### 1.1 Vytvorenie rozhrania pre službu WeatherStation [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/a4f6c7a4048ba65376826d0a30d4991424f9c2ec/)
<!--end-->

Práca z [Retrofit](https://square.github.io/retrofit/) knižnicou je veľmi jednoduchá. Tento nástroj pomocou anotácií mení HTTP API na jednoduché Java rozhranie (`interface`).  Anotácie `@GET(<url>), @POST(<url>), @DELETE(<url>), ...` definujú HTTP metódu, ktorá zodpovedá danému HTTP zdroju. 

 Anotované vstupné parametre metódy zase definujú parametre URL dresy. V nižšie uvedenom príklade pomocou anotácie `@Path(<názov premennej v URL>)` definujeme ID meteo stanice. Zavolaním metódy`getCurrentWeatherAsMap("station_1")` sa vygeneruje nasledovná URL adresa`/weather/station_1/current`. 

Výstupné parametre metódy musia korešpondovať s formou prenášaných dát danej URL adresy. V tomto prípade vieme, že dáta sú v tvare JSON a daná url adresa vracia nasledujúce dáta: 

```json
{
  "Station Name": "string",
  "Date": "string",
  "Time": "string",
  "Air Temperature": 0,
  "Wet Bulb Temperature": 0,
  "Humidity": 0,
  "Rain Intensity": 0,
  "Interval Rain": 0,
  "Total Rain": 0,
  "Precipitation Type": 0,
  "Wind Direction": 0,
  "Wind Speed": 0,
  "Maximum Wind Speed": 0,
  "Barometric Pressure": 0,
  "Solar Radiation": 0,
  "Heading": 0,
  "Battery Life": 0,
  "Measurement Timestamp Label": "string"
}
```

Vidíme, že JSON dáta sú zapísané v tvare dvojíc *kľúč -> dáta* a preto najjednoduchší spôsob ako dané dáta konvertovať z JSON textovej podoby do Java podoby je použiť `Map<String,String>` (klúč je String a aj dáta sú String) ako výstupný parameter. Retrofit automaticky konvertuje JSON do Java podoby. Retrofit vyžaduje aby výstupné parametre boli zaobalené do objektu `Call<typ>`.

!!! note "Konvertovanie JSON-JAVA"
	Interne Retrofit na konverziu medzi JSON a Java objektom používa rôzne knižnice, ktoré si užívateľ môže zvoliť. Tieto knižnice dovoľujú používať aj iné formatý ako JSON napr. XML, YAML, atď. My používme [Jackson](https://github.com/FasterXML/jackson-dataformat-xml) knižnicu.

<!--tgen step=1.1 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/a4f6c7a4048ba65376826d0a30d4991424f9c2ec/src/main/java/sk/fri/uniza/api/WeatherStationService.java) src/main/java/sk/fri/uniza/api/WeatherStationService.java**

 > import java.util.Map;

``` java tab="Nové" hl_lines="3 4 5"
public interface WeatherStationService {

    @GET("/weather/{station}/current")
    Call<Map<String, String>> getCurrentWeatherAsMap(
            @Path("station") String station);


    // ... getCurrentWeatherAsMap(station, fields);

```

``` java tab="Pred úpravou" hl_lines="3"
public interface WeatherStationService {

    // ... getCurrentWeatherAsMap(station);


    // ... getCurrentWeatherAsMap(station, fields);

```


<!--end-->


<!--tgen step=1.2 template='mkdocs_header_only'  -->
#### 1.2 Inicializovanie Retrofit klienta [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/92b028b6ecd69e3f580581c7b3338e81c14f81f4/)
<!--end-->

<!--tgen step=1.2 template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/92b028b6ecd69e3f580581c7b3338e81c14f81f4/src/main/java/sk/fri/uniza/IotNode.java) src/main/java/sk/fri/uniza/IotNode.java**

 > import retrofit2.converter.jackson.JacksonConverterFactory;

``` java tab="Nové" hl_lines="4 5 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23"
import sk.fri.uniza.api.WeatherStationService;

public class IotNode {
    private final Retrofit retrofit;
    private final WeatherStationService weatherStationService;

    public IotNode() {

        retrofit = new Retrofit.Builder()
                // Url adresa kde je umietnená WeatherStation služba
                .baseUrl("http://localhost:9000/")
                // Na konvertovanie JSON objektu na java POJO použijeme
                // Jackson knižnicu
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        // Vytvorenie inštancie komunikačného rozhrania
        weatherStationService = retrofit.create(WeatherStationService.class);

    }

    public WeatherStationService getWeatherStationService() {
        return weatherStationService;
    }
}

```

``` java tab="Pred úpravou" 
import sk.fri.uniza.api.WeatherStationService;

public class IotNode {

}

```


<!--end-->

#### 1.3 Získanie údajov o aktuálnom počasí

V predchádzajúcom príklade sme si vytvorili jednoduchého HTTP klienta pomocou knižnice Retrofit. V nasledujúcej časti si ukážeme ako sa tento klient používa na získanie dát. Získanie dát prebieha v dvoch krokoch: 

- V prvom kroku si vytvoríme objekt, ktorý reprezentuje požiadavku na server. Tento objekt `Call` predstavuje definíciu ako tá požiadavka vyzerá ale samotné odoslanie sa vykonáva v samostatnom kroku. Takto vytvorený objekt sa môže použiť iba raz.
- V druhom kroku sa požiadavka odošle na server. Retrofit poskytuje dve možnosti odoslanie požiadavky: synchrónne pomocou metódu `execute()` alebo asynchrónne pomocou metódy `enqueue()`, ktorá je vykonaná na pomocnom vlákne a odpoveď je odoslaná cez callback metódy. My budeme používať synchrónnu metódu. 

<!--tgen step=1.3.a template='mkdocs_header_only' noupdate  -->

##### 1.3.a Vytvorenie požiadavky na získanie údajov o aktuálnom počasí [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/83496648ea9805d9e304673d7b374614495a2413/)

<!--end-->

<!--tgen step=1.3.a template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/83496648ea9805d9e304673d7b374614495a2413/src/main/java/sk/fri/uniza/App.java) src/main/java/sk/fri/uniza/App.java**


``` java tab="Nové" hl_lines="3 4 5 6 7 13 14 15 16 17 18 19"
package sk.fri.uniza;

import retrofit2.Call;

import java.util.List;
import java.util.Map;

/**
 * Hello IoT!
 */
public class App {
    public static void main(String[] args) {
        IotNode iotNode = new IotNode();
        // Vytvorenie požiadavky na získanie údajov o aktuálnom počasí z
        // meteo stanice s ID: station_1
        Call<Map<String, String>> currentWeather =
                iotNode.getWeatherStationService()
                        .getCurrentWeatherAsMap("station_1");


    }
}

```

``` java tab="Pred úpravou" 
package sk.fri.uniza;

/**
 * Hello IoT!
 */
public class App {
    public static void main(String[] args) {

    }
}

```


<!--end-->

<!--tgen step=1.3.b template='mkdocs_header_only' noupdate  -->

##### 1.3.b Odoslanie požiadavky a spracovanie odpovede [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/a5f5ff03ae4573f4952b9ffb25ae7f215cfbd7b2/)

<!--end-->

Po odoslaní požiadavky, čakáme na odpoveď, ktorá príde vo forme `Response` objektu. Obdržaním daného objektu nieje automaticky zaručené, že komunikácia prebehla úspešne. Na určenie či všetko prebehlo v poriadku treba skontrolovať hodnotu metódy `response.isSuccessful()`.  Ak sa vráti hodnota `false` chybové oznámenie sa nachádza v `response.errorBody()` v opačnom prípade prijaté dáta sa získajú cez `response.body()`.

<!--tgen step=1.3.b template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/a5f5ff03ae4573f4952b9ffb25ae7f215cfbd7b2/src/main/java/sk/fri/uniza/App.java) src/main/java/sk/fri/uniza/App.java**


``` java tab="Nové" hl_lines="4 6"
package sk.fri.uniza;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;


```

``` java tab="Pred úpravou" 
package sk.fri.uniza;

import retrofit2.Call;

import java.util.List;
import java.util.Map;


```

 > public class App {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17"
                        .getCurrentWeatherAsMap("station_1");


        try {
            // Odoslanie požiadavky na server pomocou REST rozhranie
            Response<Map<String, String>> response = currentWeather.execute();

            if (response.isSuccessful()) { // Dotaz na server bol neúspešný
                //Získanie údajov vo forme Mapy stringov
                Map<String, String> body = response.body();
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

```

``` java tab="Pred úpravou" 
                        .getCurrentWeatherAsMap("station_1");


    }
}

```


<!--end-->


<!--tgen step=1.4 template='mkdocs_header_only'  -->
#### 1.4 Upresnenie požiadavky o aké údaje máme záujem [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/e749d4cc97dc6130c140170e1c98600accbd56a5/)
<!--end-->

Ďalšou možnosťou, ktorú webová služba poskytuje je možnosť definovať o aké konkrétne dáta máme záujem. Napríklad, ak nás zaujíma iba teplota vzduch podla dokumentácie vieme, že sa jedná o pole `airTemperature` a URL adresa požiadavky by vyzerala nasledovne: 
`http://localhost:9000/weather/station_1/current?fields=airTemperature`.  Výsledné dáta budú vyzerať nejako takto: 

``` json
{
  "Air Temperature": 7.2
}
```

Vidíme, že časť `?fields=airTemperature` nám zabezpečuje filtrovanie obsahu. V našom HTTP api rozhraní si danú funkcionalitu definujeme pomocou anotácie `@Query("fields")`. Trieda `List` nám zabezpečí, že filtrov v jednej požiadavke môžeme mať viacej.

<!--tgen step=1.4 template='mkdocs_body_only' noupdate  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/e749d4cc97dc6130c140170e1c98600accbd56a5/src/main/java/sk/fri/uniza/api/WeatherStationService.java) src/main/java/sk/fri/uniza/api/WeatherStationService.java**

 > public interface WeatherStationService {

``` java tab="Nové" hl_lines="4 5 6 7"
            @Path("station") String station);


    @GET("/weather/{station}/current")
    Call<Map<String, String>> getCurrentWeatherAsMap(
            @Path("station") String station,
            @Query("fields") List<String> fields);


    // ... getStationLocations();

```

``` java tab="Pred úpravou" hl_lines="4"
            @Path("station") String station);


    // ... getCurrentWeatherAsMap(station, fields);


    // ... getStationLocations();

```

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/e749d4cc97dc6130c140170e1c98600accbd56a5/src/main/java/sk/fri/uniza/App.java) src/main/java/sk/fri/uniza/App.java**

 > public class App {

``` java tab="Nové" hl_lines="4 5 6"
        // meteo stanice s ID: station_1
        Call<Map<String, String>> currentWeather =
                iotNode.getWeatherStationService()
                        .getCurrentWeatherAsMap("station_1",
                                List.of("time", "date",
                                        "airTemperature"));


        try {

```

``` java tab="Pred úpravou" hl_lines="4"
        // meteo stanice s ID: station_1
        Call<Map<String, String>> currentWeather =
                iotNode.getWeatherStationService()
                        .getCurrentWeatherAsMap("station_1");


        try {

```



<!--end-->

<!--tgen step=1.5.a template='mkdocs_header_only'  -->
#### 1.5.a Získanie zoznamu dostupných meteorologických staníc [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/bde33cde9e725c9d3e4ad442190595c4b5799a91/)
<!--end-->
Výstup na adrese `http://localhost:9000/weather/locations` vyzerá nasledovne:
``` json
[
  {
    "id": "station_1",
    "country": "Slovakia",
    "address": "Veľká okružná",
    "town": "Žilna",
    "gps": "49.219715, 18.744436"
  },
  {
    "id": "station_2",
    "country": "Slovakia",
    "address": "Predmestská",
    "town": "Žilna",
    "gps": "49.2200445,18.7447796"
  },
  {
    "id": "station_3",
    "country": "Slovakia",
    "address": "1. mája",
    "town": "Žilna",
    "gps": "49.2219346,18.7441314"
  }
]
```
V tomto prípade vidíme, že sa nám odosiela viacero objektov naraz. Je to zoznam dostupných meteorologických staníc. V prípade Retrofit to vyriešime jednoducho tak, že výstupný objekt zabalíme do triedy `List`.
<!--tgen step=1.5.a template='mkdocs_body_only' noupdate  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/bde33cde9e725c9d3e4ad442190595c4b5799a91/src/main/java/sk/fri/uniza/api/WeatherStationService.java) src/main/java/sk/fri/uniza/api/WeatherStationService.java**

 > public interface WeatherStationService {

``` java tab="Nové" hl_lines="4 5"
            @Query("fields") List<String> fields);


    @GET("/weather/locations")
    Call<List<Location>> getStationLocations();


    // ... getCurrentWeather(station);

```

``` java tab="Pred úpravou" hl_lines="4"
            @Query("fields") List<String> fields);


    // ... getStationLocations();


    // ... getCurrentWeather(station);

```

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/bde33cde9e725c9d3e4ad442190595c4b5799a91/src/main/java/sk/fri/uniza/App.java) src/main/java/sk/fri/uniza/App.java**

 > package sk.fri.uniza;

``` java tab="Nové" hl_lines="3"
import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.model.Location;

import java.io.IOException;
import java.util.List;

```

``` java tab="Pred úpravou" 
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

```

 > public class App {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21"
            e.printStackTrace();
        }

        // Vytvorenie požiadavky na získanie údajov o všetkých meteo staniciach
        Call<List<Location>> stationLocations =
                iotNode.getWeatherStationService().getStationLocations();

        try {
            Response<List<Location>> response = stationLocations.execute();

            if (response.isSuccessful()) { // Dotaz na server bol neúspešný
                //Získanie údajov vo forme Zoznam lokacií
                List<Location> body = response.body();

                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

```

``` java tab="Pred úpravou" 
            e.printStackTrace();
        }

    }
}

```



<!--end-->

<!--tgen step=1.5.b template='mkdocs_header_only'  -->
#### 1.5.b Úpráva java triedy Location [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/a2c693bb55d6f789f71cd1081d83f0782731d9cf/)

V predchádzajúcom častiach sme používali generické výstupné premenné typu `Map<String,String>`, ktoré sú jednoduché na použitie ale pre ďalšiu prácu už niesu moc praktické. V praxi sa používajú konkrétne navrhnuté dátové triedy, ktoré ako celok predstavujú dátový model. V tomto prípade je definovaná trieda `Location`, ktorá využíva `Jackson` anotáciu `@JsonProperty(<názov premennej v JSON formáte>)` na definovanie pravidiel použitých pri konvertovaní dát medzi JSON a Java triedou. Táto anotácie nieje vždy nutná a ani v tomto príklade by sa nemusela použiť, lebo názvy Java premenných zodpovedajú názvu JSON premenných a je dodržaná [JavaBeans](https://www.tutorialspoint.com/jsp/jsp_java_beans.htm) notácia. 

<!--end-->

<!--tgen step=1.5.b template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/a2c693bb55d6f789f71cd1081d83f0782731d9cf/src/main/java/sk/fri/uniza/model/Location.java) src/main/java/sk/fri/uniza/model/Location.java**

 > package sk.fri.uniza.model;

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 15 16 17 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64"
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty("id")
    private String id;
    @JsonProperty("country")
    private String country;
    @JsonProperty("address")
    private String address;
    @JsonProperty("town")
    private String town;
    @JsonProperty("gps")
    private String gps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", town='" + town + '\'' +
                ", gps='" + gps + '\'' +
                '}';
    }
}

```

``` java tab="Pred úpravou" 
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {


}

```


<!--end-->


<!--tgen step=1.6.a template='mkdocs_header_only'  -->
#### 1.6.a Nahradenie všeobecnej Map<String, String> triedou WeatherData [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/8fbf1ba78c146bed6c3edf7a59dfe9e2c707aeb0/)
<!--end-->

<!--tgen step=1.6.a template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/8fbf1ba78c146bed6c3edf7a59dfe9e2c707aeb0/src/main/java/sk/fri/uniza/App.java) src/main/java/sk/fri/uniza/App.java**

 > package sk.fri.uniza;

``` java tab="Nové" hl_lines="4"
import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.model.Location;
import sk.fri.uniza.model.WeatherData;

import java.io.IOException;
import java.util.List;

```

``` java tab="Pred úpravou" 
import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.model.Location;

import java.io.IOException;
import java.util.List;

```

 > public class App {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23"
            e.printStackTrace();
        }

        // Vytvorenie požiadavky na získanie údajov o aktuálnom počasí z
        // meteo stanice s ID: station_1
        Call<WeatherData> currentWeatherPojo =
                iotNode.getWeatherStationService()
                        .getCurrentWeather("station_1");


        try {
            // Odoslanie požiadavky na server pomocou REST rozhranie
            Response<WeatherData> response = currentWeatherPojo.execute();

            if (response.isSuccessful()) { // Dotaz na server bol neúspešný
                //Získanie údajov vo forme inštancie triedy WeatherData
                WeatherData body = response.body();
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

``` java tab="Pred úpravou" 
            e.printStackTrace();
        }

    }
}

```

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/8fbf1ba78c146bed6c3edf7a59dfe9e2c707aeb0/src/main/java/sk/fri/uniza/api/WeatherStationService.java) src/main/java/sk/fri/uniza/api/WeatherStationService.java**

 > public interface WeatherStationService {

``` java tab="Nové" hl_lines="4 5 7 8 9"
    Call<List<Location>> getStationLocations();


    @GET("/weather/{station}/current")
    Call<WeatherData> getCurrentWeather(@Path("station") String station);

    @GET("/weather/{station}/current")
    Call<WeatherData> getCurrentWeather(@Path("station") String station,
                                        @Query("fields") List<String> fields);


    // ... getHistoryWeather(station, from, to);

```

``` java tab="Pred úpravou" hl_lines="4 5 7"
    Call<List<Location>> getStationLocations();


    // ... getCurrentWeather(station);


    // ... getCurrentWeather(station, fields);


    // ... getHistoryWeather(station, from, to);

```


<!--end-->


<!--tgen step=1.6.b template='mkdocs_header_only'  -->
#### 1.6.b Nahradenie všeobecnej Map<String, String> triedou WeatherData [:link:](https://github.com/hudikm/IoTNode_CPD2020/commit/e405986688b4a6cf7315d2d990244a5435ca31be/)
<!--end-->

<!--tgen step=1.6.b template='mkdocs_body_only'  -->

>  **[🖹](https://github.com/hudikm/IoTNode_CPD2020/blob/e405986688b4a6cf7315d2d990244a5435ca31be/src/main/java/sk/fri/uniza/model/WeatherData.java) src/main/java/sk/fri/uniza/model/WeatherData.java**

 > import com.fasterxml.jackson.annotation.JsonProperty;

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126 127 128 129 130 131 132 133 134 135 136 137 138 139 140 141 142 143 144 145 146 147 148 149 150 151 152 153 154 155 156 157 158 159 160 161 162 163 164 165 166 167 168 169 170 171 172 173 174 175 176 177 178 179 180 181 182 183 184 185 186 187 188 189 190 191 192 193 194 195 196 197 198 199 200 201 202 203 204 205 206 207 208 209 210 211 212 213 214 215 216 217 218 219 220 221 222 223 224 225 226 227 228 229 230 231 232 233 234 235 236 237 238 239 240 241 242 243 244 245 246 247 248 249 250 251 252 253 254 255 256 257 258 259 260 261 262 263"
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Station Name",
        "Date",
        "Time",
        "Air Temperature",
        "Wet Bulb Temperature",
        "Humidity",
        "Rain Intensity",
        "Interval Rain",
        "Total Rain",
        "Precipitation Type",
        "Wind Direction",
        "Wind Speed",
        "Maximum Wind Speed",
        "Barometric Pressure",
        "Solar Radiation",
        "Heading",
        "Battery Life",
        "Measurement Timestamp Label"
})
public class WeatherData {

    @JsonProperty("Station Name")
    private String stationName;
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Time")
    private String time;
    @JsonProperty("Air Temperature")
    private Double airTemperature;
    @JsonProperty("Wet Bulb Temperature")
    private Double wetBulbTemperature;
    @JsonProperty("Humidity")
    private Integer humidity;
    @JsonProperty("Rain Intensity")
    private Integer rainIntensity;
    @JsonProperty("Interval Rain")
    private Integer intervalRain;
    @JsonProperty("Total Rain")
    private Integer totalRain;
    @JsonProperty("Precipitation Type")
    private Integer precipitationType;
    @JsonProperty("Wind Direction")
    private Integer windDirection;
    @JsonProperty("Wind Speed")
    private Double windSpeed;
    @JsonProperty("Maximum Wind Speed")
    private Double maximumWindSpeed;
    @JsonProperty("Barometric Pressure")
    private Double barometricPressure;
    @JsonProperty("Solar Radiation")
    private Integer solarRadiation;
    @JsonProperty("Heading")
    private Integer heading;
    @JsonProperty("Battery Life")
    private Integer batteryLife;
    @JsonProperty("Measurement Timestamp Label")
    private String measurementTimestampLabel;

    @JsonProperty("Station Name")
    public String getStationName() {
        return stationName;
    }

    @JsonProperty("Station Name")
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @JsonProperty("Date")
    public String getDate() {
        return date;
    }

    @JsonProperty("Date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("Time")
    public String getTime() {
        return time;
    }

    @JsonProperty("Time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("Air Temperature")
    public Double getAirTemperature() {
        return airTemperature;
    }

    @JsonProperty("Air Temperature")
    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    @JsonProperty("Wet Bulb Temperature")
    public Double getWetBulbTemperature() {
        return wetBulbTemperature;
    }

    @JsonProperty("Wet Bulb Temperature")
    public void setWetBulbTemperature(Double wetBulbTemperature) {
        this.wetBulbTemperature = wetBulbTemperature;
    }

    @JsonProperty("Humidity")
    public Integer getHumidity() {
        return humidity;
    }

    @JsonProperty("Humidity")
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("Rain Intensity")
    public Integer getRainIntensity() {
        return rainIntensity;
    }

    @JsonProperty("Rain Intensity")
    public void setRainIntensity(Integer rainIntensity) {
        this.rainIntensity = rainIntensity;
    }

    @JsonProperty("Interval Rain")
    public Integer getIntervalRain() {
        return intervalRain;
    }

    @JsonProperty("Interval Rain")
    public void setIntervalRain(Integer intervalRain) {
        this.intervalRain = intervalRain;
    }

    @JsonProperty("Total Rain")
    public Integer getTotalRain() {
        return totalRain;
    }

    @JsonProperty("Total Rain")
    public void setTotalRain(Integer totalRain) {
        this.totalRain = totalRain;
    }

    @JsonProperty("Precipitation Type")
    public Integer getPrecipitationType() {
        return precipitationType;
    }

    @JsonProperty("Precipitation Type")
    public void setPrecipitationType(Integer precipitationType) {
        this.precipitationType = precipitationType;
    }

    @JsonProperty("Wind Direction")
    public Integer getWindDirection() {
        return windDirection;
    }

    @JsonProperty("Wind Direction")
    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    @JsonProperty("Wind Speed")
    public Double getWindSpeed() {
        return windSpeed;
    }

    @JsonProperty("Wind Speed")
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @JsonProperty("Maximum Wind Speed")
    public Double getMaximumWindSpeed() {
        return maximumWindSpeed;
    }

    @JsonProperty("Maximum Wind Speed")
    public void setMaximumWindSpeed(Double maximumWindSpeed) {
        this.maximumWindSpeed = maximumWindSpeed;
    }

    @JsonProperty("Barometric Pressure")
    public Double getBarometricPressure() {
        return barometricPressure;
    }

    @JsonProperty("Barometric Pressure")
    public void setBarometricPressure(Double barometricPressure) {
        this.barometricPressure = barometricPressure;
    }

    @JsonProperty("Solar Radiation")
    public Integer getSolarRadiation() {
        return solarRadiation;
    }

    @JsonProperty("Solar Radiation")
    public void setSolarRadiation(Integer solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    @JsonProperty("Heading")
    public Integer getHeading() {
        return heading;
    }

    @JsonProperty("Heading")
    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    @JsonProperty("Battery Life")
    public Integer getBatteryLife() {
        return batteryLife;
    }

    @JsonProperty("Battery Life")
    public void setBatteryLife(Integer batteryLife) {
        this.batteryLife = batteryLife;
    }

    @JsonProperty("Measurement Timestamp Label")
    public String getMeasurementTimestampLabel() {
        return measurementTimestampLabel;
    }

    @JsonProperty("Measurement Timestamp Label")
    public void setMeasurementTimestampLabel(String measurementTimestampLabel) {
        this.measurementTimestampLabel = measurementTimestampLabel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("stationName", stationName)
                .append("date", date).append("time", time)
                .append("airTemperature", airTemperature)
                .append("wetBulbTemperature", wetBulbTemperature)
                .append("humidity", humidity)
                .append("rainIntensity", rainIntensity)
                .append("intervalRain", intervalRain)
                .append("totalRain", totalRain)
                .append("precipitationType", precipitationType)
                .append("windDirection", windDirection)
                .append("windSpeed", windSpeed)
                .append("maximumWindSpeed", maximumWindSpeed)
                .append("barometricPressure", barometricPressure)
                .append("solarRadiation", solarRadiation)
                .append("heading", heading).append("batteryLife", batteryLife)
                .append("measurementTimestampLabel", measurementTimestampLabel)
                .toString();
    }

}

```

``` java tab="Pred úpravou" 
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WeatherData {


}

```


<!--end-->




<!--end-->
