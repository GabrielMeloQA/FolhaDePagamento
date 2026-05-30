package payroll.model.utils;

import java.util.Locale;
import java.util.Scanner;

public class Utils {

    public static Scanner sc = new Scanner(System.in).useLocale(new Locale("pt", "BR"));

    public static boolean newOperation() {
        while (true) {
            System.out.print("\nDeseja realizar nova operação de cadastramento de um outro colaborador? (Y / N)\n");
            String yesOrNo = sc.nextLine().trim();

            if (yesOrNo.equalsIgnoreCase("Y")) {
                return true;
            } else if (yesOrNo.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Não entendi, por favor, insira Y ou N.");
            }
        }
    }

}
