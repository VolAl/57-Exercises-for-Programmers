import java.awt.*;
import java.awt.datatransfer.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.System.in;

public class Ex37_PasswordGenerator {

    private static final Pattern VOWELS_PATTERN = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);
    private static final Pattern isNumericPattern = Pattern.compile("^\\d*$");

    private static final Map<String,Integer> vowelsToNum = Map.of(
            "a", new Random().nextInt(10),
            "e", new Random().nextInt(10),
            "i", new Random().nextInt(10),
            "o", new Random().nextInt(10),
            "u", new Random().nextInt(10)
    );

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        List<Integer> numbers = IntStream.range(0, 9).boxed().toList();
        List<Character> letters = new ArrayList<>();
        for(int i=65; i<=90; i++) {
            letters.add((char)i);
        }
        for(int i=97; i<=122; i++) {
            letters.add((char)i);
        }
        List<Character> specialChars = new ArrayList<>();
        for(int i=33; i<=47; i++) {
            specialChars.add((char)i);
        }
        for(int i=58; i<=64; i++) {
            specialChars.add((char)i);
        }
        for(int i=91; i<=96; i++) {
            specialChars.add((char)i);
        }
        specialChars.add((char)125);
        specialChars.add((char)126);

        try {
            System.out.print("What's the minimum length? ");
            int minLength = sc.nextInt();
            System.out.print("How many special characters? ");
            int specialCharNumber = sc.nextInt();
            System.out.print("How many numbers? ");
            int totalNumbers = sc.nextInt();
            System.out.print("How many suggestions would you like to see? ");
            int suggestions = sc.nextInt();

            Map<Integer, String> passwordSuggestions = new HashMap<>();

            for(int j=0; j<suggestions; j++) {

                List<Object> passwordComponents = new ArrayList<>();

                for (int i = 0; i < specialCharNumber; i++) {
                    passwordComponents.add(specialChars.get(new Random().nextInt(specialChars.size())));
                }

                for (int i = 0; i < totalNumbers; i++) {
                    passwordComponents.add(numbers.get(new Random().nextInt(numbers.size())));
                }

                int totalLetters = minLength - specialCharNumber - totalNumbers;
                for (int i = 0; i < totalLetters; i++) {
                    passwordComponents.add(letters.get(new Random().nextInt(letters.size())));
                }

                StringBuilder pwd = new StringBuilder();
                while (!passwordComponents.isEmpty()) {
                    Object randomComponent = passwordComponents.get(new Random().nextInt(passwordComponents.size()));
                    if(VOWELS_PATTERN.matcher((randomComponent + "")).matches()) {
                        int randomComponentNumber = convertToNumber(randomComponent);
                        pwd.append(randomComponentNumber);
                    } else {
                        pwd.append(randomComponent);
                    }
                    passwordComponents.remove(randomComponent);
                }

                passwordSuggestions.put(j, String.valueOf(pwd));

            }

            // Consume any new line left
            sc.nextLine();

            System.out.print("Password suggestions:\n" + passwordSuggestions +
                               "\nWhich suggestion would you like to copy on your clipboard? ");

            String suggestionNumToCopy = sc.nextLine();

            while(suggestionNumToCopy.isEmpty()
                  || !isNumericPattern.matcher(suggestionNumToCopy).matches()
                  || Integer.parseInt(suggestionNumToCopy) < 0
                  || Integer.parseInt(suggestionNumToCopy) > passwordSuggestions.size()-1 ) {
                System.out.print("Your choice is not one of the available suggestions. Please retry.\nWhich suggestion would you like to copy on your clipboard? ");
                suggestionNumToCopy = sc.nextLine();
            }

            String pwdToCopy = passwordSuggestions.get(Integer.parseInt(suggestionNumToCopy));

            StringSelection stringSelection = new StringSelection(pwdToCopy);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            System.out.println("Copied!");

            // Test to see if copy worked
            /*
            String result = "";
            Transferable contents = clipboard.getContents(null);
            boolean hasTransferableText =
                    (contents != null) &&
                    contents.isDataFlavorSupported(DataFlavor.stringFlavor)
                    ;
            if (hasTransferableText) {
                try {
                    result = (String)contents.getTransferData(DataFlavor.stringFlavor);
                }
                catch (UnsupportedFlavorException | IOException ex){
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }
            System.out.println(result);

             */

        } catch (Exception e) {
            System.out.println("Cannot proceed. The value inserted is non-numeric. Exit.");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private static int convertToNumber(Object randomComponent) {
        return vowelsToNum.get(randomComponent.toString().toLowerCase());
    }
}