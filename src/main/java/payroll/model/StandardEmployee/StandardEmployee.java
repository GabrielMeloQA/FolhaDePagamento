package payroll.model.StandardEmployee;

import payroll.model.employee.Employee;

public class StandardEmployee extends Employee {

    private double extras = 0;

    public StandardEmployee(
            String name,
            String surName,
            String registration,
            double extras ) {

        super(name, surName, registration);

        if (extras < 0){
            throw new IllegalArgumentException("O valor extra não pode ser negativo.");
        }

        this.extras = extras;
    }

    @Override
    public double getSalary() {
        return BASE_SALARY;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n| Salário Fixo: " + String.format("%.2f", BASE_SALARY) +
                "\n| Extras: " + String.format("%.2f", extras) +
                "\n| Salário Final: " + String.format("%.2f", getSalary() + extras);
    }
}