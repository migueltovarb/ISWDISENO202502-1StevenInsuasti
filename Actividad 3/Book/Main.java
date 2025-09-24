package book;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Autor autor = null;
        Libro libro = null;
        int opcion = 0;

        while (opcion != 5) {
            System.out.println("\n===== MENÚ LIBRERÍA =====");
            System.out.println("1. Crear autor");
            System.out.println("2. Crear libro");
            System.out.println("3. Ver información del libro");
            System.out.println("4. Actualizar libro");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del autor: ");
                    String nombre = sc.nextLine();
                    System.out.print("Email del autor: ");
                    String email = sc.nextLine();
                    System.out.print("Género del autor (M/F): ");
                    char genero = sc.next().charAt(0);

                    autor = new Autor(nombre, email, genero);
                    System.out.println("Autor creado con éxito.");
                    break;

                case 2:
                    if (autor == null) {
                        System.out.println("Primero debes crear un autor.");
                        break;
                    }
                    System.out.print("Título del libro: ");
                    String titulo = sc.nextLine();
                    System.out.print("Precio del libro: ");
                    double precio = sc.nextDouble();
                    System.out.print("Cantidad disponible: ");
                    int cantidad = sc.nextInt();

                    libro = new Libro(titulo, autor, precio, cantidad);
                    System.out.println("Libro creado con éxito.");
                    break;

                case 3:
                    if (libro == null) {
                        System.out.println("Primero debes crear un libro.");
                    } else {
                        System.out.println("\n--- Información del libro ---");
                        System.out.println(libro);
                    }
                    break;

                case 4:
                    if (libro == null) {
                        System.out.println("Primero debes crear un libro.");
                    } else {
                        System.out.println("1. Cambiar precio");
                        System.out.println("2. Cambiar cantidad");
                        System.out.print("Elige opción: ");
                        int subOpcion = sc.nextInt();

                        if (subOpcion == 1) {
                            System.out.print("Nuevo precio: ");
                            double nuevoPrecio = sc.nextDouble();
                            libro.setPrecio(nuevoPrecio);
                            System.out.println("Precio actualizado.");
                        } else if (subOpcion == 2) {
                            System.out.print("Nueva cantidad: ");
                            int nuevaCantidad = sc.nextInt();
                            libro.setCantidad(nuevaCantidad);
                            System.out.println("Cantidad actualizada.");
                        } else {
                            System.out.println("Opción inválida.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }
}

