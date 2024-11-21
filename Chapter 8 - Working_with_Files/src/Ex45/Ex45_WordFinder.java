package Ex45;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.in;

public class Ex45_WordFinder {

    private static Map<String, String> configWordsMapping = new HashMap<>();
    private static final Map<String, Integer> wordsReplacementOccurrences = new HashMap<>();

    public static void main(String[] args) {

        String dataReplaced = "";
        try {
            ObjectMapper objMapper = new ObjectMapper();
            configWordsMapping = objMapper.readValue(new File("config/config.json"), new TypeReference<>() {
            });

            dataReplaced = findBadWordsInFiles();
        } catch (FileNotFoundException e) {
           System.out.println("An error occurred." +e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = new Scanner(in);
        System.out.print("Which is the name of the output file? ");
        String outputFileName = sc.nextLine();
        try {
            FileWriter myWriter = new FileWriter(outputFileName);
            myWriter.write(dataReplaced);
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Map.Entry<String, Integer> entry : wordsReplacementOccurrences.entrySet()) {
            System.out.println("The word " + entry.getKey() + " has been substituted " + entry.getValue() + " times.");
        }

    }

    private static String findBadWordsInFiles() throws IOException {

        Set<String> fileList = listFilesUsingFilesList("inputFiles");
        StringBuilder dataReplaced = new StringBuilder();
        for(String s : fileList) {
            Scanner myReader = new Scanner(new File("inputFiles/" + s));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (Map.Entry<String, String> entry : configWordsMapping.entrySet()) {
                    String wordToSubstitute = entry.getValue();
                    if (data.contains(wordToSubstitute)) {
                        wordsReplacementOccurrences.put(wordToSubstitute, StringUtils.countMatches(data, wordToSubstitute));
                        dataReplaced.append(data.replaceAll(wordToSubstitute, entry.getKey())).append(System.lineSeparator());
                    }
                }
            }
            myReader.close();
        }

        return dataReplaced.toString();
    }

    public static Set<String> listFilesUsingFilesList(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }
}
