package Ex50;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.*;

import static java.lang.System.in;

public class Ex50_MovieRecommendations {

    private static final Map<String, CachedResponse> cache = new HashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        Map<String,String> configInfo;
        String continueSearch;

        do {

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


            try {
                HttpURLConnection connection = (HttpURLConnection) URI.create(MOVIE_API).toURL().openConnection();
                connection.setRequestMethod("GET");

                String cacheControlHeader = connection.getHeaderField("Cache-Control");
                JsonValue responseMovieObject;

                if (cacheControlHeader != null) {
                    int maxAge = parseMaxAge(cacheControlHeader);

                    CachedResponse cachedResponse = cache.get(movieName);

                    if (cachedResponse != null && !cachedResponse.isExpired()) {
                        responseMovieObject = Json.parse(cachedResponse.getResponse());
                    } else {

                        String response = readResponse(connection);
                        responseMovieObject = invokeAPi(response, "Cache miss! Fetching data from the server.");
                        cache.put(movieName, new CachedResponse(response, maxAge));
                    }
                } else {
                    responseMovieObject = invokeAPi(readResponse(connection),"Server did not provide caching instructions. Proceeding without caching.");
                }

                connection.disconnect();

                JsonArray results = responseMovieObject.asObject().get("results").asArray();
                JsonObject result = null;
                for (int i = 0; i < results.size(); i++) {
                    if (results.get(i).asObject().get("original_title").asString().equalsIgnoreCase(movieName)) {
                        result = results.get(i).asObject();
                        break;
                    }
                }

                if (result != null) {
                    String title = result.get("original_title").asString();
                    String year = result.get("release_date").asString().split("-")[0];
                    float voteAverage = result.get("vote_average").asFloat();
                    boolean adult = result.get("adult").asBoolean();
                    String description = result.get("overview").asString();

                    printMovieInfo(title, year, voteAverage, adult, description);
                } else {
                    System.out.println("Sorry this movie is not in our database! Try again.");
                }

                System.out.print("Do you want to search for another movie? (type 'y' or 'yes' to continue) - ");
                continueSearch = sc.nextLine();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (continueSearch.equalsIgnoreCase("y") || continueSearch.equalsIgnoreCase("yes"));
    }

    private static JsonValue invokeAPi(String response, String message) {
        System.out.println(message);
        return Json.parse(response);
    }

    private static void printMovieInfo(String title, String year, float voteAverage, boolean adult, String description) {
        System.out.println("Title: " + title + "\n" +
                           "Year: " + year + "\n" +
                           "Rating: " + (adult ? "R-18" : "PG") + "\n" +
                           "Average Vote:  " + voteAverage + "\n\n" +
                           "Description: " + description + "\n\n" +
                           "You should watch this movie right now!");
    }

    private static int parseMaxAge(String cacheControlHeader) {
        String[] parts = cacheControlHeader.split("=");
        return Integer.parseInt(parts[1]);
    }

    private static String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private static class CachedResponse {
        private final String response;
        private final long expirationTime;

        public CachedResponse(String response, int maxAge) {
            this.response = response;
            this.expirationTime = System.currentTimeMillis() + (maxAge * 1000L);
        }

        public String getResponse() {
            return response;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}