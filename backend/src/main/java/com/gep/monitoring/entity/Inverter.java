package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entité représentant les spécifications techniques d'un onduleur (inverter).
 * Correspond à la table "inverters" en base de données.
 * Un onduleur est lié à un système PV via system_id.
 */
@Entity
@Table(name = "inverters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inverter {

    @Id
    @Column(name = "inverter_id")
    private String inverterId;          // Identifiant unique de l'onduleur

    private String brand;               // Marque (ex: "Huawei", "Sungrow")
    private String model;               // Modèle (ex: "SUN2000-100KTL")

    @Column(name = "power_kw_ac")
    private Double powerKwAc;           // Puissance AC nominale en kW

    @Column(name = "nb_mppt")
    private Integer nbMppt;             // Nombre d'entrées MPPT

    @Column(name = "max_input_voltage_v")
    private Double maxInputVoltageV;    // Tension d'entrée max en Volts

    @Column(name = "max_input_current_a")
    private Double maxInputCurrentA;    // Courant d'entrée max en Ampères

    @Column(name = "efficiency_pct")
    private Double efficiencyPct;       // Rendement en pourcentage

    @Column(name = "serial_number")
    private String serialNumber;        // Numéro de série physique

    @Column(name = "system_id")
    private String systemId;            // Référence au système PV parent
}