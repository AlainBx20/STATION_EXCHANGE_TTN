package com.example.stationdechange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tn.smi.authentification.DTO.ReceptionResponse;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.CompletableFuture;

import static com.example.stationdechange.service.ScheduledProcessingService.STATUS_MAP;
import static com.example.stationdechange.service.ScheduledProcessingService.buildKey;

@Service
public class KafkaService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private DocumentProcessingService documentProcessingService;
    
    @Autowired
    @Lazy
    private ScheduledProcessingService scheduledProcessingService;
    
    @Value("${kafka.topic.aperak}")
    private String aperakTopic;
    
    @Value("${kafka.topic.sancrt:flux-inbound}")
    private String sancrtTopic;
    
    @Value("${kafka.topic.cusres:flux-inbound}")
    private String cusresTopic;
    
    /**
     * Send APERAK JSON message to Kafka topic
     * @param jsonMessage The JSON string to send
     * @param key Optional key for the message (can be null)
     */
    public void sendAperakMessage(String jsonMessage, String key) {
        try {
            System.out.println("[KAFKA] Sending APERAK JSON:\n" + jsonMessage);
            String messageKey = key != null ? key : "aperak-" + System.currentTimeMillis();
            
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(aperakTopic, messageKey, jsonMessage);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("APERAK message sent successfully to topic: {}, partition: {}, offset: {}", 
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send APERAK message to Kafka", ex);
                }
            });
            
        } catch (Exception e) {
            logger.error("Error sending APERAK message to Kafka", e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
    
    public void sendAperakMessage(String jsonMessage) {
        sendAperakMessage(jsonMessage, null);
    }

    public void sendSancrtMessage(String jsonMessage, String key) {
        try {
            System.out.println("[KAFKA] Sending SANCRT JSON:\n" + jsonMessage);
            String messageKey = key != null ? key : "sancrt-" + System.currentTimeMillis();
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(sancrtTopic, messageKey, jsonMessage);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("SANCRT message sent successfully to topic: {}, partition: {}, offset: {}", 
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send SANCRT message to Kafka", ex);
                }
            });
        } catch (Exception e) {
            logger.error("Error sending SANCRT message to Kafka", e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }

    public void sendSancrtMessage(String jsonMessage) {
        sendSancrtMessage(jsonMessage, null);
    }

    public void sendCusresMessage(String jsonMessage, String key) {
        try {
            System.out.println("[KAFKA] Sending CUSRES JSON:\n" + jsonMessage);
            String messageKey = key != null ? key : "cusres-" + System.currentTimeMillis();
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(cusresTopic, messageKey, jsonMessage);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("CUSRES message sent successfully to topic: {}, partition: {}, offset: {}", 
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send CUSRES message to Kafka", ex);
                }
            });
        } catch (Exception e) {
            logger.error("Error sending CUSRES message to Kafka", e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }

    public void sendCusresMessage(String jsonMessage) {
        sendCusresMessage(jsonMessage, null);
    }

    /**
     * Listen for responses from flux-response topic
     * If response is successful, process the document (save to _SAVE table and delete from original table)
     * If response is failed, do nothing (keep in original table)
     */
    @KafkaListener(topics = "flux-response", groupId = "flux-response-group")
    public void listen(@Payload ReceptionResponse response,
                       @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
        logger.info("Received response from topic 'flux-response'");
        logger.info("Key: {}", key);
        logger.info("Response: {}", response);
        
        try {
            // Check if response is successful
            if (response != null && "SUCCESS".equalsIgnoreCase(response.getStatus())) {
                String messageId = response.getMessageId() != null ? response.getMessageId().toString() : null;
                String documentType = determineDocumentType(key, response.getMessage());
                String docKey = null;
                // Try to extract numDossTtn and numdom from the key or message (pseudo-code, adjust as needed)
                if (key != null && key.contains(":")) {
                    docKey = key.substring(key.indexOf("_") + 1); // expects key like CUSRES_12345:6789
                }
                if (docKey != null) {
                    STATUS_MAP.put(docKey, "SUCCESS");
                }
                if (messageId != null) {
                    // Determine document type from the key or message
                    logger.info("\u2705 Processing successful response for document type: {} with messageId: {}", documentType, messageId);
                    // Process based on document type
                    switch (documentType.toUpperCase()) {
                        case "APERAK":
                            documentProcessingService.processSuccessfulAperak(messageId);
                            logger.info("\u2705 Successfully processed APERAK document with messageId: {}", messageId);
                            break;
                        case "SANCRT":
                            // Always process CUSRES before SANCRT
                            documentProcessingService.processSuccessfulCusres(messageId);
                            documentProcessingService.processSuccessfulSancrt(messageId);
                            logger.info("\u2705 Successfully processed SANCRT document with messageId: {} (CUSRES processed first)", messageId);
                            break;
                        case "CUSRES":
                            documentProcessingService.processSuccessfulCusres(messageId);
                            logger.info("\u2705 Successfully processed CUSRES document with messageId: {}", messageId);
                            break;
                        default:
                            logger.warn("Unknown document type: {}", documentType);
                            break;
                    }
                    // Reset processing flag so scheduler can send the next document
                    scheduledProcessingService.setProcessing(false);
                } else {
                    logger.warn("Response received but no messageId found");
                }
            } else {
                // Set status to FAILED in the map
                String docKey = null;
                if (key != null && key.contains(":")) {
                    docKey = key.substring(key.indexOf("_") + 1);
                }
                if (docKey != null) {
                    STATUS_MAP.put(docKey, "FAILED");
                }
                logger.error("\u274C Response indicates failure or no status. Keeping document in original table. Status: {}", 
                    response != null ? response.getStatus() : "null");
            }
        } catch (Exception e) {
            logger.error("Error processing response", e);
        }
    }
    
    /**
     * Determine document type from Kafka key or message
     */
    private String determineDocumentType(String key, String message) {
        if (key != null) {
            if (key.startsWith("APERAK_")) return "APERAK";
            if (key.startsWith("SANCRT_")) return "SANCRT";
            if (key.startsWith("CUSRES_")) return "CUSRES";
        }
        
        if (message != null) {
            if (message.toUpperCase().contains("APERAK")) return "APERAK";
            if (message.toUpperCase().contains("SANCRT")) return "SANCRT";
            if (message.toUpperCase().contains("CUSRES")) return "CUSRES";
        }
        
        return "UNKNOWN";
    }
}



