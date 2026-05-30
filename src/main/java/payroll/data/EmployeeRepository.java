package payroll.data;

import payroll.model.employee.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public static List<Employee> employees = new ArrayList<Employee>();

    public static void registerEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Não é possível cadastrar um funcionário nulo.");
        }

        if (isRegistered(employee.getRegistration())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com a matrícula: " + employee.getRegistration());
        }

        employees.add(employee);
    }

    public static List<Employee> getEmployeeList() {
        return employees;
    }

    public static boolean isRegistered(String registration) {
        for (Employee emp : employees) {
            if (emp.getRegistration().equalsIgnoreCase(registration)) {
                return true;
            }
        }
        return false;
    }

}
