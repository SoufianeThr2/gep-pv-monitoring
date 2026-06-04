package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "modules")
@Data
public class ModuleSpec {

    @Id
    @Column(name = "module_id")
    private String moduleId;

    private String brand;
    private String model;
    private String technology;

    @Column(name = "power_wc")
    private Integer powerWc;

    @Column(name = "nb_per_string")
    private Integer nbPerString;

    @Column(name = "voc_v")
    private Double vocV;

    @Column(name = "isc_a")
    private Double iscA;

    @Column(name = "temp_coeff_pmax")
    private Double tempCoeffPmax;
}