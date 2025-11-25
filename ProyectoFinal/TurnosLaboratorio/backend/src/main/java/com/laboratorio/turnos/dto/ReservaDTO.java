package com.laboratorio.turnos.dto;

import java.time.LocalDate;

public class ReservaDTO {
    private String laboratorioId;
    private String equipoCodigo;
    private LocalDate fecha;
    private String franja; // MANANA, TARDE, NOCHE

    public String getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(String laboratorioId) { this.laboratorioId = laboratorioId; }
    public String getEquipoCodigo() { return equipoCodigo; }
    public void setEquipoCodigo(String equipoCodigo) { this.equipoCodigo = equipoCodigo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getFranja() { return franja; }
    public void setFranja(String franja) { this.franja = franja; }
}