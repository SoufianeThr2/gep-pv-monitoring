package com.gep.monitoring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant une mesure horaire de production DC (courant continu).
 * Correspond à la table "dc_production" en base de données.
 * Une ligne = 1 heure de mesure pour 1 système PV.
 */
@Entity
@Table(name = "dc_production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // ID auto-généré

    private LocalDateTime timestamp;    // Date et heure de la mesure

    @Column(name = "system_id")
    private String systemId;            // Référence au système PV

    @Column(name = "dc_power_kw")
    private Double dcPowerKw;           // Puissance DC en kW

    @Column(name = "dc_voltage_v")
    private Double dcVoltageV;          // Tension DC en Volts

    @Column(name = "dc_current_a")
    private Double dcCurrentA;          // Courant DC en Ampères

    @Column(name = "irradiance_wm2")
    private Double irradianceWm2;       // Irradiance solaire en W/m²
}