package payroll.model.StandardEmployee;

import payroll.data.DTOEmployeesData;
import payroll.data.EmployeeRepository;
import payroll.enums.EmployeeType;
import payroll.model.employee.EmployeeConsole;

public class InitStandardEmployee {

    public static void registerStandEmployee() {
        String registration = EmployeeConsole.registrationCode();

        if (EmployeeRepository.isRegistered(registration)) {
            System.out.println("\n[ERRO] Esta matrícula já está cadastrada!");
            return;
        }
        String name = EmployeeConsole.name();
        String surname = EmployeeConsole.surname();
        int extras = LogicStandardEmployee.extras();

        DTOEmployeesData dto = new DTOEmployeesData(
                name, surname, registration,
                EmployeeType.STANDARD,
                0, 0,0, 0, extras
        );

        EmployeeRepository.registerEmployee(dto.toEntity());
        System.out.println("Colaborador(a) cadastrado.");
    }
}
