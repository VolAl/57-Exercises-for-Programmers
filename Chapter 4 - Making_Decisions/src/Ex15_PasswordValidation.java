import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Ex15_PasswordValidation {

    private static final Map<String,String> userPasswordMap = new HashMap<>();
    
    private static final String REGISTER = "Register", LOGIN = "Login", EXIT = "Exit";

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        String[] options = {REGISTER, LOGIN, EXIT};

        label:
        while(true) {
            int action = JOptionPane.showOptionDialog(f, "Login or Register?", "Please choose one option",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            Map<String,String> userInfo;
            switch (options[action]) {
                case EXIT:
                    System.exit(0);
                    break label;
                case REGISTER:
                    userInfo = userInfo(f, REGISTER);
                    userPasswordMap.put(userInfo.get("user"), BCrypt.withDefaults().hashToString(12, userInfo.get("pass").toCharArray()));
                    break;
                case LOGIN:
                    userInfo = userInfo(f, LOGIN);
                    String userName = userInfo.get("user");
                    String password = userInfo.get("pass");
                    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), userPasswordMap.get(userName));

                    if (userPasswordMap.containsKey(userName) && result.validFormat && result.verified) {
                        System.out.println("Welcome!");
                        System.exit(0);
                    } else {
                        System.out.println("I don't know you!");
                    }
                    break;
            }
        }

    }

    public static Map<String, String> userInfo(JFrame frame, String action) {
        Map<String, String> loginInformation = new HashMap<>();
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(frame, panel, action, JOptionPane.QUESTION_MESSAGE);

        loginInformation.put("user", username.getText());
        loginInformation.put("pass", new String(password.getPassword()));
        return loginInformation;
    }
}