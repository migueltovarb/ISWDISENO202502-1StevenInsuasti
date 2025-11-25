package com.laboratorio.turnos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

// import lombok.Data;
@Document(collection = "bloqueos")
public class Bloqueo {
    @Id
    private String id;
    private String profesorId;
    private String profesorNombre;
    private String laboratorioId;
    private List<String> equiposIds;
    private FranjaHoraria franja;
    private String motivo;
    private LocalDateTime fechaCreacion;
    private Boolean activo;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getProfesorId() {
        return profesorId;
    }
    public void setProfesorId(String profesorId) {
        this.profesorId = profesorId;
    }
    public String getProfesorNombre() {
        return profesorNombre;
    }
    public void setProfesorNombre(String profesorNombre) {
        this.profesorNombre = profesorNombre;
    }
    public String getLaboratorioId() {
        return laboratorioId;
    }
    public void setLaboratorioId(String laboratorioId) {
        this.laboratorioId = laboratorioId;
    }
    public List<String> getEquiposIds() {
        return equiposIds;
    }
    public void setEquiposIds(List<String> equiposIds) {
        this.equiposIds = equiposIds;
    }
    public FranjaHoraria getFranja() {
        return franja;
    }
    public void setFranja(FranjaHoraria franja) {
        this.franja = franja;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}