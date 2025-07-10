package com.example.stationdechange;

import com.example.stationdechange.dto.SancrtDocumentDTO;
import com.example.stationdechange.dto.CusresDocumentDTO;
import com.example.stationdechange.dto.AperakDocumentDTO;
import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.entity.Nsh;
import com.example.stationdechange.entity.Imputations;
import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.repository.TitresRepository;
import com.example.stationdechange.repository.PiecesJointesRepository;
import com.example.stationdechange.repository.NshRepository;
import com.example.stationdechange.repository.ImputationsRepository;
import com.example.stationdechange.repository.Aperak0Repository;
import com.example.stationdechange.repository.Aperak1Repository;
import com.example.stationdechange.service.KafkaService;
import com.example.stationdechange.util.SancrtMapper;
import com.example.stationdechange.util.CusresMapper;
import com.example.stationdechange.util.AperakMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Collections;
import java.util.UUID;
import com.example.stationdechange.kafka.KafkaRequestModel;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;
import org.springframework.kafka.core.KafkaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SancrtJsonPrinter implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(SancrtJsonPrinter.class);
    
    @Autowired
    private TitresRepository titresRepository;
    @Autowired
    private PiecesJointesRepository piecesJointesRepository;
    @Autowired
    private NshRepository nshRepository;
    @Autowired
    private SancrtMapper sancrtMapper;
    @Autowired
    private ImputationsRepository imputationsRepository;
    @Autowired
    private CusresMapper cusresMapper;
    @Autowired
    private Aperak0Repository aperak0Repository;
    @Autowired
    private Aperak1Repository aperak1Repository;
    @Autowired
    private AperakMapper aperakMapper;
    @Autowired
    private KafkaService kafkaService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, KafkaRequestModel> kafkaTemplateReception;

    @Override
    public void run(String... args) throws Exception {
        // Disabled to prevent sending documents at startup
    }
    
    public boolean processSancrtDocument() {
        try {
            Titres titres = titresRepository.findAll().stream().findFirst().orElse(null);
            if (titres != null) {
                List<PiecesJointes> piecesJointesList = piecesJointesRepository.findAll().stream()
                    .filter(pj -> pj.getIdSeq() != null && pj.getIdSeq().equals(titres.getIdSeq()))
                    .toList();
                List<Nsh> nshList = nshRepository.findAll().stream()
                    .filter(nsh -> nsh.getIdSeq() != null && nsh.getIdSeq().equals(titres.getIdSeq()))
                    .toList();
                List<SancrtDocumentDTO.Observation> observationList = Collections.emptyList();
                SancrtDocumentDTO sancrtDto = sancrtMapper.mapFromEntity(titres, piecesJointesList, nshList, observationList);
                // Print the DTO as JSON for verification
                try {
                    ObjectMapper printMapper = new ObjectMapper();
                    printMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    printMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));
                    String json = printMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sancrtDto);
                    System.out.println("SANCRT SENT DATA (SancrtJsonPrinter):\n" + json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String messageId = UUID.randomUUID().toString();
                String key = "SANCRT_" + messageId;
                String metaData = messageId;
                JsonNode bodyNode = objectMapper.valueToTree(sancrtDto.getBody());
                KafkaRequestModel request = new KafkaRequestModel(
                    sancrtDto.getTypeDocument() != null ? sancrtDto.getTypeDocument().toUpperCase() : "SANCRT",
                    sancrtDto.getSendDate() != null ? sancrtDto.getSendDate() : new Date(),
                    metaData,
                    bodyNode);
                logger.info("Sending SANCRT document to 'flux-inbound' with key: {}", key);
                kafkaTemplateReception.send("flux-inbound", key, request);
                logger.info("SANCRT document sent successfully: {} with messageId: {}", titres.getIdSeq(), messageId);
                return true;
            } else {
                logger.info("No SANCRT (Titres) entity found in database.");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error processing SANCRT document", e);
            return false;
        }
    }
    
    public boolean processCusresDocument() {
        try {
            Imputations imputations = imputationsRepository.findAll().stream().findFirst().orElse(null);
            if (imputations != null) {
                List<PiecesJointes> cusresPjList = piecesJointesRepository.findAll();
                Titres titres = titresRepository.findByNumDossTtnAndNumdom(imputations.getNumDossTtn(), imputations.getNumdom());
                String typeMessage = titres != null && titres.getTypemsg() != null ? titres.getTypemsg().toUpperCase() : "CUSRES";
                if ("SANCRT".equals(typeMessage)) {
                    processSancrtDocument();
                    return true; // Considered as processed
                }
                CusresDocumentDTO cusresDto = cusresMapper.mapFromEntity(imputations, cusresPjList, titres);
                // Print the DTO as JSON for verification
                try {
                    ObjectMapper printMapper = new ObjectMapper();
                    printMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    printMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));
                    String json = printMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cusresDto);
                    System.out.println("CUSRES SENT DATA (SancrtJsonPrinter):\n" + json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String messageId = UUID.randomUUID().toString();
                String key = "CUSRES_" + messageId;
                String metaData = messageId;
                JsonNode bodyNode = objectMapper.valueToTree(cusresDto.getBody());
                KafkaRequestModel request = new KafkaRequestModel(
                    cusresDto.getTypeDocument() != null ? cusresDto.getTypeDocument().toUpperCase() : "CUSRES",
                    cusresDto.getSendDate() != null ? cusresDto.getSendDate() : new Date(),
                    metaData,
                    bodyNode);
                logger.info("Sending CUSRES document to 'flux-inbound' with key: {}", key);
                kafkaTemplateReception.send("flux-inbound", key, request);
                logger.info("CUSRES document sent successfully: {} with messageId: {}", imputations.getIdImp(), messageId);
                return true;
            } else {
                logger.info("No CUSRES (Imputations) entity found in database.");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error processing CUSRES document", e);
            return false;
        }
    }
    
    public boolean processAperakDocument() {
        try {
            Aperak0 aperak0 = aperak0Repository.findAll().stream().findFirst().orElse(null);
            if (aperak0 != null) {
                List<Aperak1> aperak1List = aperak1Repository.findAll().stream()
                    .filter(a -> aperak0.getNumeroDossierTtn().equals(a.getNumeroDossierTtn()))
                    .toList();
                List<PiecesJointes> aperakPjList = piecesJointesRepository.findAll().stream()
                    .filter(pj -> aperak0.getNumeroDossierTtn().equals(pj.getNumDoc()))
                    .toList();
                // Find the corresponding Titres
                Titres titres = titresRepository.findAll().stream()
                    .filter(t -> aperak0.getNumeroDossierTtn().equals(t.getNumDossTtn()))
                    .findFirst().orElse(null);
                // Pass Titres to the mapper
                AperakDocumentDTO aperakDto = aperakMapper.mapFromEntity(aperak0, aperak1List, aperakPjList, titres);
                // Print the DTO as JSON for verification
                try {
                    ObjectMapper printMapper = new ObjectMapper();
                    printMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    printMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));
                    String json = printMapper.writerWithDefaultPrettyPrinter().writeValueAsString(aperakDto);
                    System.out.println("APERAK SENT DATA (SancrtJsonPrinter):\n" + json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String messageId = UUID.randomUUID().toString();
                String key = "APERAK_" + messageId;
                String metaData = messageId;
                // Set the messageId on Aperak0 for later matching
                aperak0.setMessageId(messageId);
                aperak0Repository.save(aperak0);
                JsonNode bodyNode = objectMapper.valueToTree(aperakDto.getBody());
                KafkaRequestModel request = new KafkaRequestModel(
                    aperakDto.getTypeDocument() != null ? aperakDto.getTypeDocument().toUpperCase() : "APERAK",
                    aperakDto.getSendDate() != null ? aperakDto.getSendDate() : new Date(),
                    metaData,
                    bodyNode);
                logger.info("Sending APERAK document to 'flux-inbound' with key: {}", key);
                kafkaTemplateReception.send("flux-inbound", key, request);
                logger.info("APERAK document sent successfully: {} with messageId: {}", aperak0.getNumeroDossierTtn(), messageId);
                return true;
            } else {
                logger.info("No APERAK (Aperak0) entity found in database.");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error processing APERAK document", e);
            return false;
        }
    }
} 



