package com.gep.monitoring.repository;

import com.gep.monitoring.entity.AcProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcProductionRepository extends JpaRepository<AcProduction, Long> {
}