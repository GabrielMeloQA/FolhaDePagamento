package payroll.model.printPayroll;

import payroll.data.EmployeeRepository;
import payroll.model.employee.Employee;

public class LogicPrintPayroll {

    public void printAll() {
        if(emptyArray()){
            System.out.println("Nenhum colaborador cadastrado.");
            return;
        }

        for (Employee emp : EmployeeRepository.getEmployeeList()){
            System.out.println(emp);
            System.out.println("-----------------------");
        }
    }

    public void printOne(String registration) {
        for (Employee emp : EmployeeRepository.getEmployeeList()) {
            if (emp.getRegistration().equalsIgnoreCase(registration)) {
                System.out.println(emp);
                System.out.println("-----------------------");
                return;
            }
        }
        System.out.println("Colaborador não encontrado.");
    }

    public void printByFilter(Class<? extends Employee> type) {
        boolean found = false;
        for (Employee emp : EmployeeRepository.getEmployeeList()) {
            if (type.isInstance(emp)) {
                System.out.println(emp);
                System.out.println("-----------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nenhum colaborador desse tipo encontrado.");
        }
    }

    public boolean emptyArray() {
        return EmployeeRepository.getEmployeeList().isEmpty();
    }

}
