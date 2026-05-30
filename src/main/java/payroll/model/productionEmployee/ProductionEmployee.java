package payroll.model.productionEmployee;

import payroll.model.employee.Employee;

public class ProductionEmployee extends Employee {

    private int qtdPieces = 0;
    private double piecesValue = 0;

    public ProductionEmployee(
            String name,
            String surname,
            String registration,
            int qtdPieces,
            double piecesValue) {

        super(name, surname, registration);

        if(qtdPieces < 0) {
            throw new IllegalArgumentException("O número de peças não pode ser negativo.");
        }
        if(piecesValue < 0) {
            throw new IllegalArgumentException("O valor das peças não pode ser negativo.");
        }

        this.qtdPieces = qtdPieces;
        this.piecesValue = piecesValue;
    }

    @Override
    public double getSalary(){
        double salaryBonus = (double) qtdPieces * piecesValue;
        return BASE_SALARY + salaryBonus;
    }

    @Override
    public String toString(){
        return super.toString() +
                "\n| Salário Fixo: " + String.format("%.2f", BASE_SALARY) +
                "\n| Produtividade: " + String.format("%.2f", piecesValue * qtdPieces) +
                "\n| Salário Final: R$ " + String.format("%.2f", getSalary());
    }

}
