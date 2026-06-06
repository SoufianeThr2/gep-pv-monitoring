package com.gep.monitoring.dto;

import java.time.LocalDate;

/**
 * DTO de réponse pour le résumé d'un système PV (Dashboard principal).
 */
public class SystemSummaryDto {

    private String systemId;
    private String systemName;
    private Double totalCapacityKwc;
    private LocalDate commissioningDate;
    private String orientation;
    private Integer tiltAngle;
    private Integer nbStrings;

    private Double lastAcPowerKw;
    private Double dailyEnergyKwh;

    private ModuleDto module;
    private InverterDto inverter;

    public SystemSummaryDto() {
    }

    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }
    public String getSystemName() { return systemName; }
    public void setSystemName(String systemName) { this.systemName = systemName; }
    public Double getTotalCapacityKwc() { return totalCapacityKwc; }
    public void setTotalCapacityKwc(Double totalCapacityKwc) { this.totalCapacityKwc = totalCapacityKwc; }
    public LocalDate getCommissioningDate() { return commissioningDate; }
    public void setCommissioningDate(LocalDate commissioningDate) { this.commissioningDate = commissioningDate; }
    public String getOrientation() { return orientation; }
    public void setOrientation(String orientation) { this.orientation = orientation; }
    public Integer getTiltAngle() { return tiltAngle; }
    public void setTiltAngle(Integer tiltAngle) { this.tiltAngle = tiltAngle; }
    public Integer getNbStrings() { return nbStrings; }
    public void setNbStrings(Integer nbStrings) { this.nbStrings = nbStrings; }
    public Double getLastAcPowerKw() { return lastAcPowerKw; }
    public void setLastAcPowerKw(Double lastAcPowerKw) { this.lastAcPowerKw = lastAcPowerKw; }
    public Double getDailyEnergyKwh() { return dailyEnergyKwh; }
    public void setDailyEnergyKwh(Double dailyEnergyKwh) { this.dailyEnergyKwh = dailyEnergyKwh; }
    public ModuleDto getModule() { return module; }
    public void setModule(ModuleDto module) { this.module = module; }
    public InverterDto getInverter() { return inverter; }
    public void setInverter(InverterDto inverter) { this.inverter = inverter; }

    public static class ModuleDto {
        private String brand;
        private String model;
        private String technology;
        private Integer powerWc;
        private Integer totalModules;

        public ModuleDto() {}

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getTechnology() { return technology; }
        public void setTechnology(String technology) { this.technology = technology; }
        public Integer getPowerWc() { return powerWc; }
        public void setPowerWc(Integer powerWc) { this.powerWc = powerWc; }
        public Integer getTotalModules() { return totalModules; }
        public void setTotalModules(Integer totalModules) { this.totalModules = totalModules; }
    }

    public static class InverterDto {
        private String brand;
        private String model;
        private Double powerKwAc;
        private Integer nbMppt;
        private String serialNumber;

        public InverterDto() {}

        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public Double getPowerKwAc() { return powerKwAc; }
        public void setPowerKwAc(Double powerKwAc) { this.powerKwAc = powerKwAc; }
        public Integer getNbMppt() { return nbMppt; }
        public void setNbMppt(Integer nbMppt) { this.nbMppt = nbMppt; }
        public String getSerialNumber() { return serialNumber; }
        public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    }
}