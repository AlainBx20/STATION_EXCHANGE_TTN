package com.example.stationdechange.repository;

import com.example.stationdechange.entity.CondOctrTitresSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondOctrTitresSaveRepository extends JpaRepository<CondOctrTitresSave, Long> {
    // Custom queries if needed
} 