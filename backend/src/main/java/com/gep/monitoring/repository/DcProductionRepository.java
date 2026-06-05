package com.gep.monitoring.repository;

import com.gep.monitoring.entity.DcProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour accéder aux données de production DC (courant continu).
 *
 * Toutes les méthodes suivent la convention de nommage Spring Data JPA.
 * Spring génère automatiquement le SQL correspondant.
 */
@Repository
public interface DcProductionRepository extends JpaRepository<DcProduction, Long> {

    /**
     * Récupère toutes les mesures DC d'un système, triées par date croissante.
     * Utilisé quand aucun filtre de date n'est fourni.
     *
     * SQL généré : SELECT * FROM dc_production WHERE system_id = ? ORDER BY timestamp ASC
     */
    List<DcProduction> findBySystemIdOrderByTimestampAsc(String systemId);

    /**
     * Récupère les mesures DC d'un système dans un intervalle de dates, triées par date.
     * Utilisé quand l'utilisateur sélectionne une plage de dates dans les graphiques.
     *
     * SQL généré : SELECT * FROM dc_production
     *              WHERE system_id = ? AND timestamp BETWEEN ? AND ?
     *              ORDER BY timestamp ASC
     */
    List<DcProduction> findBySystemIdAndTimestampBetweenOrderByTimestampAsc(
            String systemId, LocalDateTime start, LocalDateTime end);
}