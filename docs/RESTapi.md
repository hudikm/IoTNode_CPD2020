## Meteorologická webová služba


<a name="overview"></a>

## Overview

Získanie dát o počasí


### Version information

*Version* : V1.0.0


### Contact information

*Contact* : Martin Húdik  
*Contact Email* : martin.hudik@fri.uniza.sk


### URI scheme

*Schemes* : HTTP


### Tags

* apikey
* weather
* weatherAuth


### Consumes

* `application/json`


### Produces

* `application/json`




<a name="paths"></a>

## Paths

<a name="createjwt"></a>

### Získanie JWT tókenu. Zabezpečené pomocou BasicAuth(Meno: admin, Heslo: heslo)

```
POST /apikey/createjwt
```


#### Description

JWT tokén je potrebný na prístup k zabezpečeným zdrojom: Značené ako 'Auth' zdroje


#### Parameters

| Type         | Name                       | Description                                            | Schema                                                   |
| ------------ | -------------------------- | ------------------------------------------------------ | -------------------------------------------------------- |
| **FormData** | **claims**  <br>*required* | Požiadavky na určenie opravnení pre vygenerovaný tóken | < enum (history, current, locations, all) > array(multi) |


#### Responses

| HTTP Code | Description          | Schema          |
| --------- | -------------------- | --------------- |
| **200**   | successful operation | [Token](#token) |


#### Consumes

* `application/x-www-form-urlencoded`


#### Produces

* `application/json`


#### Tags

* apikey


#### Security

| Type      | Name                |
| --------- | ------------------- |
| **basic** | **[basic](#basic)** |


<a name="getlocations"></a>

### Zoznam meteo staníc

```
GET /weather/locations
```


#### Responses

| HTTP Code | Description          | Schema                          |
| --------- | -------------------- | ------------------------------- |
| **200**   | successful operation | < [Location](#location) > array |


#### Produces

* `application/json`


#### Tags

* weather


<a name="getcurrentweather"></a>

### Aktuálne počasie

```
GET /weather/{station}/current
```


#### Description

Získanie dát o aktuálnom počasí. Dáta sú obnovované každú minútu.


#### Parameters

| Type      | Name                        | Description                       | Schema                                                       |
| --------- | --------------------------- | --------------------------------- | ------------------------------------------------------------ |
| **Path**  | **station**  <br>*required* | ID meteo stanice                  | string                                                       |
| **Query** | **fields**  <br>*optional*  | Vyfiltrovania požadavaných údajov | < enum (stationName, date, time, airTemperature, wetBulbTemperature, humidity, rainIntensity, intervalRain, totalRain, precipitationType, windDirection, windSpeed, maximumWindSpeed, barometricPressure, solarRadiation, heading, batteryLife, measurementTimestampLabel) > array(multi) |


#### Responses

| HTTP Code | Description          | Schema                      |
| --------- | -------------------- | --------------------------- |
| **200**   | successful operation | [WeatherData](#weatherdata) |


#### Produces

* `application/json`


#### Tags

* weather


<a name="gethistoryweather"></a>

### História počasia

```
GET /weather/{station}/history
```


#### Description

Vráti historické dáta o počasí z vybranej meteo stanice v zadanom časovom rozmedzí


#### Parameters

| Type      | Name                        | Description                       | Schema                                                       |
| --------- | --------------------------- | --------------------------------- | ------------------------------------------------------------ |
| **Path**  | **station**  <br>*required* | ID meteo stanice                  | string                                                       |
| **Query** | **fields**  <br>*optional*  | Vyfiltrovania požadavaných údajov | < enum (stationName, date, time, airTemperature, wetBulbTemperature, humidity, rainIntensity, intervalRain, totalRain, precipitationType, windDirection, windSpeed, maximumWindSpeed, barometricPressure, solarRadiation, heading, batteryLife, measurementTimestampLabel) > array(multi) |
| **Query** | **from**  <br>*required*    | DateTime from: dd/MM/yyyy HH:mm   | string                                                       |
| **Query** | **to**  <br>*required*      | DateTime from: dd/MM/yyyy HH:mm   | string                                                       |


#### Responses

| HTTP Code | Description          | Schema                                |
| --------- | -------------------- | ------------------------------------- |
| **200**   | successful operation | < [WeatherData](#weatherdata) > array |


#### Produces

* `application/json`


#### Tags

* weather


<a name="getlocations_1"></a>

### Zoznam meteo staníc

```
GET /weatherAuth/locations
```


#### Responses

| HTTP Code | Description          | Schema                          |
| --------- | -------------------- | ------------------------------- |
| **200**   | successful operation | < [Location](#location) > array |


#### Produces

* `application/json`


#### Tags

* weatherAuth


#### Security

| Type       | Name                          |
| ---------- | ----------------------------- |
| **apiKey** | **[bearerAuth](#bearerauth)** |


<a name="getcurrentweather_1"></a>

### Aktuálne počasie

```
GET /weatherAuth/{station}/current
```


#### Description

Získanie dát o aktuálnom počasí. Dáta sú obnovované každú minútu.


#### Parameters

| Type      | Name                        | Description                       | Schema                                                       |
| --------- | --------------------------- | --------------------------------- | ------------------------------------------------------------ |
| **Path**  | **station**  <br>*required* | ID meteo stanice                  | string                                                       |
| **Query** | **fields**  <br>*optional*  | Vyfiltrovania požadavaných údajov | < enum (stationName, date, time, airTemperature, wetBulbTemperature, humidity, rainIntensity, intervalRain, totalRain, precipitationType, windDirection, windSpeed, maximumWindSpeed, barometricPressure, solarRadiation, heading, batteryLife, measurementTimestampLabel) > array(multi) |


#### Responses

| HTTP Code | Description          | Schema                      |
| --------- | -------------------- | --------------------------- |
| **200**   | successful operation | [WeatherData](#weatherdata) |


#### Produces

* `application/json`


#### Tags

* weatherAuth


#### Security

| Type       | Name                          |
| ---------- | ----------------------------- |
| **apiKey** | **[bearerAuth](#bearerauth)** |


<a name="gethistoryweather_1"></a>

### História počasia

```
GET /weatherAuth/{station}/history
```


#### Description

Vráti historické dáta o počasí z vybranej meteo stanice v zadanom časovom rozmedzí


#### Parameters

| Type      | Name                        | Description                       | Schema                                                       |
| --------- | --------------------------- | --------------------------------- | ------------------------------------------------------------ |
| **Path**  | **station**  <br>*required* | ID meteo stanice                  | string                                                       |
| **Query** | **fields**  <br>*optional*  | Vyfiltrovania požadavaných údajov | < enum (stationName, date, time, airTemperature, wetBulbTemperature, humidity, rainIntensity, intervalRain, totalRain, precipitationType, windDirection, windSpeed, maximumWindSpeed, barometricPressure, solarRadiation, heading, batteryLife, measurementTimestampLabel) > array(multi) |
| **Query** | **from**  <br>*required*    | DateTime from: dd/MM/yyyy HH:mm   | string                                                       |
| **Query** | **to**  <br>*required*      | DateTime from: dd/MM/yyyy HH:mm   | string                                                       |


#### Responses

| HTTP Code | Description          | Schema                                |
| --------- | -------------------- | ------------------------------------- |
| **200**   | successful operation | < [WeatherData](#weatherdata) > array |


#### Produces

* `application/json`


#### Tags

* weatherAuth


#### Security

| Type       | Name                          |
| ---------- | ----------------------------- |
| **apiKey** | **[bearerAuth](#bearerauth)** |




<a name="definitions"></a>

## Definitions

<a name="location"></a>

### Location

| Name                        | Schema |
| --------------------------- | ------ |
| **address**  <br>*optional* | string |
| **country**  <br>*optional* | string |
| **gps**  <br>*optional*     | string |
| **id**  <br>*optional*      | string |
| **town**  <br>*optional*    | string |


<a name="optional"></a>

### Optional

| Name                        | Schema  |
| --------------------------- | ------- |
| **empty**  <br>*optional*   | boolean |
| **present**  <br>*optional* | boolean |


<a name="optionalweatherdata"></a>

### OptionalWeatherData

| Name                        | Schema  |
| --------------------------- | ------- |
| **empty**  <br>*optional*   | boolean |
| **present**  <br>*optional* | boolean |


<a name="token"></a>

### Token

| Name                      | Schema |
| ------------------------- | ------ |
| **token**  <br>*optional* | string |


<a name="weatherdata"></a>

### WeatherData

| Name                                            | Schema          |
| ----------------------------------------------- | --------------- |
| **Air Temperature**  <br>*optional*             | number (double) |
| **Barometric Pressure**  <br>*optional*         | number (double) |
| **Battery Life**  <br>*optional*                | integer (int32) |
| **Date**  <br>*optional*                        | string          |
| **Heading**  <br>*optional*                     | integer (int32) |
| **Humidity**  <br>*optional*                    | integer (int32) |
| **Interval Rain**  <br>*optional*               | number (double) |
| **Maximum Wind Speed**  <br>*optional*          | number (double) |
| **Measurement Timestamp Label**  <br>*optional* | string          |
| **Precipitation Type**  <br>*optional*          | integer (int32) |
| **Rain Intensity**  <br>*optional*              | number (double) |
| **Solar Radiation**  <br>*optional*             | integer (int32) |
| **Station Name**  <br>*optional*                | string          |
| **Time**  <br>*optional*                        | string          |
| **Total Rain**  <br>*optional*                  | integer (int32) |
| **Wet Bulb Temperature**  <br>*optional*        | number (double) |
| **Wind Direction**  <br>*optional*              | integer (int32) |
| **Wind Speed**  <br>*optional*                  | number (double) |




<a name="securityscheme"></a>

## Security

<a name="basic"></a>

### basic

Basic Authentication (Login: admin/heslo)

*Type* : basic


<a name="bearerauth"></a>

### bearerAuth

*Type* : apiKey  
*Name* : Authorization  
*In* : HEADER

