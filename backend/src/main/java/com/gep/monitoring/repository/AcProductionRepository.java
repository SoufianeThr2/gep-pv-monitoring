package com.gep.monitoring.repository;

import com.gep.monitoring.entity.AcProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour accéder aux données de production AC (courant alternatif).
 *
 * Toutes les méthodes ci-dessous suivent la convention de nommage Spring Data JPA.
 * Spring génère automatiquement le SQL correspondant sans qu'on ait besoin de l'écrire.
 *
 * Convention de nommage :
 * findBy[Champ][Condition][Et|Ou][AutreChamp][OrderBy][Champ][Asc|Desc]
 */
@Repository
public interface AcProductionRepository extends JpaRepository<AcProduction, Long> {

    /**
     * Récupère toutes les mesures AC d'un système, triées par date croissante.
     * Utilisé quand aucun filtre de date n'est fourni.
     *
     * SQL généré : SELECT * FROM ac_production WHERE system_id = ? ORDER BY timestamp ASC
     */
    List<AcProduction> findBySystemIdOrderByTimestampAsc(String systemId);

    /**
     * Récupère les mesures AC d'un système dans un intervalle de dates, triées par date.
     * Utilisé quand l'utilisateur sélectionne une plage de dates dans les graphiques.
     *
     * SQL généré : SELECT * FROM ac_production
     *              WHERE system_id = ? AND timestamp BETWEEN ? AND ?
     *              ORDER BY timestamp ASC
     */
    List<AcProduction> findBySystemIdAndTimestampBetweenOrderByTimestampAsc(
            String systemId, LocalDateTime start, LocalDateTime end);

    /**
     * Récupère la dernière mesure AC d'un système (pour afficher les données "live" sur le Dashboard).
     *
     * SQL généré : SELECT * FROM ac_production WHERE system_id = ?
     *              ORDER BY timestamp DESC LIMIT 1
     */
    Optional<AcProduction> findFirstBySystemIdOrderByTimestampDesc(String systemId);

    /**
     * Calcule l'énergie totale produite aujourd'hui par un système.
     * Utilise une requête JPQL personnalisée (pas de convention de nommage possible pour SUM).
     *
     * @param systemId L'identifiant du système PV
     * @param start    Début de la journée (00:00:00)
     * @param end      Fin de la journée (23:59:59)
     * @return La somme des énergies en kWh pour la journée
     */
    @Query("SELECT COALESCE(SUM(a.acEnergyKwh), 0.0) FROM AcProduction a " +
           "WHERE a.systemId = :systemId AND a.timestamp BETWEEN :start AND :end")
    Double sumDailyEnergyBySystemId(
            @Param("systemId") String systemId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}