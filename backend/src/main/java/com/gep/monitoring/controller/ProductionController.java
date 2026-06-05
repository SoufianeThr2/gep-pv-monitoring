package com.gep.monitoring.controller;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.service.ProductionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionChartDto> getProductionDataQuery(
            @RequestParam String systemId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        return productionService.getSystemProductionData(systemId, start, end);
    }

    @GetMapping("/{systemId}")
    public List<ProductionChartDto> getProductionDataPath(
            @PathVariable String systemId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        return productionService.getSystemProductionData(systemId, start, end);
    }
}