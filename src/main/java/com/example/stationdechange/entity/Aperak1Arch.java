package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "APERAK1_ARCH")
public class Aperak1Arch implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE_ERREUR", length = 10)
    private String codeErreur;

    @Column(name = "LIBELLE_ERREUR", length = 250)
    private String libelleErreur;

    @Column(name = "REF_ERREUR", length = 35)
    private String refErreur;

    @Column(name = "IND_TRANSACT", length = 1)
    private String indTransact;

    @Column(name = "NCOL")
    private Long ncol;

    @Column(name = "PROCESSED_DATE")
    @Temporal(TemporalType.DATE)
    private Date processedDate;

    @Column(name = "MESSAGE_ID")
    private String messageId;

    @Column(name = "NUMERO_DOSSIER_TTN", length = 35)
    private String numeroDossierTtn;

    @Column(name = "NUMERO_MESSAGE", length = 35)
    private String numeroMessage;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodeErreur() { return codeErreur; }
    public void setCodeErreur(String codeErreur) { this.codeErreur = codeErreur; }

    public String getLibelleErreur() { return libelleErreur; }
    public void setLibelleErreur(String libelleErreur) { this.libelleErreur = libelleErreur; }

    public String getRefErreur() { return refErreur; }
    public void setRefErreur(String refErreur) { this.refErreur = refErreur; }

    public String getIndTransact() { return indTransact; }
    public void setIndTransact(String indTransact) { this.indTransact = indTransact; }

    public Long getNcol() { return ncol; }
    public void setNcol(Long ncol) { this.ncol = ncol; }

    public Date getProcessedDate() { return processedDate; }
    public void setProcessedDate(Date processedDate) { this.processedDate = processedDate; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getNumeroDossierTtn() { return numeroDossierTtn; }
    public void setNumeroDossierTtn(String numeroDossierTtn) { this.numeroDossierTtn = numeroDossierTtn; }

    public String getNumeroMessage() { return numeroMessage; }
    public void setNumeroMessage(String numeroMessage) { this.numeroMessage = numeroMessage; }
} 