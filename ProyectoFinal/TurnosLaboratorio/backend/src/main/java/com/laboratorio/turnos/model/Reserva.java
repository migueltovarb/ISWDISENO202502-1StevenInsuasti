package com.laboratorio.turnos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reservas")
public class Reserva {
    @Id
    private String id;
    private String estudianteId;
    private String estudianteNombre;
    private String estudianteCorreo;
    private String equipoId;
    private String equipoCodigo;
    private String laboratorioId;
    private String laboratorioNombre;
    private FranjaHoraria franja;
    private EstadoReserva estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    // Getters y setters manuales
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEstudianteId() { return estudianteId; }
    public void setEstudianteId(String estudianteId) { this.estudianteId = estudianteId; }
    public String getEstudianteNombre() { return estudianteNombre; }
    public void setEstudianteNombre(String estudianteNombre) { this.estudianteNombre = estudianteNombre; }
    public String getEstudianteCorreo() { return estudianteCorreo; }
    public void setEstudianteCorreo(String estudianteCorreo) { this.estudianteCorreo = estudianteCorreo; }
    public String getEquipoId() { return equipoId; }
    public void setEquipoId(String equipoId) { this.equipoId = equipoId; }
    public String getEquipoCodigo() { return equipoCodigo; }
    public void setEquipoCodigo(String equipoCodigo) { this.equipoCodigo = equipoCodigo; }
    public String getLaboratorioId() { return laboratorioId; }
    public void setLaboratorioId(String laboratorioId) { this.laboratorioId = laboratorioId; }
    public String getLaboratorioNombre() { return laboratorioNombre; }
    public void setLaboratorioNombre(String laboratorioNombre) { this.laboratorioNombre = laboratorioNombre; }
    public FranjaHoraria getFranja() { return franja; }
    public void setFranja(FranjaHoraria franja) { this.franja = franja; }
    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }
    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalDateTime checkOutTime) { this.checkOutTime = checkOutTime; }
}