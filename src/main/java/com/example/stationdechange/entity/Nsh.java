package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "NSH")
public class Nsh implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ID_SEQ")
    private Long idSeq;

    @Column(name = "CODNSH", length = 11)
    private String codnsh;

    @Column(name = "MNTPFT", precision = 15, scale = 3)
    private BigDecimal mntpft;

    @Column(name = "NBRUNT", precision = 38, scale = 3)
    private BigDecimal nbrunt;

    @Column(name = "LIBPDT", length = 250)
    private String libpdt;

    @Column(name = "CODUNTMES", length = 2)
    private String coduntmes;

    @Column(name = "NUMRBQ", precision = 3)
    private BigDecimal numrbq;

    @Column(name = "CODPAYORG", length = 2)
    private String codpayorg;

    @Column(name = "STATUS_MC", length = 2)
    private String statusMc;

    @Column(name = "STATUS_OT", length = 2)
    private String statusOt;

    @Column(name = "STATUS_BQE", length = 2)
    private String statusBqe;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(Long idSeq) {
        this.idSeq = idSeq;
    }

    public String getCodnsh() {
        return codnsh;
    }

    public void setCodnsh(String codnsh) {
        this.codnsh = codnsh;
    }

    public BigDecimal getMntpft() {
        return mntpft;
    }

    public void setMntpft(BigDecimal mntpft) {
        this.mntpft = mntpft;
    }

    public BigDecimal getNbrunt() {
        return nbrunt;
    }

    public void setNbrunt(BigDecimal nbrunt) {
        this.nbrunt = nbrunt;
    }

    public String getLibpdt() {
        return libpdt;
    }

    public void setLibpdt(String libpdt) {
        this.libpdt = libpdt;
    }

    public String getCoduntmes() {
        return coduntmes;
    }

    public void setCoduntmes(String coduntmes) {
        this.coduntmes = coduntmes;
    }

    public BigDecimal getNumrbq() {
        return numrbq;
    }

    public void setNumrbq(BigDecimal numrbq) {
        this.numrbq = numrbq;
    }

    public String getCodpayorg() {
        return codpayorg;
    }

    public void setCodpayorg(String codpayorg) {
        this.codpayorg = codpayorg;
    }

    public String getStatusMc() {
        return statusMc;
    }

    public void setStatusMc(String statusMc) {
        this.statusMc = statusMc;
    }

    public String getStatusOt() {
        return statusOt;
    }

    public void setStatusOt(String statusOt) {
        this.statusOt = statusOt;
    }

    public String getStatusBqe() {
        return statusBqe;
    }

    public void setStatusBqe(String statusBqe) {
        this.statusBqe = statusBqe;
    }


}



