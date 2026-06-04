package com.gep.monitoring.repository;

import com.gep.monitoring.entity.ModuleSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleSpecRepository extends JpaRepository<ModuleSpec, String> {
}