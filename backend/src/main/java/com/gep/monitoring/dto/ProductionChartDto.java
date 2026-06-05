package com.gep.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}