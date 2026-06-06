package com.gep.monitoring.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité représentant une mesure horaire de production AC (courant alternatif).
 * Correspond à la table "ac_production" en base de données.
 * Une ligne = 1 heure de mesure pour 1 système PV.
 */
@Entity
@Table(name = "ac_production")
public class AcProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public AcProduction() {
    }

    public AcProduction(Long id, LocalDateTime timestamp, String systemId, Double acPowerKw, Double acEnergyKwh, Double acVoltageV, Double acFrequencyHz, Double powerFactor) {
        this.id = id;
        this.timestamp = timestamp;
        this.systemId = systemId;
        this.acPowerKw = acPowerKw;
        this.acEnergyKwh = acEnergyKwh;
        this.acVoltageV = acVoltageV;
        this.acFrequencyHz = acFrequencyHz;
        this.powerFactor = powerFactor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }

    public Double getAcPowerKw() { return acPowerKw; }
    public void setAcPowerKw(Double acPowerKw) { this.acPowerKw = acPowerKw; }

    public Double getAcEnergyKwh() { return acEnergyKwh; }
    public void setAcEnergyKwh(Double acEnergyKwh) { this.acEnergyKwh = acEnergyKwh; }

    public Double getAcVoltageV() { return acVoltageV; }
    public void setAcVoltageV(Double acVoltageV) { this.acVoltageV = acVoltageV; }

    public Double getAcFrequencyHz() { return acFrequencyHz; }
    public void setAcFrequencyHz(Double acFrequencyHz) { this.acFrequencyHz = acFrequencyHz; }

    public Double getPowerFactor() { return powerFactor; }
    public void setPowerFactor(Double powerFactor) { this.powerFactor = powerFactor; }
}