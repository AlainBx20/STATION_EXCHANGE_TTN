package com.example.stationdechange.repository;

import com.example.stationdechange.entity.Titres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface TitresRepository extends JpaRepository<Titres, String> {
    Titres findByNumDossTtnAndNumdom(String numDossTtn, java.math.BigDecimal numdom);

    @Modifying
    @Transactional
    void deleteByNumDossTtnAndTypemsg(String numDossTtn, String typemsg);
}
