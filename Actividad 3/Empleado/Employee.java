package empleado;

public class Employee {
    private int id;
    private String name;
    private String lastName;
    private double salary; // salario mensual

    public Employee(int id, String name, String lastName, double salary) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.salary = salary;
    }

    // getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }

    // setter
    public void setSalary(double salary) { this.salary = salary; }

    // salario anual
    public double getAnnualSalary() {
        return salary * 12;
    }

    // aumentar salario en porcentaje (ej. 10 => 10%)
    public double raiseSalary(double percent) {
        if (percent < 0) return salary; 
        salary += salary * percent / 100.0;
        return salary;
    }

    @Override
    public String toString() {
        return String.format("Empleado con id:%d, de nombre %s %s, tiene un salario mensual de:%.2f", 
                id, name, lastName, salary);
    }
}



