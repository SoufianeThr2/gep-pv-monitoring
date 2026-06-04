package com.gep.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductionChartDto {
    private LocalDateTime timestamp;
    private Double acPower;
    private Double dcPower;
    private Double irradiance;
}