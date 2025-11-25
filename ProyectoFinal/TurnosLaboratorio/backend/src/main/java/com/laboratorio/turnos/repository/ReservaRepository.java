package com.laboratorio.turnos.repository;

import com.laboratorio.turnos.model.Reserva;
import com.laboratorio.turnos.model.EstadoReserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {
    List<Reserva> findByEstudianteId(String estudianteId);
    
    List<Reserva> findByEquipoIdAndEstadoIn(String equipoId, List<EstadoReserva> estados);
    
    List<Reserva> findByEstadoAndFranjaInicioAfter(EstadoReserva estado, LocalDateTime fecha);
    
    List<Reserva> findByLaboratorioIdAndEstadoIn(String laboratorioId, List<EstadoReserva> estados);
    
    List<Reserva> findByEstudianteIdAndEstadoIn(String estudianteId, List<EstadoReserva> estados);
}