package com.gep.monitoring.repository;

import com.gep.monitoring.entity.ModuleSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour accéder aux données des modules photovoltaïques.
 *
 * La clé primaire est de type String (ex: "MOD-001").
 * Hérite de JpaRepository pour les opérations CRUD standard.
 * Les méthodes héritées (findById, findAll, save, etc.) suffisent
 * pour les besoins actuels de l'application.
 */
@Repository
public interface ModuleSpecRepository extends JpaRepository<ModuleSpec, String> {
    // Les méthodes CRUD de base suffisent pour ce repository.
    // findById(moduleId) est utilisé par PvSystemService pour récupérer les specs du module.
}