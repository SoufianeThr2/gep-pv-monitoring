package com.gep.monitoring.repository;

import com.gep.monitoring.entity.AcProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AcProductionRepository extends JpaRepository<AcProduction, Long> {
    List<AcProduction> findBySystemIdOrderByTimestampAsc(String systemId);
    List<AcProduction> findBySystemIdAndTimestampBetweenOrderByTimestampAsc(
            String systemId, LocalDateTime start, LocalDateTime end);
}