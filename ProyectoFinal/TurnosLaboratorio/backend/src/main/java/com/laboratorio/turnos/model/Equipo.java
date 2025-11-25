package com.laboratorio.turnos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "equipos")
public class Equipo {
    @Id
    private String id;
    private String codigo;
    private String laboratorioId;
    private String tipo;
    private EstadoEquipo estado;
    private String especificaciones;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getLaboratorioId() {
        return laboratorioId;
    }
    public void setLaboratorioId(String laboratorioId) {
        this.laboratorioId = laboratorioId;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public EstadoEquipo getEstado() {
        return estado;
    }
    public void setEstado(EstadoEquipo estado) {
        this.estado = estado;
    }
    public String getEspecificaciones() {
        return especificaciones;
    }
    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }
}