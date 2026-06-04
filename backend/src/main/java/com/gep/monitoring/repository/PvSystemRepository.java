package com.gep.monitoring.repository;

import com.gep.monitoring.entity.PvSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PvSystemRepository extends JpaRepository<PvSystem, String> {
}