import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.in;

public class Ex11_CurrencyConversion {

    private static final String EXCHANGE_RATE_API = "https://open.er-api.com/v6/latest/USD";
    private static final String COUNTRY_CURRENCY_API = "https://api.dedolist.com/api/v1/business/country-currency-codes/";

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest requestExchangeRate = HttpRequest.newBuilder(URI.create(EXCHANGE_RATE_API)).GET().build();
        HttpRequest requestCountryCurrency = HttpRequest.newBuilder(URI.create(COUNTRY_CURRENCY_API)).GET().build();

        try {

            System.out.print("In which country would you like to exchange? ");
            String country = sc.nextLine();
            System.out.print("How much money are you exchanging? ");
            String moneyString = sc.nextLine();

            HttpResponse<String> responseCountryCurrency = httpClient.send(requestCountryCurrency,
                    HttpResponse.BodyHandlers.ofString());

            JsonArray countryCurrencyArray = Json.parse(responseCountryCurrency.body()).asArray();

            Optional<JsonValue> countryCurrencyObject = countryCurrencyArray
                    .values()
                    .stream()
                    .filter( item -> item.asObject().get("entity").asString().equals(country.toUpperCase()))
                    .findFirst();

            String countryCurrencyCode = countryCurrencyObject.isPresent() ?
                    countryCurrencyObject.get().asObject().get("alphabetic_code").asString() : "";

            HttpResponse<String> responseExchangeRates = httpClient.send(requestExchangeRate,
                    HttpResponse.BodyHandlers.ofString());

            JsonObject responseExchangeRatesObject = Json.parse(responseExchangeRates.body()).asObject();
            JsonObject ratesObject = responseExchangeRatesObject.get("rates").asObject();

            BigDecimal money = new BigDecimal(moneyString);
            BigDecimal exchangeRateFrom = new BigDecimal(ratesObject.get(countryCurrencyCode).toString());
            BigDecimal exchangeRateTo = new BigDecimal(ratesObject.get("USD").toString());
            BigDecimal dollars = (money.multiply(exchangeRateFrom)).divide(exchangeRateTo, RoundingMode.HALF_UP);

            System.out.println(MessageFormat.format("{0} {1} at an exchange rate of {2} in {3} is {4} U.S. dollars.",
                    money.setScale(2, RoundingMode.HALF_UP).toString(),
                    countryCurrencyCode,
                    exchangeRateFrom.setScale(2, RoundingMode.HALF_UP).toString(),
                    country,
                    dollars.setScale(2, RoundingMode.HALF_UP).toString()));

        } catch (Exception e) {
            System.out.println(MessageFormat.format("Error - {0}. Exit.",e.getMessage()));
        }

        sc.close();
    }
}