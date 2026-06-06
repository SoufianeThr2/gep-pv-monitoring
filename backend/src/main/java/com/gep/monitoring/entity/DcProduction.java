package com.gep.monitoring.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité représentant une mesure horaire de production DC (courant continu).
 * Correspond à la table "dc_production" en base de données.
 * Une ligne = 1 heure de mesure pour 1 système PV.
 */
@Entity
@Table(name = "dc_production")
public class DcProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public DcProduction() {
    }

    public DcProduction(Long id, LocalDateTime timestamp, String systemId, Double dcPowerKw, Double dcVoltageV, Double dcCurrentA, Double irradianceWm2) {
        this.id = id;
        this.timestamp = timestamp;
        this.systemId = systemId;
        this.dcPowerKw = dcPowerKw;
        this.dcVoltageV = dcVoltageV;
        this.dcCurrentA = dcCurrentA;
        this.irradianceWm2 = irradianceWm2;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }

    public Double getDcPowerKw() { return dcPowerKw; }
    public void setDcPowerKw(Double dcPowerKw) { this.dcPowerKw = dcPowerKw; }

    public Double getDcVoltageV() { return dcVoltageV; }
    public void setDcVoltageV(Double dcVoltageV) { this.dcVoltageV = dcVoltageV; }

    public Double getDcCurrentA() { return dcCurrentA; }
    public void setDcCurrentA(Double dcCurrentA) { this.dcCurrentA = dcCurrentA; }

    public Double getIrradianceWm2() { return irradianceWm2; }
    public void setIrradianceWm2(Double irradianceWm2) { this.irradianceWm2 = irradianceWm2; }
}