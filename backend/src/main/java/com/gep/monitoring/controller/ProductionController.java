package com.gep.monitoring.controller;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 1. MODIFICATION ICI : On aligne la base de l'URL sur celle de React
@RequestMapping("/api/systems")
@CrossOrigin(origins = "*")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    // 2. MODIFICATION ICI : On ajoute /production à la fin, et on accepte les paramètres de date (start, end)
    @GetMapping("/{systemId}/production")
    public ResponseEntity<List<ProductionChartDto>> getProductionCharts(
            @PathVariable String systemId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {

        // Pour l'instant on n'utilise pas start et end dans le service, mais on les accepte pour ne pas faire planter React
        List<ProductionChartDto> chartData = productionService.getSystemProductionData(systemId);

        if(chartData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(chartData);
    }
}