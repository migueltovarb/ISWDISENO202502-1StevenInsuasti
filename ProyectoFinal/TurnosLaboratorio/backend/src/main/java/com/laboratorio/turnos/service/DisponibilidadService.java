package com.laboratorio.turnos.service;

import com.laboratorio.turnos.dto.DisponibilidadDTO;
import com.laboratorio.turnos.model.*;
import com.laboratorio.turnos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DisponibilidadService {
    
    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private BloqueoRepository bloqueoRepository;
    
    // HU003: Ver disponibilidad por franja
    public Map<String, Object> consultarDisponibilidad(DisponibilidadDTO dto) {
        FranjaHoraria franjaConsulta = convertirFranja(dto.getFecha(), dto.getFranja());
        
        // Obtener equipos del laboratorio
        List<Equipo> equipos = dto.getLaboratorioId() != null
            ? equipoRepository.findByLaboratorioId(dto.getLaboratorioId())
            : equipoRepository.findAll();
        
        System.out.println("=== DEBUG DISPONIBILIDAD ===");
        System.out.println("LaboratorioId buscado: " + dto.getLaboratorioId());
        System.out.println("Total equipos encontrados: " + equipos.size());
        if (!equipos.isEmpty()) {
            System.out.println("Primer equipo - ID: " + equipos.get(0).getId() + 
                             ", Codigo: " + equipos.get(0).getCodigo() + 
                             ", LabId: " + equipos.get(0).getLaboratorioId() +
                             ", Estado: " + equipos.get(0).getEstado());
        }
        
        // Filtrar por tipo si se especifica
        if (dto.getTipo() != null && !dto.getTipo().isEmpty()) {
            equipos = equipos.stream()
                .filter(e -> e.getTipo().equalsIgnoreCase(dto.getTipo()))
                .collect(Collectors.toList());
        }
        
        // Obtener bloqueos activos
        List<Bloqueo> bloqueosActivos = bloqueoRepository.findByActivoTrue();
        
        // Filtrar equipos disponibles
        List<Map<String, Object>> equiposDisponibles = new ArrayList<>();
        
        System.out.println("Bloqueos activos: " + bloqueosActivos.size());
        
        for (Equipo equipo : equipos) {
            System.out.println("\nAnalizando equipo: " + equipo.getCodigo() + " - Estado: " + equipo.getEstado());
            
            // Verificar si está bloqueado
            boolean estaBloqueado = bloqueosActivos.stream()
                .anyMatch(b -> estaEquipoBloqueado(equipo, b, franjaConsulta));
            
            System.out.println("  ¿Está bloqueado?: " + estaBloqueado);
            
            if (estaBloqueado) {
                continue;
            }
            
            // Verificar si tiene reservas en la franja
            List<Reserva> reservas = reservaRepository.findByEquipoIdAndEstadoIn(
                equipo.getId(),
                Arrays.asList(EstadoReserva.PROGRAMADA, EstadoReserva.EN_CURSO)
            );
            
            System.out.println("  Reservas activas: " + reservas.size());
            
            boolean tieneReserva = reservas.stream()
                .anyMatch(r -> r.getFranja().seSuperpone(franjaConsulta));
            
            System.out.println("  ¿Tiene reserva en franja?: " + tieneReserva);
            System.out.println("  Estado del equipo: " + equipo.getEstado());
            
            if (!tieneReserva && equipo.getEstado() == EstadoEquipo.DISPONIBLE) {
                System.out.println("  ✓ EQUIPO DISPONIBLE - Agregando a la lista");
                Map<String, Object> equipoInfo = new HashMap<>();
                equipoInfo.put("id", equipo.getId());
                equipoInfo.put("codigo", equipo.getCodigo());
                equipoInfo.put("tipo", equipo.getTipo());
                equipoInfo.put("especificaciones", equipo.getEspecificaciones());
                equipoInfo.put("laboratorioId", equipo.getLaboratorioId());
                
                equiposDisponibles.add(equipoInfo);
            } else {
                System.out.println("  ✗ EQUIPO NO DISPONIBLE");
            }
        }
        
        System.out.println("\nTotal equipos disponibles: " + equiposDisponibles.size());
        System.out.println("=== FIN DEBUG ===\n");
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("franja", franjaConsulta);
        resultado.put("equiposDisponibles", equiposDisponibles);
        resultado.put("totalDisponibles", equiposDisponibles.size());
        
        return resultado;
    }
    
    private boolean estaEquipoBloqueado(Equipo equipo, Bloqueo bloqueo, FranjaHoraria franja) {
        if (!bloqueo.getActivo()) {
            return false;
        }
        
        // Verificar si el bloqueo ya expiró (pasó su hora de fin)
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        if (bloqueo.getFranja().getFin().isBefore(ahora)) {
            return false; // El bloqueo ya expiró, el equipo está disponible
        }
        
        if (!bloqueo.getFranja().seSuperpone(franja)) {
            return false;
        }
        
        // Si el bloqueo es para todo el laboratorio
        if (bloqueo.getEquiposIds() == null || bloqueo.getEquiposIds().isEmpty()) {
            return bloqueo.getLaboratorioId().equals(equipo.getLaboratorioId());
        }
        
        // Si el bloqueo es para equipos específicos
        return bloqueo.getEquiposIds().contains(equipo.getId());
    }
    
    private FranjaHoraria convertirFranja(java.time.LocalDate fecha, String franja) {
        return switch (franja.toUpperCase()) {
            case "FRANJA_07_09" -> new FranjaHoraria(fecha.atTime(7, 0), fecha.atTime(9, 0));
            case "FRANJA_09_11" -> new FranjaHoraria(fecha.atTime(9, 0), fecha.atTime(11, 0));
            case "FRANJA_11_13" -> new FranjaHoraria(fecha.atTime(11, 0), fecha.atTime(13, 0));
            case "FRANJA_13_15" -> new FranjaHoraria(fecha.atTime(13, 0), fecha.atTime(15, 0));
            case "FRANJA_15_17" -> new FranjaHoraria(fecha.atTime(15, 0), fecha.atTime(17, 0));
            case "FRANJA_17_19" -> new FranjaHoraria(fecha.atTime(17, 0), fecha.atTime(19, 0));
            // Mantener compatibilidad con franjas antiguas
            case "MANANA" -> new FranjaHoraria(fecha.atTime(7, 0), fecha.atTime(12, 0));
            case "TARDE" -> new FranjaHoraria(fecha.atTime(12, 0), fecha.atTime(18, 0));
            case "NOCHE" -> new FranjaHoraria(fecha.atTime(18, 0), fecha.atTime(22, 0));
            default -> throw new IllegalArgumentException("Franja inválida");
        };
    }
    
    // Obtener equipos por laboratorio (para el panel de profesor)
    public List<Map<String, Object>> obtenerEquiposPorLaboratorio(String laboratorioId) {
        List<Equipo> equipos = equipoRepository.findByLaboratorioId(laboratorioId);
        
        return equipos.stream()
            .filter(e -> e.getEstado() == EstadoEquipo.DISPONIBLE)
            .map(equipo -> {
                Map<String, Object> equipoMap = new HashMap<>();
                equipoMap.put("id", equipo.getId());
                equipoMap.put("codigo", equipo.getCodigo());
                equipoMap.put("tipo", equipo.getTipo());
                equipoMap.put("estado", equipo.getEstado());
                return equipoMap;
            })
            .collect(Collectors.toList());
    }
}