package payroll.model.employee;

public abstract class Employee {
    protected String name;
    protected String surname;
    protected String registration;

    public static final double BASE_SALARY = 2000.00;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRegistration() {
        return registration;
    }

    public Employee(String name, String surname, String registration) {
        this.name = name;
        this.surname = surname;
        this.registration = registration;
    }

    public abstract double getSalary();

    @Override
    public String toString() {
        return "\n| Matrícula: " + registration +
                "\n| Nome: " + name + " " + surname;
    }
}