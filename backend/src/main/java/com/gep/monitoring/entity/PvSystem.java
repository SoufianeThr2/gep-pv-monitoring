package com.gep.monitoring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Entité représentant un système photovoltaïque.
 * Correspond à la table "pvsystems" en base de données.
 * Contient les métadonnées statiques d'un système PV.
 */
@Entity
@Table(name = "pvsystems")
public class PvSystem {

    @Id
    @Column(name = "system_id")
    private String systemId;

    @Column(name = "system_name")
    private String systemName;

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

    public PvSystem() {
    }

    public PvSystem(String systemId, String systemName, Double latitude, Double longitude, Double totalCapacityKwc, LocalDate commissioningDate, String orientation, Integer tiltAngle, Integer nbStrings, String moduleId, String inverterId) {
        this.systemId = systemId;
        this.systemName = systemName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalCapacityKwc = totalCapacityKwc;
        this.commissioningDate = commissioningDate;
        this.orientation = orientation;
        this.tiltAngle = tiltAngle;
        this.nbStrings = nbStrings;
        this.moduleId = moduleId;
        this.inverterId = inverterId;
    }

    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }

    public String getSystemName() { return systemName; }
    public void setSystemName(String systemName) { this.systemName = systemName; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

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

    public String getModuleId() { return moduleId; }
    public void setModuleId(String moduleId) { this.moduleId = moduleId; }

    public String getInverterId() { return inverterId; }
    public void setInverterId(String inverterId) { this.inverterId = inverterId; }
}