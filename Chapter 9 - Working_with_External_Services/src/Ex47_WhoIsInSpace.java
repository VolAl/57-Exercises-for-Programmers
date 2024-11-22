import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Ex47_WhoIsInSpace {

    private static final String WHO_IS_IN_SPACE_API = "http://api.open-notify.org/astros.json";

    public static void main(String[] args) {


        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest requestPeopleInSpace = HttpRequest.newBuilder(URI.create(WHO_IS_IN_SPACE_API)).GET().build();

        try {
            HttpResponse<String> responsePeopleInSpace = httpClient.send(requestPeopleInSpace,
                    HttpResponse.BodyHandlers.ofString());

            JsonObject responsePeopleInSpaceObject = Json.parse(responsePeopleInSpace.body()).asObject();

            JsonArray people = responsePeopleInSpaceObject.get("people").asArray();
            int peopleNumber = responsePeopleInSpaceObject.get("number").asInt();

            System.out.println("There are " + peopleNumber + " people in space right now:");

            Map<String, List<String>> peopleInSpacePerSpacecraft = new HashMap<>(0);
            for(JsonValue jv : people) {
                JsonObject jo = jv.asObject();
                String spacecraft = jo.get("craft").asString();
                String personName = jo.get("name").asString();
                if(peopleInSpacePerSpacecraft.containsKey(spacecraft)) {
                    peopleInSpacePerSpacecraft.get(spacecraft).add(personName);
                } else {
                    peopleInSpacePerSpacecraft.put(spacecraft, new ArrayList<>());
                }
            }

            Map<String, List<String>> peopleInSpacePerSpacecraftSorted = sortPeopleByLastName(peopleInSpacePerSpacecraft);

            printPeopleInSpaceList(peopleInSpacePerSpacecraftSorted);


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String,List<String>> sortPeopleByLastName(Map<String, List<String>> peopleInSpacePerSpacecraft) {

        Map<String,List<String>> sortedPeople = new HashMap<>();

        for(Map.Entry<String,List<String>> entry : peopleInSpacePerSpacecraft.entrySet()) {
            List<String> peopleSorted = new ArrayList<>();
            Map<String, String> peopleFirstLastNames = getStringStringMap(entry);
            LinkedHashMap<String, String> sortedMap = peopleFirstLastNames.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            for(Map.Entry<String,String> firstLastName : sortedMap.entrySet()) {
                peopleSorted.add(firstLastName.getKey() + " " + firstLastName.getValue());
            }

            sortedPeople.put(entry.getKey(), peopleSorted);
        }

        return sortByKey(sortedPeople);

    }

    private static Map<String, List<String>> sortByKey(Map<String, List<String>> sortedPeople) {
        Map<String, List<String>> sortedPeopleByKey = new LinkedHashMap<>();

        ArrayList<String> sortedKeys = new ArrayList<>(sortedPeople.keySet());

        Collections.sort(sortedKeys);

        for (String x : sortedKeys) {
            sortedPeopleByKey.put(x, sortedPeople.get(x));
        }

        return sortedPeopleByKey;
    }

    private static Map<String, String> getStringStringMap(Map.Entry<String, List<String>> entry) {
        List<String> people = entry.getValue();
        Map<String,String> peopleFirstLastNames = new HashMap<>();
        for(String s : people) {
            String[] name = s.split(" ");
            switch (name.length) {
                case 2 -> peopleFirstLastNames.put(name[0], name[1]);
                case 3 -> peopleFirstLastNames.put(name[0] + " " + name[1], name[2]);
                case 4 -> peopleFirstLastNames.put(name[0] + " " + name[1], name[2] + " " + name[3]);
            }
        }
        return peopleFirstLastNames;
    }

    private static void printPeopleInSpaceList(Map<String, List<String>> peopleInSpacePerSpacecraft) {
        int longestSpacecraftNameSize = findLongest(peopleInSpacePerSpacecraft).get(0);
        int longestPersonNameSize = findLongest(peopleInSpacePerSpacecraft).get(1);
        System.out.format("\n%-" + longestSpacecraftNameSize + "s%-" + longestPersonNameSize +"s\n", "Craft", "| Name");
        printSeparatorLine(longestSpacecraftNameSize, longestPersonNameSize);
        for(Map.Entry<String,List<String>> entry : peopleInSpacePerSpacecraft.entrySet()) {
            boolean hasKeyBeenPrintedAlready = false;
            String spacecraft = entry.getKey();
            for(String s : entry.getValue()) {
                System.out.format("%-" + longestSpacecraftNameSize + "s%-" + longestPersonNameSize +"s\n", (hasKeyBeenPrintedAlready ? "" : spacecraft), "| " + s);
                hasKeyBeenPrintedAlready = true;
            }
            printSeparatorLine(longestSpacecraftNameSize, longestPersonNameSize);
        }
    }

    private static void printSeparatorLine(int longestSpacecraftNameSize, int longestPersonNameSize) {
        for(int i=0; i<longestSpacecraftNameSize; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for(int i=0; i<longestPersonNameSize; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private static Map<Integer,Integer> findLongest(Map<String, List<String>> peopleInSpacePerSpacecraft) {
        int longestSpacecraftNameSize = 0, longestPersonNameSize = 0;
        Map<Integer,Integer> result = new HashMap<>();
        for(Map.Entry<String,List<String>> entry : peopleInSpacePerSpacecraft.entrySet()) {
            if(entry.getKey().length() > longestSpacecraftNameSize) {
                longestSpacecraftNameSize = entry.getKey().length();
            }
            for(String s : entry.getValue()) {
                if (s.length() > longestPersonNameSize) {
                    longestPersonNameSize = s.length();
                }
            }
        }
        result.put(0,longestSpacecraftNameSize + 1);
        result.put(1,longestPersonNameSize + 1);

        return result;
    }
}