package com.gep.monitoring.mappers;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.entity.AcProduction;
import com.gep.monitoring.entity.DcProduction;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion des entités de production vers les DTOs.
 *
 * Responsabilité unique : transformer les données brutes AC et DC (entités JPA)
 * en objets de réponse JSON (ProductionChartDto) pour les graphiques du Frontend.
 *
 * Ce mapper extrait la logique de fusion AC/DC qui était dans ProductionService.
 */
@Component
public class ProductionMapper {

    /**
     * Convertit une mesure AcProduction en ProductionChartDto de base.
     * Les champs DC sont initialisés à 0.0 et seront enrichis si une mesure DC
     * au même timestamp existe (via enrichWithDcData).
     *
     * @param ac L'entité de production AC
     * @return Un ProductionChartDto partiellement rempli (données AC uniquement)
     */
    public ProductionChartDto fromAcProduction(AcProduction ac) {
        return new ProductionChartDto(
                ac.getTimestamp(),
                ac.getAcPowerKw(),      // acPower
                0.0,                    // dcPower (sera complété par enrichWithDcData)
                0.0,                    // irradiance (sera complété par enrichWithDcData)
                ac.getAcEnergyKwh(),    // acEnergy
                0.0,                    // dcVoltage (sera complété par enrichWithDcData)
                0.0,                    // dcCurrent (sera complété par enrichWithDcData)
                25.0                    // ambientTemperature (valeur par défaut)
        );
    }

    /**
     * Enrichit un ProductionChartDto existant avec les données DC.
     * Appelé quand on trouve une mesure DC correspondant au même timestamp.
     *
     * La température ambiante est simulée à partir de l'irradiance :
     * température = 20°C + (irradiance / 1000) * 15°C
     * (Formule de simulation car les CSV ne contiennent pas de données météo.)
     *
     * @param dto Le DTO à enrichir (déjà créé depuis fromAcProduction)
     * @param dc  L'entité de production DC correspondant au même timestamp
     */
    public void enrichWithDcData(ProductionChartDto dto, DcProduction dc) {
        // Simulation de la température ambiante depuis l'irradiance solaire
        double simulatedTemp = 20.0 + (dc.getIrradianceWm2() / 1000.0) * 15.0;

        dto.setDcPower(dc.getDcPowerKw());
        dto.setIrradiance(dc.getIrradianceWm2());
        dto.setDcVoltage(dc.getDcVoltageV());
        dto.setDcCurrent(dc.getDcCurrentA());
        dto.setAmbientTemperature(simulatedTemp);
    }

    /**
     * Crée un ProductionChartDto depuis une mesure DC uniquement
     * (quand il n'y a pas de mesure AC au même timestamp).
     *
     * @param dc L'entité de production DC
     * @return Un ProductionChartDto avec données DC uniquement
     */
    public ProductionChartDto fromDcProductionOnly(DcProduction dc) {
        double simulatedTemp = 20.0 + (dc.getIrradianceWm2() / 1000.0) * 15.0;

        return new ProductionChartDto(
                dc.getTimestamp(),
                0.0,                        // acPower (pas de données AC)
                dc.getDcPowerKw(),           // dcPower
                dc.getIrradianceWm2(),       // irradiance
                0.0,                         // acEnergy (pas de données AC)
                dc.getDcVoltageV(),          // dcVoltage
                dc.getDcCurrentA(),          // dcCurrent
                simulatedTemp                // température simulée
        );
    }
}
