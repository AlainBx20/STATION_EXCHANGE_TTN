package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SIGNATAIRES")
public class Signataires implements Serializable {
    @Id
    @Column(name = "ID_SEQ", nullable = false)
    private Long idSeq;

    @Column(name = "ID_SIGN")
    private Long idSign;

    @Column(name = "DATE_DEB_VALIDITE")
    @Temporal(TemporalType.DATE)
    private Date dateDebValidite;

    @Column(name = "NOM_OT", length = 70)
    private String nomOt;

    @Column(name = "NOM_SIGNAT_OT", length = 35)
    private String nomSignatOt;

    @Column(name = "DATE_OT")
    @Temporal(TemporalType.DATE)
    private Date dateOt;

    @Column(name = "NOM_MC", length = 70)
    private String nomMc;

    @Column(name = "NOM_SIGNAT_MC", length = 35)
    private String nomSignatMc;

    @Column(name = "DATE_MC")
    @Temporal(TemporalType.DATE)
    private Date dateMc;

    @Column(name = "NOM_SIGNAT_OD", length = 35)
    private String nomSignatOd;

    @Column(name = "DATE_OD")
    @Temporal(TemporalType.DATE)
    private Date dateOd;

    @Column(name = "NOM_BCT", length = 70)
    private String nomBct;

    @Column(name = "NOM_SIGNAT_BCT", length = 35)
    private String nomSignatBct;

    @Column(name = "DATE_BCT")
    @Temporal(TemporalType.DATE)
    private Date dateBct;

    @Column(name = "NOM_SIGNAT_DECL", length = 35)
    private String nomSignatDecl;

    @Column(name = "DATE_DECL")
    @Temporal(TemporalType.DATE)
    private Date dateDecl;

    @Column(name = "CODE_STATUS_OT", length = 2)
    private String codeStatusOt;

    @Column(name = "CODE_STATUS_MC", length = 2)
    private String codeStatusMc;

    @Column(name = "CODE_STATUS_BQE", length = 2)
    private String codeStatusBqe;

    // Getters and setters
    public Long getIdSeq() { return idSeq; }
    public void setIdSeq(Long idSeq) { this.idSeq = idSeq; }

    public Long getIdSign() { return idSign; }
    public void setIdSign(Long idSign) { this.idSign = idSign; }

    public Date getDateDebValidite() { return dateDebValidite; }
    public void setDateDebValidite(Date dateDebValidite) { this.dateDebValidite = dateDebValidite; }

    public String getNomOt() { return nomOt; }
    public void setNomOt(String nomOt) { this.nomOt = nomOt; }

    public String getNomSignatOt() { return nomSignatOt; }
    public void setNomSignatOt(String nomSignatOt) { this.nomSignatOt = nomSignatOt; }

    public Date getDateOt() { return dateOt; }
    public void setDateOt(Date dateOt) { this.dateOt = dateOt; }

    public String getNomMc() { return nomMc; }
    public void setNomMc(String nomMc) { this.nomMc = nomMc; }

    public String getNomSignatMc() { return nomSignatMc; }
    public void setNomSignatMc(String nomSignatMc) { this.nomSignatMc = nomSignatMc; }

    public Date getDateMc() { return dateMc; }
    public void setDateMc(Date dateMc) { this.dateMc = dateMc; }

    public String getNomSignatOd() { return nomSignatOd; }
    public void setNomSignatOd(String nomSignatOd) { this.nomSignatOd = nomSignatOd; }

    public Date getDateOd() { return dateOd; }
    public void setDateOd(Date dateOd) { this.dateOd = dateOd; }

    public String getNomBct() { return nomBct; }
    public void setNomBct(String nomBct) { this.nomBct = nomBct; }

    public String getNomSignatBct() { return nomSignatBct; }
    public void setNomSignatBct(String nomSignatBct) { this.nomSignatBct = nomSignatBct; }

    public Date getDateBct() { return dateBct; }
    public void setDateBct(Date dateBct) { this.dateBct = dateBct; }

    public String getNomSignatDecl() { return nomSignatDecl; }
    public void setNomSignatDecl(String nomSignatDecl) { this.nomSignatDecl = nomSignatDecl; }

    public Date getDateDecl() { return dateDecl; }
    public void setDateDecl(Date dateDecl) { this.dateDecl = dateDecl; }

    public String getCodeStatusOt() { return codeStatusOt; }
    public void setCodeStatusOt(String codeStatusOt) { this.codeStatusOt = codeStatusOt; }

    public String getCodeStatusMc() { return codeStatusMc; }
    public void setCodeStatusMc(String codeStatusMc) { this.codeStatusMc = codeStatusMc; }

    public String getCodeStatusBqe() { return codeStatusBqe; }
    public void setCodeStatusBqe(String codeStatusBqe) { this.codeStatusBqe = codeStatusBqe; }
}
