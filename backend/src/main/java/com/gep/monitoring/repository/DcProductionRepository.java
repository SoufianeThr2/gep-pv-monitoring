package com.gep.monitoring.repository;

import com.gep.monitoring.entity.DcProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DcProductionRepository extends JpaRepository<DcProduction, Long> {
}