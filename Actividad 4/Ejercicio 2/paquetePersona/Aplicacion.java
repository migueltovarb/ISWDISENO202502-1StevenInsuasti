package paquetePersona;

import java.util.Scanner;

public class Aplicacion {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pedir datos por teclado
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese edad: ");
        int edad = sc.nextInt();

        System.out.print("Ingrese sexo (H/M): ");
        char sexo = sc.next().charAt(0);

        System.out.print("Ingrese peso en Kg (utilice la ',' como decimal): ");
        double peso = sc.nextDouble();

        System.out.print("Ingrese altura en 'm' (utilice la ',' como decimal): ");
        double altura = sc.nextDouble();

        // Crear los 3 objetos
        Persona p1 = new Persona(nombre, edad, sexo, peso, altura);
        Persona p2 = new Persona(nombre, edad, sexo); // sin peso ni altura
        Persona p3 = new Persona(); // por defecto

        // Usar setters para dar valores a p3
        p3.setNombre("Carlos");
        p3.setEdad(30);
        p3.setSexo('H');
        p3.setPeso(80);
        p3.setAltura(1.75);

        // Mostrar resultados
        mostrarInfo(p1);
        mostrarInfo(p2);
        mostrarInfo(p3);

        sc.close();
    }

        private static void mostrarInfo(Persona persona) {
        // Estado de peso
        int imc = persona.calcularIMC();
        String estadoPeso;
        switch (imc) {
            case Persona.BAJO_PESO:
                estadoPeso = "Bajo peso";
                break;
            case Persona.PESO_IDEAL:
                estadoPeso = "Peso ideal";
                break;
            case Persona.SOBREPESO:
                estadoPeso = "Sobrepeso";
                break;
            default:
                estadoPeso = "Desconocido";
        }

        // Mayor de edad
        String mayorEdad = persona.esMayorDeEdad() ? "Sí" : "No";

        // Mostrar resultados
        System.out.println("\n--- Información de la persona ---");
        System.out.println(persona.toString());
        System.out.println("Estado de peso: " + estadoPeso);
        System.out.println("¿Es mayor de edad?: " + mayorEdad);	
    }
}

