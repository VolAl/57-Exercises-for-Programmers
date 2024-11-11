import java.util.Scanner;

import static java.lang.System.in;

public class Ex23_TroubleshootingCarIssues {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(in);
        String reply;

        System.out.print("Is the car silent when you turn on the key? ");
        reply = sc.nextLine();
        if(reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("yes")) {
            System.out.print("Are the battery terminals corroded? ");
            reply = sc.nextLine();

            if(reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("yes")) {
                System.out.print("Clean terminals and try starting again.");
            } else {
                System.out.println("Replace cables and try again.");
            }
        } else {
            System.out.print("Does the car make a clicking noise? ");
            reply = sc.nextLine();

            if(reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("yes")) {
                System.out.print("Replace the battery.");
            } else {
                System.out.print("Does the car crank up but fail to start? ");
                reply = sc.nextLine();

                if(reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("yes")) {
                    System.out.print("Check spark plug connections.");
                } else {
                    System.out.print("Does the engine start and then die? ");
                    reply = sc.nextLine();

                    if(reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("yes")) {
                        System.out.print("Does your car have full injection? ");
                        reply = sc.nextLine();

                        if(reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("yes")) {
                            System.out.println("Get it in for service.");
                        } else {
                            System.out.print("Check to insure the choke is opening and closing.");
                        }
                    } else {
                        System.out.println("Get it in for service.");
                    }
                }
            }
        }

        sc.close();
    }
}
