package com.gep.monitoring.controller;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*") // Pour ne pas bloquer React
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    // Point d'entrée pour les graphiques d'un système spécifique
    @GetMapping("/{systemId}")
    public ResponseEntity<List<ProductionChartDto>> getProductionCharts(@PathVariable String systemId) {
        List<ProductionChartDto> chartData = productionService.getSystemProductionData(systemId);

        if(chartData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(chartData);
    }
}