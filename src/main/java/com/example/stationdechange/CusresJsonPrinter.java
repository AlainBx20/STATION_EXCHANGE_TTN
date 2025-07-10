package com.example.stationdechange;

import com.example.stationdechange.dto.CusresDocumentDTO;
import com.example.stationdechange.service.CusresService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CusresJsonPrinter implements CommandLineRunner {
    @Autowired
    private CusresService cusresService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        // Disabled to prevent sending documents at startup
    }

    //    @Override
    //    public void run(String... args) throws Exception {
    //        CusresDocumentDTO dto = cusresService.getCusresDocument();
    //        if (dto != null) {
    //            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
    //            System.out.println("Generated CUSRES JSON:");
    //            System.out.println(json);
    //        } else {
    //            System.out.println("No Imputations entity found in database.");
    //        }
    //    }
}
