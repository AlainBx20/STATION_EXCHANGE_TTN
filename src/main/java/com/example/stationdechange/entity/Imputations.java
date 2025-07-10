package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "IMPUTATIONS")
public class Imputations implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_IMP")
    private Long idImp;

    @Column(name = "NUM_DOSS_TTN", length = 35)
    private String numDossTtn;

    @Column(name = "NUMDOM")
    private BigDecimal numdom;

    @Column(name = "CODDEV", length = 6)
    private String coddev;

    @Column(name = "DATIMP")
    @Temporal(TemporalType.DATE)
    private Date datimp;

    @Column(name = "MNTIMP", precision = 15, scale = 3)
    private BigDecimal mntimp;

    @Column(name = "QTECOMPL", precision = 6)
    private BigDecimal qtecompl;

    @Column(name = "TYPDECL", length = 6)
    private String typedecl;

    @Column(name = "NUMDECL", precision = 38, scale = 0)
    private BigDecimal numdecl;

    @Column(name = "BURIMP", length = 6)
    private String burimp;

    @Column(name = "INSPIMP", length = 40)
    private String inspimp;

    @Column(name = "CODUGDOM", length = 2)
    private String codugdom;

    @Column(name = "ANNEEDOM", length = 4)
    private String anneedom;

    @Column(name = "COURS_DEV_IMP", precision = 8, scale = 3)
    private BigDecimal coursDevImp;

    @Column(name = "MNTIMP_DEV", precision = 15, scale = 3)
    private BigDecimal mntimpDev;

    @Column(name = "MODLIV", length = 2)
    private String modliv;

    @Column(name = "NUMART", length = 3)
    private String numart;

    @Column(name = "NGP", length = 11)
    private String ngp;

    @Column(name = "CODTIT", length = 3)
    private String codtit;

    @Column(name = "MODREG", length = 2)
    private String modreg;

    @Column(name = "REGSTAT", length = 2)
    private String regstat;

    public Long getIdImp() {
        return idImp;
    }

    public void setIdImp(Long idImp) {
        this.idImp = idImp;
    }

    public String getNumDossTtn() {
        return numDossTtn;
    }

    public void setNumDossTtn(String numDossTtn) {
        this.numDossTtn = numDossTtn;
    }

    public BigDecimal getNumdom() {
        return numdom;
    }

    public void setNumdom(BigDecimal numdom) {
        this.numdom = numdom;
    }

    public String getCoddev() {
        return coddev;
    }

    public void setCoddev(String coddev) {
        this.coddev = coddev;
    }

    public Date getDatimp() {
        return datimp;
    }

    public void setDatimp(Date datimp) {
        this.datimp = datimp;
    }

    public BigDecimal getMntimp() {
        return mntimp;
    }

    public void setMntimp(BigDecimal mntimp) {
        this.mntimp = mntimp;
    }

    public BigDecimal getQtecompl() {
        return qtecompl;
    }

    public void setQtecompl(BigDecimal qtecompl) {
        this.qtecompl = qtecompl;
    }

    public String getTypedecl() {
        return typedecl;
    }

    public void setTypedecl(String typedecl) {
        this.typedecl = typedecl;
    }

    public BigDecimal getNumdecl() {
        return numdecl;
    }

    public void setNumdecl(BigDecimal numdecl) {
        this.numdecl = numdecl;
    }

    public String getBurimp() {
        return burimp;
    }

    public void setBurimp(String burimp) {
        this.burimp = burimp;
    }

    public String getInspimp() {
        return inspimp;
    }

    public void setInspimp(String inspimp) {
        this.inspimp = inspimp;
    }

    public String getCodugdom() {
        return codugdom;
    }

    public void setCodugdom(String codugdom) {
        this.codugdom = codugdom;
    }

    public String getAnneedom() {
        return anneedom;
    }

    public void setAnneedom(String anneedom) {
        this.anneedom = anneedom;
    }

    public BigDecimal getCoursDevImp() {
        return coursDevImp;
    }

    public void setCoursDevImp(BigDecimal coursDevImp) {
        this.coursDevImp = coursDevImp;
    }

    public BigDecimal getMntimpDev() {
        return mntimpDev;
    }

    public void setMntimpDev(BigDecimal mntimpDev) {
        this.mntimpDev = mntimpDev;
    }

    public String getModliv() {
        return modliv;
    }

    public void setModliv(String modliv) {
        this.modliv = modliv;
    }

    public String getNumart() {
        return numart;
    }

    public void setNumart(String numart) {
        this.numart = numart;
    }

    public String getNgp() {
        return ngp;
    }

    public void setNgp(String ngp) {
        this.ngp = ngp;
    }

    public String getCodtit() {
        return codtit;
    }

    public void setCodtit(String codtit) {
        this.codtit = codtit;
    }

    public String getModreg() {
        return modreg;
    }

    public void setModreg(String modreg) {
        this.modreg = modreg;
    }

    public String getRegstat() {
        return regstat;
    }

    public void setRegstat(String regstat) {
        this.regstat = regstat;
    }
}
