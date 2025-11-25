package com.laboratorio.turnos.model;

// Lombok removed
import org.springframework.data.mongodb.core.mapping.Document;

// Lombok annotations removed
@Document(collection = "usuarios")
public class Profesor extends Usuario {
    private String departamento;

    public Profesor() {
        setRol(RolUsuario.PROFESOR);
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}