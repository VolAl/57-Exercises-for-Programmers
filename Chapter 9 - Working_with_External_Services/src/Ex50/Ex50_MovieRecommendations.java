package Ex50;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static java.lang.System.in;

public class Ex50_MovieRecommendations {

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        Map<String,String> configInfo;

        System.out.print("Enter the name of a movie: ");
        String movieName = sc.nextLine();
        String movieNameForQuery = movieName.replaceAll(" ", "+");
        String apiKey, MOVIE_API;
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            configInfo = objectMapper.readValue(new File("config/config.json"), new TypeReference<>() {
            });
            apiKey = configInfo.get("movie-api-key");
            MOVIE_API = configInfo.get("movie-api-url").replace("%par1", movieNameForQuery).replace("%par2", apiKey);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest requestMovie = HttpRequest.newBuilder(URI.create(MOVIE_API)).GET().build();

        try {
            HttpResponse<String> responseMovie= httpClient.send(requestMovie,
                    HttpResponse.BodyHandlers.ofString());

            JsonObject responseMovieObject = Json.parse(responseMovie.body()).asObject();
            JsonArray results = responseMovieObject.get("results").asArray();
            JsonObject result = null;
            for(int i=0; i<results.size(); i++) {
                if(results.get(i).asObject().get("original_title").asString().equalsIgnoreCase(movieName)) {
                    result = results.get(i).asObject();
                    break;
                }
            }

            if(result != null) {

                String title = result.get("original_title").asString();
                String year = result.get("release_date").asString().split("-")[0];
                float voteAverage = result.get("vote_average").asFloat();
                boolean adult = result.get("adult").asBoolean();
                String description = result.get("overview").asString();

                printMovieInfo(title, year, voteAverage, adult, description);
            } else {
                System.out.println("Sorry this movie is not in our database! Try again.");
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printMovieInfo(String title, String year, float voteAverage, boolean adult, String description) {
        System.out.println("Title: " + title + "\n" +
                           "Year: " + year + "\n" +
                           "Rating: " + (adult ? "R-18" : "PG") + "\n" +
                           "Average Vote:  " + voteAverage + "\n\n" +
                           "Description: " + description + "\n\n" +
                           "You should watch this movie right now!");
    }
}