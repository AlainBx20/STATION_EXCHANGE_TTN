package com.example.stationdechange.repository;

import com.example.stationdechange.entity.Aperak1Arch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Aperak1ArchRepository extends JpaRepository<Aperak1Arch, String> {
    Aperak1Arch findByMessageId(String messageId);
} 