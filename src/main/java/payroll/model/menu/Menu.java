package payroll.model.menu;

import payroll.model.StandardEmployee.InitStandardEmployee;
import payroll.model.commissionedEmployee.InitCommissionedEmployee;
import payroll.model.printPayroll.InitPrintPayroll;
import payroll.model.productionEmployee.InitProductionDataEmployee;

import static payroll.model.utils.Utils.sc;

public class Menu {

    private static void showMenu() {
        System.out.print("""
                --------MENU--------
                0 - Sair da aplicação
                1 - Cadastrar colaborador padrão
                2 - Cadastrar colaborador comissionado
                3 - Cadastrar colaborador produção
                4 - Imprimir folha de pagamento
                """);
    }

    private static void showMenuByType() {
        System.out.print("""
                --------OPÇÕES--------
                1 - Cadastrar colaborador padrão
                2 - Cadastrar colaborador comissionado
                3 - Cadastrar colaborador produção
                """);
    }

    private static void chosenCategory(int choice) {
        switch (choice) {
            case 0 -> {
                System.out.println("Finalizando aplicação.");
                System.exit(0);
            }
            case 1, 2, 3 -> callMenuByType(choice);
            case 4 -> new InitPrintPayroll().printOption();
            default -> System.out.println("Erro inesperado: Opção fora do escopo permitido.");
        }
    }

    public static void callMenu() {
        boolean exit = false;
        do {
            showMenu();
            System.out.print("\nEscolha a opção desejada: \n");

            if (sc.hasNextInt()){
                int resp = sc.nextInt();
                sc.nextLine();
                if (resp >= 0 && resp <= 4){
                    chosenCategory(resp);
                    if (resp == 0) exit = true;
                } else {
                    System.out.println("Entrada inválida! Por favor, escolha um número de 0 a 4.");
                }
            } else {
                System.out.println("Entrada inválida! Por favor, digite somente números entre 0 e 4.");
                sc.nextLine();
            }
        } while (!exit);
    }

    public static void callMenuByType(int initialType){
        boolean continueRegistering;
        int type = initialType;

        do {
            switch (type){
                case 1 -> InitStandardEmployee.registerStandEmployee();
                case 2 -> InitCommissionedEmployee.registerCommissionedEmployee();
                case 3 -> InitProductionDataEmployee.registerProdEmployee();
                default -> System.out.println("Erro inesperado: Opção fora do escopo permitido.\n");
            }

            continueRegistering = continueOrReturn();

            if (continueRegistering) {
                Menu.showMenuByType();
                System.out.print("\nQual o tipo do colaborador que deseja cadastrar?\n");
                if (sc.hasNextInt()) {
                    type = sc.nextInt();
                    sc.nextLine();
                } else {
                    System.out.println("Entrada inválida! Retornando ao menu principal.");
                    sc.nextLine();
                    continueRegistering = false;
                }
            }

        } while (continueRegistering);
    }

    private static boolean continueOrReturn() {
        while (true) {
            System.out.print("\nDeseja realizar nova operação de cadastramento de um outro colaborador? (Y / N)\n");
            String choice = sc.nextLine().trim();

            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (choice.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Não entendi, por favor, insira Y ou N.");
            }
        }
    }

    public static void returnToMenu() {
        boolean isValid = false;

        System.out.print("\nDeseja voltar ao menu? (Y / N)\n");
        String newOperation = sc.nextLine();

        do {
            if (newOperation.equalsIgnoreCase("Y")) {
                isValid = true;
            } else if (newOperation.equalsIgnoreCase("N")) {
                System.out.println("Finalizando aplicação.");
                System.exit(0);
            } else {
                System.out.println("Essa opção não é válida. Por favor, digite uma nova opção entre Y / N.");
                newOperation = sc.nextLine();
            }
        } while (!isValid);
    }
}