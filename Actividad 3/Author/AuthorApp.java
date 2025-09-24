package gestion.autores;


import java.util.ArrayList;
import java.util.Scanner;

public class AuthorApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Author> authors = new ArrayList<>();
        int option;

        do {
            System.out.println("\n--- MENÚ AUTORES ---");
            System.out.println("1. Agregar nuevo autor");
            System.out.println("2. Listar autores");
            System.out.println("3. Modificar email de un autor");
            System.out.println("4. Salir");
            System.out.print("Elija una opción: ");
            option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("Ingrese el nombre del autor: ");
                    String name = sc.nextLine();

                    System.out.print("Ingrese el email del autor: ");
                    String email = sc.nextLine();

                    char gender;
                    do {
                        System.out.print("Ingrese el genero (m/f): ");
                        gender = sc.next().toLowerCase().charAt(0);
                    } while (gender != 'm' && gender != 'f');

                    authors.add(new Author(name, email, gender));
                    System.out.println("Autor agregado exitosamente.");
                    break;

                case 2:
                    if (authors.isEmpty()) {
                        System.out.println("No hay autores registrados.");
                    } else {
                        System.out.println("\n--- Lista de Autores ---");
                        for (int i = 0; i < authors.size(); i++) {
                            System.out.println((i + 1) + ". " + authors.get(i));
                        }
                    }
                    break;

                case 3:
                    if (authors.isEmpty()) {
                        System.out.println("No hay autores para modificar.");
                    } else {
                        System.out.print("Ingrese el numero del autor a modificar: ");
                        int index = sc.nextInt() - 1;
                        sc.nextLine();

                        if (index >= 0 && index < authors.size()) {
                            System.out.print("Ingrese el nuevo email: ");
                            String newEmail = sc.nextLine();
                            authors.get(index).setEmail(newEmail);
                            System.out.println("Email actualizado con exito.");
                        } else {
                            System.out.println("Numero de autor invalido.");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción invalida.");
            }
        } while (option != 4);

        sc.close();
    }
}

