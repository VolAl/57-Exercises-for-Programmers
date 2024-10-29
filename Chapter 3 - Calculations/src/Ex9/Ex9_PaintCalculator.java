package Ex9;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex9_PaintCalculator {

    private final static Scanner sc = new Scanner(in);

    public static void main(String[] args) {
        try {

            JFrame f = new JFrame();
            f.setAlwaysOnTop(true);
            String[] options = {"Rectangular", "Round", "L-Shaped"};
            int roomShape = JOptionPane.showOptionDialog(f, "Which shape is your room?", "Choose one",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            BigDecimal areaCoveredByOneGallon = BigDecimal.valueOf(350);
            BigDecimal roomArea = calculateRoomAreaGivenShape(roomShape);
            BigDecimal gallonsNeeded = roomArea.divide(areaCoveredByOneGallon, RoundingMode.UP);

            System.out.println("You will need to purchase " + gallonsNeeded + " gallon" + (gallonsNeeded.compareTo(BigDecimal.valueOf(1)) > 0 ? "s" : "") +
                               " of paint to cover " + roomArea + " square feet.");

            f.dispose();

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }

    private static BigDecimal calculateRoomAreaGivenShape(int shape) {

        switch(shape) {
            case 0: // Rectangular
                System.out.print("How long is the room? ");
                BigDecimal roomLength = sc.nextBigDecimal();
                System.out.print("How wide is the room? ");
                BigDecimal roomWidth = sc.nextBigDecimal();

                if (roomLength.signum() < 1 || roomWidth.signum() < 1) {
                    System.out.println("Negative or none measurements don't make sense. Exit.");
                    sc.close();
                    System.exit(0);
                }
                return roomWidth.multiply(roomLength);

            case 1: // Round
                System.out.print("Which is the radius of the room? ");
                BigDecimal roomRadius = sc.nextBigDecimal();

                if (roomRadius.signum() < 1) {
                    System.out.println("Negative or none measurements don't make sense. Exit.");
                    sc.close();
                    System.exit(0);
                }
                return roomRadius.multiply(roomRadius).multiply(BigDecimal.valueOf(Math.PI).setScale(0, RoundingMode.HALF_EVEN));
            case 2: // L-Shape
                System.out.println("""
                        Write the room's measurements as follow:
                          _____
                          |    |
                          |    |
                        1 |    | 4\s
                          |    |___
                          |         | 3
                          |_________| \s
                              2     \s""");
                System.out.print("Measurement 1: ");
                BigDecimal m1 = sc.nextBigDecimal();
                System.out.print("Measurement 2: ");
                BigDecimal m2 = sc.nextBigDecimal();
                System.out.print("Measurement 3: ");
                BigDecimal m3 = sc.nextBigDecimal();
                System.out.print("Measurement 4: ");
                BigDecimal m4 = sc.nextBigDecimal();

                if (m1.signum() < 1 || m2.signum() < 1 || m3.signum() < 1 || m4.signum() < 1) {
                    System.out.println("Negative or none measurements don't make sense. Exit.");
                    sc.close();
                    System.exit(0);
                }

                BigDecimal firstArea = m2.multiply(m3);
                BigDecimal secondArea = (m1.subtract(m3)).multiply(m2.subtract(m4));
                return firstArea.add(secondArea);
            default:
                System.out.println("No known shape. Exit.");
                return null;
        }
    }
}