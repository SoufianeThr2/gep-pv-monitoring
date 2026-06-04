package com.gep.monitoring.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SystemSummaryDto {
    private String systemId;
    private String systemName;
    private Double totalCapacityKwc;
    private LocalDate commissioningDate;
    private Double latestAcPowerKw; // On mettra ici la toute dernière valeur de production connue
}