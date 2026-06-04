package com.gep.monitoring.repository;

import com.gep.monitoring.entity.Inverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InverterRepository extends JpaRepository<Inverter, String> {
}