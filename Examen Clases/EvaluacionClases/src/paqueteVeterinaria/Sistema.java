package paqueteVeterinaria;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Dueño> dueños;

    public Sistema() {
        this.dueños = new ArrayList<>();
    }

    public void registrarDueño(Dueño dueño) {
        dueños.add(dueño);
    }

    public Dueño buscarDueño(String documento) {
        for (Dueño d : dueños) {
            if (d.getDocumento().equalsIgnoreCase(documento)) {
                return d;
            }
        }
        return null;
    }

    public void registrarMascota(String documentoDueño, Mascota mascota) {
        Dueño d = buscarDueño(documentoDueño);
        if (d != null && !d.existeMascota(mascota.getNombre())) {
            d.registrarMascota(mascota);
        } else {
            System.out.println("Error: Dueño no encontrado o mascota duplicada.");
        }
    }

    public void registrarControl(String documentoDueño, String nombreMascota, ControlVeterinario control) {
        Dueño d = buscarDueño(documentoDueño);
        if (d != null) {
            Mascota m = d.buscarMascota(nombreMascota);
            if (m != null) {
                m.registrarControl(control);
            } else {
                System.out.println("Error: Mascota no encontrada.");
            }
        }
    }

    public String generarReporteMascota(String documentoDueño, String nombreMascota) {
        Dueño d = buscarDueño(documentoDueño);
        if (d != null) {
            Mascota m = d.buscarMascota(nombreMascota);
            if (m != null) {
                return m.generarResumen();
            }
        }
        return "Mascota o dueño no encontrados.";
    }

    public boolean camposObligatorios(Object entidad) {
        return entidad != null; // validación simple para ejemplo
    }
}
