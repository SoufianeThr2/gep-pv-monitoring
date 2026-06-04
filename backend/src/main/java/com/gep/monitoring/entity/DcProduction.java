package com.gep.monitoring.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "dc_production")
@Data
public class DcProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID auto-généré car le CSV n'en a pas

    private LocalDateTime timestamp;

    @Column(name = "system_id")
    private String systemId;

    @Column(name = "dc_power_kw")
    private Double dcPowerKw;

    @Column(name = "dc_voltage_v")
    private Double dcVoltageV;

    @Column(name = "dc_current_a")
    private Double dcCurrentA;

    @Column(name = "irradiance_wm2")
    private Double irradianceWm2;
}