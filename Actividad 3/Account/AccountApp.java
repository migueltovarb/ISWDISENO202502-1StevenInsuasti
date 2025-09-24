package cuenta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountApp {
    private static final List<Account> cuentas = new ArrayList<>();
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
                System.out.println("Opción no válida.");
                continue;
            }

            switch (opcion) {
                case 1 -> crearCuenta();
                case 2 -> listarCuentas();
                case 3 -> depositar();
                case 4 -> retirar();
                case 5 -> transferir();
                case 0 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        } while (true);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ CUENTAS =====");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Ver cuentas");
        System.out.println("3. Depositar dinero");
        System.out.println("4. Retirar dinero");
        System.out.println("5. Transferir dinero");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void crearCuenta() {
        System.out.print("Ingrese ID de la cuenta: ");
        String id = scanner.nextLine().trim();

        if (findCuentaById(id) != null) {
            System.out.println("Ya existe una cuenta con ese ID.");
            return;
        }

        System.out.print("Ingrese nombre del titular: ");
        String name = scanner.nextLine().trim();

        int saldo = 0;
        try {
            System.out.print("Ingrese saldo inicial (0 si no tiene): ");
            saldo = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Saldo inválido, se asignará 0.");
        }

        cuentas.add(new Account(id, name, saldo));
        System.out.println("Cuenta creada con éxito.");
    }

    private static void listarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }
        System.out.println("\nLista de cuentas:");
        for (Account c : cuentas) {
            System.out.println(c);
        }
    }

    private static void depositar() {
        System.out.print("Ingrese ID de la cuenta: ");
        String id = scanner.nextLine().trim();
        Account c = findCuentaById(id);

        if (c == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }

        try {
            System.out.print("Ingrese monto a depositar: ");
            int monto = Integer.parseInt(scanner.nextLine().trim());
            c.credit(monto);
            System.out.println("Depósito realizado. Nuevo saldo: " + c.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        }
    }

    private static void retirar() {
        System.out.print("Ingrese ID de la cuenta: ");
        String id = scanner.nextLine().trim();
        Account c = findCuentaById(id);

        if (c == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }

        try {
            System.out.print("Ingrese monto a retirar: ");
            int monto = Integer.parseInt(scanner.nextLine().trim());
            c.debit(monto);
            System.out.println("Nuevo saldo: " + c.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        }
    }

    private static void transferir() {
        System.out.print("Ingrese ID de la cuenta origen: ");
        String idOrigen = scanner.nextLine().trim();
        Account origen = findCuentaById(idOrigen);

        if (origen == null) {
            System.out.println("Cuenta origen no encontrada.");
            return;
        }

        System.out.print("Ingrese ID de la cuenta destino: ");
        String idDestino = scanner.nextLine().trim();
        Account destino = findCuentaById(idDestino);

        if (destino == null) {
            System.out.println("Cuenta destino no encontrada.");
            return;
        }

        try {
            System.out.print("Ingrese monto a transferir: ");
            int monto = Integer.parseInt(scanner.nextLine().trim());

            origen.transferTo(destino, monto);
            System.out.println("Transferencia realizada.");
            System.out.println("Saldo cuenta origen: " + origen.getBalance());
            System.out.println("Saldo cuenta destino: " + destino.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
        }
    }

    private static Account findCuentaById(String id) {
        for (Account c : cuentas) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }
}

