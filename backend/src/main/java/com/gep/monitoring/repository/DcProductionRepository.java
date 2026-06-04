package com.gep.monitoring.repository;

import com.gep.monitoring.entity.DcProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DcProductionRepository extends JpaRepository<DcProduction, Long> {
    List<DcProduction> findBySystemIdOrderByTimestampAsc(String systemId);
}