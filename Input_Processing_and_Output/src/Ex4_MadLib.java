import javax.swing.*;
import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.*;

public class Ex4_MadLib {
    private static final String NOUN = "noun";
    private static final String VERB = "verb";
    private static final String ADJECTIVE = "adjective";
    private static final String ADVERB = "adverb";

    public static void main(String[] args) {
        Scanner sc = new Scanner(in);

        Map<String, String> storyElements = gatherStoryElements(sc);
        printMainStory(storyElements);

        Map<String, List<String>> branchingStoryElements = gatherBranchingStoryElements(sc);

        if(!branchingStoryElements.isEmpty()) {
            JFrame f = new JFrame();
            f.setAlwaysOnTop(true);
            printMainStory(storyElements);

            while(true) {
                String[] options = {"Random", "Alphabetically", "Reverse", "EXIT"};
                int category = JOptionPane.showOptionDialog(f, "How would you like your story to continue?", "Let's expand the story!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if(options[category].equals("EXIT")) {
                    break;
                }

                List<String> verbValues = branchingStoryElements.get(VERB) != null ?
                        branchingStoryElements.get(VERB) : new ArrayList<>();
                List<String> adjectiveValues = branchingStoryElements.get(ADJECTIVE) != null ?
                        branchingStoryElements.get(ADJECTIVE) : new ArrayList<>();
                List<String> nounValues = branchingStoryElements.get(NOUN) != null ?
                        branchingStoryElements.get(NOUN) : new ArrayList<>();
                List<String> adverbValues = branchingStoryElements.get(ADVERB) != null ?
                        branchingStoryElements.get(ADVERB) : new ArrayList<>();

                switch (category) {
                    case 0:
                        System.out.println(MessageFormat.format("Then you {0} your {1} {2} {3}!",
                                verbValues.isEmpty() ? "" : verbValues.get(new Random().nextInt(verbValues.size())),
                                adjectiveValues.isEmpty() ? "" : adjectiveValues.get(new Random().nextInt(adjectiveValues.size())),
                                nounValues.isEmpty() ? "" : nounValues.get(new Random().nextInt(nounValues.size())),
                                adverbValues.isEmpty() ? "" : adverbValues.get(new Random().nextInt(adverbValues.size()))));
                        break;
                    case 1:
                        System.out.println(MessageFormat.format("Then you {0} your {1} {2} {3}!",
                                verbValues.isEmpty() ? "" : verbValues.getFirst(),
                                adjectiveValues.isEmpty() ? "" : adjectiveValues.getFirst(),
                                nounValues.isEmpty() ? "" : nounValues.getFirst(),
                                adverbValues.isEmpty() ? "" : adverbValues.getFirst()));
                        break;
                    case 2:
                        System.out.println(MessageFormat.format("Then you {3} your {2} {1} {0}!",
                                verbValues.isEmpty() ? "" : verbValues.get(new Random().nextInt(verbValues.size())),
                                adjectiveValues.isEmpty() ? "" : adjectiveValues.get(new Random().nextInt(adjectiveValues.size())),
                                nounValues.isEmpty() ? "" : nounValues.get(new Random().nextInt(nounValues.size())),
                                adverbValues.isEmpty() ? "" : adverbValues.get(new Random().nextInt(adverbValues.size()))));
                        break;
                    default:
                        System.exit(0);
                }
            }

            f.dispose();
        }

        sc.close();
    }

    private static Map<String, String> gatherStoryElements(Scanner sc) {
        Map<String, String> storyElements = new HashMap<>();
        System.out.print("Enter a noun: ");
        storyElements.put(NOUN, sc.nextLine());
        System.out.print("Enter a verb: ");
        storyElements.put(VERB, sc.nextLine());
        System.out.print("Enter a adjective: ");
        storyElements.put(ADJECTIVE, sc.nextLine());
        System.out.print("Enter a adverb: ");
        storyElements.put(ADVERB, sc.nextLine());
        return storyElements;
    }

    private static Map<String, List<String>> gatherBranchingStoryElements(Scanner sc) {
        Map<String, List<String>> branchingStoryElements = new HashMap<>();
        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        while (true) {
            List<String> values = new ArrayList<>();
            String[] categories = { NOUN, VERB, ADJECTIVE, ADVERB, "EXIT" };
            int category = JOptionPane.showOptionDialog(f, "Specify a Category or click \"EXIT\" to stop. \nYou can click a Category multiple times to add different values to it :)", "Let's expand the story!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);
            String categoryText = categories[category];
            if(categoryText.equals("EXIT")) {
                break;
            }
            System.out.print("Enter the " + categoryText  + ": ");
            List<String> currentValues = branchingStoryElements.get(categoryText);
            if(currentValues != null) {
                values.addAll(branchingStoryElements.get(categoryText));
            }
            values.add(sc.nextLine());
            Collections.sort(values);
            branchingStoryElements.put(categories[category], values);
        }

        f.dispose();

        return branchingStoryElements;
    }

    private static void printMainStory(Map<String, String> storyElements) {
        System.out.println(MessageFormat.format("Do you {0} your {1} {2} {3}? That's hilarious!",
                storyElements.get(VERB),
                storyElements.get(ADJECTIVE),
                storyElements.get(NOUN),
                storyElements.get(ADVERB)));
    }
}