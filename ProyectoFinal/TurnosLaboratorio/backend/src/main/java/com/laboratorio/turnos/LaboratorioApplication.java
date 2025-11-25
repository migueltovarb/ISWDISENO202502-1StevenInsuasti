package com.laboratorio.turnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
public class LaboratorioApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaboratorioApplication.class, args);
        System.out.println("\n" +
            "================================================================\n" +
            "🖥️  Sistema de Gestión de Turnos - Laboratorio\n" +
            "📍 URL: http://localhost:8080\n" +
            "📊 MongoDB: laboratorio_db\n" +
            "✅ 10 HUs implementadas (HU001-HU010)\n" +
            "✅ 7 Escenarios de Calidad (EC01-EC07)\n" +
            "================================================================\n");
    }
}