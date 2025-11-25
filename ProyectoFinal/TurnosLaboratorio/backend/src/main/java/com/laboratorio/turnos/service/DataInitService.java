package com.laboratorio.turnos.service;

import com.laboratorio.turnos.model.*;
import com.laboratorio.turnos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataInitService implements CommandLineRunner {
    
    @Autowired
    private LaboratorioRepository laboratorioRepository;
    
    @Autowired
    private EquipoRepository equipoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== VERIFICANDO DATOS ===");
        System.out.println("Laboratorios: " + laboratorioRepository.count());
        System.out.println("Equipos: " + equipoRepository.count());
        
        // Inicializar equipos si no existen
        if (equipoRepository.count() == 0 && laboratorioRepository.count() > 0) {
            System.out.println("=== INICIALIZANDO EQUIPOS ===");
            inicializarEquipos();
            System.out.println("=== EQUIPOS INICIALIZADOS ===");
            System.out.println("Total equipos: " + equipoRepository.count());
        }
    }
    
    private void inicializarLaboratorios() {
        Laboratorio labA = new Laboratorio();
        labA.setNombre("Laboratorio A");
        labA.setUbicacion("Edificio Central - Piso 2");
        labA.setCapacidadTotal(30);
        laboratorioRepository.save(labA);
        
        Laboratorio labB = new Laboratorio();
        labB.setNombre("Laboratorio B");
        labB.setUbicacion("Edificio Central - Piso 3");
        labB.setCapacidadTotal(25);
        laboratorioRepository.save(labB);
        
        System.out.println("✓ Laboratorios creados");
    }
    
    private void inicializarEquipos() {
        // Obtener laboratorios por ID conocidos
        Laboratorio labA = laboratorioRepository.findById("LAB-A").orElse(null);
        Laboratorio labB = laboratorioRepository.findById("LAB-B").orElse(null);
        
        if (labA != null) {
            // Equipos Lab A
            for (int i = 1; i <= 10; i++) {
                Equipo equipo = new Equipo();
                equipo.setCodigo("PC-A-" + String.format("%02d", i));
                equipo.setLaboratorioId("LAB-A");
                equipo.setTipo("PC");
                equipo.setEstado(EstadoEquipo.DISPONIBLE);
                equipo.setEspecificaciones(i <= 5 ? "Intel i7, 16GB RAM, SSD 512GB" : "Intel i5, 8GB RAM, SSD 256GB");
                equipoRepository.save(equipo);
            }
            System.out.println("✓ 10 Equipos creados para LAB-A");
        }
        
        if (labB != null) {
            // Equipos Lab B
            for (int i = 1; i <= 8; i++) {
                Equipo equipo = new Equipo();
                equipo.setCodigo("PC-B-" + String.format("%02d", i));
                equipo.setLaboratorioId("LAB-B");
                equipo.setTipo("PC");
                equipo.setEstado(EstadoEquipo.DISPONIBLE);
                equipo.setEspecificaciones("Intel i5, 8GB RAM, SSD 256GB");
                equipoRepository.save(equipo);
            }
            System.out.println("✓ 8 Equipos creados para LAB-B");
        }
    }
}
