package com.example.stationdechange.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * KafkaRequestModel represents the structure of a request sent to Kafka.
 * It includes fields for document type, send date, metadata, and the body of the request.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaRequestModel {
    private String typeDocument;
    private LocalDateTime sendDate;
    private String metaData;
    private JsonNode body;

    public KafkaRequestModel() {}

    public KafkaRequestModel(String typeDocument, LocalDateTime sendDate, String metaData, JsonNode body) {
        this.typeDocument = typeDocument;
        this.sendDate = sendDate;
        this.metaData = metaData;
        this.body = body;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    /**
     * Sets the type of document for the Kafka request.
     * 
     * @param typeDocument The type of document as a String.
     */

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    /**
     * Sets the send date for the Kafka request.
     * 
     * @param sendDate The send date as a Date.
     */

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * Gets the metadata associated with the Kafka request.
     * 
     * @return The metadata as a String.
     */

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public JsonNode getBody() {
        return body;
    }

    /**
     * Sets the body of the Kafka request.
     * 
     * @param body The body as a JsonNode.
     */

    public void setBody(JsonNode body) {
        this.body = body;
    }
} 


