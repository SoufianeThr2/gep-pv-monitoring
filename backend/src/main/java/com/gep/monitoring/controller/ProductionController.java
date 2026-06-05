package com.gep.monitoring.controller;

import com.gep.monitoring.dto.ProductionChartDto;
import com.gep.monitoring.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller gérant les endpoints de données de production (AC et DC).
 *
 * Responsabilité unique : recevoir les requêtes HTTP avec les paramètres
 * de filtrage, déléguer au ProductionService, et retourner la réponse.
 *
 * Ce controller ne contient AUCUNE logique métier.
 * Il n'injecte qu'UN SEUL service (ProductionService).
 * Il ne parle à AUCUN Repository directement.
 *
 * Endpoints exposés :
 * GET /api/production?systemId={id}&start={date}&end={date}
 *     → Données de production filtrées par systemId et plage de dates
 * GET /api/production/{systemId}?start={date}&end={date}
 *     → Même fonctionnalité mais avec systemId dans le path (compatibilité Frontend)
 */
@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    /**
     * Retourne les données de production pour un système PV via query param.
     * Route utilisée par le Frontend React dans les graphiques.
     *
     * @param systemId Identifiant du système (obligatoire)
     * @param start    Date de début au format yyyy-MM-dd (optionnel)
     * @param end      Date de fin au format yyyy-MM-dd (optionnel)
     * @return HTTP 200 avec la liste de ProductionChartDto triée par timestamp
     */
    @GetMapping
    public ResponseEntity<List<ProductionChartDto>> getProductionByQueryParam(
            @RequestParam String systemId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        List<ProductionChartDto> data = productionService.getSystemProductionData(systemId, start, end);
        return ResponseEntity.ok(data);
    }

    /**
     * Retourne les données de production pour un système PV via path variable.
     * Route alternative pour compatibilité avec le Frontend.
     *
     * @param systemId Identifiant du système dans le chemin URL
     * @param start    Date de début au format yyyy-MM-dd (optionnel)
     * @param end      Date de fin au format yyyy-MM-dd (optionnel)
     * @return HTTP 200 avec la liste de ProductionChartDto triée par timestamp
     */
    @GetMapping("/{systemId}")
    public ResponseEntity<List<ProductionChartDto>> getProductionByPathVariable(
            @PathVariable String systemId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        List<ProductionChartDto> data = productionService.getSystemProductionData(systemId, start, end);
        return ResponseEntity.ok(data);
    }
}