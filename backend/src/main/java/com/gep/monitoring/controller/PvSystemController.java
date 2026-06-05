package com.gep.monitoring.controller;

import com.gep.monitoring.dto.SystemSummaryDto;
import com.gep.monitoring.entity.PvSystem;
import com.gep.monitoring.repository.InverterRepository;
import com.gep.monitoring.repository.ModuleSpecRepository;
import com.gep.monitoring.repository.PvSystemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/systems")
@CrossOrigin(origins = "*")
public class PvSystemController {

    private final PvSystemRepository systemRepo;
    private final ModuleSpecRepository moduleRepo;
    private final InverterRepository inverterRepo;

    public PvSystemController(PvSystemRepository systemRepo,
                              ModuleSpecRepository moduleRepo,
                              InverterRepository inverterRepo) {
        this.systemRepo = systemRepo;
        this.moduleRepo = moduleRepo;
        this.inverterRepo = inverterRepo;
    }

    @GetMapping
    public List<SystemSummaryDto> getAllSystemsSummary() {
        return systemRepo.findAll().stream().map(system -> {
            SystemSummaryDto dto = new SystemSummaryDto();
            dto.setSystemId(system.getSystemId());
            dto.setSystemName(system.getSystemName());
            dto.setTotalCapacityKwc(system.getTotalCapacityKwc());
            dto.setCommissioningDate(system.getCommissioningDate());
            dto.setOrientation(system.getOrientation());
            dto.setTiltAngle(system.getTiltAngle());
            dto.setNbStrings(system.getNbStrings());

            dto.setLastAcPowerKw(0.0);
            dto.setDailyEnergyKwh(3374.35);

            // 1. Récupération du Module (SÉCURISÉE)
            if (system.getModuleId() != null) {
                moduleRepo.findById(system.getModuleId()).ifPresent(mod -> {
                    SystemSummaryDto.ModuleDto modDto = new SystemSummaryDto.ModuleDto();
                    modDto.setBrand(mod.getBrand());
                    modDto.setModel(mod.getModel());
                    modDto.setTechnology(mod.getTechnology());
                    modDto.setPowerWc(mod.getPowerWc());

                    // Calcul sécurisé : on vérifie que rien n'est null avant de multiplier
                    if (system.getNbStrings() != null && mod.getNbPerString() != null) {
                        modDto.setTotalModules(system.getNbStrings() * mod.getNbPerString());
                    } else {
                        modDto.setTotalModules(0);
                    }
                    dto.setModule(modDto);
                });
            }

            // 2. Récupération de l'Onduleur (SÉCURISÉE)
            if (system.getInverterId() != null) {
                inverterRepo.findById(system.getInverterId()).ifPresent(inv -> {
                    SystemSummaryDto.InverterDto invDto = new SystemSummaryDto.InverterDto();
                    invDto.setBrand(inv.getBrand());
                    invDto.setModel(inv.getModel());
                    invDto.setPowerKwAc(inv.getPowerKwAc());
                    invDto.setNbMppt(inv.getNbMppt());
                    invDto.setSerialNumber(inv.getSerialNumber());
                    dto.setInverter(invDto);
                });
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PvSystem> getSystemById(@PathVariable String id) {
        return systemRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}