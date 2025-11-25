package com.laboratorio.turnos.repository;

import com.laboratorio.turnos.model.Equipo;
import com.laboratorio.turnos.model.EstadoEquipo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends MongoRepository<Equipo, String> {
    List<Equipo> findByLaboratorioId(String laboratorioId);
    List<Equipo> findByLaboratorioIdAndEstado(String laboratorioId, EstadoEquipo estado);
}