package payroll.model.productionEmployee;

import static payroll.model.utils.Utils.sc;

public class LogicProductionEmployee {

    public static int qtPieces() {
        boolean isValid = false;
        int pieces;
        System.out.println("Por favor, informe quantas peças o colaborador(a) produziu.");
        do {
            pieces = sc.nextInt();
            sc.nextLine();
            if (pieces < 0) {
                System.out.println("Este número é inválido, por favor, insira um número válido.");
            } else {
                isValid = true;
            }
        } while (!isValid);
        return pieces;
    }

    public static double piecesValue() {
        boolean isValid = false;
        double value = 0;
        System.out.println("Por favor, informe o valor unitário das peças.");
        do {
            String input = sc.nextLine().trim().replace(",", ".");
            try {
                value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Este número é inválido, por favor, insira um número válido.");
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, por favor insira um número válido.");
            }
        } while (!isValid);
        return value;
    }

}
