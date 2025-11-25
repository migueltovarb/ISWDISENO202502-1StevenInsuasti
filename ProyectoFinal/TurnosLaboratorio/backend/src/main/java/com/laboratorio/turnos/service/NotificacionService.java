package com.laboratorio.turnos.service;

import com.laboratorio.turnos.model.Reserva;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    
    public void enviarConfirmacionReserva(Reserva reserva) {
        String mensaje = String.format(
            "Reserva confirmada: %s en %s\nFecha: %s\nHorario: %s - %s",
            reserva.getEquipoCodigo(),
            reserva.getLaboratorioNombre(),
            reserva.getFranja().getInicio().toLocalDate(),
            reserva.getFranja().getInicio().toLocalTime(),
            reserva.getFranja().getFin().toLocalTime()
        );
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📧 [NOTIFICACIÓN] → " + reserva.getEstudianteCorreo());
        System.out.println("📝 Tipo: CONFIRMACIÓN DE RESERVA");
        System.out.println("📝 Mensaje:\n" + mensaje);
        System.out.println("🆔 Reserva ID: " + reserva.getId());
        System.out.println("=".repeat(60) + "\n");
    }
    
    public void enviarCancelacionReserva(Reserva reserva) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📧 [NOTIFICACIÓN] → " + reserva.getEstudianteCorreo());
        System.out.println("📝 Tipo: CANCELACIÓN DE RESERVA");
        System.out.println("📝 Mensaje: Tu reserva ha sido cancelada");
        System.out.println("🆔 Reserva ID: " + reserva.getId());
        System.out.println("=".repeat(60) + "\n");
    }
    
    public void enviarRecordatorio(Reserva reserva) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📧 [NOTIFICACIÓN] → " + reserva.getEstudianteCorreo());
        System.out.println("📝 Tipo: RECORDATORIO");
        System.out.println("📝 Mensaje: Tu reserva inicia en 1 hora");
        System.out.println("=".repeat(60) + "\n");
    }
    
    public void enviarCancelacionPorMantenimiento(Reserva reserva, String motivo) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📧 [NOTIFICACIÓN] → " + reserva.getEstudianteCorreo());
        System.out.println("📝 Tipo: CANCELACIÓN POR MANTENIMIENTO");
        System.out.println("📝 Mensaje: Tu reserva fue cancelada por: " + motivo);
        System.out.println("🆔 Reserva ID: " + reserva.getId());
        System.out.println("=".repeat(60) + "\n");
    }
    
    public void enviarCheckInExitoso(Reserva reserva) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ [CHECK-IN EXITOSO] → " + reserva.getEstudianteCorreo());
        System.out.println("📝 Equipo: " + reserva.getEquipoCodigo());
        System.out.println("⏰ Hora: " + reserva.getCheckInTime().toLocalTime());
        System.out.println("=".repeat(60) + "\n");
    }
    
    public void enviarCheckOutExitoso(Reserva reserva) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ [CHECK-OUT EXITOSO] → " + reserva.getEstudianteCorreo());
        System.out.println("📝 Equipo: " + reserva.getEquipoCodigo());
        System.out.println("⏰ Hora: " + reserva.getCheckOutTime().toLocalTime());
        System.out.println("=".repeat(60) + "\n");
    }
}