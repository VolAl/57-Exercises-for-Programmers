package Ex35;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex35_PickingWinner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(in);
        List<String> contestantsNames = new ArrayList<>();

        while (true) {
            System.out.print("Enter a name: ");
            String name = sc.nextLine();
            if(name.isEmpty()) {
                break;
            }
            contestantsNames.add(name);
        }
        int winnerOrder = 1;
        while(!contestantsNames.isEmpty()) {
            String winner = contestantsNames.get(new Random().nextInt((contestantsNames.size() - 1) + 1));
            System.out.println("The " + winnerOrder++ + "° winner is... " + winner);
            contestantsNames.remove(winner);
        }
    }
}