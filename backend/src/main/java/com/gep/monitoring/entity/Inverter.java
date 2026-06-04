package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "inverters")
@Data
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
}