package com.example.stationdechange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StationdechangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationdechangeApplication.class, args);
    }

}
