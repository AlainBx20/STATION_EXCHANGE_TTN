package com.example.stationdechange.repository;

import com.example.stationdechange.entity.TitresSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface TitresSaveRepository extends JpaRepository<TitresSave, Long> {
    TitresSave findByMessageId(String messageId);
    @Modifying
    @Transactional
    long deleteByNumDossTtnAndNumdom(String numDossTtn, java.math.BigDecimal numdom);
} 