package com.gep.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de réponse pour les graphiques de production (Page de détail).
 * Envoyé par le GET /api/production/{systemId} vers le Frontend.
 * Agrège les données AC et DC au même timestamp pour un affichage en graphique.
 *
 * Mapping des données :
 * - acPower, acEnergy    → vient de la table ac_production
 * - dcPower, dcVoltage,
 *   dcCurrent, irradiance → vient de la table dc_production
 * - ambientTemperature   → valeur simulée (calculée depuis l'irradiance)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionChartDto {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;    // Date et heure de la mesure

    private Double acPower;             // Puissance AC en kW
    private Double dcPower;             // Puissance DC en kW
    private Double irradiance;          // Irradiance solaire en W/m²
    private Double acEnergy;            // Energie AC en kWh
    private Double dcVoltage;           // Tension DC en Volts
    private Double dcCurrent;           // Courant DC en Ampères
    private Double ambientTemperature;  // Température ambiante simulée en °C
}