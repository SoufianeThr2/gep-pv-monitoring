package com.gep.monitoring.controller;

import com.gep.monitoring.dto.SystemSummaryDto;
import com.gep.monitoring.entity.PvSystem;
import com.gep.monitoring.service.PvSystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller gérant les endpoints des systèmes PV.
 *
 * Responsabilité unique : recevoir les requêtes HTTP, déléguer
 * au PvSystemService, et retourner la réponse HTTP appropriée.
 *
 * Ce controller ne contient AUCUNE logique métier.
 * Il n'injecte qu'UN SEUL service (PvSystemService).
 * Il ne parle à AUCUN Repository directement.
 *
 * Endpoints exposés :
 * GET /api/systems/system     → Liste de tous les systèmes (Dashboard)
 * GET /api/systems/{id}       → Détail d'un système par son ID
 */
@RestController
@RequestMapping("/api/systems")
@CrossOrigin(origins = "*")
public class PvSystemController {

    private final PvSystemService pvSystemService;

    public PvSystemController(PvSystemService pvSystemService) {
        this.pvSystemService = pvSystemService;
    }

    /**
     * Retourne le résumé de tous les systèmes PV pour le Dashboard.
     *
     * @return HTTP 200 avec la liste de SystemSummaryDto
     */
    @GetMapping("/system")
    public ResponseEntity<List<SystemSummaryDto>> getAllSystemsSummary() {
        List<SystemSummaryDto> summaries = pvSystemService.getAllSystemsSummary();
        return ResponseEntity.ok(summaries);
    }

    /**
     * Retourne les données complètes d'un système PV spécifique.
     *
     * @param id L'identifiant du système (ex: "SYS-001")
     * @return HTTP 200 avec l'entité PvSystem, ou HTTP 404 si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<PvSystem> getSystemById(@PathVariable String id) {
        return pvSystemService.getSystemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}