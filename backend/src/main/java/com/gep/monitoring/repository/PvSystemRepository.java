package com.gep.monitoring.repository;

import com.gep.monitoring.entity.PvSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour accéder aux données des systèmes PV.
 *
 * Hérite de JpaRepository<PvSystem, String> ce qui fournit gratuitement :
 * - findAll()       → SELECT * FROM pvsystems
 * - findById(id)   → SELECT * FROM pvsystems WHERE system_id = ?
 * - save(entity)   → INSERT ou UPDATE
 * - delete(entity) → DELETE
 * - count()        → SELECT COUNT(*) FROM pvsystems
 *
 * La clé primaire est de type String (ex: "SYS-001").
 */
@Repository
public interface PvSystemRepository extends JpaRepository<PvSystem, String> {
    // Les méthodes CRUD de base suffisent pour ce repository.
    // Spring Data JPA les génère automatiquement depuis JpaRepository.
}