package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "APERAK1_ARCH")
public class Aperak1Arch implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private Aperak1Id id;

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

    // Getters and setters
    public Aperak1Id getId() { return id; }
    public void setId(Aperak1Id id) { this.id = id; }
    public String getNumeroDossierTtn() { return id != null ? id.getNumeroDossierTtn() : null; }
    public void setNumeroDossierTtn(String numeroDossierTtn) { if (id == null) id = new Aperak1Id(); id.setNumeroDossierTtn(numeroDossierTtn); }
    public String getNumeroMessage() { return id != null ? id.getNumeroMessage() : null; }
    public void setNumeroMessage(String numeroMessage) { if (id == null) id = new Aperak1Id(); id.setNumeroMessage(numeroMessage); }

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
} 