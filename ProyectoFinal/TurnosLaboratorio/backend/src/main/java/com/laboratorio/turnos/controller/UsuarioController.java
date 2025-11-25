package com.laboratorio.turnos.controller;

import com.laboratorio.turnos.dto.*;
import com.laboratorio.turnos.model.Usuario;
import com.laboratorio.turnos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    // HU001: Registro
    @PostMapping("/registro")
    public ResponseEntity<ApiResponse> registrar(@Valid @RequestBody RegistroDTO dto) {
        try {
            Usuario usuario = usuarioService.registrar(dto);
            
            Map<String, Object> data = new HashMap<>();
            data.put("id", usuario.getId());
            data.put("nombre", usuario.getNombre());
            data.put("correo", usuario.getCorreo());
            data.put("rol", usuario.getRol());
            
            return ResponseEntity.status(201).body(
                new ApiResponse(true, "Usuario registrado exitosamente", data)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // HU002: Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
        @Valid @RequestBody LoginDTO dto,
        HttpSession session) {
        try {
            Usuario usuario = usuarioService.login(dto);
            
            // Guardar en sesión
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("usuarioRol", usuario.getRol());
            
            Map<String, Object> data = new HashMap<>();
            data.put("id", usuario.getId());
            data.put("nombre", usuario.getNombre());
            data.put("correo", usuario.getCorreo());
            data.put("rol", usuario.getRol());
            data.put("sessionId", session.getId());
            
            return ResponseEntity.ok(
                new ApiResponse(true, "Login exitoso", data)
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // Logout
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(
            new ApiResponse(true, "Sesión cerrada exitosamente", null)
        );
    }
    
    // Verificar sesión
    @GetMapping("/session")
    public ResponseEntity<ApiResponse> verificarSesion(HttpSession session) {
        String usuarioId = (String) session.getAttribute("usuarioId");
        
        if (usuarioId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Sesión no válida", null)
            );
        }
        
        Usuario usuario = usuarioService.obtenerPorId(usuarioId).orElse(null);
        
        if (usuario == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Usuario no encontrado", null)
            );
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("id", usuario.getId());
        data.put("nombre", usuario.getNombre());
        data.put("correo", usuario.getCorreo());
        data.put("rol", usuario.getRol());
        
        return ResponseEntity.ok(
            new ApiResponse(true, "Sesión activa", data)
        );
    }
}