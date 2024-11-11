import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.in;

public class Ex24_AnagramChecker {
    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        System.out.print("Enter two strings and I'll tell you if they are anagrams:\n" +
                         "Enter the first string: ");
        String firstString = sc.nextLine();
        System.out.print("Enter the second string: ");
        String secondString = sc.nextLine();

        boolean isAnagram = isAnagram(firstString,secondString);

        System.out.println("\"" + firstString + "\" and \"" + secondString + "\" are " +
                           (isAnagram ? "" : "not ") + "anagrams");

        sc.close();
    }

    private static boolean isAnagram(String firstString, String secondString) {
        List<Character> secondStringList = new ArrayList<>();
        for (char c : secondString.toCharArray()) {
            secondStringList.add(c);
        }

        AtomicBoolean isAnagram = new AtomicBoolean(true);
        for (char c : firstString.toCharArray()) {
            if (!secondStringList.contains(c)) {
                isAnagram.set(false);
                break;
            }
        }

        return firstString.length() == secondString.length() && isAnagram.get();
    }
}