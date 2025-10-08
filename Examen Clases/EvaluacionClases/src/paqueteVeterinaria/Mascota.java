package paqueteVeterinaria;

import java.util.ArrayList;
import java.util.List;

public class Mascota {
    private String nombre;
    private String especie;
    private String edad;
    private Dueño dueño;
    private List<ControlVeterinario> controles;

    public Mascota() {
        this.controles = new ArrayList<>();
    }

    public Mascota(String nombre, String especie, String edad, Dueño dueño) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.dueño = dueño;
        this.controles = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }

    public Dueño getDueño() { return dueño; }
    public void setDueño(Dueño dueño) { this.dueño = dueño; }

    public void registrarControl(ControlVeterinario control) {
        controles.add(control);
    }

    public List<ControlVeterinario> consultarHistorial() {
        return controles;
    }

    public int contarControles() {
        return controles.size();
    }

    public String generarResumen() {
        return "Mascota: " + nombre + " (" + especie + ") - Controles: " + contarControles();
    }

    @Override
    public String toString() {
        return generarResumen();
    }
}
