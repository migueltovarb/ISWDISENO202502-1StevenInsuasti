package book;

public class Libro {
    private String nombre;
    private Autor autor;
    private double precio;
    private int cantidad = 0;

    public Libro(String nombre, Autor autor, double precio){
        this.nombre = nombre;
        this.autor = autor;
        this.precio = precio;
    }

    public Libro(String nombre, Autor autor, double precio,int cantidad){
        this.nombre = nombre;
        this.autor = autor;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public Autor getAutor() {
        return autor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


}

