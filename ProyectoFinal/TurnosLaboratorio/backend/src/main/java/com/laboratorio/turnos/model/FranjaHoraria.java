package com.laboratorio.turnos.model;

import java.time.LocalDateTime;

public class FranjaHoraria {
    private LocalDateTime inicio;
    private LocalDateTime fin;

    public FranjaHoraria() {}
    public FranjaHoraria(LocalDateTime inicio, LocalDateTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }

    public boolean seSuperpone(FranjaHoraria otra) {
        return !(this.fin.isBefore(otra.inicio) || 
                 this.fin.isEqual(otra.inicio) ||
                 this.inicio.isAfter(otra.fin) ||
                 this.inicio.isEqual(otra.fin));
    }

    public boolean contiene(LocalDateTime momento) {
        return !momento.isBefore(inicio) && !momento.isAfter(fin);
    }
}