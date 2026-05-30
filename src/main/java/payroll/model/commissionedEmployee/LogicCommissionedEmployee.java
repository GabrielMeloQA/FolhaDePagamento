package payroll.model.commissionedEmployee;

import static payroll.model.utils.Utils.sc;

public class LogicCommissionedEmployee {

    public static double validateVlrSales() {
        double sales = -1;
        while (sales < 0) {
            System.out.print("\nQual o valor total das vendas? \n");
            try {
                sales = Double.parseDouble(sc.nextLine().replace(",", "."));
                if (sales < 0) System.out.println("O valor não pode ser negativo.");
            } catch (Exception e) {
                System.out.println("Entrada inválida. Use apenas números.");
            }
        }
        return sales;
    }

    public static int validatePercentageCommission() {
        int percent = -1;
        while (percent <= 0) {
            System.out.print("\nQual a porcentagem da comissão? (Ex: 10)\n");
            try {
                percent = Integer.parseInt(sc.nextLine());
                if (percent <= 0) System.out.println("A comissão deve ser maior que 0.");
            } catch (Exception e) {
                System.out.println("Entrada inválida. Use números inteiros.");
            }
        }
        return percent;
    }
}