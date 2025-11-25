package com.laboratorio.turnos.service;

import com.laboratorio.turnos.dto.BloqueoDTO;
import com.laboratorio.turnos.model.*;
import com.laboratorio.turnos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BloqueoService {
    
    @Autowired
    private BloqueoRepository bloqueoRepository;
    
    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private NotificacionService notificacionService;
    
    // HU008: Bloquear equipos
    public Bloqueo crearBloqueo(BloqueoDTO dto, String profesorId) {
        Usuario usuario = usuarioService.obtenerPorId(profesorId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (usuario.getRol() != RolUsuario.PROFESOR) {
            throw new RuntimeException("Solo los profesores pueden bloquear equipos");
        }
        
        // Crear franja horaria - soporta ambos formatos
        FranjaHoraria franja;
        
        if (dto.getInicio() != null && dto.getFin() != null) {
            // Formato antiguo: inicio y fin directos
            if (dto.getInicio().isAfter(dto.getFin()) || dto.getInicio().isEqual(dto.getFin())) {
                throw new RuntimeException("La hora de inicio debe ser anterior a la hora de fin");
            }
            
            LocalDateTime ahora = LocalDateTime.now();
            if (dto.getInicio().isBefore(ahora)) {
                throw new RuntimeException("No puedes crear un bloqueo en el pasado");
            }
            
            franja = new FranjaHoraria(dto.getInicio(), dto.getFin());
        } else if (dto.getFecha() != null && dto.getFranja() != null) {
            // Formato nuevo: fecha + franja
            if (dto.getFecha().isBefore(java.time.LocalDate.now())) {
                throw new RuntimeException("No puedes crear un bloqueo en el pasado");
            }
            
            franja = convertirFranja(dto.getFecha(), dto.getFranja());
        } else {
            throw new RuntimeException("Debes proporcionar inicio/fin o fecha/franja");
        }
        
        Bloqueo bloqueo = new Bloqueo();
        bloqueo.setProfesorId(profesorId);
        bloqueo.setProfesorNombre(usuario.getNombre());
        bloqueo.setLaboratorioId(dto.getLaboratorioId());
        bloqueo.setFranja(franja);
        bloqueo.setMotivo(dto.getMotivo());
        bloqueo.setFechaCreacion(LocalDateTime.now());
        bloqueo.setActivo(true);
        
        // Si se especifica un equipo, bloquear solo ese equipo
        // Si no, bloquear todo el laboratorio (equiposIds = null o vacío)
        if (dto.getEquipoCodigo() != null && !dto.getEquipoCodigo().trim().isEmpty()) {
            List<Equipo> equipos = equipoRepository.findByLaboratorioId(dto.getLaboratorioId());
            Equipo equipo = equipos.stream()
                .filter(e -> e.getCodigo().equals(dto.getEquipoCodigo()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            
            bloqueo.setEquiposIds(Arrays.asList(equipo.getId()));
        } else {
            // Bloquear todo el laboratorio
            bloqueo.setEquiposIds(null);
        }
        
        Bloqueo bloqueoGuardado = bloqueoRepository.save(bloqueo);
        
        // Cancelar reservas afectadas
        cancelarReservasAfectadas(bloqueo);
        
        return bloqueoGuardado;
    }
    
    private void cancelarReservasAfectadas(Bloqueo bloqueo) {
        List<Reserva> reservas;
        
        if (bloqueo.getEquiposIds() == null || bloqueo.getEquiposIds().isEmpty()) {
            // Bloqueo de todo el laboratorio
            reservas = reservaRepository.findByLaboratorioIdAndEstadoIn(
                bloqueo.getLaboratorioId(),
                Arrays.asList(EstadoReserva.PROGRAMADA)
            );
        } else {
            // Bloqueo de equipos específicos
            reservas = new ArrayList<>();
            for (String equipoId : bloqueo.getEquiposIds()) {
                reservas.addAll(reservaRepository.findByEquipoIdAndEstadoIn(
                    equipoId,
                    Arrays.asList(EstadoReserva.PROGRAMADA)
                ));
            }
        }
        
        // Filtrar solo las que se superponen con el bloqueo
        reservas.stream()
            .filter(r -> r.getFranja().seSuperpone(bloqueo.getFranja()))
            .forEach(r -> {
                r.setEstado(EstadoReserva.CANCELADA_POR_MANTENIMIENTO);
                reservaRepository.save(r);
                notificacionService.enviarCancelacionPorMantenimiento(r, bloqueo.getMotivo());
            });
    }
    
    // HU009: Desbloquear
    public void desbloquear(String bloqueoId, String profesorId) {
        Bloqueo bloqueo = bloqueoRepository.findById(bloqueoId)
            .orElseThrow(() -> new RuntimeException("Bloqueo no encontrado"));
        
        if (!bloqueo.getProfesorId().equals(profesorId)) {
            throw new RuntimeException("No tienes permiso para desbloquear");
        }
        
        bloqueo.setActivo(false);
        bloqueoRepository.save(bloqueo);
    }
    
    // HU009: Listar bloqueos del profesor
    public List<Bloqueo> obtenerBloqueoProfesor(String profesorId) {
        List<Bloqueo> bloqueos = bloqueoRepository.findByProfesorId(profesorId);
        
        // Desactivar automáticamente los bloqueos que ya pasaron su hora de fin
        LocalDateTime ahora = LocalDateTime.now();
        bloqueos.forEach(bloqueo -> {
            if (bloqueo.getActivo() && bloqueo.getFranja().getFin().isBefore(ahora)) {
                bloqueo.setActivo(false);
                bloqueoRepository.save(bloqueo);
            }
        });
        
        // Retornar solo los bloqueos activos (que no han expirado)
        return bloqueos.stream()
            .filter(Bloqueo::getActivo)
            .collect(java.util.stream.Collectors.toList());
    }
    
    public List<Bloqueo> obtenerBloqueosActivos() {
        List<Bloqueo> bloqueos = bloqueoRepository.findByActivoTrue();
        
        // Desactivar automáticamente los bloqueos que ya pasaron su hora de fin
        LocalDateTime ahora = LocalDateTime.now();
        bloqueos.forEach(bloqueo -> {
            if (bloqueo.getFranja().getFin().isBefore(ahora)) {
                bloqueo.setActivo(false);
                bloqueoRepository.save(bloqueo);
            }
        });
        
        // Retornar solo los que siguen activos
        return bloqueos.stream()
            .filter(b -> !b.getFranja().getFin().isBefore(ahora))
            .collect(java.util.stream.Collectors.toList());
    }
    
    private FranjaHoraria convertirFranja(java.time.LocalDate fecha, String franja) {
        return switch (franja.toUpperCase()) {
            case "FRANJA_07_09" -> new FranjaHoraria(fecha.atTime(7, 0), fecha.atTime(9, 0));
            case "FRANJA_09_11" -> new FranjaHoraria(fecha.atTime(9, 0), fecha.atTime(11, 0));
            case "FRANJA_11_13" -> new FranjaHoraria(fecha.atTime(11, 0), fecha.atTime(13, 0));
            case "FRANJA_13_15" -> new FranjaHoraria(fecha.atTime(13, 0), fecha.atTime(15, 0));
            case "FRANJA_15_17" -> new FranjaHoraria(fecha.atTime(15, 0), fecha.atTime(17, 0));
            case "FRANJA_17_19" -> new FranjaHoraria(fecha.atTime(17, 0), fecha.atTime(19, 0));
            default -> throw new RuntimeException("Franja horaria no válida: " + franja);
        };
    }
}