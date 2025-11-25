package com.laboratorio.turnos.controller;

import com.laboratorio.turnos.dto.ApiResponse;
import com.laboratorio.turnos.dto.ReservaDTO;
import com.laboratorio.turnos.model.Reserva;
import com.laboratorio.turnos.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    // HU004: Crear reserva
    @PostMapping
    public ResponseEntity<ApiResponse> crearReserva(
        @RequestBody ReservaDTO dto,
        HttpSession session) {
        
        String estudianteId = (String) session.getAttribute("usuarioId");
        if (estudianteId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Debes iniciar sesión", null)
            );
        }
        
        try {
            Reserva reserva = reservaService.crearReserva(dto, estudianteId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("reservaId", reserva.getId());
            data.put("equipo", reserva.getEquipoCodigo());
            data.put("laboratorio", reserva.getLaboratorioNombre());
            data.put("franja", reserva.getFranja());
            data.put("estado", reserva.getEstado());
            
            return ResponseEntity.status(201).body(
                new ApiResponse(true, "Reserva creada exitosamente", data)
            );
        } catch (Exception e) {
            return ResponseEntity.status(409).body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // HU005: Cancelar reserva
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse> cancelarReserva(
        @PathVariable String id,
        HttpSession session) {
        
        String estudianteId = (String) session.getAttribute("usuarioId");
        if (estudianteId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Debes iniciar sesión", null)
            );
        }
        
        try {
            reservaService.cancelarReserva(id, estudianteId);
            return ResponseEntity.ok(
                new ApiResponse(true, "Reserva cancelada exitosamente", null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // HU006: Check-in
    @PutMapping("/{id}/checkin")
    public ResponseEntity<ApiResponse> checkIn(
        @PathVariable String id,
        HttpSession session) {
        
        String estudianteId = (String) session.getAttribute("usuarioId");
        if (estudianteId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Debes iniciar sesión", null)
            );
        }
        
        try {
            reservaService.checkIn(id, estudianteId);
            return ResponseEntity.ok(
                new ApiResponse(true, "Check-in exitoso", null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // HU006: Check-out
    @PutMapping("/{id}/checkout")
    public ResponseEntity<ApiResponse> checkOut(
        @PathVariable String id,
        HttpSession session) {
        
        String estudianteId = (String) session.getAttribute("usuarioId");
        if (estudianteId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Debes iniciar sesión", null)
            );
        }
        
        try {
            reservaService.checkOut(id, estudianteId);
            return ResponseEntity.ok(
                new ApiResponse(true, "Check-out exitoso", null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // HU010: Ver mis reservas
    @GetMapping("/mis-reservas")
    public ResponseEntity<ApiResponse> obtenerMisReservas(HttpSession session) {
        String estudianteId = (String) session.getAttribute("usuarioId");
        if (estudianteId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Debes iniciar sesión", null)
            );
        }
        
        List<Reserva> reservas = reservaService.obtenerReservasEstudiante(estudianteId);
        return ResponseEntity.ok(
            new ApiResponse(true, "Reservas obtenidas", reservas)
        );
    }
    
    // Ver todas (para admin/profesor)
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }
    
    // Ver por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable String id) {
        return reservaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Ver reservas por laboratorio (para profesores)
    @GetMapping("/laboratorio/{laboratorioId}")
    public ResponseEntity<ApiResponse> obtenerReservasPorLaboratorio(
        @PathVariable String laboratorioId,
        HttpSession session) {
        
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Debes iniciar sesión", null)
            );
        }
        
        List<Reserva> reservas = reservaService.obtenerReservasPorLaboratorio(laboratorioId);
        return ResponseEntity.ok(
            new ApiResponse(true, "Reservas obtenidas", reservas)
        );
    }
    
    // Limpiar reservas canceladas (eliminar permanentemente)
    @DeleteMapping("/limpiar-canceladas")
    public ResponseEntity<ApiResponse> limpiarReservasCanceladas(HttpSession session) {
        String estudianteId = (String) session.getAttribute("usuarioId");
        if (estudianteId == null) {
            return ResponseEntity.status(401).body(
                new ApiResponse(false, "Debes iniciar sesión", null)
            );
        }
        
        try {
            int eliminadas = reservaService.eliminarReservasCanceladas(estudianteId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("eliminadas", eliminadas);
            
            return ResponseEntity.ok(
                new ApiResponse(true, 
                    eliminadas + " reserva(s) cancelada(s) eliminada(s) permanentemente", 
                    data)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
}