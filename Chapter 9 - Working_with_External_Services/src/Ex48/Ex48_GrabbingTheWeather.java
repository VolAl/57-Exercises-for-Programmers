package Ex48;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.System.in;

public class Ex48_GrabbingTheWeather {

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        Map<String,String> configInfo;

        System.out.print("Where are you? ");
        String location = sc.nextLine();
        String city_name = location.split(" ")[0];
        String state_name;
        if(location.contains(" ")) {
           state_name = location.split(" ")[1];
        } else {
            state_name = "";
        }
        String apiKey, latitude, longitude, GEOLOCATION_API, WEATHER_API;
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            configInfo = objectMapper.readValue(new File("config/config.json"), new TypeReference<>() {
            });
            apiKey = configInfo.get("open-weather-api-key");
            GEOLOCATION_API = configInfo.get("open-geolocation-api-url").replace("%par1", city_name).replace("%par2", apiKey);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest requestGeolocation = HttpRequest.newBuilder(URI.create(GEOLOCATION_API)).GET().build();

        try {
            HttpResponse<String> responseGeolocation= httpClient.send(requestGeolocation,
                    HttpResponse.BodyHandlers.ofString());

            JsonNode responseGeolocationTree = objectMapper.readTree(String.valueOf(Json.parse(responseGeolocation.body())));
            Stream<JsonNode> s = StreamSupport.stream(responseGeolocationTree.spliterator(), false);
            Optional<JsonNode> result = !state_name.isEmpty() ? s.filter(entry -> entry.get("state").asText().toUpperCase().contains(state_name.toUpperCase()))
                    .findFirst() : s.findFirst();

            if(!responseGeolocationTree.isEmpty() && result.isPresent()) {

                latitude = String.valueOf(result.get().get("lat").asDouble());
                longitude = String.valueOf(result.get().get("lon").asDouble());

                WEATHER_API = configInfo.get("open-weather-api-url")
                        .replace("%par1", latitude)
                        .replace("%par2", longitude)
                        .replace("%par3", apiKey);

                httpClient = HttpClient.newHttpClient();
                HttpRequest requestWeather = HttpRequest.newBuilder(URI.create(WEATHER_API)).GET().build();

                HttpResponse<String> responseWeather = httpClient.send(requestWeather,
                        HttpResponse.BodyHandlers.ofString());

                JsonObject responseWeatherObject = Json.parse(responseWeather.body()).asObject();

                String temperatureFahrenheit = String.valueOf(responseWeatherObject.get("main").asObject().get("temp").asDouble());
                String sunriseUTC = String.valueOf(responseWeatherObject.get("sys").asObject().get("sunrise").asInt());
                String sunsetUTC = String.valueOf(responseWeatherObject.get("sys").asObject().get("sunset").asInt());
                String humidity = String.valueOf(responseWeatherObject.get("main").asObject().get("humidity").asDouble());
                String weatherDescription = responseWeatherObject.get("weather").asArray().get(0).asObject().get("description").asString();
                String windDirectionInDegrees = String.valueOf(responseWeatherObject.get("wind").asObject().get("deg").asDouble());


                printWeatherInfo(city_name, temperatureFahrenheit, sunriseUTC, sunsetUTC, humidity, weatherDescription, windDirectionInDegrees);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printWeatherInfo(String city_name, String temperatureFahrenheit, String sunriseUTC, String sunsetUTC, String humidity, String weatherDescription, String windDirectionInDegrees) {
        try {
            System.out.println("Currently in " + city_name + " there " +
                               (weatherDescription.endsWith("s") ? "are " : "is ") +
                               weatherDescription + ".\n" +
                               getKindOfDayMessage(temperatureFahrenheit, weatherDescription) +
                               "There are " + temperatureFahrenheit + " degrees Fahrenheit (" +
                               convertTempToCelsius(temperatureFahrenheit) + " degrees Celsius)" +
                               ", with " + humidity + "% humidity.\n" +
                               "The wind is blowing in direction " + convertWindDegreesToDirection(windDirectionInDegrees) + ".\n" +
                               "The sunrise time today is " + convertTimeFromLong(sunriseUTC) + " and the sunset time is " + convertTimeFromLong(sunsetUTC) + ".\n");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getKindOfDayMessage(String temperatureFahrenheit, String weatherDescription) {
        String message = "What a ";
        if(weatherDescription.contains("rain") || weatherDescription.contains("storm")) {
            message += messageConditionBasedOnTemperature(temperatureFahrenheit);
            message += " rainy day out! You need an umbrella!\n";
        } else if(weatherDescription.contains("snow")) {
            message += messageConditionBasedOnTemperature(temperatureFahrenheit);
            message += " snowy day out! You need a coat!\n";
        } else if(weatherDescription.contains("clear")) {
            message += messageConditionBasedOnTemperature(temperatureFahrenheit);
            message += " clear day out!\n";
        } else if(weatherDescription.contains("cloud")) {
            message += messageConditionBasedOnTemperature(temperatureFahrenheit);
            message += " cloudy day out!\n";
        } else {
            message += messageConditionBasedOnTemperature(temperatureFahrenheit);
            message += " lovely day out!\n";
        }
        return message;
    }

    private static String messageConditionBasedOnTemperature(String temperatureFahrenheit) {
        String message = "";
        double temp = Double.parseDouble(temperatureFahrenheit);
        if(temp <= 37.5) {
            message = "freezing";
        } else if (temp > 37.5 && temp < 41.5) {
            message = "very cold";
        } else if (temp > 41.5 && temp < 50.5) {
            message = "cold";
        } else if (temp > 50.5 && temp < 51) {
            message = "chilly";
        } else if (temp > 51 && temp < 68) {
            message = "nice";
        } else if (temp > 68 && temp < 77) {
            message = "warm";
        } else if (temp > 77 && temp < 86) {
            message = "hot";
        } else if (temp > 68) {
            message = "very hot";
        }
        return message;
        
    }

    private static String convertWindDegreesToDirection(String windDirectionInDegrees) {
        double num = Double.parseDouble(windDirectionInDegrees);
        return switch ((num >= 0 && num <= 22.49 || num == 360) ? 0 :
                (num >= 22.5 && num <= 44.99) ? 1 :
                        (num >= 45 && num <= 67.49) ? 2 :
                                (num >= 67.5 && num <= 89.99) ? 3 :
                                        (num >= 90 && num <= 112.49) ? 4 :
                                                (num >= 112.5 && num <= 134.99) ? 5 :
                                                        (num >= 135 && num <= 157.49) ? 6 :
                                                                (num >= 157.5 && num <= 179.99) ? 7 :
                                                                        (num >= 180 && num <= 202.49) ? 8 :
                                                                                (num >= 202.5 && num <= 224.99) ? 9 :
                                                                                        (num >= 225 && num <= 247.49) ? 10 :
                                                                                                (num >= 247.5 && num <= 269.99) ? 11 :
                                                                                                        (num >= 270 && num <= 292.49) ? 12 :
                                                                                                                (num >= 292.5 && num <= 315.99) ? 13 :
                                                                                                                        (num >= 315 && num <= 337.49) ? 14 :
                                                                                                                                (num >= 337.5 && num <= 359.99) ? 15 : 0) {
            case 0 -> "north";
            case 1 -> "north-northeast";
            case 2 -> "northeast";
            case 3 -> "east-northeast";
            case 4 -> "east";
            case 5 -> "east-southeast";
            case 6 -> "southeast";
            case 7 -> "south-southeast";
            case 8 -> "south";
            case 9 -> "south-southwest";
            case 10 -> "southwest";
            case 11 -> "west-southwest";
            case 12 -> "west";
            case 13 -> "west-northwest";
            case 14 -> "northwest";
            case 15 -> "north-northwest";
            default -> "not a direction";
        };
    }

    private static String convertTempToCelsius(String temperatureFahrenheit) {
        double tempDouble = Double.parseDouble(temperatureFahrenheit);
        return String.valueOf(Math.round(tempDouble - 32) * 5 / 9);
    }

    private static String convertTimeFromLong(String unixSeconds) throws ParseException {
        Date time = new Date(Long.parseLong(unixSeconds)*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(time);
    }
}