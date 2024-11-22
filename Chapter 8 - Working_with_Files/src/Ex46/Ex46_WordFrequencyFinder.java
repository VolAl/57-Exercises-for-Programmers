package Ex46;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Ex46_WordFrequencyFinder {

    public static void main(String[] args) {

        Path path = Paths.get("words.txt");

        try {
            String input = Files.readAllLines(path).toString();

            Map<String, Integer> wordOccurrences = Pattern.compile("\\W+")
                    .splitAsStream(input)
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.groupingBy(String::toLowerCase,
                            Collectors.summingInt(s -> 1)));

            LinkedHashMap<String, Integer> wordOccurrencesSorted = wordOccurrences.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Map.Entry<String, Integer> entry : wordOccurrencesSorted.entrySet()) {
                System.out.print(entry.getKey() + ": ");
                for(int i=0; i< entry.getValue(); i++) {
                    System.out.print("*");
                }
                System.out.println();

                dataset.addValue(entry.getValue(), entry.getKey(), entry.getValue());
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Words Occurrences",
                    "Word",
                    "Occurrence",
                    dataset);

            ChartPanel chartPanel = new ChartPanel(chart);
            JFrame frame = new JFrame();
            frame.setSize(700, 500);
            frame.setContentPane(chartPanel);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
