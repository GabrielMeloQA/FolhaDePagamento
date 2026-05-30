package payroll.model.commissionedEmployee;

import payroll.data.DTOEmployeesData;
import payroll.data.EmployeeRepository;
import payroll.enums.EmployeeType;
import payroll.model.employee.EmployeeConsole;

import static payroll.model.commissionedEmployee.LogicCommissionedEmployee.validatePercentageCommission;
import static payroll.model.commissionedEmployee.LogicCommissionedEmployee.validateVlrSales;

public class InitCommissionedEmployee {

    public static void registerCommissionedEmployee() {
        String registration = EmployeeConsole.registrationCode();

        if (EmployeeRepository.isRegistered(registration)) {
            System.out.println("\n[ERRO] Esta matrícula já está cadastrada!");
            return;
        }

        String name = EmployeeConsole.name();
        String surname = EmployeeConsole.surname();
        double vlrSales = validateVlrSales();
        int percent = validatePercentageCommission();

        DTOEmployeesData dto = new DTOEmployeesData(
                name, surname, registration,
                EmployeeType.COMMISSIONED,
                0, percent, vlrSales, 0, 0
        );

        EmployeeRepository.registerEmployee(dto.toEntity());
        System.out.print("\nColaborador Comissionado cadastrado com sucesso!\n");
    }
}