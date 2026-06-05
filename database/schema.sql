-- SQL Schema definition for PV Monitoring Platform
-- Note: In this project, tables are automatically created by Hibernate (spring.jpa.hibernate.ddl-auto=update).
-- This file serves as a reference documentation for the schema.

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pv_systems (
    system_id VARCHAR(50) PRIMARY KEY,
    system_name VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    total_capacity_kwc DOUBLE PRECISION,
    commissioning_date DATE,
    orientation VARCHAR(50),
    tilt_angle INTEGER,
    nb_strings INTEGER,
    module_id VARCHAR(50),
    inverter_id VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS module_specs (
    module_id VARCHAR(50) PRIMARY KEY,
    brand VARCHAR(100),
    model VARCHAR(100),
    technology VARCHAR(100),
    power_wc INTEGER,
    nb_per_string INTEGER,
    voc_v DOUBLE PRECISION,
    isc_a DOUBLE PRECISION,
    temp_coeff_pmax DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS inverters (
    inverter_id VARCHAR(50) PRIMARY KEY,
    brand VARCHAR(100),
    model VARCHAR(100),
    power_kw_ac DOUBLE PRECISION,
    nb_mppt INTEGER,
    max_input_voltage_v DOUBLE PRECISION,
    max_input_current_a DOUBLE PRECISION,
    efficiency_pct DOUBLE PRECISION,
    serial_number VARCHAR(100),
    system_id VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ac_production (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP,
    system_id VARCHAR(50),
    ac_power_kw DOUBLE PRECISION,
    ac_energy_kwh DOUBLE PRECISION,
    ac_voltage_v DOUBLE PRECISION,
    ac_frequency_hz DOUBLE PRECISION,
    power_factor DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS dc_production (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP,
    system_id VARCHAR(50),
    dc_power_kw DOUBLE PRECISION,
    dc_voltage_v DOUBLE PRECISION,
    dc_current_a DOUBLE PRECISION,
    irradiance_wm2 DOUBLE PRECISION
);

-- Index recommendations for time-series queries
CREATE INDEX IF NOT EXISTS idx_ac_prod_sys_time ON ac_production (system_id, timestamp);
CREATE INDEX IF NOT EXISTS idx_dc_prod_sys_time ON dc_production (system_id, timestamp);
