package com.laboratorio.turnos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laboratorio.turnos.dto.ApiResponse;
import com.laboratorio.turnos.dto.BloqueoDTO;
import com.laboratorio.turnos.model.Bloqueo;
import com.laboratorio.turnos.model.RolUsuario;
import com.laboratorio.turnos.service.BloqueoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/bloqueos")
@CrossOrigin(origins = "http://localhost:3000")

public class BloqueoController {
    
    @Autowired
    private BloqueoService bloqueoService;
    
    // HU008: Crear bloqueo
    @PostMapping
    public ResponseEntity<ApiResponse> crearBloqueo(
        @RequestBody BloqueoDTO dto,
        HttpSession session) {
        
        String profesorId = (String) session.getAttribute("usuarioId");
        RolUsuario rol = (RolUsuario) session.getAttribute("usuarioRol");
        
        if (profesorId == null || rol != RolUsuario.PROFESOR) {
            return ResponseEntity.status(403).body(
                new ApiResponse(false, "Solo los profesores pueden bloquear equipos", null)
            );
        }
        
        try {
            Bloqueo bloqueo = bloqueoService.crearBloqueo(dto, profesorId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("bloqueoId", bloqueo.getId());
            data.put("laboratorioId", bloqueo.getLaboratorioId());
            data.put("franja", bloqueo.getFranja());
            data.put("motivo", bloqueo.getMotivo());
            data.put("activo", bloqueo.getActivo());
            
            return ResponseEntity.status(201).body(
                new ApiResponse(true, "Bloqueo creado. Reservas afectadas canceladas.", data)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // HU009: Desbloquear
    @PutMapping("/{id}/desbloquear")
    public ResponseEntity<ApiResponse> desbloquear(
        @PathVariable String id,
        HttpSession session) {
        
        String profesorId = (String) session.getAttribute("usuarioId");
        RolUsuario rol = (RolUsuario) session.getAttribute("usuarioRol");
        
        if (profesorId == null || rol != RolUsuario.PROFESOR) {
            return ResponseEntity.status(403).body(
                new ApiResponse(false, "Solo los profesores pueden desbloquear", null)
            );
        }
        
        try {
            bloqueoService.desbloquear(id, profesorId);
            return ResponseEntity.ok(
                new ApiResponse(true, "Bloqueo desactivado exitosamente", null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // HU009: Listar mis bloqueos
    @GetMapping("/mis-bloqueos")
    public ResponseEntity<ApiResponse> obtenerMisBloqueos(HttpSession session) {
        String profesorId = (String) session.getAttribute("usuarioId");
        RolUsuario rol = (RolUsuario) session.getAttribute("usuarioRol");
        
        if (profesorId == null || rol != RolUsuario.PROFESOR) {
            return ResponseEntity.status(403).body(
                new ApiResponse(false, "Solo profesores", null)
            );
        }
        
        List<Bloqueo> bloqueos = bloqueoService.obtenerBloqueoProfesor(profesorId);
        return ResponseEntity.ok(
            new ApiResponse(true, "Bloqueos obtenidos", bloqueos)
        );
    }
    
    // Listar bloqueos activos (público)
    @GetMapping("/activos")
    public ResponseEntity<List<Bloqueo>> obtenerBloqueosActivos() {
        return ResponseEntity.ok(bloqueoService.obtenerBloqueosActivos());
    }
}