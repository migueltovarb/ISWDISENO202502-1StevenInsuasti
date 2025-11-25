package com.laboratorio.turnos.service;

import com.laboratorio.turnos.dto.ReservaDTO;
import com.laboratorio.turnos.model.*;
import com.laboratorio.turnos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private LaboratorioRepository laboratorioRepository;
    
    @Autowired
    private BloqueoRepository bloqueoRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private NotificacionService notificacionService;
    
    // HU004: Reservar franja
    public Reserva crearReserva(ReservaDTO dto, String estudianteId) {
        Usuario usuario = usuarioService.obtenerPorId(estudianteId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (usuario.getRol() != RolUsuario.ESTUDIANTE) {
            throw new RuntimeException("Solo los estudiantes pueden hacer reservas");
        }
        
        // Buscar equipo por código y laboratorio
        List<Equipo> equipos = equipoRepository.findByLaboratorioId(dto.getLaboratorioId());
        Equipo equipo = equipos.stream()
            .filter(e -> e.getCodigo().equals(dto.getEquipoCodigo()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        
        // Convertir franja a FranjaHoraria
        FranjaHoraria franja = convertirFranja(dto.getFecha(), dto.getFranja());
        
        // Validar que la franja no haya pasado
        if (franja.getInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No puedes reservar una franja horaria que ya pasó");
        }
        
        // Validar que la franja esté disponible
        validarDisponibilidad(equipo, franja);
        
        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setEstudianteId(estudianteId);
        reserva.setEstudianteNombre(usuario.getNombre());
        reserva.setEstudianteCorreo(usuario.getCorreo());
        reserva.setEquipoId(equipo.getId());
        reserva.setEquipoCodigo(equipo.getCodigo());
        reserva.setLaboratorioId(equipo.getLaboratorioId());
        
        Laboratorio lab = laboratorioRepository.findById(equipo.getLaboratorioId()).orElse(null);
        reserva.setLaboratorioNombre(lab != null ? lab.getNombre() : "");
        
        reserva.setFranja(franja);
        reserva.setEstado(EstadoReserva.PROGRAMADA);
        reserva.setFechaCreacion(LocalDateTime.now());
        
        Reserva reservaGuardada = reservaRepository.save(reserva);
        
        // HU007: Enviar notificación
        notificacionService.enviarConfirmacionReserva(reservaGuardada);
        
        return reservaGuardada;
    }
    
    private void validarDisponibilidad(Equipo equipo, FranjaHoraria franja) {
        // Verificar reservas existentes
        List<Reserva> reservasExistentes = reservaRepository.findByEquipoIdAndEstadoIn(
            equipo.getId(),
            Arrays.asList(EstadoReserva.PROGRAMADA, EstadoReserva.EN_CURSO)
        );
        
        boolean tieneConflicto = reservasExistentes.stream()
            .anyMatch(r -> r.getFranja().seSuperpone(franja));
        
        if (tieneConflicto) {
            throw new RuntimeException("El equipo ya está reservado en ese horario");
        }
        
        // Verificar bloqueos
        List<Bloqueo> bloqueosActivos = bloqueoRepository.findByActivoTrue();
        boolean estaBloqueado = bloqueosActivos.stream()
            .anyMatch(b -> equipoEstaBloqueado(equipo, b, franja));
        
        if (estaBloqueado) {
            throw new RuntimeException("El equipo está bloqueado en ese horario");
        }
    }
    
    private boolean equipoEstaBloqueado(Equipo equipo, Bloqueo bloqueo, FranjaHoraria franja) {
        if (!bloqueo.getActivo() || !bloqueo.getFranja().seSuperpone(franja)) {
            return false;
        }
        
        if (bloqueo.getEquiposIds() == null || bloqueo.getEquiposIds().isEmpty()) {
            return bloqueo.getLaboratorioId().equals(equipo.getLaboratorioId());
        }
        
        return bloqueo.getEquiposIds().contains(equipo.getId());
    }
    
    // HU005: Cancelar reserva
    public void cancelarReserva(String reservaId, String estudianteId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        if (!reserva.getEstudianteId().equals(estudianteId)) {
            throw new RuntimeException("No tienes permiso para cancelar esta reserva");
        }
        
        // Si la reserva está EN_CURSO, hacer check-out automáticamente
        if (reserva.getEstado() == EstadoReserva.EN_CURSO) {
            reserva.setEstado(EstadoReserva.COMPLETADA);
            reserva.setCheckOutTime(LocalDateTime.now());
            reservaRepository.save(reserva);
            notificacionService.enviarCheckOutExitoso(reserva);
            return;
        }
        
        // Si aún no ha comenzado, cancelar normalmente
        if (reserva.getEstado() == EstadoReserva.PROGRAMADA) {
            reserva.setEstado(EstadoReserva.CANCELADA);
            reservaRepository.save(reserva);
            notificacionService.enviarCancelacionReserva(reserva);
            return;
        }
        
        throw new RuntimeException("No se puede cancelar esta reserva en su estado actual");
    }
    
    // HU006: Check-in
    public void checkIn(String reservaId, String estudianteId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        if (!reserva.getEstudianteId().equals(estudianteId)) {
            throw new RuntimeException("No tienes permiso");
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicio = reserva.getFranja().getInicio();
        
        // Ventana: 10 min antes hasta 20 min después del inicio
        if (ahora.isBefore(inicio.minusMinutes(10)) || ahora.isAfter(inicio.plusMinutes(20))) {
            throw new RuntimeException("Check-in solo disponible desde 10 min antes hasta 20 min después del inicio");
        }
        
        reserva.setEstado(EstadoReserva.EN_CURSO);
        reserva.setCheckInTime(ahora);
        reservaRepository.save(reserva);
        
        notificacionService.enviarCheckInExitoso(reserva);
    }
    
    // HU006: Check-out
    public void checkOut(String reservaId, String estudianteId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        if (!reserva.getEstudianteId().equals(estudianteId)) {
            throw new RuntimeException("No tienes permiso");
        }
        
        if (reserva.getEstado() != EstadoReserva.EN_CURSO) {
            throw new RuntimeException("Debes hacer check-in primero");
        }
        
        reserva.setEstado(EstadoReserva.COMPLETADA);
        reserva.setCheckOutTime(LocalDateTime.now());
        reservaRepository.save(reserva);
        
        notificacionService.enviarCheckOutExitoso(reserva);
    }
    
    // HU010: Ver mis reservas
    public List<Reserva> obtenerReservasEstudiante(String estudianteId) {
        return reservaRepository.findByEstudianteId(estudianteId);
    }
    
    public Optional<Reserva> obtenerPorId(String id) {
        return reservaRepository.findById(id);
    }
    
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }
    
    public List<Reserva> obtenerReservasPorLaboratorio(String laboratorioId) {
        return reservaRepository.findByLaboratorioIdAndEstadoIn(
            laboratorioId,
            Arrays.asList(EstadoReserva.PROGRAMADA, EstadoReserva.EN_CURSO)
        );
    }
    
    // Eliminar permanentemente reservas canceladas del estudiante
    public int eliminarReservasCanceladas(String estudianteId) {
        List<Reserva> reservasCanceladas = reservaRepository.findByEstudianteIdAndEstadoIn(
            estudianteId,
            Arrays.asList(EstadoReserva.CANCELADA, EstadoReserva.CANCELADA_POR_MANTENIMIENTO)
        );
        
        int cantidad = reservasCanceladas.size();
        
        // Eliminar todas las reservas canceladas
        reservasCanceladas.forEach(reserva -> reservaRepository.delete(reserva));
        
        return cantidad;
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
}