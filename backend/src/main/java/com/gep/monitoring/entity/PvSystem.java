package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "pvsystems")
@Data // C'est Lombok : ça génère automatiquement les Getters et Setters pour garder le code propre !
public class PvSystem {

    @Id
    @Column(name = "system_id")
    private String systemId; // Ex: "SYS-001"

    @Column(name = "system_name")
    private String systemName; // Ex: "GEP Zone Nord"

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "total_capacity_kwc")
    private Double totalCapacityKwc;

    @Column(name = "commissioning_date")
    private LocalDate commissioningDate;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "tilt_angle")
    private Integer tiltAngle;

    @Column(name = "nb_strings")
    private Integer nbStrings;

    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "inverter_id")
    private String inverterId;
}