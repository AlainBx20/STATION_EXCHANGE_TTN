package com.example.stationdechange.repository;

import com.example.stationdechange.entity.PiecesJointesSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PiecesJointesSaveRepository extends JpaRepository<PiecesJointesSave, String> {
    PiecesJointesSave findByMessageId(String messageId);
} 