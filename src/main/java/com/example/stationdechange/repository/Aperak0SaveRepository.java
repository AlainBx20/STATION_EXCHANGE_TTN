package com.example.stationdechange.repository;

import com.example.stationdechange.entity.Aperak0Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Aperak0SaveRepository extends JpaRepository<Aperak0Save, String> {
    Aperak0Save findByMessageId(String messageId);
} 