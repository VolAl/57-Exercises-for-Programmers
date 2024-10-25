package Ex7;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex7_AreaOfRectangularRoom {

    private static final String ROOM_LENGTH = "room_length";
    private static final String ROOM_WIDTH = "room_width";
    private static final Scanner sc = new Scanner(in);

    public static void main(String[] args) {
        System.out.print("Which version of \"Area Of Rectangular Room\" would you like to run? (Type 1 or 2) - ");
        switch (sc.nextLine()) {
            case "1":
                roomAreaV1();
                break;
            case "2":
                roomAreaV2();
                break;
            default:
                System.out.println("Invalid version. Exit.");
        }
        sc.close();
    }

    private static void roomAreaV1() {
        try {
            Map<String, String> userInputStrings = gatherInputStrings("1");
            BigDecimal roomLength = new BigDecimal(userInputStrings.get(ROOM_LENGTH));
            BigDecimal roomWidth = new BigDecimal(userInputStrings.get(ROOM_WIDTH));

            System.out.println(MessageFormat.format("You entered dimensions of {0} feet by {1} feet.",
                    roomLength, roomWidth));

            BigDecimal areaInFeet = roomLength.multiply(roomWidth);
            BigDecimal areaInSquareMeters = convertArea(areaInFeet, "F");
            System.out.println(MessageFormat.format("The area is \n {0} square feet \n {1} square meters",
                    areaInFeet, areaInSquareMeters));

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }
    }

    private static void roomAreaV2() {
        try {
            JFrame f = new JFrame();
            f.setAlwaysOnTop(true);
            String[] options = {"Meters", "Feet"};
            int unitOfMeasurement = JOptionPane.showOptionDialog(f, "Which Unit of Measurement would you like to use?", "Choose one",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if(unitOfMeasurement == 0) {
                Map<String, String> userInputStrings = gatherInputStrings("2");

                BigDecimal roomLength = new BigDecimal(userInputStrings.get(ROOM_LENGTH));
                BigDecimal roomWidth = new BigDecimal(userInputStrings.get(ROOM_WIDTH));

                System.out.println(MessageFormat.format("You entered dimensions of {0} square meters by {1} square meters.",
                        roomLength, roomWidth));

                BigDecimal areaInSquareMeters = roomLength.multiply(roomWidth);
                BigDecimal areaInFeet = convertArea(areaInSquareMeters, "M");
                System.out.println(MessageFormat.format("The area is \n {0} square feet \n {1} square meters",
                        areaInFeet, areaInSquareMeters));
            } else {
                roomAreaV1();
            }

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }
    }

    private static Map<String, String> gatherInputStrings(String version) {
        System.out.print("What is the length of the room" + (version.equals("1") ? " in feet" : "") + "? ");
        String roomLength = sc.nextLine();
        System.out.print("What is the width of the room" + (version.equals("1") ? " in feet" : "") + "? ");
        String roomWidth = sc.nextLine();

        return new HashMap<>() {{
            put(ROOM_LENGTH, roomLength);
            put(ROOM_WIDTH, roomWidth);
        }};
    }

    private static BigDecimal convertArea(BigDecimal area, String areaMeasurementUnit) {
        if(areaMeasurementUnit.equals("F")) {
            return area.multiply(new BigDecimal("0.09290304"));
        } else {
            return area.divideToIntegralValue(new BigDecimal("0.09290304"));
        }
    }
}