package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TITRES_SAVE")
public class TitresSave implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_SEQ")
    private Long idSeq;

    @Column(name = "PROCESSED_DATE")
    @Temporal(TemporalType.DATE)
    private Date processedDate;

    @Column(name = "MESSAGE_ID")
    private String messageId;

    @Column(name = "NUM_DOSS_TTN", length = 35)
    private String numDossTtn;
    @Column(name = "NUMDOM")
    private BigDecimal numdom;
    @Column(name = "NATTIT", length = 2)
    private String nattit;
    @Column(name = "CODDEV_CNT", length = 6)
    private String coddevCnt;
    @Column(name = "CODDEV_REGL", length = 6)
    private String coddevRegl;
    @Column(name = "CODREG", length = 6)
    private String codreg;
    @Column(name = "CODLIV", length = 6)
    private String codliv;
    @Column(name = "CODDEL", length = 6)
    private String coddel;
    @Column(name = "CODREGSTAT", length = 6)
    private String codregstat;
    @Column(name = "DATDOM")
    @Temporal(TemporalType.DATE)
    private Date datdom;
    @Column(name = "NUMDEP")
    private BigDecimal numdep;
    @Column(name = "NUMCNT", length = 35)
    private String numcnt;
    @Column(name = "DATCNT")
    @Temporal(TemporalType.DATE)
    private Date datcnt;
    @Column(name = "MNTPTFNDEV", precision = 15, scale = 3)
    private BigDecimal mntptfndev;
    @Column(name = "MNTFOBDEV", precision = 15, scale = 3)
    private BigDecimal mntfobdev;
    @Column(name = "LIBFRS", length = 80)
    private String libfrs;
    @Column(name = "ADRFRS", length = 209)
    private String adrfrs;
    @Column(name = "MNTPTFNTND", precision = 15, scale = 3)
    private BigDecimal mntptfntnd;
    @Column(name = "MNTFOBTND", precision = 15, scale = 3)
    private BigDecimal mntfobtnd;
    @Column(name = "DATDEPAUT")
    @Temporal(TemporalType.DATE)
    private Date datdepaut;
    @Column(name = "DATENRGMIN")
    @Temporal(TemporalType.DATE)
    private Date datenrgmin;
    @Column(name = "NUMENRGMIN", length = 15)
    private String numenrgmin;
    @Column(name = "DATFINTIT")
    @Temporal(TemporalType.DATE)
    private Date datfintit;
    @Column(name = "CODUGDOM", length = 6)
    private String codugdom;
    @Column(name = "NUMDEPEXT", length = 15)
    private String numdepext;
    @Column(name = "RAISOC", length = 80)
    private String raisoc;
    @Column(name = "ADRCLT", length = 209)
    private String adrclt;
    @Column(name = "CODDOU", length = 10)
    private String coddou;
    @Column(name = "CODUGDEP", length = 6)
    private String codugdep;
    @Column(name = "CODBURDOU", length = 6)
    private String codburdou;
    @Column(name = "NUMDECLDOU", length = 15)
    private String numdecldou;
    @Column(name = "DATDECLDOU")
    @Temporal(TemporalType.DATE)
    private Date datdecldou;
    @Column(name = "CODPAY_ORG", length = 6)
    private String codpayOrg;
    @Column(name = "CODPAY_ACH_DESTDEB", length = 6)
    private String codpayAchDestdeb;
    @Column(name = "CODPAY_PROV_DESTFIN", length = 6)
    private String codpayProvDestfin;
    @Column(name = "NUMCPT", length = 35)
    private String numcpt;
    @Column(name = "CODPAY_TIER", length = 6)
    private String codpayTier;
    @Column(name = "NBRRBQ")
    private BigDecimal nbrrbq;
    @Column(name = "COURS", precision = 8, scale = 3)
    private BigDecimal cours;
    @Column(name = "NUM_DEMANDE_TTN", length = 35)
    private String numDemandeTtn;
    @Column(name = "TYPEMSG", length = 6)
    private String typemsg;
    @Column(name = "TYPEDOC", length = 3)
    private String typedoc;
    @Column(name = "NUM_MESSAGE", length = 14)
    private String numMessage;
    @Column(name = "ETAT", length = 2)
    private String etat;
    @Column(name = "EMETTEUR", length = 35)
    private String emetteur;
    @Column(name = "DESTINATAIRE", length = 35)
    private String destinataire;
    @Column(name = "CLE_AUTH", length = 32)
    private String cleAuth;
    @Column(name = "DATE_RECEPTION")
    @Temporal(TemporalType.DATE)
    private Date dateReception;
    @Column(name = "SEQ_RECEPTION", precision = 35, scale = 0)
    private BigDecimal seqReception;
    @Column(name = "CODE_ERREUR", precision = 1, scale = 0)
    private Integer codeErreur;
    @Column(name = "CODE_ETAT", precision = 1, scale = 0)
    private Integer codeEtat;
    @Column(name = "IND_TRANSACT", length = 1)
    private String indTransact;
    @Column(name = "NUM_DOSS_TTN_ORIG", length = 35)
    private String numDossTtnOrig;
    @Column(name = "NUM_DEMANDE_TTN_ORIG", length = 35)
    private String numDemandeTtnOrig;
    @Column(name = "NUM_MESSAGE_ORIG", length = 14)
    private String numMessageOrig;
    @Column(name = "APERAK_ENVOYE", length = 1)
    private String aperakEnvoye;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdSeq() { return idSeq; }
    public void setIdSeq(Long idSeq) { this.idSeq = idSeq; }

    public Date getProcessedDate() { return processedDate; }
    public void setProcessedDate(Date processedDate) { this.processedDate = processedDate; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getNumDossTtn() { return numDossTtn; }
    public void setNumDossTtn(String numDossTtn) { this.numDossTtn = numDossTtn; }

    public BigDecimal getNumdom() { return numdom; }
    public void setNumdom(BigDecimal numdom) { this.numdom = numdom; }

    public String getNattit() { return nattit; }
    public void setNattit(String nattit) { this.nattit = nattit; }

    public String getCoddevCnt() { return coddevCnt; }
    public void setCoddevCnt(String coddevCnt) { this.coddevCnt = coddevCnt; }

    public String getCoddevRegl() { return coddevRegl; }
    public void setCoddevRegl(String coddevRegl) { this.coddevRegl = coddevRegl; }

    public String getCodreg() { return codreg; }
    public void setCodreg(String codreg) { this.codreg = codreg; }

    public String getCodliv() { return codliv; }
    public void setCodliv(String codliv) { this.codliv = codliv; }

    public String getCoddel() { return coddel; }
    public void setCoddel(String coddel) { this.coddel = coddel; }

    public String getCodregstat() { return codregstat; }
    public void setCodregstat(String codregstat) { this.codregstat = codregstat; }

    public Date getDatdom() { return datdom; }
    public void setDatdom(Date datdom) { this.datdom = datdom; }

    public BigDecimal getNumdep() { return numdep; }
    public void setNumdep(BigDecimal numdep) { this.numdep = numdep; }

    public String getNumcnt() { return numcnt; }
    public void setNumcnt(String numcnt) { this.numcnt = numcnt; }

    public Date getDatcnt() { return datcnt; }
    public void setDatcnt(Date datcnt) { this.datcnt = datcnt; }

    public BigDecimal getMntptfndev() { return mntptfndev; }
    public void setMntptfndev(BigDecimal mntptfndev) { this.mntptfndev = mntptfndev; }

    public BigDecimal getMntfobdev() { return mntfobdev; }
    public void setMntfobdev(BigDecimal mntfobdev) { this.mntfobdev = mntfobdev; }

    public String getLibfrs() { return libfrs; }
    public void setLibfrs(String libfrs) { this.libfrs = libfrs; }

    public String getAdrfrs() { return adrfrs; }
    public void setAdrfrs(String adrfrs) { this.adrfrs = adrfrs; }

    public BigDecimal getMntptfntnd() { return mntptfntnd; }
    public void setMntptfntnd(BigDecimal mntptfntnd) { this.mntptfntnd = mntptfntnd; }

    public BigDecimal getMntfobtnd() { return mntfobtnd; }
    public void setMntfobtnd(BigDecimal mntfobtnd) { this.mntfobtnd = mntfobtnd; }

    public Date getDatdepaut() { return datdepaut; }
    public void setDatdepaut(Date datdepaut) { this.datdepaut = datdepaut; }

    public Date getDatenrgmin() { return datenrgmin; }
    public void setDatenrgmin(Date datenrgmin) { this.datenrgmin = datenrgmin; }

    public String getNumenrgmin() { return numenrgmin; }
    public void setNumenrgmin(String numenrgmin) { this.numenrgmin = numenrgmin; }

    public Date getDatfintit() { return datfintit; }
    public void setDatfintit(Date datfintit) { this.datfintit = datfintit; }

    public String getCodugdom() { return codugdom; }
    public void setCodugdom(String codugdom) { this.codugdom = codugdom; }

    public String getNumdepext() { return numdepext; }
    public void setNumdepext(String numdepext) { this.numdepext = numdepext; }

    public String getRaisoc() { return raisoc; }
    public void setRaisoc(String raisoc) { this.raisoc = raisoc; }

    public String getAdrclt() { return adrclt; }
    public void setAdrclt(String adrclt) { this.adrclt = adrclt; }

    public String getCoddou() { return coddou; }
    public void setCoddou(String coddou) { this.coddou = coddou; }

    public String getCodugdep() { return codugdep; }
    public void setCodugdep(String codugdep) { this.codugdep = codugdep; }

    public String getCodburdou() { return codburdou; }
    public void setCodburdou(String codburdou) { this.codburdou = codburdou; }

    public String getNumdecldou() { return numdecldou; }
    public void setNumdecldou(String numdecldou) { this.numdecldou = numdecldou; }

    public Date getDatdecldou() { return datdecldou; }
    public void setDatdecldou(Date datdecldou) { this.datdecldou = datdecldou; }

    public String getCodpayOrg() { return codpayOrg; }
    public void setCodpayOrg(String codpayOrg) { this.codpayOrg = codpayOrg; }

    public String getCodpayAchDestdeb() { return codpayAchDestdeb; }
    public void setCodpayAchDestdeb(String codpayAchDestdeb) { this.codpayAchDestdeb = codpayAchDestdeb; }

    public String getCodpayProvDestfin() { return codpayProvDestfin; }
    public void setCodpayProvDestfin(String codpayProvDestfin) { this.codpayProvDestfin = codpayProvDestfin; }

    public String getNumcpt() { return numcpt; }
    public void setNumcpt(String numcpt) { this.numcpt = numcpt; }

    public String getCodpayTier() { return codpayTier; }
    public void setCodpayTier(String codpayTier) { this.codpayTier = codpayTier; }

    public BigDecimal getNbrrbq() { return nbrrbq; }
    public void setNbrrbq(BigDecimal nbrrbq) { this.nbrrbq = nbrrbq; }

    public BigDecimal getCours() { return cours; }
    public void setCours(BigDecimal cours) { this.cours = cours; }

    public String getNumDemandeTtn() { return numDemandeTtn; }
    public void setNumDemandeTtn(String numDemandeTtn) { this.numDemandeTtn = numDemandeTtn; }

    public String getTypemsg() { return typemsg; }
    public void setTypemsg(String typemsg) { this.typemsg = typemsg; }

    public String getTypedoc() { return typedoc; }
    public void setTypedoc(String typedoc) { this.typedoc = typedoc; }

    public String getNumMessage() { return numMessage; }
    public void setNumMessage(String numMessage) { this.numMessage = numMessage; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public String getEmetteur() { return emetteur; }
    public void setEmetteur(String emetteur) { this.emetteur = emetteur; }

    public String getDestinataire() { return destinataire; }
    public void setDestinataire(String destinataire) { this.destinataire = destinataire; }

    public String getCleAuth() { return cleAuth; }
    public void setCleAuth(String cleAuth) { this.cleAuth = cleAuth; }

    public Date getDateReception() { return dateReception; }
    public void setDateReception(Date dateReception) { this.dateReception = dateReception; }

    public BigDecimal getSeqReception() { return seqReception; }
    public void setSeqReception(BigDecimal seqReception) { this.seqReception = seqReception; }

    public Integer getCodeErreur() { return codeErreur; }
    public void setCodeErreur(Integer codeErreur) { this.codeErreur = codeErreur; }

    public Integer getCodeEtat() { return codeEtat; }
    public void setCodeEtat(Integer codeEtat) { this.codeEtat = codeEtat; }

    public String getIndTransact() { return indTransact; }
    public void setIndTransact(String indTransact) { this.indTransact = indTransact; }

    public String getNumDossTtnOrig() { return numDossTtnOrig; }
    public void setNumDossTtnOrig(String numDossTtnOrig) { this.numDossTtnOrig = numDossTtnOrig; }

    public String getNumDemandeTtnOrig() { return numDemandeTtnOrig; }
    public void setNumDemandeTtnOrig(String numDemandeTtnOrig) { this.numDemandeTtnOrig = numDemandeTtnOrig; }

    public String getNumMessageOrig() { return numMessageOrig; }
    public void setNumMessageOrig(String numMessageOrig) { this.numMessageOrig = numMessageOrig; }

    public String getAperakEnvoye() { return aperakEnvoye; }
    public void setAperakEnvoye(String aperakEnvoye) { this.aperakEnvoye = aperakEnvoye; }
} 