import javax.swing.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.in;

public class Ex21_NumbersToNames {
    
    private static final String EN = "English", IT = "Italiano", DE = "Deutsch";

    private static final Map<Integer, Map<String, String>> MONTHS_PER_LANGUAGE = Map.ofEntries(
            Map.entry(1, Map.of(
                    EN, "January",
                    IT, "Gennaio",
                    DE, "Januar"
            )),
            Map.entry(2, Map.of(
                    EN, "Februar",
                    IT, "Febbraio",
                    DE, "Februar"
            )),
            Map.entry(3, Map.of(
                    EN, "March",
                    IT, "Marzo",
                    DE, "März"
            )),
            Map.entry(4, Map.of(
                    EN, "April",
                    IT, "Aprile",
                    DE, "April"
            )),
            Map.entry(5, Map.of(
                    EN, "May",
                    IT, "Maggio",
                    DE, "Mai"
            )),
            Map.entry(6, Map.of(
                    EN, "June",
                    IT, "Giugno",
                    DE, "Juni"
            )),
            Map.entry(7, Map.of(
                    EN, "July",
                    IT, "Luglio",
                    DE, "Juli"
            )),
            Map.entry(8, Map.of(
                    EN, "August",
                    IT, "Agosto",
                    DE, "August"
            )),
            Map.entry(9, Map.of(
                    EN, "September",
                    IT, "Settembre",
                    DE, "September"
            )),
            Map.entry(10, Map.of(
                    EN, "October",
                    IT, "Ottobre",
                    DE, "Oktober"
            )),
            Map.entry(11, Map.of(
                    EN, "November",
                    IT, "Novembre",
                    DE, "November"
            )),
            Map.entry(12, Map.of(
                    EN, "December",
                    IT, "Dicembre",
                    DE, "Dezember"
            ))
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(in);
        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        String[] options = {EN, IT, DE, "EXIT"};

        while(true) {
            int lang = JOptionPane.showOptionDialog(f, "Choose a language to continue:", "Choose one",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if(lang == 3) { // "Exit" option
                sc.close();
                f.dispose();
                exit(0);
            }

            switch (lang) {
                case 0 -> System.out.print("Please enter the number of the month: ");
                case 1 -> System.out.print("Specifica il numero del mese: ");
                case 2 -> System.out.print("Bitte geben Sie die Monatszahl ein: ");
            }

            try {
                int monthNumber = sc.nextInt();

                while (monthNumber < 1 || monthNumber > 12) {
                    monthNumber = switch (lang) {
                        case 0 -> {
                            System.out.print("Invalid number. An Year has only 12 months. Retry." +
                                             "\nPlease enter the number of the month: ");
                            yield sc.nextInt();
                        }
                        case 1 -> {
                            System.out.print("Numero invalido. Un anno ha solo 12 mesi. Riprova." +
                                             "\nSpecifica il numero del mese: ");
                            yield sc.nextInt();
                        }
                        case 2 -> {
                            System.out.print("Ungültige Zahl. Ein Jahr hat nur 12 Monate. Bitte versuchen Sie es noch einmal." +
                                             "\nBitte geben Sie die Monatszahl ein: ");
                            yield sc.nextInt();
                        }
                        default -> monthNumber;
                    };
                }

                String monthName = MONTHS_PER_LANGUAGE.get(monthNumber).get(lang == 0 ? EN : (lang == 1 ? IT : DE));

                switch (lang) {
                    case 0 -> System.out.println(MessageFormat.format("The name of the month is {0}.", monthName));
                    case 1 -> System.out.println(MessageFormat.format("Il nome del mese e'' {0}.", monthName));
                    case 2 -> System.out.println(MessageFormat.format("Der Name des Monats ist {0}.", monthName));
                }

            } catch (Exception e) {
                switch (lang) {
                    case 0 -> System.out.print("Cannot proceed. Inserted input is not numeric. Exit.");
                    case 1 ->
                            System.out.print("Impossibile procedere. L'input inserito non e' numerico. Esecuzione interrotta.");
                    case 2 ->
                            System.out.print("Kann nicht fortfahren. Eingegebene Eingabe ist nicht numerisch. Abbruch.");
                }

                sc.close();
                f.dispose();
                exit(0);
            }
        }
    }
}