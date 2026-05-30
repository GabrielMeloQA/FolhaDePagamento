package payroll.model.employee;

import static payroll.model.utils.Utils.sc;

public class EmployeeConsole {

    public static String registrationCode() {
        System.out.print("\nQual a matrícula do colaborador(a)? \n");
        String code;
        boolean validCode = false;
        do {
            code = sc.nextLine();
            if (code.isBlank()) {
                System.out.print("\nPor favor, insira a matrícula do colaborador(a) \n");
            } else {
                validCode = true;
            }
        } while (!validCode);
        return code;
    }

    public static String name() {
        System.out.print("\nQual o nome do colaborador(a)? \n");
        String name;
        boolean validName = false;
        do {
            name = sc.nextLine();
            if (name.isBlank()) {
                System.out.print("\nPor favor, insira o nome do colaborador(a) \n");
            } else {
                validName = true;
            }
        } while (!validName);
        return name;
    }

    public static String surname() {
        System.out.print("\nQual o sobrenome do colaborador(a)? \n");
        String surname;
        boolean validSurname = false;
        do {
            surname = sc.nextLine();
            if (surname.isBlank()) {
                System.out.print("\nPor favor, insira o sobrenome do colaborador(a) \n");
            } else {
                validSurname = true;
            }
        } while (!validSurname);
        return surname;
    }
}
