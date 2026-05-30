package payroll.model.commissionedEmployee;

import payroll.model.employee.Employee;

public class CommissionedEmployee extends Employee {

    private double vlrSales = 0;
    private int percentageCommission = 0;


    public CommissionedEmployee(
            String name,
            String surname,
            String registration,
            double vlrSales,
            int percentageCommission) {

        super(name, surname, registration);

        if (vlrSales < 0){
            throw new IllegalArgumentException("O valor das vendas não pode ser negativo.");
        }
        if (percentageCommission < 0){
            throw new IllegalArgumentException("O valor da comissão das vendas não pode ser negativo.");
        }
        if (percentageCommission > 20){
            throw new IllegalArgumentException("O valor da comissão das vendas não pode ultrapassar 20%");
        }

        this.vlrSales = vlrSales;
        this.percentageCommission = percentageCommission;

    }

    @Override
    public double getSalary() {
        double commissionBonus = vlrSales * (double) percentageCommission / 100;
        return BASE_SALARY + commissionBonus;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n| Salário Fixo: " + String.format("%.2f", BASE_SALARY) +
                "\n| Comissão: " + String.format("%.2f", vlrSales * (double) percentageCommission / 100) +
                "\n| Salário Final: " + String.format("%.2f", getSalary());
    }
}
