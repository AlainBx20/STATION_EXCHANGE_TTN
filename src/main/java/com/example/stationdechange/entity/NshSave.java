package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "NSH_SAVE")
public class NshSave implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ID_SEQ")
    private Long idSeq;

    @Column(name = "CODNSH", length = 20)
    private String codnsh;

    @Column(name = "LIBPDT", length = 80)
    private String libpdt;

    @Column(name = "NBRUNT")
    private BigDecimal nbrunt;

    @Column(name = "CODUNTMES", length = 6)
    private String coduntmes;

    @Column(name = "MNTNFT", precision = 15, scale = 3)
    private BigDecimal mntpft;

    @Column(name = "CODPAYORG", length = 6)
    private String codpayorg;

    @Column(name = "STATUS_MC", length = 2)
    private String statusMc;

    @Column(name = "STATUS_OT", length = 2)
    private String statusOt;

    @Column(name = "STATUS_BQE", length = 2)
    private String statusBqe;

    @Column(name = "PROCESSED_DATE")
    @Temporal(TemporalType.DATE)
    private Date processedDate;

    @Column(name = "MESSAGE_ID")
    private String messageId;

    @Column(name = "NUMRBQ")
    private java.math.BigDecimal numrbq;

    // Getters and setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdSeq() { return idSeq; }
    public void setIdSeq(Long idSeq) { this.idSeq = idSeq; }
    public String getCodnsh() { return codnsh; }
    public void setCodnsh(String codnsh) { this.codnsh = codnsh; }
    public String getLibpdt() { return libpdt; }
    public void setLibpdt(String libpdt) { this.libpdt = libpdt; }
    public BigDecimal getNbrunt() { return nbrunt; }
    public void setNbrunt(BigDecimal nbrunt) { this.nbrunt = nbrunt; }
    public String getCoduntmes() { return coduntmes; }
    public void setCoduntmes(String coduntmes) { this.coduntmes = coduntmes; }
    public BigDecimal getMntpft() { return mntpft; }
    public void setMntpft(BigDecimal mntpft) { this.mntpft = mntpft; }
    public String getCodpayorg() { return codpayorg; }
    public void setCodpayorg(String codpayorg) { this.codpayorg = codpayorg; }
    public String getStatusMc() { return statusMc; }
    public void setStatusMc(String statusMc) { this.statusMc = statusMc; }
    public String getStatusOt() { return statusOt; }
    public void setStatusOt(String statusOt) { this.statusOt = statusOt; }
    public String getStatusBqe() { return statusBqe; }
    public void setStatusBqe(String statusBqe) { this.statusBqe = statusBqe; }
    public Date getProcessedDate() { return processedDate; }
    public void setProcessedDate(Date processedDate) { this.processedDate = processedDate; }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public java.math.BigDecimal getNumrbq() { return numrbq; }
    public void setNumrbq(java.math.BigDecimal numrbq) { this.numrbq = numrbq; }
} 