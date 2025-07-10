package com.example.stationdechange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ScheduledProcessingService {
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduledProcessingService.class);
    
    @Autowired
    private DocumentProcessingService documentProcessingService;
    
    @Autowired
    private com.example.stationdechange.SancrtJsonPrinter sancrtJsonPrinter;
    
    // Track which document type to process next
    private int currentDocumentType = 0; // 0=APERAK, 1=CUSRES, 2=SANCRT
    
    // In-memory status map: key = numDossTtn + ":" + numdom, value = status
    public static final ConcurrentHashMap<String, String> STATUS_MAP = new ConcurrentHashMap<>();
    
    private volatile boolean isProcessing = false;
    
    /**
     * Scheduled task that runs every 1 minute to process one document
     * This runs independently of Kafka responses
     * Processes one document at a time in round-robin fashion
     */
    @Scheduled(fixedRate = 60000) // 1 minute
    public synchronized void processDocumentsScheduled() {
        logger.info("[SCHEDULER] Entered processDocumentsScheduled. isProcessing={}", isProcessing);
        if (isProcessing) {
            logger.info("[SCHEDULER] A document is already being processed. Waiting for response before sending next. isProcessing={}", isProcessing);
            return;
        }
        boolean documentSent = false;
        int attempts = 0;
        int typeToTry = currentDocumentType;
        // Try all 3 types in order, starting from currentDocumentType
        while (attempts < 3 && !documentSent) {
            switch (typeToTry) {
                case 0:
                    documentSent = processOneAperakDocument();
                    break;
                case 1:
                    documentSent = processOneCusresDocument();
                    break;
                case 2:
                    documentSent = processOneSancrtDocument();
                    break;
            }
            if (!documentSent) {
                typeToTry = (typeToTry + 1) % 3;
                attempts++;
            }
        }
        if (documentSent) {
            isProcessing = true;
            // Next run will try the next type after the one just processed
            currentDocumentType = (typeToTry + 1) % 3;
            logger.info("[SCHEDULER] Document sent to Kafka. Next type: {}. Setting isProcessing=true", getDocumentTypeName(currentDocumentType));
        } else {
            // No document found for any type, try next type on next run
            currentDocumentType = (currentDocumentType + 1) % 3;
            logger.info("[SCHEDULER] No document found for any type. Will try next type: {} on next run", getDocumentTypeName(currentDocumentType));
        }
        logger.info("[SCHEDULER] Scheduled document processing completed. isProcessing={}", isProcessing);
    }
    
    /**
     * Process one APERAK document
     */
    private boolean processOneAperakDocument() {
        logger.info("Looking for one APERAK document to process...");
        boolean sent = sancrtJsonPrinter.processAperakDocument();
        if (sent) {
            logger.info("Sent one APERAK document to Kafka (archiving/deletion will occur after SUCCESS response)");
        }
        return sent;
    }
    
    /**
     * Process one CUSRES document
     */
    private boolean processOneCusresDocument() {
        logger.info("Looking for one CUSRES document to process...");
        boolean sent = sancrtJsonPrinter.processCusresDocument();
        if (sent) {
            logger.info("Sent one CUSRES document to Kafka (archiving/deletion will occur after SUCCESS response)");
        }
        return sent;
    }
    
    /**
     * Process one SANCRT document
     */
    private boolean processOneSancrtDocument() {
        logger.info("Looking for one SANCRT document to process...");
        boolean sent = sancrtJsonPrinter.processSancrtDocument();
        if (sent) {
            logger.info("Sent one SANCRT document to Kafka (archiving/deletion will occur after SUCCESS response)");
        }
        return sent;
    }
    
    /**
     * Get the name of the document type for logging
     */
    private String getDocumentTypeName(int type) {
        switch (type) {
            case 0: return "APERAK";
            case 1: return "CUSRES";
            case 2: return "SANCRT";
            default: return "UNKNOWN";
        }
    }
    
    // Utility to build the key for the map
    public static String buildKey(String numDossTtn, java.math.BigDecimal numdom) {
        return numDossTtn + ":" + (numdom != null ? numdom.toPlainString() : "null");
    }
    
    public void setProcessing(boolean processing) {
        logger.info("[SCHEDULER] setProcessing({}) called. Previous value was {}", processing, this.isProcessing);
        this.isProcessing = processing;
    }
} 