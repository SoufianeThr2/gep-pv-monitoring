package com.gep.monitoring.service;

import com.gep.monitoring.dto.SystemSummaryDto;
import com.gep.monitoring.entity.Inverter;
import com.gep.monitoring.entity.ModuleSpec;
import com.gep.monitoring.entity.PvSystem;
import com.gep.monitoring.mappers.PvSystemMapper;
import com.gep.monitoring.repository.AcProductionRepository;
import com.gep.monitoring.repository.InverterRepository;
import com.gep.monitoring.repository.ModuleSpecRepository;
import com.gep.monitoring.repository.PvSystemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service gérant la logique métier liée aux systèmes PV.
 *
 * Ce service a été créé pour extraire toute la logique qui était
 * directement dans PvSystemController (ce qui violait le principe SRP).
 *
 * Responsabilités :
 * - Récupérer les systèmes PV depuis la base de données
 * - Enrichir les données avec les informations module, onduleur, et live
 * - Déléguer le mapping Entité → DTO au PvSystemMapper
 */
@Service
public class PvSystemService {

    private final PvSystemRepository systemRepo;
    private final ModuleSpecRepository moduleRepo;
    private final InverterRepository inverterRepo;
    private final AcProductionRepository acRepo;
    private final PvSystemMapper pvSystemMapper;

    public PvSystemService(PvSystemRepository systemRepo,
                           ModuleSpecRepository moduleRepo,
                           InverterRepository inverterRepo,
                           AcProductionRepository acRepo,
                           PvSystemMapper pvSystemMapper) {
        this.systemRepo = systemRepo;
        this.moduleRepo = moduleRepo;
        this.inverterRepo = inverterRepo;
        this.acRepo = acRepo;
        this.pvSystemMapper = pvSystemMapper;
    }

    /**
     * Récupère le résumé de tous les systèmes PV pour affichage sur le Dashboard.
     *
     * Pour chaque système, cette méthode :
     * 1. Charge le module lié (via moduleId)
     * 2. Charge l'onduleur lié (via inverterId)
     * 3. Calcule la dernière puissance AC connue (données "live")
     * 4. Calcule l'énergie produite aujourd'hui
     * 5. Délègue le mapping Entité → DTO au PvSystemMapper
     *
     * @return Liste de SystemSummaryDto prêts pour le Frontend
     */
    public List<SystemSummaryDto> getAllSystemsSummary() {
        return systemRepo.findAll().stream()
                .map(this::buildSystemSummary)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les détails complets d'un système PV par son ID.
     *
     * @param systemId L'identifiant du système (ex: "SYS-001")
     * @return Un Optional<PvSystem> (vide si l'ID n'existe pas)
     */
    public Optional<PvSystem> getSystemById(String systemId) {
        return systemRepo.findById(systemId);
    }

    /**
     * Construit le DTO résumé complet pour un système PV donné.
     * Méthode privée utilisée dans le stream de getAllSystemsSummary().
     *
     * @param system L'entité système PV à convertir
     * @return Un SystemSummaryDto enrichi avec les données live, module et onduleur
     */
    private SystemSummaryDto buildSystemSummary(PvSystem system) {
        // 1. Charger le module lié (null si moduleId est null ou introuvable)
        ModuleSpec module = null;
        if (system.getModuleId() != null) {
            module = moduleRepo.findById(system.getModuleId()).orElse(null);
        }

        // 2. Charger l'onduleur lié (null si inverterId est null ou introuvable)
        Inverter inverter = null;
        if (system.getInverterId() != null) {
            inverter = inverterRepo.findById(system.getInverterId()).orElse(null);
        }

        // 3. Construire le DTO via le mapper (mapping Entité → DTO)
        SystemSummaryDto dto = pvSystemMapper.toSummaryDto(system, module, inverter);

        // 4. Enrichir avec la dernière puissance AC mesurée (données "live")
        acRepo.findFirstBySystemIdOrderByTimestampDesc(system.getSystemId())
                .ifPresent(lastAc -> dto.setLastAcPowerKw(lastAc.getAcPowerKw()));

        // 5. Calculer l'énergie produite aujourd'hui
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        Double dailyEnergy = acRepo.sumDailyEnergyBySystemId(
                system.getSystemId(), startOfDay, endOfDay);
        dto.setDailyEnergyKwh(dailyEnergy != null ? dailyEnergy : 0.0);

        return dto;
    }
}
