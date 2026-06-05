package com.gep.monitoring.mappers;

import com.gep.monitoring.dto.SystemSummaryDto;
import com.gep.monitoring.entity.Inverter;
import com.gep.monitoring.entity.ModuleSpec;
import com.gep.monitoring.entity.PvSystem;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion des entités PV vers les DTOs.
 *
 * Responsabilité unique : transformer les données brutes (entités JPA)
 * en objets de réponse JSON (DTOs) pour le Frontend.
 *
 * Ce mapper centralise toute la logique de mapping qui était avant
 * dispersée directement dans PvSystemController (lignes 33-79).
 */
@Component
public class PvSystemMapper {

    /**
     * Convertit un PvSystem + ses entités liées en SystemSummaryDto.
     *
     * @param system   L'entité système PV (données de la table pvsystems)
     * @param module   L'entité module lié (peut être null si non trouvé)
     * @param inverter L'entité onduleur lié (peut être null si non trouvé)
     * @return Un SystemSummaryDto prêt à être envoyé au Frontend en JSON
     */
    public SystemSummaryDto toSummaryDto(PvSystem system, ModuleSpec module, Inverter inverter) {
        SystemSummaryDto dto = new SystemSummaryDto();

        // --- Mapping des champs du système ---
        dto.setSystemId(system.getSystemId());
        dto.setSystemName(system.getSystemName());
        dto.setTotalCapacityKwc(system.getTotalCapacityKwc());
        dto.setCommissioningDate(system.getCommissioningDate());
        dto.setOrientation(system.getOrientation());
        dto.setTiltAngle(system.getTiltAngle());
        dto.setNbStrings(system.getNbStrings());

        // --- Données live (valeurs par défaut — à enrichir par le service) ---
        dto.setLastAcPowerKw(0.0);
        dto.setDailyEnergyKwh(0.0);

        // --- Mapping du module (uniquement si l'entité module existe) ---
        if (module != null) {
            dto.setModule(toModuleDto(system, module));
        }

        // --- Mapping de l'onduleur (uniquement si l'entité onduleur existe) ---
        if (inverter != null) {
            dto.setInverter(toInverterDto(inverter));
        }

        return dto;
    }

    /**
     * Convertit un ModuleSpec + données du système en ModuleDto.
     * Calcule le nombre total de modules (nbStrings * nbPerString).
     *
     * @param system Le système parent (nécessaire pour nbStrings)
     * @param module L'entité module à convertir
     * @return Un ModuleDto avec le calcul du nombre de modules total
     */
    private SystemSummaryDto.ModuleDto toModuleDto(PvSystem system, ModuleSpec module) {
        SystemSummaryDto.ModuleDto modDto = new SystemSummaryDto.ModuleDto();
        modDto.setBrand(module.getBrand());
        modDto.setModel(module.getModel());
        modDto.setTechnology(module.getTechnology());
        modDto.setPowerWc(module.getPowerWc());

        // Calcul sécurisé : on vérifie que les deux valeurs ne sont pas null avant de multiplier
        if (system.getNbStrings() != null && module.getNbPerString() != null) {
            modDto.setTotalModules(system.getNbStrings() * module.getNbPerString());
        } else {
            modDto.setTotalModules(0);
        }

        return modDto;
    }

    /**
     * Convertit un Inverter en InverterDto.
     *
     * @param inverter L'entité onduleur à convertir
     * @return Un InverterDto avec les données de l'onduleur
     */
    private SystemSummaryDto.InverterDto toInverterDto(Inverter inverter) {
        SystemSummaryDto.InverterDto invDto = new SystemSummaryDto.InverterDto();
        invDto.setBrand(inverter.getBrand());
        invDto.setModel(inverter.getModel());
        invDto.setPowerKwAc(inverter.getPowerKwAc());
        invDto.setNbMppt(inverter.getNbMppt());
        invDto.setSerialNumber(inverter.getSerialNumber());
        return invDto;
    }
}
