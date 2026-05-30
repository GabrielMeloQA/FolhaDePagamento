package payroll.model.productionEmployee;

import payroll.data.DTOEmployeesData;
import payroll.data.EmployeeRepository;
import payroll.enums.EmployeeType;
import payroll.model.employee.EmployeeConsole;

import static payroll.model.productionEmployee.LogicProductionEmployee.piecesValue;
import static payroll.model.productionEmployee.LogicProductionEmployee.qtPieces;

public class InitProductionDataEmployee {

    public static void registerProdEmployee() {
        String registration = EmployeeConsole.registrationCode();

        if (EmployeeRepository.isRegistered(registration)) {
            System.out.println("\n[ERRO] Esta matrícula já está cadastrada!");
            return;
        }

        String name = EmployeeConsole.name();
        String surname = EmployeeConsole.surname();
        int qt = qtPieces();
        double value = piecesValue();
        DTOEmployeesData dto = new DTOEmployeesData(
                name, surname, registration,
                EmployeeType.PRODUCTION,
                qt, 0, 0, value, 0
        );

        EmployeeRepository.registerEmployee(dto.toEntity());
        System.out.println("Colaborador(a) cadastrado.");
    }

}
