package com.example.stationdechange.repository;

import com.example.stationdechange.entity.ImputationsSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImputationsSaveRepository extends JpaRepository<ImputationsSave, Long> {
    ImputationsSave findByMessageId(String messageId);
} 