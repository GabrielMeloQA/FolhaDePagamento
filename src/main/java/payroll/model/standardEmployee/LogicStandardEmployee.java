package payroll.model.standardEmployee;

import static payroll.model.utils.Utils.sc;

public class LogicStandardEmployee {

    public static int extras() {
        boolean isValid = false;
        int extrasValue;
        System.out.println("Se houver extras, por favor insira abaixo. Se não, apenas digite '0' (ZERO).");
        do {
            extrasValue = sc.nextInt();
            sc.nextLine();
            if (extrasValue < 0) {
                System.out.println("Este número é inválido, por favor, insira um número válido.");
            } else {
                isValid = true;
            }
        } while (!isValid);
        return extrasValue;
    }


}
