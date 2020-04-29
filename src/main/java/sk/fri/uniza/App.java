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
