package paqueteVeterinaria;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        int opcion;
        do {
            System.out.println("\n--- MENÚ VETERINARIA ---");
            System.out.println("1. Registrar dueño");
            System.out.println("2. Registrar mascota");
            System.out.println("3. Registrar control veterinario");
            System.out.println("4. Consultar historial de mascota");
            System.out.println("5. Generar resumen");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre completo: ");
                    String nombre = sc.nextLine();
                    System.out.print("Documento: ");
                    String doc = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String tel = sc.nextLine();
                    sistema.registrarDueño(new Dueño(nombre, doc, tel));
                    break;

                case 2:
                    System.out.print("Documento del dueño: ");
                    String docD = sc.nextLine();
                    System.out.print("Nombre mascota: ");
                    String nomMascota = sc.nextLine();
                    System.out.print("Especie: ");
                    String especie = sc.nextLine();
                    System.out.print("Edad: ");
                    String edad = sc.nextLine();
                    Dueño dueño = sistema.buscarDueño(docD);
                    if (dueño != null) {
                        Mascota m = new Mascota(nomMascota, especie, edad, dueño);
                        sistema.registrarMascota(docD, m);
                    } else {
                        System.out.println("Dueño no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Documento del dueño: ");
                    String docDueño = sc.nextLine();
                    System.out.print("Nombre de la mascota: ");
                    String nombreMascota = sc.nextLine();
                    System.out.print("Fecha (dd-MM-yyyy): ");
                    String fechaStr = sc.nextLine();
                    System.out.println("Tipo de control (VACUNA, CHEQUEO, DESPARASITACION, CIRUGIA, OTRO): ");
                    String tipoStr = sc.nextLine().toUpperCase();
                    System.out.print("Observaciones: ");
                    String obs = sc.nextLine();

                    try {
                        Date fecha = sdf.parse(fechaStr);
                        TipoControl tipo = TipoControl.valueOf(tipoStr);
                        ControlVeterinario c = new ControlVeterinario(fecha, tipo, obs);
                        sistema.registrarControl(docDueño, nombreMascota, c);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Documento del dueño: ");
                    String docHist = sc.nextLine();
                    System.out.print("Nombre de la mascota: ");
                    String nomHist = sc.nextLine();
                    Dueño d = sistema.buscarDueño(docHist);
                    if (d != null) {
                        Mascota m = d.buscarMascota(nomHist);
                        if (m != null) {
                            System.out.println("\nHistorial de " + m.getNombre() + ":");
                            for (ControlVeterinario ctrl : m.consultarHistorial()) {
                                System.out.println(ctrl.obtenerDetalle());
                            }
                        } else System.out.println("Mascota no encontrada.");
                    } else System.out.println("Dueño no encontrado.");
                    break;

                case 5:
                    System.out.print("Documento del dueño: ");
                    String docRep = sc.nextLine();
                    System.out.print("Nombre de la mascota: ");
                    String nomRep = sc.nextLine();
                    System.out.println(sistema.generarReporteMascota(docRep, nomRep));
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}

