import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex8_PizzaParty {

    private static final Scanner sc = new Scanner(in);

    public static void main(String[] args) {

        System.out.print("Which version of \"Pizza Party\" would you like to run? (Type 1 or 2) - ");
        switch (sc.nextLine()) {
            case "1":
                pizzaPartyV1();
                break;
            case "2":
                pizzaPartyV2();
                break;
            default:
                System.out.println("Invalid version. Exit.");
        }
        sc.close();
    }

    private static void pizzaPartyV1() {
        try {

            System.out.print("How many people? ");
            BigDecimal peopleQty = sc.nextBigDecimal();
            System.out.print("How many pizzas do you have? ");
            BigDecimal pizzasQty = sc.nextBigDecimal();

            if(peopleQty.signum() < 1 || pizzasQty.signum() < 1) {
                System.out.println("Negative or none people and/or pizzas don't make sense. Exit.");
                sc.close();
                System.exit(0);
            }

            BigDecimal slicesPerPizza = BigDecimal.valueOf(8);
            BigDecimal[] divisionAndRemainder = pizzasQty.multiply(slicesPerPizza).divideAndRemainder(peopleQty);
            BigDecimal division = divisionAndRemainder[0];
            boolean pizzaPieceQtyEval = division.compareTo(BigDecimal.valueOf(1)) > 0;
            BigDecimal remainder = divisionAndRemainder[1];
            boolean leftoverPieceQtyEVal = remainder.compareTo(BigDecimal.valueOf(1)) != 0;

            System.out.println("\n" + peopleQty + " people with " + pizzasQty + " pizzas.\n" +
                               "Each person gets " + division + " piece" + (pizzaPieceQtyEval ? "s" : "") + " of pizzas.\n" +
                               "There " + (leftoverPieceQtyEVal ? "are " : "is " ) + remainder + " leftover piece" + (leftoverPieceQtyEVal ? "s" : "") + ".");

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }
    }

    private static void pizzaPartyV2() {
        try {

            System.out.print("How many people? ");
            BigDecimal peopleQty = sc.nextBigDecimal();
            System.out.print("How many pieces of pizzas each person wants? ");
            BigDecimal piecesQty = sc.nextBigDecimal();

            if(peopleQty.signum() < 1 || piecesQty.signum() < 1) {
                System.out.println("Negative or none people and/or pieces of pizza don't make sense. Exit.");
                sc.close();
                System.exit(0);
            }

            BigDecimal slicesPerPizza = BigDecimal.valueOf(8);
            BigDecimal piecesPerPeople = peopleQty.multiply(piecesQty);
            BigDecimal pizzasQty = piecesPerPeople.divide(slicesPerPizza, RoundingMode.UP);

            boolean peopleQtyEVal = peopleQty.compareTo(BigDecimal.valueOf(1)) > 0;
            boolean piecesQtyEVal = piecesQty.compareTo(BigDecimal.valueOf(1)) > 0;
            boolean pizzasQtyEVal = pizzasQty.compareTo(BigDecimal.valueOf(1)) != 0;

            System.out.println("\n" + peopleQty + (peopleQtyEVal ? " people want " : " person wants ") + piecesQty + " piece" + (piecesQtyEVal ? "s" : "") + " of pizza.\n" +
                               "You need to buy " + pizzasQty + " pizza" + (pizzasQtyEVal ? "s" : "") +".");

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }
    }
}