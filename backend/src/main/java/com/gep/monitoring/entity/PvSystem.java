package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * Entité représentant un système photovoltaïque.
 * Correspond à la table "pvsystems" en base de données.
 * Contient les métadonnées statiques d'un système PV.
 */
@Entity
@Table(name = "pvsystems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PvSystem {

    @Id
    @Column(name = "system_id")
    private String systemId;           // Ex: "SYS-001"

    @Column(name = "system_name")
    private String systemName;         // Ex: "GEP Zone Nord"

    @Column(name = "latitude")
    private Double latitude;           // Coordonnée GPS latitude

    @Column(name = "longitude")
    private Double longitude;          // Coordonnée GPS longitude

    @Column(name = "total_capacity_kwc")
    private Double totalCapacityKwc;   // Puissance installée en kWc

    @Column(name = "commissioning_date")
    private LocalDate commissioningDate; // Date de mise en service

    @Column(name = "orientation")
    private String orientation;        // Ex: "South", "South-East"

    @Column(name = "tilt_angle")
    private Integer tiltAngle;         // Inclinaison des panneaux en degrés

    @Column(name = "nb_strings")
    private Integer nbStrings;         // Nombre de strings (rangées de panneaux)

    @Column(name = "module_id")
    private String moduleId;           // Clé vers ModuleSpec

    @Column(name = "inverter_id")
    private String inverterId;         // Clé vers Inverter
}