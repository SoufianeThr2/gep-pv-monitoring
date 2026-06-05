package com.gep.monitoring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant une mesure horaire de production AC (courant alternatif).
 * Correspond à la table "ac_production" en base de données.
 * Une ligne = 1 heure de mesure pour 1 système PV.
 */
@Entity
@Table(name = "ac_production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // ID auto-généré

    private LocalDateTime timestamp;    // Date et heure de la mesure

    @Column(name = "system_id")
    private String systemId;            // Référence au système PV

    @Column(name = "ac_power_kw")
    private Double acPowerKw;           // Puissance AC en kW

    @Column(name = "ac_energy_kwh")
    private Double acEnergyKwh;         // Energie AC produite en kWh

    @Column(name = "ac_voltage_v")
    private Double acVoltageV;          // Tension AC en Volts

    @Column(name = "ac_frequency_hz")
    private Double acFrequencyHz;       // Fréquence du réseau en Hz

    @Column(name = "power_factor")
    private Double powerFactor;         // Facteur de puissance (cos phi)
}