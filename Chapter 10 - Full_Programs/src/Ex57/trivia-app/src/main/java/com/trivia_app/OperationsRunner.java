package com.trivia_app;

import com.trivia_app.entity.TriviaElement;
import com.trivia_app.model.TriviaElementDTO;
import com.trivia_app.service.TriviaAppService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

@Component
public class OperationsRunner implements CommandLineRunner {

    private final static Scanner sc = new Scanner(in);
    private TriviaAppService triviaAppService;

    public OperationsRunner(TriviaAppService triviaAppService) {
        this.triviaAppService = triviaAppService;
    }

    @Override
    public void run(String... args) {

        List<TriviaElementDTO> triviaElements = triviaAppService.getAllTriviaElements();

        System.out.print("Welcome to Trivia App!\nTo proceed you need to login\nEnter your username: ");
        String userName = sc.nextLine();

        switch (userName) {
            case "user":
                playGame(triviaElements);
                break;
            case "admin":
                adminSection(triviaElements);
                break;
            default:
                System.out.println("You're not an authenticated user. Exit.");
                break;
        }

        sc.close();
        System.exit(0);
    }

    private void adminSection(List<TriviaElementDTO> triviaElements) {
        System.out.println("""
                What would you like to do?
                1) Add a Trivia element
                2) Edit a Trivia element
                3) Remove a Trivia element
                4) Exit.""");

        String answer = sc.nextLine();

        while(!answer.equals("1") && !answer.equals("2") && !answer.equals("3") && !answer.equals("4")) {
            System.out.print("Invalid answer, specify only 1, 2, 3 or 4. Retry: ");
            answer = sc.nextLine();
        }

        switch (answer) {
            case "1":
                addNewElement(triviaElements);
                break;
            case "2":
                editExistingElement(triviaElements);
                break;
            case "3":
                deleteExistingElement(triviaElements);
                break;
            case "4":
                System.out.println("See you next time!");
                sc.close();
                System.exit(0);
        }
    }

    private void addNewElement(List<TriviaElementDTO> triviaElements) {
        System.out.println("Enter the question: ");
        String question = sc.nextLine();
        System.out.println("Enter the right answer: ");
        String rightAnswer = sc.nextLine();
        System.out.println("Enter the distractors, separated by comma: ");
        List<String> distractors = Arrays.stream(sc.nextLine().split(",")).toList();
        System.out.println("Enter the level: ");
        String level = sc.nextLine();

        TriviaElementDTO triviaElementDTO = new TriviaElementDTO((long) triviaElements.size(), question, rightAnswer, distractors, Integer.parseInt(level));
        triviaAppService.saveOrUpdateTriviaElement((long) triviaElements.size(), triviaAppService.convertToEntity(triviaElementDTO));
        triviaElements = triviaAppService.getAllTriviaElements();

        System.out.println("Trivia Element added!\nBack to Admin Section.");
        adminSection(triviaElements);
    }

    private void editExistingElement(List<TriviaElementDTO> triviaElements) {
        if(triviaElements.isEmpty()) {
            System.out.println("There aren't any Trivia Elements yet.\nGo back and add some!");
        } else {
            System.out.println("The current Trivia elements are: ");
            triviaElements.forEach(System.out::println);

            System.out.println("Enter the element's id: ");
            String elementId = sc.nextLine();

            TriviaElementDTO triviaElementDTO = triviaAppService.getTriviaElementById(Long.parseLong(elementId)).orElseThrow();
            TriviaElement triviaElement = triviaAppService.convertToEntity(triviaElementDTO);
            triviaElement.setId(Long.parseLong(elementId));

            System.out.println("""
                    What would you like edit?
                    1) Question
                    2) Right answer
                    3) Distractors
                    4) Level""");

            String answer = sc.nextLine();

            while (!answer.equals("1") && !answer.equals("2") && !answer.equals("3") && !answer.equals("4")) {
                System.out.print("Invalid answer, specify only 1, 2, 3 or 4. Retry: ");
                answer = sc.nextLine();
            }

            switch (answer) {
                case "1":
                    System.out.println("Enter the question: ");
                    String question = sc.nextLine();
                    triviaElement.setQuestion(question);
                    break;
                case "2":
                    System.out.println("Enter the right answer: ");
                    String rightAnswer = sc.nextLine();
                    triviaElement.setRightAnswer(rightAnswer);
                    break;
                case "3":
                    List<String> distractors = triviaElementDTO.distractors();
                    System.out.println("The current distractors are: ");
                    int i = 0;
                    for (String s : distractors) {
                        System.out.println(i++ + ") " + s);
                    }
                    System.out.print("Which distractor would you like to edit? (specify the index number): ");
                    int indexToEdit;
                    try {
                        indexToEdit = sc.nextInt();
                        // consume new line
                        sc.nextLine();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if(indexToEdit >= 0 && indexToEdit < distractors.size()) {
                        System.out.println("Enter the updated distractor's text: ");
                        String textToEdit = sc.nextLine();
                        distractors.set(indexToEdit, textToEdit);
                        triviaElement.setDistractors(distractors);
                    } else {
                        System.out.println("This index is not present.\nTry again.");
                        editExistingElement(triviaElements);
                    }
                    break;
                case "4":
                    System.out.println("Enter the level: ");
                    String level = sc.nextLine();
                    triviaElement.setLevel(Integer.parseInt(level));
                    break;
            }

            triviaAppService.saveOrUpdateTriviaElement(triviaElement.getId(), triviaElement);

            triviaElements = triviaAppService.getAllTriviaElements();

            System.out.println("Trivia Element updated!\nBack to Admin Section.");
        }
        adminSection(triviaElements);
    }

    private void deleteExistingElement(List<TriviaElementDTO> triviaElements) {
        if(triviaElements.isEmpty()) {
            System.out.println("There aren't any Trivia Elements yet.\nGo back and add some!");
        } else {
            System.out.println("The current Trivia elements are: ");
            triviaElements.forEach(System.out::println);
            System.out.print("Which Trivia element would you like to remove? (specify the index number): ");
            long indexToRemove;
            try {
                indexToRemove = sc.nextLong();
                // consume new line
                sc.nextLine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if(triviaElements.stream().anyMatch(t -> t.id().equals(indexToRemove))) {
                triviaAppService.deleteTriviaElement(indexToRemove);
                triviaElements = triviaAppService.getAllTriviaElements();
            } else {
                System.out.println("This index is not present.\nTry again.");
                deleteExistingElement(triviaElements);
            }

            System.out.println("Trivia Element deleted!\nBack to Admin Section.");
        }
        adminSection(triviaElements);
    }

    private void playGame(List<TriviaElementDTO> triviaElements) {
        System.out.println("Play a game");
    }
}