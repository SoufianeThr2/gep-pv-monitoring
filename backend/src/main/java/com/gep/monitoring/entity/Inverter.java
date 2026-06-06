package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entité représentant les spécifications techniques d'un onduleur (inverter).
 * Correspond à la table "inverters" en base de données.
 * Un onduleur est lié à un système PV via system_id.
 */
@Entity
@Table(name = "inverters")
public class Inverter {

    @Id
    @Column(name = "inverter_id")
    private String inverterId;

    private String brand;
    private String model;

    @Column(name = "power_kw_ac")
    private Double powerKwAc;

    @Column(name = "nb_mppt")
    private Integer nbMppt;

    @Column(name = "max_input_voltage_v")
    private Double maxInputVoltageV;

    @Column(name = "max_input_current_a")
    private Double maxInputCurrentA;

    @Column(name = "efficiency_pct")
    private Double efficiencyPct;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "system_id")
    private String systemId;

    public Inverter() {
    }

    public Inverter(String inverterId, String brand, String model, Double powerKwAc, Integer nbMppt, Double maxInputVoltageV, Double maxInputCurrentA, Double efficiencyPct, String serialNumber, String systemId) {
        this.inverterId = inverterId;
        this.brand = brand;
        this.model = model;
        this.powerKwAc = powerKwAc;
        this.nbMppt = nbMppt;
        this.maxInputVoltageV = maxInputVoltageV;
        this.maxInputCurrentA = maxInputCurrentA;
        this.efficiencyPct = efficiencyPct;
        this.serialNumber = serialNumber;
        this.systemId = systemId;
    }

    public String getInverterId() { return inverterId; }
    public void setInverterId(String inverterId) { this.inverterId = inverterId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Double getPowerKwAc() { return powerKwAc; }
    public void setPowerKwAc(Double powerKwAc) { this.powerKwAc = powerKwAc; }

    public Integer getNbMppt() { return nbMppt; }
    public void setNbMppt(Integer nbMppt) { this.nbMppt = nbMppt; }

    public Double getMaxInputVoltageV() { return maxInputVoltageV; }
    public void setMaxInputVoltageV(Double maxInputVoltageV) { this.maxInputVoltageV = maxInputVoltageV; }

    public Double getMaxInputCurrentA() { return maxInputCurrentA; }
    public void setMaxInputCurrentA(Double maxInputCurrentA) { this.maxInputCurrentA = maxInputCurrentA; }

    public Double getEfficiencyPct() { return efficiencyPct; }
    public void setEfficiencyPct(Double efficiencyPct) { this.efficiencyPct = efficiencyPct; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }
}