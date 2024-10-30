import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex10_SelfCheckout {

    private static final Scanner sc = new Scanner(in);

    public static void main(String[] args) {
        try {
            int itemNumber = 1;
            BigDecimal subTotal = BigDecimal.valueOf(0);
            BigDecimal taxRate = BigDecimal.valueOf(5.5);
            System.out.printf("Welcome to Self-Checkout!\nAdd prices and quantities of your items to calculate subtotal, tax and total.\nIn order to stop inserting items, just press \"↩\" (\"Enter\" key) instead of inserting the item's price.\nIf have inserted an item's price, but then you press \"↩\" (\"Enter\" key) instead of inserting the item's quantity, the price will not be considered.\nWarning: Any non-numeric character and negative values will make the program exit.\n%n");
            while(true) {
                System.out.print("Enter the price of item " + itemNumber + ": ");
                BigDecimal itemPrice, itemQty;
                String itemPriceString = sc.nextLine();
                if (itemPriceString.isEmpty())  {
                    break;
                } else {
                    itemPrice = new BigDecimal(itemPriceString);
                }
                System.out.print("Enter the quantity of item " + itemNumber + ": ");
                String itemQtyString = sc.nextLine();
                if (itemQtyString.isEmpty())  {
                    break;
                } else {
                    itemQty = new BigDecimal(itemQtyString);
                }

                if (itemPrice.signum() < 1 || itemQty.signum() < 1) {
                    System.out.println("Negative values don't make sense. Exit.");
                    sc.close();
                    System.exit(0);
                }
                itemNumber++;
                subTotal = subTotal.add(itemPrice.multiply(itemQty));
            }
            BigDecimal tax = subTotal.divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY).multiply(taxRate);
            BigDecimal total = subTotal.add(tax);

            System.out.println(MessageFormat.format("\nSubtotal: ${0}\nTax: ${1}\nTotal: ${2}",
                    subTotal.setScale(2,RoundingMode.UNNECESSARY).toString(),
                    tax.setScale(2,RoundingMode.UNNECESSARY).toString(),
                    total.setScale(2,RoundingMode.UNNECESSARY).toString()));
        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }
}