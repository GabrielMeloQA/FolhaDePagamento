package payroll.data;

import payroll.enums.EmployeeType;
import payroll.model.standardEmployee.StandardEmployee;
import payroll.model.commissionedEmployee.CommissionedEmployee;
import payroll.model.employee.Employee;
import payroll.model.productionEmployee.ProductionEmployee;

public class DTOEmployeesData {

    String name;
    String surname;
    String registration;
    EmployeeType typeEmployee;
    int qtdPieces;
    int percentageCommission;
    double vlrSales = 0;
    double piecesValue = 0;
    double extras = 0;

    public DTOEmployeesData(
            String name,
            String surname,
            String registration,
            EmployeeType typeEmployee, // STANDARD / COMMISSIONED / PRODUCTION
            int qtdPieces, //production
            int percentageCommission, //commissioned
            double vlrSales, //commissioned
            double piecesValue, //production
            double extras
    ) {
        this.name = name;
        this.surname = surname;
        this.registration = registration;
        this.typeEmployee = typeEmployee;
        this.qtdPieces = qtdPieces;
        this.piecesValue = piecesValue;
        this.percentageCommission = percentageCommission;
        this.vlrSales = vlrSales;
        this.extras = extras;
    }

    public Employee toEntity() {
        if (this.typeEmployee == null) {
            throw new IllegalArgumentException("O tipo de funcionário é obrigatório e deve ser preenchido.");
        }

        return switch (typeEmployee) {
            case STANDARD -> new StandardEmployee(name, surname, registration, extras);
            case COMMISSIONED -> new CommissionedEmployee(name, surname, registration, vlrSales, percentageCommission);
            case PRODUCTION -> new ProductionEmployee(name, surname, registration, qtdPieces, piecesValue);
        };

    }


}
