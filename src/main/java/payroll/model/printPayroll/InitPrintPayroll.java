package payroll.model.printPayroll;

import payroll.model.standardEmployee.StandardEmployee;
import payroll.model.commissionedEmployee.CommissionedEmployee;
import payroll.model.productionEmployee.ProductionEmployee;

import static payroll.model.utils.Utils.sc;

public class InitPrintPayroll {

    LogicPrintPayroll logicPrint = new LogicPrintPayroll();

    private int welcome() {
        System.out.print("""
                O que você deseja fazer?
                1 - Retornar a folha de pagamento de um(a) único(a) colaborador(a).
                2 - Retornar a folha de pagamento de todos os colaboradores.
                3 - Retornar a folha de pagamento de todos os colaboradores de produção.
                4 - Retornar a folha de pagamento de todos os colaboradores comissionados.
                5 - Retornar a folha de pagamento de todos os colaboradores padrões.
                6 - Voltar ao menu principal.
                Por favor, insira a opção desejada:\s
                """);
        int option = sc.nextInt();
        sc.nextLine();
        return option;
    }

    private void availableOptions(int option) {
        switch (option) {
            case 1 -> {
                System.out.print("Digite a Mátricula: ");
                String registration = sc.nextLine();
                logicPrint.printOne(registration);
            }
            case 2 -> {
                System.out.println("--- FOLHA DE PAGAMENTO DE TODOS OS COLABORADORES ---");
                logicPrint.printAll();
            }
            case 3 -> {
                System.out.println("--- FOLHA DE PAGAMENTO DE TODOS OS COLABORADORES DE PRODUÇÃO ---");
                logicPrint.printByFilter(ProductionEmployee.class);
            }
            case 4 -> {
                System.out.println("--- FOLHA DE PAGAMENTO DE TODOS OS COLABORADORES COMISSIONADOS ---");
                logicPrint.printByFilter(CommissionedEmployee.class);
            }
            case 5 -> {
                System.out.println("--- FOLHA DE PAGAMENTO DE TODOS OS COLABORADORES PADRÃO ---");
                logicPrint.printByFilter(StandardEmployee.class);
            }

            default ->
                System.out.println("Opção inválida. Tente novamente.");
        }

    }

    public void printOption() {
        boolean stayOnMenu = true;
        while (stayOnMenu) {
            int desiredOption = welcome();
            if (desiredOption == 6) {
                stayOnMenu = false;
                System.out.println("Retornando ao menu principal...");
            } else {
                boolean validResponse = false;
                availableOptions(desiredOption);
                while (!validResponse){
                    System.out.println("Deseja realizar uma nova consulta? (Y/N)");
                    String resp = sc.nextLine().trim();
                    if(resp.equalsIgnoreCase("Y")){
                        validResponse = true;
                    } else if (resp.equalsIgnoreCase("N")){
                        System.out.print("\nRetornando ao menu principal...\n\n");
                        validResponse = true;
                        stayOnMenu = false;
                    } else {
                        System.out.println("Opção Inválida! Por favor, digite apenas 'Y' para SIM ou 'N' para NÃO.");
                    }
                }
            }
        }
    }
}