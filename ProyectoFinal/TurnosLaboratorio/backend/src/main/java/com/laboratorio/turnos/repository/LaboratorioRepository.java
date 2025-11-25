package com.laboratorio.turnos.repository;

import com.laboratorio.turnos.model.Laboratorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratorioRepository extends MongoRepository<Laboratorio, String> {
}