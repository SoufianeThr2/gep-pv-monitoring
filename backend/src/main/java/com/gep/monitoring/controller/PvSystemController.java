package com.gep.monitoring.controller;

import com.gep.monitoring.dto.SystemSummaryDto;
import com.gep.monitoring.entity.PvSystem;
import com.gep.monitoring.repository.AcProductionRepository;
import com.gep.monitoring.repository.PvSystemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/systems")
@CrossOrigin(origins = "*") // Autorise React à appeler cette API sans bloquer (CORS)
public class PvSystemController {

    private final PvSystemRepository systemRepo;
    private final AcProductionRepository acRepo;

    public PvSystemController(PvSystemRepository systemRepo, AcProductionRepository acRepo) {
        this.systemRepo = systemRepo;
        this.acRepo = acRepo;
    }

    // Point d'entrée 1 : Récupérer tous les systèmes (Pour le Dashboard)
    @GetMapping
    public ResponseEntity<List<SystemSummaryDto>> getAllSystems() {
        List<PvSystem> systems = systemRepo.findAll();

        // On transforme nos Entities complexes en DTOs légers pour le frontend
        List<SystemSummaryDto> dtoList = systems.stream().map(sys -> {
            SystemSummaryDto dto = new SystemSummaryDto();
            dto.setSystemId(sys.getSystemId());
            dto.setSystemName(sys.getSystemName());
            dto.setTotalCapacityKwc(sys.getTotalCapacityKwc());
            dto.setCommissioningDate(sys.getCommissioningDate());
            dto.setLatestAcPowerKw(0.0); // On simulera la vraie valeur dans le vrai Service plus tard
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    // Point d'entrée 2 : Détails d'un seul système (Pour les pages de détails)
    @GetMapping("/{id}")
    public ResponseEntity<PvSystem> getSystemById(@PathVariable String id) {
        return systemRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}