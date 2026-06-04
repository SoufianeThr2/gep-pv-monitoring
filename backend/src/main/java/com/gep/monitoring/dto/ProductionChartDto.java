package com.gep.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor // Toujours une bonne pratique d'ajouter un constructeur vide
public class ProductionChartDto {
    private LocalDateTime timestamp;

    // Champs déjà présents
    private Double acPower;
    private Double dcPower;
    private Double irradiance;

    // Nouveaux champs ajoutés pour remplir TOUS les graphiques
    private Double acEnergy;
    private Double dcVoltage;
    private Double dcCurrent;
    private Double ambientTemperature;
}