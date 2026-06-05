package com.gep.monitoring.repository;

import com.gep.monitoring.entity.Inverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour accéder aux données des onduleurs (inverters).
 *
 * La clé primaire est de type String (ex: "INV-001").
 * Hérite de JpaRepository pour les opérations CRUD standard.
 */
@Repository
public interface InverterRepository extends JpaRepository<Inverter, String> {

    /**
     * Récupère tous les onduleurs liés à un système PV spécifique.
     * Utile si un système possède plusieurs onduleurs.
     *
     * SQL généré : SELECT * FROM inverters WHERE system_id = ?
     */
    List<Inverter> findBySystemId(String systemId);
}