package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "APERAK0_ARCH")
public class Aperak0Save implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "NUMERO_DOSSIER_TTN", length = 35)
    private String numeroDossierTtn;

    @Column(name = "NUMERO_MESSAGE", length = 35)
    private String numeroMessage;

    @Column(name = "CODE_TYPE_DOC", length = 3)
    private String codeTypeDoc;

    @Column(name = "EMETTEUR", length = 35)
    private String emetteur;

    @Column(name = "CODE_FLUX", length = 3)
    private String codeFlux;

    @Column(name = "DATE_TIME")
    private Date dateTime;

    @Column(name = "IND_TRANSACT", length = 1)
    private String indTransact;

    @Column(name = "DESTINATAIRE", length = 35)
    private String destinataire;

    @Column(name = "NUMERO_DEMANDE", length = 35)
    private String numeroDemande;

    @Column(name = "PROCESSED_DATE")
    @Temporal(TemporalType.DATE)
    private Date processedDate;

    @Column(name = "MESSAGE_ID")
    private String messageId;

    @Column(name = "DATE_RECEPTION")
    @Temporal(TemporalType.DATE)
    private Date dateReception;

    // Getters and setters
    public String getNumeroDossierTtn() { return numeroDossierTtn; }
    public void setNumeroDossierTtn(String numeroDossierTtn) { this.numeroDossierTtn = numeroDossierTtn; }

    public String getNumeroMessage() { return numeroMessage; }
    public void setNumeroMessage(String numeroMessage) { this.numeroMessage = numeroMessage; }

    public String getCodeTypeDoc() { return codeTypeDoc; }
    public void setCodeTypeDoc(String codeTypeDoc) { this.codeTypeDoc = codeTypeDoc; }

    public String getEmetteur() { return emetteur; }
    public void setEmetteur(String emetteur) { this.emetteur = emetteur; }

    public String getCodeFlux() { return codeFlux; }
    public void setCodeFlux(String codeFlux) { this.codeFlux = codeFlux; }

    public Date getDateTime() { return dateTime; }
    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }

    public String getIndTransact() { return indTransact; }
    public void setIndTransact(String indTransact) { this.indTransact = indTransact; }

    public String getDestinataire() { return destinataire; }
    public void setDestinataire(String destinataire) { this.destinataire = destinataire; }

    public String getNumeroDemande() { return numeroDemande; }
    public void setNumeroDemande(String numeroDemande) { this.numeroDemande = numeroDemande; }

    public Date getProcessedDate() { return processedDate; }
    public void setProcessedDate(Date processedDate) { this.processedDate = processedDate; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public Date getDateReception() { return dateReception; }
    public void setDateReception(Date dateReception) { this.dateReception = dateReception; }
} 