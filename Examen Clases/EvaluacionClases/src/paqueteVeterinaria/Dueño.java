package paqueteVeterinaria;

import java.util.ArrayList;
import java.util.List;

public class Dueño {
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private List<Mascota> mascotas;

    public Dueño() {
        this.mascotas = new ArrayList<>();
    }

    public Dueño(String nombreCompleto, String documento, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.documento = documento;
        this.telefono = telefono;
        this.mascotas = new ArrayList<>();
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Mascota> getMascotas() { return mascotas; }

    public void registrarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    public Mascota buscarMascota(String nombreMascota) {
        for (Mascota m : mascotas) {
            if (m.getNombre().equalsIgnoreCase(nombreMascota)) {
                return m;
            }
        }
        return null;
    }

    public boolean existeMascota(String nombreMascota) {
        return buscarMascota(nombreMascota) != null;
    }

    public String generarResumenMascota() {
        StringBuilder resumen = new StringBuilder("Dueño: " + nombreCompleto + "\n");
        for (Mascota m : mascotas) {
            resumen.append(" - ").append(m.generarResumen()).append("\n");
        }
        return resumen.toString();
    }

    @Override
    public String toString() {
        return "Dueño: " + nombreCompleto + " (" + documento + ")";
    }
}

