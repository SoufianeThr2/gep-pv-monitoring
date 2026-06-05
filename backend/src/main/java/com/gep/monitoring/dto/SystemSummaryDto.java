package com.gep.monitoring.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SystemSummaryDto {
    private String systemId;
    private String systemName;
    private Double totalCapacityKwc;
    private LocalDate commissioningDate;

    private String orientation;
    private Integer tiltAngle;
    private Integer nbStrings;

    private Double lastAcPowerKw;
    private Double dailyEnergyKwh;

    // Nouveaux sous-objets pour l'affichage détaillé sur les cartes
    private ModuleDto module;
    private InverterDto inverter;

    @Data
    public static class ModuleDto {
        private String brand;
        private String model;
        private String technology;
        private Integer powerWc;
        private Integer totalModules; // Calculé : nbStrings * nbPerString
    }

    @Data
    public static class InverterDto {
        private String brand;
        private String model;
        private Double powerKwAc;
        private Integer nbMppt;
        private String serialNumber;
    }
}