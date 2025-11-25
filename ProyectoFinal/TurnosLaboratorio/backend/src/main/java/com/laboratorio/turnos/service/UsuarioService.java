package com.laboratorio.turnos.service;

import com.laboratorio.turnos.dto.LoginDTO;
import com.laboratorio.turnos.dto.RegistroDTO;
import com.laboratorio.turnos.model.*;
import com.laboratorio.turnos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // HU001: Registro de usuario
    public Usuario registrar(RegistroDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Correo ya registrado");
        }
        
        Usuario usuario;
        if (dto.getRol() == RolUsuario.ESTUDIANTE) {
            Estudiante estudiante = new Estudiante();
            estudiante.setCodigo(dto.getCodigo());
            usuario = estudiante;
        } else {
            Profesor profesor = new Profesor();
            profesor.setDepartamento(dto.getDepartamento());
            usuario = profesor;
        }
        
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRol());
        
        return usuarioRepository.save(usuario);
    }
    
    // HU002: Inicio de sesión
    public Usuario login(LoginDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(dto.getCorreo());
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        
        return usuario;
    }
    
    public Optional<Usuario> obtenerPorId(String id) {
        return usuarioRepository.findById(id);
    }
}