package com.gep.monitoring.service;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.entity.AcProduction;
import com.gep.monitoring.entity.DcProduction;
import com.gep.monitoring.mappers.ProductionMapper;
import com.gep.monitoring.repository.AcProductionRepository;
import com.gep.monitoring.repository.DcProductionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Service gérant la logique métier liée aux données de production (AC et DC).
 *
 * Responsabilités :
 * - Récupérer les données AC et DC depuis les repositories
 * - Fusionner les données AC et DC par timestamp commun
 * - Déléguer la conversion Entité → DTO au ProductionMapper
 *
 * Ce service a été refactorisé pour déléguer le mapping au ProductionMapper,
 * conformément au principe de responsabilité unique (SRP).
 */
@Service
public class ProductionService {

    private final AcProductionRepository acRepo;
    private final DcProductionRepository dcRepo;
    private final ProductionMapper productionMapper;

    public ProductionService(AcProductionRepository acRepo,
                             DcProductionRepository dcRepo,
                             ProductionMapper productionMapper) {
        this.acRepo = acRepo;
        this.dcRepo = dcRepo;
        this.productionMapper = productionMapper;
    }

    /**
     * Surcharge sans filtre de dates — retourne toutes les données disponibles.
     *
     * @param systemId L'identifiant du système PV
     * @return Liste de ProductionChartDto triée par timestamp croissant
     */
    public List<ProductionChartDto> getSystemProductionData(String systemId) {
        return getSystemProductionData(systemId, null, null);
    }

    /**
     * Récupère les données de production AC et DC d'un système PV
     * pour une période donnée, et les fusionne par timestamp.
     *
     * Algorithme de fusion :
     * 1. Récupérer toutes les mesures AC (filtrées par date si fournie)
     * 2. Créer un DTO pour chaque mesure AC via le ProductionMapper
     * 3. Récupérer toutes les mesures DC (filtrées par date si fournie)
     * 4. Pour chaque mesure DC, enrichir le DTO existant au même timestamp
     *    OU créer un nouveau DTO si aucune mesure AC ne correspond
     * 5. Retourner la liste triée par timestamp (TreeMap garantit l'ordre)
     *
     * @param systemId L'identifiant du système PV (ex: "SYS-001")
     * @param start    Date de début au format "yyyy-MM-dd" (null = pas de filtre)
     * @param end      Date de fin au format "yyyy-MM-dd" (null = pas de filtre)
     * @return Liste de ProductionChartDto fusionnée et triée chronologiquement
     */
    public List<ProductionChartDto> getSystemProductionData(String systemId, String start, String end) {
        List<AcProduction> acList;
        List<DcProduction> dcList;

        // Récupération des données selon qu'un filtre de dates est fourni ou non
        if (start != null && end != null) {
            LocalDateTime startDt = LocalDate.parse(start).atStartOfDay();
            LocalDateTime endDt = LocalDate.parse(end).atTime(23, 59, 59);
            acList = acRepo.findBySystemIdAndTimestampBetweenOrderByTimestampAsc(systemId, startDt, endDt);
            dcList = dcRepo.findBySystemIdAndTimestampBetweenOrderByTimestampAsc(systemId, startDt, endDt);
        } else {
            acList = acRepo.findBySystemIdOrderByTimestampAsc(systemId);
            dcList = dcRepo.findBySystemIdOrderByTimestampAsc(systemId);
        }

        // TreeMap utilisé pour garantir le tri chronologique par timestamp
        Map<LocalDateTime, ProductionChartDto> chartMap = new TreeMap<>();

        // Étape 1 : Créer un DTO pour chaque mesure AC
        for (AcProduction ac : acList) {
            chartMap.put(ac.getTimestamp(), productionMapper.fromAcProduction(ac));
        }

        // Étape 2 : Fusionner avec les données DC au même timestamp
        for (DcProduction dc : dcList) {
            if (chartMap.containsKey(dc.getTimestamp())) {
                // Un DTO AC existe déjà pour ce timestamp → on l'enrichit avec les données DC
                productionMapper.enrichWithDcData(chartMap.get(dc.getTimestamp()), dc);
            } else {
                // Pas de mesure AC à ce timestamp → on crée un DTO DC-only
                chartMap.put(dc.getTimestamp(), productionMapper.fromDcProductionOnly(dc));
            }
        }

        return new ArrayList<>(chartMap.values());
    }
}