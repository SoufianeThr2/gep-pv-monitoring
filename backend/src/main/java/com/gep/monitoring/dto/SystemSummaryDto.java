package com.gep.monitoring.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SystemSummaryDto {
    private String systemId;
    private String systemName;
    private Double totalCapacityKwc;
    private LocalDate commissioningDate;

    // Nouveaux champs pour remplir le milieu de la carte sur le Dashboard
    private String orientation;
    private Integer tiltAngle;
    private Integer nbStrings;

    // Données en temps réel (affichées en bas de la carte, fond vert foncé)
    private Double lastAcPowerKw; // Renommé pour correspondre exactement à React
    private Double dailyEnergyKwh;

    // NOTE : Pour les détails techniques (Marque du module, Modèle onduleur),
    // le frontend s'attend à des sous-objets (module et inverter).
    // Si tu veux les envoyer, il faudra créer des DTOs imbriqués plus tard.
}