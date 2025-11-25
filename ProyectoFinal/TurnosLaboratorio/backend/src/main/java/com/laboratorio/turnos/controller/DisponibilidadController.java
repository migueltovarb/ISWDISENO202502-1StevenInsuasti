package com.laboratorio.turnos.controller;

import com.laboratorio.turnos.dto.ApiResponse;
import com.laboratorio.turnos.dto.DisponibilidadDTO;
import com.laboratorio.turnos.model.Laboratorio;
import com.laboratorio.turnos.repository.LaboratorioRepository;
import com.laboratorio.turnos.service.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disponibilidad")
@CrossOrigin(origins = "http://localhost:3000")

public class DisponibilidadController {
    
    @Autowired
    private DisponibilidadService disponibilidadService;
    
    @Autowired
    private LaboratorioRepository laboratorioRepository;
    
    // HU003: Consultar disponibilidad
    @PostMapping("/consultar")
    public ResponseEntity<ApiResponse> consultarDisponibilidad(@RequestBody DisponibilidadDTO dto) {
        try {
            Map<String, Object> disponibilidad = disponibilidadService.consultarDisponibilidad(dto);
            
            return ResponseEntity.ok(
                new ApiResponse(true, "Disponibilidad consultada exitosamente", disponibilidad)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
    
    // Listar laboratorios
    @GetMapping("/laboratorios")
    public ResponseEntity<List<Laboratorio>> listarLaboratorios() {
        return ResponseEntity.ok(laboratorioRepository.findAll());
    }
    
    // Listar equipos por laboratorio
    @GetMapping("/equipos/{laboratorioId}")
    public ResponseEntity<ApiResponse> listarEquiposPorLaboratorio(@PathVariable String laboratorioId) {
        try {
            List<Map<String, Object>> equipos = disponibilidadService.obtenerEquiposPorLaboratorio(laboratorioId);
            return ResponseEntity.ok(
                new ApiResponse(true, "Equipos obtenidos exitosamente", equipos)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, e.getMessage(), null)
            );
        }
    }
}