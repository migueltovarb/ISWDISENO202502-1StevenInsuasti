package factura;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramaInvoiceItem {

    private static final List<Invoice> facturas = new ArrayList<>();
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
                case 1 -> agregarFactura();
                case 2 -> listarFacturas();
                case 3 -> modificarCantidad();
                case 4 -> modificarPrecioUnitario();
                case 0 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        } while (true);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ FACTURAS =====");
        System.out.println("1. Crear nueva factura");
        System.out.println("2. Ver facturas");
        System.out.println("3. Modificar cantidad");
        System.out.println("4. Modificar valor unitario");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void agregarFactura() {
        System.out.print("Ingrese ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Ingrese descripción: ");
        String desc = scanner.nextLine().trim();

        System.out.print("Ingrese cantidad: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Ingrese valor unitario: ");
        double precio = Double.parseDouble(scanner.nextLine().trim());

        facturas.add(new Invoice(id, desc, qty, precio));
        System.out.println("Factura creada con exito.");
    }

    private static void listarFacturas() {
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
            return;
        }
        for (Invoice f : facturas) {
            System.out.println(f);
        }
    }

    private static void modificarCantidad() {
        System.out.print("Ingrese ID de la factura: ");
        String id = scanner.nextLine().trim();
        Invoice f = findFacturaById(id);

        if (f == null) {
            System.out.println("Factura no encontrada.");
            return;
        }

        System.out.print("Ingrese nueva cantidad: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        f.setQuantity(qty);
        System.out.println("Cantidad actualizada.");
    }

    private static void modificarPrecioUnitario() {
        System.out.print("Ingrese ID de la factura: ");
        String id = scanner.nextLine().trim();
        Invoice f = findFacturaById(id);

        if (f == null) {
            System.out.println("Factura no encontrada.");
            return;
        }

        System.out.print("Ingrese nuevo precio unitario: ");
        double precio = Double.parseDouble(scanner.nextLine().trim());
        f.setUnitPrice(precio);
        System.out.println("Precio actualizado.");
    }

    private static Invoice findFacturaById(String id) {
        for (Invoice f : facturas) {
            if (f.getId().equals(id)) return f;
        }
        return null;
    }
}
