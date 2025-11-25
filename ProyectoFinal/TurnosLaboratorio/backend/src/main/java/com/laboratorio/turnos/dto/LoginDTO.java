package com.laboratorio.turnos.dto;

import jakarta.validation.constraints.*;

public class LoginDTO {
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}