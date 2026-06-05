package com.gep.monitoring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de réponse pour le résumé d'un système PV (Dashboard principal).
 * Envoyé par le GET /api/systems/system vers le Frontend.
 * Agrège les données de PvSystem + ModuleSpec + Inverter + données live.
 * Ce DTO ne contient QUE ce que le Frontend a besoin d'afficher sur les cartes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemSummaryDto {

    // --- Informations du Système PV ---
    private String systemId;            // Ex: "SYS-001"
    private String systemName;          // Ex: "GEP Zone Nord"
    private Double totalCapacityKwc;    // Puissance installée totale en kWc
    private LocalDate commissioningDate; // Date de mise en service
    private String orientation;         // Ex: "South", "South-East"
    private Integer tiltAngle;          // Inclinaison en degrés
    private Integer nbStrings;          // Nombre de strings

    // --- Données Live (dernière mesure connue) ---
    private Double lastAcPowerKw;       // Dernière puissance AC mesurée en kW
    private Double dailyEnergyKwh;      // Energie produite aujourd'hui en kWh

    // --- Sous-objet Module ---
    private ModuleDto module;

    // --- Sous-objet Onduleur ---
    private InverterDto inverter;

    /**
     * Sous-DTO pour les spécifications du module photovoltaïque.
     * Correspond aux données de la table "modules".
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModuleDto {
        private String brand;           // Marque du panneau
        private String model;           // Modèle du panneau
        private String technology;      // Technologie (ex: "Mono-Si")
        private Integer powerWc;        // Puissance unitaire en Wc
        private Integer totalModules;   // Calculé : nbStrings * nbPerString
    }

    /**
     * Sous-DTO pour les spécifications de l'onduleur.
     * Correspond aux données de la table "inverters".
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InverterDto {
        private String brand;           // Marque de l'onduleur
        private String model;           // Modèle de l'onduleur
        private Double powerKwAc;       // Puissance AC nominale en kW
        private Integer nbMppt;         // Nombre d'entrées MPPT
        private String serialNumber;    // Numéro de série
    }
}