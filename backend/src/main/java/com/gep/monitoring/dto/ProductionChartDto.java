package com.gep.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * DTO de réponse pour les graphiques de production (Page de détail).
 */
public class ProductionChartDto {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private Double acPower;
    private Double dcPower;
    private Double irradiance;
    private Double acEnergy;
    private Double dcVoltage;
    private Double dcCurrent;
    private Double ambientTemperature;

    public ProductionChartDto() {
    }

    public ProductionChartDto(LocalDateTime timestamp, Double acPower, Double dcPower, Double irradiance, Double acEnergy, Double dcVoltage, Double dcCurrent, Double ambientTemperature) {
        this.timestamp = timestamp;
        this.acPower = acPower;
        this.dcPower = dcPower;
        this.irradiance = irradiance;
        this.acEnergy = acEnergy;
        this.dcVoltage = dcVoltage;
        this.dcCurrent = dcCurrent;
        this.ambientTemperature = ambientTemperature;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Double getAcPower() { return acPower; }
    public void setAcPower(Double acPower) { this.acPower = acPower; }

    public Double getDcPower() { return dcPower; }
    public void setDcPower(Double dcPower) { this.dcPower = dcPower; }

    public Double getIrradiance() { return irradiance; }
    public void setIrradiance(Double irradiance) { this.irradiance = irradiance; }

    public Double getAcEnergy() { return acEnergy; }
    public void setAcEnergy(Double acEnergy) { this.acEnergy = acEnergy; }

    public Double getDcVoltage() { return dcVoltage; }
    public void setDcVoltage(Double dcVoltage) { this.dcVoltage = dcVoltage; }

    public Double getDcCurrent() { return dcCurrent; }
    public void setDcCurrent(Double dcCurrent) { this.dcCurrent = dcCurrent; }

    public Double getAmbientTemperature() { return ambientTemperature; }
    public void setAmbientTemperature(Double ambientTemperature) { this.ambientTemperature = ambientTemperature; }
}