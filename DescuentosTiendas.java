package practica.java;

import java.util.Scanner;

public class DescuentosTienda {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Constantes de descuento por tipo de producto
        final double DESC_ROPA = 0.10;       // 10%
        final double DESC_TECNO = 0.05;      // 5%
        final double DESC_ALIM = 0.02;       // 2%
        final double DESC_ADICIONAL = 0.05;  // 5% adicional si total > 100000

        // 1. Ingresar número de productos (mínimo 1)
        System.out.print("Ingrese el número de productos (mínimo 1): ");
        int n = sc.nextInt();
        while (n < 1) {
            System.out.print("Número inválido. Ingrese nuevamente: ");
            n = sc.nextInt();
        }

        // Vectores para almacenar datos de productos
        int[] tipos = new int[n];
        String[] nombres = new String[n];
        double[] precios = new double[n];

        // 2. Ingreso de datos de cada producto
        sc.nextLine(); // limpiar buffer
        for (int i = 0; i < n; i++) {
            System.out.println("\nProducto " + (i + 1) + ":");

            System.out.print("Tipo (1: ropa, 2: tecnología, 3: alimentos): ");
            int tipo = sc.nextInt();
            while (tipo < 1 || tipo > 3) {
                System.out.print("Tipo inválido. Ingrese 1, 2 o 3: ");
                tipo = sc.nextInt();
            }
            tipos[i] = tipo;

            sc.nextLine(); // limpiar buffer antes de leer el nombre
            System.out.print("Nombre: ");
            nombres[i] = sc.nextLine();

            System.out.print("Precio: ");
            precios[i] = sc.nextDouble();
            sc.nextLine(); // limpiar buffer
        }

        // 3. Calcular total y aplicar descuentos
        double totalInicial = 0;
        double totalConDescuento = 0;

        for (int i = 0; i < n; i++) {
            totalInicial += precios[i];

            double descuento = 0;
            if (tipos[i] == 1) descuento = DESC_ROPA;
            else if (tipos[i] == 2) descuento = DESC_TECNO;
            else if (tipos[i] == 3) descuento = DESC_ALIM;

            double precioFinal = precios[i] - (precios[i] * descuento);
            totalConDescuento += precioFinal;
        }

        // 4. Descuento adicional si supera 100000
        if (totalInicial > 100000) {
            totalConDescuento -= totalConDescuento * DESC_ADICIONAL;
        }

        // 5. Mostrar resultados
        System.out.println("\n--- RESUMEN DE COMPRA ---");
        System.out.printf("Total inicial: $%,.2f%n", totalInicial);
        System.out.printf("Total con descuento: $%,.2f%n", totalConDescuento);
        System.out.printf("Ahorro: $%,.2f%n", (totalInicial - totalConDescuento));

        sc.close();
    }
}
		
				
		
               	

