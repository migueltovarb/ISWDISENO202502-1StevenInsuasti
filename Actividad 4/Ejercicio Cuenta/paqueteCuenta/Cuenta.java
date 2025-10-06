package paqueteCuenta;

public class Cuenta {
    // Atributos
    private String titular;
    private double cantidad;

    // Constructores
    public Cuenta(String titular) {
        this.titular = titular;
        this.cantidad = 0.0;
    }

    public Cuenta(String titular, double cantidad) {
        this.titular = titular;
        this.cantidad = cantidad;
    }

    // Métodos getters y setters
    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    // Métodos funcionales
    public void ingresar(double cantidad) {
        if (cantidad > 0) {
            this.cantidad += cantidad;
        }
    }

    public void retirar(double cantidad) {
        if (cantidad > 0) {
            this.cantidad -= cantidad;
            if (this.cantidad < 0) {
                this.cantidad = 0; // No se permiten saldos negativos
            }
        }
    }

    // Metodo toString
    @Override
    public String toString() {
        return "Titular: " + titular + ", Cantidad: " + cantidad;
    }

    // Método main para probar la clase 
    public static void main(String[] args) {
        Cuenta cuenta1 = new Cuenta("Steven");
        Cuenta cuenta2 = new Cuenta("Maria", 1500.0);

        cuenta1.ingresar(500);
        cuenta1.retirar(100);

        cuenta2.retirar(1200);

        System.out.println(cuenta1.toString());
        System.out.println(cuenta2.toString());
    }
}
