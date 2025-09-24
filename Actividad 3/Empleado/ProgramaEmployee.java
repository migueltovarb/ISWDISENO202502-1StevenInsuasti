package empleado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramaEmployee {
    private static final List<Employee> empleados = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            String linea = scanner.nextLine().trim();
            if (linea.isEmpty()) continue;
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Intente nuevamente.");
                continue;
            }

            switch (opcion) {
                case 1 -> agregarEmpleado();
                case 2 -> listarEmpleados();
                case 3 -> mostrarSalarioAnualEmpleado();
                case 4 -> aumentarSalarioPorcentaje();
                case 5 -> cambiarSalario();
                case 0 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        } while (true);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ EMPLEADOS =====");
        System.out.println("1. Añadir nuevo empleado");
        System.out.println("2. Ver empleados (salario mensual y anual)");
        System.out.println("3. Ver salario anual de un empleado (por ID)");
        System.out.println("4. Aumentar salario (porcentaje) a un empleado");
        System.out.println("5. Cambiar salario (nuevo salario)");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void agregarEmpleado() {
        try {
            System.out.print("Ingrese ID numérico del empleado: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (findEmployeeById(id) != null) {
                System.out.println("Ya existe un empleado con ese ID.");
                return;
            }

            System.out.print("Ingrese nombre del empleado: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Ingrese apellido del empleado: ");
            String apellido = scanner.nextLine().trim();

            System.out.print("Ingrese salario mensual (ej. 1500.50): ");
            double salario = Double.parseDouble(scanner.nextLine().trim());

            empleados.add(new Employee(id, nombre, apellido, salario));
            System.out.println("Empleado agregado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Operación cancelada.");
        }
    }

    private static void listarEmpleados() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        System.out.println("\nLista de empleados:");
        for (Employee e : empleados) {
            System.out.printf("%s | Salario anual: %.2f%n",
                    e.toString(), e.getAnnualSalary());
        }
    }

    private static void mostrarSalarioAnualEmpleado() {
        try {
            System.out.print("Ingrese ID del empleado: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Employee e = findEmployeeById(id);
            if (e == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }
            System.out.printf("Empleado: %s %s | Salario mensual: %.2f | Salario anual: %.2f%n",
                    e.getName(), e.getLastName(), e.getSalary(), e.getAnnualSalary());
        } catch (NumberFormatException ex) {
            System.out.println("ID inválido.");
        }
    }

    private static void aumentarSalarioPorcentaje() {
        try {
            System.out.print("Ingrese ID del empleado: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Employee e = findEmployeeById(id);
            if (e == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }
            System.out.print("Ingrese porcentaje de aumento (ej. 10 para 10%): ");
            double percent = Double.parseDouble(scanner.nextLine().trim());
            if (percent < 0) {
                System.out.println("El porcentaje debe ser no negativo.");
                return;
            }
            double nuevoSalario = e.raiseSalary(percent);
            System.out.printf("Aumento aplicado. Nuevo salario mensual de %s %s: %.2f%n",
                    e.getName(), e.getLastName(), nuevoSalario);
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void cambiarSalario() {
        try {
            System.out.print("Ingrese ID del empleado: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Employee e = findEmployeeById(id);
            if (e == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }
            System.out.printf("Salario actual de %s %s: %.2f%n", e.getName(), e.getLastName(), e.getSalary());
            System.out.print("Ingrese nuevo salario mensual: ");
            double nuevo = Double.parseDouble(scanner.nextLine().trim());
            e.setSalary(nuevo);
            System.out.printf("Salario actualizado. Nuevo salario mensual de %s %s: %.2f%n", 
                    e.getName(), e.getLastName(), e.getSalary());
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida.");
        }
    }

    private static Employee findEmployeeById(int id) {
        for (Employee e : empleados) {
            if (e.getId() == id) return e;
        }
        return null;
    }
}

