package com.example.stationdechange.controller;

import com.example.stationdechange.dto.AperakDocumentDTO;
import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.repository.Aperak0Repository;
import com.example.stationdechange.repository.Aperak1Repository;
import com.example.stationdechange.repository.PiecesJointesRepository;
import com.example.stationdechange.repository.TitresRepository;
import com.example.stationdechange.util.AperakMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aperak")
public class AperakController {

    @Autowired
    private Aperak0Repository repository;
    @Autowired
    private Aperak1Repository aperak1Repository;
    @Autowired
    private PiecesJointesRepository piecesJointesRepository;
    @Autowired
    private TitresRepository titresRepository;
    @Autowired
    private AperakMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/generate-and-send")
    public ResponseEntity<Map<String, Object>> generateAndSendAperak() {
        try {
            Aperak0 entity = repository.findAll().stream().findFirst().orElse(null);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }

            // Fetch related Aperak1 and PiecesJointes by numeroDossierTtn
            List<Aperak1> aperak1List = aperak1Repository.findAll().stream()
                .filter(a -> entity.getNumeroDossierTtn().equals(a.getNumeroDossierTtn()))
                .toList();
            List<PiecesJointes> piecesJointesList = piecesJointesRepository.findAll().stream()
                .filter(pj -> entity.getNumeroDossierTtn().equals(pj.getNumDoc()))
                .toList();

            Titres titres = titresRepository.findAll().stream().filter(t -> entity.getNumeroDossierTtn().equals(t.getNumDossTtn())).findFirst().orElse(null);
            AperakDocumentDTO dto = mapper.mapFromEntity(entity, aperak1List, piecesJointesList, titres);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "APERAK JSON generated and sent to Kafka successfully",
                "numeroDossierTtn", entity.getNumeroDossierTtn(),
                "json", json
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateAperak() {
        try {
            Aperak0 entity = repository.findAll().stream().findFirst().orElse(null);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }

            // Fetch related Aperak1 and PiecesJointes by numeroDossierTtn
            List<Aperak1> aperak1List = aperak1Repository.findAll().stream()
                .filter(a -> entity.getNumeroDossierTtn().equals(a.getNumeroDossierTtn()))
                .toList();
            List<PiecesJointes> piecesJointesList = piecesJointesRepository.findAll().stream()
                .filter(pj -> entity.getNumeroDossierTtn().equals(pj.getNumDoc()))
                .toList();

            Titres titres = titresRepository.findAll().stream().filter(t -> entity.getNumeroDossierTtn().equals(t.getNumDossTtn())).findFirst().orElse(null);
            AperakDocumentDTO dto = mapper.mapFromEntity(entity, aperak1List, piecesJointesList, titres);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "numeroDossierTtn", entity.getNumeroDossierTtn(),
                "json", json
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendAperakToKafka(@RequestBody String jsonMessage) {
        try {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "JSON message sent to Kafka successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }
} 