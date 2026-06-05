package com.gep.monitoring.service;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.entity.AcProduction;
import com.gep.monitoring.entity.DcProduction;
import com.gep.monitoring.repository.AcProductionRepository;
import com.gep.monitoring.repository.DcProductionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<ProductionChartDto> getSystemProductionData(String systemId) {
        return getSystemProductionData(systemId, null, null);
    }

    public List<ProductionChartDto> getSystemProductionData(String systemId, String start, String end) {
        List<AcProduction> acList;
        List<DcProduction> dcList;

        if (start != null && end != null) {
            LocalDateTime startDt = LocalDate.parse(start).atStartOfDay();
            LocalDateTime endDt = LocalDate.parse(end).atTime(23, 59, 59);
            acList = acRepo.findBySystemIdAndTimestampBetweenOrderByTimestampAsc(systemId, startDt, endDt);
            dcList = dcRepo.findBySystemIdAndTimestampBetweenOrderByTimestampAsc(systemId, startDt, endDt);
        } else {
            acList = acRepo.findBySystemIdOrderByTimestampAsc(systemId);
            dcList = dcRepo.findBySystemIdOrderByTimestampAsc(systemId);
        }

        Map<LocalDateTime, ProductionChartDto> chartMap = new TreeMap<>();

        for (AcProduction ac : acList) {
            chartMap.put(ac.getTimestamp(), new ProductionChartDto(
                    ac.getTimestamp(),
                    ac.getAcPowerKw(),
                    0.0,
                    0.0,
                    ac.getAcEnergyKwh(),
                    0.0,
                    0.0,
                    25.0
            ));
        }

        for (DcProduction dc : dcList) {
            double simulatedTemp = 20.0 + (dc.getIrradianceWm2() / 1000.0) * 15.0;

            ProductionChartDto dto = chartMap.getOrDefault(dc.getTimestamp(),
                    new ProductionChartDto(dc.getTimestamp(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, simulatedTemp));

            dto.setDcPower(dc.getDcPowerKw());
            dto.setIrradiance(dc.getIrradianceWm2());
            dto.setDcVoltage(dc.getDcVoltageV());
            dto.setDcCurrent(dc.getDcCurrentA());
            dto.setAmbientTemperature(simulatedTemp);

            chartMap.put(dc.getTimestamp(), dto);
        }

        return new ArrayList<>(chartMap.values());
    }
}