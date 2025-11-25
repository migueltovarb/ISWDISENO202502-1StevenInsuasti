package com.laboratorio.turnos.model;

// Lombok removed
import org.springframework.data.mongodb.core.mapping.Document;

// Lombok annotations removed
@Document(collection = "usuarios")
public class Estudiante extends Usuario {
    private String codigo;

    public Estudiante() {
        setRol(RolUsuario.ESTUDIANTE);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}