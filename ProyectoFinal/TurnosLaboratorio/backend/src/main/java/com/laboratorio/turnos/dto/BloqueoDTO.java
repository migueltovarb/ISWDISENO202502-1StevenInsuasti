package com.laboratorio.turnos.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BloqueoDTO {
    private String laboratorioId;
    private String equipoCodigo; // Código del equipo a bloquear (opcional - null = todo el laboratorio)
    
    // Formato nuevo (fecha + franja)
    private LocalDate fecha;
    private String franja; // FRANJA_07_09, FRANJA_09_11, etc.
    
    // Formato antiguo (inicio + fin) - para compatibilidad con frontend
    private LocalDateTime inicio;
    private LocalDateTime fin;
    
    private String motivo;

    public String getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(String laboratorioId) { this.laboratorioId = laboratorioId; }
    public String getEquipoCodigo() { return equipoCodigo; }
    public void setEquipoCodigo(String equipoCodigo) { this.equipoCodigo = equipoCodigo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getFranja() { return franja; }
    public void setFranja(String franja) { this.franja = franja; }
    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}