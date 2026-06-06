package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entité représentant les spécifications techniques d'un module photovoltaïque.
 * Correspond à la table "modules" en base de données.
 * Un module est lié à un système PV via module_id.
 */
@Entity
@Table(name = "modules")
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

    public ModuleSpec() {
    }

    public ModuleSpec(String moduleId, String brand, String model, String technology, Integer powerWc, Integer nbPerString, Double vocV, Double iscA, Double tempCoeffPmax) {
        this.moduleId = moduleId;
        this.brand = brand;
        this.model = model;
        this.technology = technology;
        this.powerWc = powerWc;
        this.nbPerString = nbPerString;
        this.vocV = vocV;
        this.iscA = iscA;
        this.tempCoeffPmax = tempCoeffPmax;
    }

    public String getModuleId() { return moduleId; }
    public void setModuleId(String moduleId) { this.moduleId = moduleId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getTechnology() { return technology; }
    public void setTechnology(String technology) { this.technology = technology; }

    public Integer getPowerWc() { return powerWc; }
    public void setPowerWc(Integer powerWc) { this.powerWc = powerWc; }

    public Integer getNbPerString() { return nbPerString; }
    public void setNbPerString(Integer nbPerString) { this.nbPerString = nbPerString; }

    public Double getVocV() { return vocV; }
    public void setVocV(Double vocV) { this.vocV = vocV; }

    public Double getIscA() { return iscA; }
    public void setIscA(Double iscA) { this.iscA = iscA; }

    public Double getTempCoeffPmax() { return tempCoeffPmax; }
    public void setTempCoeffPmax(Double tempCoeffPmax) { this.tempCoeffPmax = tempCoeffPmax; }
}