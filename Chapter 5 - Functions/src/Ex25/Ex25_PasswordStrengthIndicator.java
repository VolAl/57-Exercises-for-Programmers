package Ex25;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Ex25_PasswordStrengthIndicator {

    private static final String VALIDATE = "Validate", EXIT = "Exit";
    private static final Pattern containsOnlyNumbersPattern = Pattern.compile("^\\d*$");
    private static final Pattern containsOnlyLettersPattern = Pattern.compile("^[a-zäöüßA-ZÄÖÜ]*$");
    private static final Pattern containsLettersNumberPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$");
    private static final Pattern containsLettersNumbersSpecialCharsPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!-\\/:-@\\[-`{-~])([a-zA-Z0-9!-\\/:-@\\[-`{-~]+)$");
    

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        String[] options = {VALIDATE, EXIT};

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField password = new JTextField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);

        label:
        while(true) {

            int action = JOptionPane.showOptionDialog(frame, panel, "Please enter a password to validate:",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (options[action]) {
                case EXIT:
                    System.exit(0);
                    break label;
                case VALIDATE:
                    String passwordText = password.getText();
                    int passwordStrength = validatePasswordWithStrength(password.getText());
                    String passwordStrengthText = switch (passwordStrength) {
                        case 0 -> "very weak";
                        case 1 -> "weak";
                        case 2 -> "strong";
                        case 3 -> "very strong";
                        default -> "";
                    };
                    System.out.println(MessageFormat.format("The password ''{0}'' is a {1} password.",
                            passwordText, passwordStrengthText));
                    password.setText("");
                    break;
            }
        }

        sc.close();
    }

    private static int validatePasswordWithStrength(String password) {
        if(password.length() < 8) {
            if(containsOnlyNumbersPattern.matcher(password).matches()) {
                return 0; // "very weak"
            } else if(containsOnlyLettersPattern.matcher(password).matches()) {
                return 1; // "weak"
            }
        } else if(containsLettersNumberPattern.matcher(password).matches()) {
            return 2; // "strong"
        } else if (containsLettersNumbersSpecialCharsPattern.matcher(password).matches()) {
            return 3; // "very strong"
        }
        return 0;
    }
}