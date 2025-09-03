package asistencia.estudiantes;

import java.util.Scanner;

public class ControlAsistenciaEstudiantes {

	public static void main(String[] args) {
        final int DIAS_SEMANA     = 5;
        final int NUM_ESTUDIANTES = 4;

        Scanner sc = new Scanner(System.in);
        int[][] asistencia = new int[NUM_ESTUDIANTES][DIAS_SEMANA];
        
        System.out.println("Registro de asistencia (1 = asistio, 0 = no asistio):");
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            System.out.println("Estudiante " + (i + 1) + ":");
            for (int j = 0; j < DIAS_SEMANA; j++) {
                System.out.print("  Dia " + (j + 1) + ": ");
                int valor = sc.nextInt();
                while (valor != 0 && valor != 1) {
                    System.out.print("    Valor invalido. Ingrese 0 o 1: ");
                    valor = sc.nextInt();
                }
                asistencia[i][j] = valor;
            }
        }

        // Menu 
        int opcion = 0;
        while (opcion !=5) {
            System.out.println("\n--- MENU DE OPCIONES ---");
            System.out.println("1. Ver asistencia por estudiante");
            System.out.println("2. Ver resumen general");
            System.out.println("3. Ver estudiantes con asistencia completa");
            System.out.println("4. Ver dias con mayor asistencia");	            
            System.out.println("5. Volver a registrar asistencia");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    // Asistencias por estudiante
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        int total = 0;
                        for (int j = 0; j < DIAS_SEMANA; j++) {
                            total += asistencia[i][j];
                        }
                        System.out.println("Estudiante " + (i + 1) + ": " + total + " asistencias");
                    }
                    break;

                case 2:
                    // Resumen general
                    int totalGeneral = 0;
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        for (int j = 0; j < DIAS_SEMANA; j++) {
                            totalGeneral += asistencia[i][j];
                        }
                    }
                    System.out.println("Total de asistencias en la semana: " + totalGeneral);
                    break;

                case 3:
                    // Estudiantes con asistencia completa
                    int completos = 0;
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        int suma = 0;
                        for (int j = 0; j < DIAS_SEMANA; j++) {
                            suma += asistencia[i][j];
                        }
                        if (suma == DIAS_SEMANA) {
                            if (completos == 0) {
                                System.out.println("Estudiantes con asistencia completa:");
                            }
                            System.out.println("  Estudiante " + (i + 1));
                            completos++;
                        }
                    }
                    if (completos == 0) {
                        System.out.println("Ningun estudiante tiene asistencia completa");
                    }
                    break;

                case 4:
                    // Dias con mayor asistencia
                    int[] asistenciaPorDia = new int[DIAS_SEMANA];
                    for (int j = 0; j < DIAS_SEMANA; j++) {
                        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                            asistenciaPorDia[j] += asistencia[i][j];
                        }
                    }
                    int maxAsistencia = 0;
                    for (int j = 0; j < DIAS_SEMANA; j++) {
                        if (asistenciaPorDia[j] > maxAsistencia) {
                            maxAsistencia = asistenciaPorDia[j];
                        }
                    }
                    System.out.println("Dias con mayor asistencia (" + maxAsistencia + " estudiantes):");
                    for (int j = 0; j < DIAS_SEMANA; j++) {
                        if (asistenciaPorDia[j] == maxAsistencia) {
                            System.out.println("  Dia " + (j + 1));
                        }
                    }
                    break;

                case 5:
                    // Volver a registrar asistencia
                    System.out.println("\nReingreso de asistencia (1 = asistio, 0 = no asistio):");
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        System.out.println("Estudiante " + (i + 1) + ":");
                        for (int j = 0; j < DIAS_SEMANA; j++) {
                            System.out.print("  Dia " + (j + 1) + ": ");
                            int valor = sc.nextInt();
                            while (valor != 0 && valor != 1) {
                                System.out.print("    Valor invalido. Ingrese 0 o 1: ");
                                valor = sc.nextInt();
                            }
                            asistencia[i][j] = valor;
                        }
                    }
                    break;

                case 6:
                	// salir del sistema
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }

        sc.close();
    }
}      
            
       
     

		
		
		
				
		
               	

