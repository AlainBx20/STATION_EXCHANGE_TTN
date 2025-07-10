package com.example.stationdechange.service;

import com.example.stationdechange.dto.CusresDocumentDTO;
import com.example.stationdechange.entity.Imputations;
import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.repository.ImputationsRepository;
import com.example.stationdechange.repository.PiecesJointesRepository;
import com.example.stationdechange.repository.TitresRepository;
import com.example.stationdechange.util.CusresMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CusresService {
    @Autowired
    private ImputationsRepository imputationsRepository;
    @Autowired
    private PiecesJointesRepository piecesJointesRepository;
    @Autowired
    private TitresRepository titresRepository;
    @Autowired
    private CusresMapper cusresMapper;

    public CusresDocumentDTO getCusresDocument() {
        Imputations entity = imputationsRepository.findAll().stream().findFirst().orElse(null);
        if (entity == null) return null;
        List<PiecesJointes> piecesJointesList = piecesJointesRepository.findAll();
        Titres titres = titresRepository.findByNumDossTtnAndNumdom(entity.getNumDossTtn(), entity.getNumdom());
        CusresDocumentDTO dto = cusresMapper.mapFromEntity(entity, piecesJointesList, titres);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
            System.out.println("CUSRES SENT DATA:\n" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
}
