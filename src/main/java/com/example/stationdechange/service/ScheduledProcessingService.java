package com.example.stationdechange.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ScheduledProcessingService {
    // In-memory status map: key = numDossTtn + ":" + numdom, value = status
    public static final ConcurrentHashMap<String, String> STATUS_MAP = new ConcurrentHashMap<>();

    // Utility to build the key for the map
    public static String buildKey(String numDossTtn, java.math.BigDecimal numdom) {
        return numDossTtn + ":" + (numdom != null ? numdom.toPlainString() : "null");
    }
} 