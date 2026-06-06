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
        ProductionChartDto dto = new ProductionChartDto();
        dto.setTimestamp(ac.getTimestamp());
        dto.setAcPower(ac.getAcPowerKw());
        dto.setDcPower(0.0);                    // sera complété par enrichWithDcData
        dto.setIrradiance(0.0);                 // sera complété par enrichWithDcData
        dto.setAcEnergy(ac.getAcEnergyKwh());
        dto.setDcVoltage(0.0);                  // sera complété par enrichWithDcData
        dto.setDcCurrent(0.0);                  // sera complété par enrichWithDcData
        dto.setAmbientTemperature(25.0);        // valeur par défaut
        return dto;
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

        ProductionChartDto dto = new ProductionChartDto();
        dto.setTimestamp(dc.getTimestamp());
        dto.setAcPower(0.0);                        // pas de données AC
        dto.setDcPower(dc.getDcPowerKw());
        dto.setIrradiance(dc.getIrradianceWm2());
        dto.setAcEnergy(0.0);                       // pas de données AC
        dto.setDcVoltage(dc.getDcVoltageV());
        dto.setDcCurrent(dc.getDcCurrentA());
        dto.setAmbientTemperature(simulatedTemp);
        return dto;
    }
}
