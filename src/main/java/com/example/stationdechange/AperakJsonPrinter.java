package com.example.stationdechange;

import com.example.stationdechange.dto.AperakDocumentDTO;
import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.repository.Aperak0Repository;
import com.example.stationdechange.repository.Aperak1Repository;
import com.example.stationdechange.repository.PiecesJointesRepository;
import com.example.stationdechange.repository.TitresRepository;
import com.example.stationdechange.service.KafkaService;
import com.example.stationdechange.util.AperakMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class AperakJsonPrinter implements CommandLineRunner {
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
    @Autowired
    private KafkaService kafkaService;

    @Override
    public void run(String... args) throws Exception {
        Aperak0 entity = repository.findAll().stream().findFirst().orElse(null);
        if (entity != null) {
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
            
            // Print to console for debugging
            System.out.println("Generated JSON:");
            System.out.println(json);
            
            // Send to Kafka
            /*
            try {
                kafkaService.sendAperakMessage(json, entity.getNumeroDossierTtn());
                System.out.println("JSON message sent to Kafka successfully!");
            } catch (Exception e) {
                System.err.println("Failed to send message to Kafka: " + e.getMessage());
                e.printStackTrace();
            }
            */
        } else {
            System.out.println("No Aperak0 entity found in database.");
        }
    }
}
