package com.laboratorio.turnos.dto;

import java.time.LocalDate;

public class DisponibilidadDTO {
    private String laboratorioId;
    private LocalDate fecha;
    private String franja; // MANANA, TARDE, NOCHE
    private String tipo; // Opcional: filtrar por tipo de equipo

    public String getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(String laboratorioId) { this.laboratorioId = laboratorioId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getFranja() { return franja; }
    public void setFranja(String franja) { this.franja = franja; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}