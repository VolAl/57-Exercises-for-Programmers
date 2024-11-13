package Ex31;

import java.util.Scanner;

import static java.lang.System.in;

public class Ex31_KarvonenHeartRate {

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        try {
            System.out.print("Resting pulse: ");
            int restingHR = sc.nextInt();
            System.out.print("Age: ");
            int age = sc.nextInt();

            System.out.println("""
                    
                    Intensity\t| Rate
                    ------------|--------""");

            for(int intensity=55; intensity<=95; intensity+=5) {
                int targetHeartRate = (((220 - age) - restingHR) * intensity/100) + restingHR;
                System.out.println(intensity + "%\t\t\t| " + targetHeartRate + " bpm");
            }

        } catch (Exception e) {
            System.out.println("Cannot proceed. Inserted input is not numeric. Exit.");
        }

        sc.close();
    }

}