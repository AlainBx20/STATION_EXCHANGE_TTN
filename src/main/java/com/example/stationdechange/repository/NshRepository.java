package com.example.stationdechange.repository;

import com.example.stationdechange.entity.Nsh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NshRepository extends JpaRepository<Nsh, Long> {
    // Custom queries if needed
}
