package com.example.stationdechange.repository;

import com.example.stationdechange.entity.NshSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NshSaveRepository extends JpaRepository<NshSave, Long> {
    // Custom queries if needed
} 