package com.gep.monitoring.service;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.entity.AcProduction;
import com.gep.monitoring.entity.DcProduction;
import com.gep.monitoring.repository.AcProductionRepository;
import com.gep.monitoring.repository.DcProductionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ProductionService {

    private final AcProductionRepository acRepo;
    private final DcProductionRepository dcRepo;

    public ProductionService(AcProductionRepository acRepo, DcProductionRepository dcRepo) {
        this.acRepo = acRepo;
        this.dcRepo = dcRepo;
    }

    // Algorithme de fusion des séries temporelles AC et DC
    public List<ProductionChartDto> getSystemProductionData(String systemId) {
        List<AcProduction> acList = acRepo.findBySystemIdOrderByTimestampAsc(systemId);
        List<DcProduction> dcList = dcRepo.findBySystemIdOrderByTimestampAsc(systemId);

        // TreeMap trie automatiquement par date (Timestamp)
        Map<LocalDateTime, ProductionChartDto> chartMap = new TreeMap<>();

        // 1. On parcourt les données AC et on les met dans la Map
        for (AcProduction ac : acList) {
            chartMap.put(ac.getTimestamp(), new ProductionChartDto(ac.getTimestamp(), ac.getAcPowerKw(), 0.0, 0.0));
        }

        // 2. On parcourt les données DC et on complète les informations manquantes
        for (DcProduction dc : dcList) {
            ProductionChartDto dto = chartMap.getOrDefault(dc.getTimestamp(), new ProductionChartDto(dc.getTimestamp(), 0.0, 0.0, 0.0));
            dto.setDcPower(dc.getDcPowerKw());
            dto.setIrradiance(dc.getIrradianceWm2());
            chartMap.put(dc.getTimestamp(), dto);
        }

        return new ArrayList<>(chartMap.values());
    }
}