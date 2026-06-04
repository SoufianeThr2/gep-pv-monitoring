package com.gep.monitoring.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ac_production")
@Data
public class AcProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID auto-généré

    private LocalDateTime timestamp;

    @Column(name = "system_id")
    private String systemId;

    @Column(name = "ac_power_kw")
    private Double acPowerKw;

    @Column(name = "ac_energy_kwh")
    private Double acEnergyKwh;

    @Column(name = "ac_voltage_v")
    private Double acVoltageV;

    @Column(name = "ac_frequency_hz")
    private Double acFrequencyHz;

    @Column(name = "power_factor")
    private Double powerFactor;
}