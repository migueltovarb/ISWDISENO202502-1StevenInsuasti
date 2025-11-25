package com.laboratorio.turnos.repository;

import com.laboratorio.turnos.model.Bloqueo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BloqueoRepository extends MongoRepository<Bloqueo, String> {
    List<Bloqueo> findByActivoTrue();
    List<Bloqueo> findByProfesorId(String profesorId);
    List<Bloqueo> findByLaboratorioId(String laboratorioId);
}