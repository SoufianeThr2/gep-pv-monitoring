package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entité représentant les spécifications techniques d'un module photovoltaïque.
 * Correspond à la table "modules" en base de données.
 * Un module est lié à un système PV via module_id.
 */
@Entity
@Table(name = "modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleSpec {

    @Id
    @Column(name = "module_id")
    private String moduleId;            // Identifiant unique du module

    private String brand;               // Marque (ex: "Jinko Solar")
    private String model;               // Modèle du panneau

    private String technology;          // Technologie (ex: "Mono-Si", "Mono PERC")

    @Column(name = "power_wc")
    private Integer powerWc;            // Puissance unitaire en Wc

    @Column(name = "nb_per_string")
    private Integer nbPerString;        // Nombre de panneaux par string

    @Column(name = "voc_v")
    private Double vocV;                // Tension de circuit ouvert en Volts

    @Column(name = "isc_a")
    private Double iscA;                // Courant de court-circuit en Ampères

    @Column(name = "temp_coeff_pmax")
    private Double tempCoeffPmax;       // Coefficient de température de la puissance max
}