package com.example.stationdechange.service;

import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.entity.Aperak0Save;
import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.entity.TitresSave;
import com.example.stationdechange.entity.Imputations;
import com.example.stationdechange.entity.ImputationsSave;
import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.entity.Aperak1Arch;
import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.entity.PiecesJointesSave;
import com.example.stationdechange.entity.Nsh;
import com.example.stationdechange.entity.NshSave;
import com.example.stationdechange.entity.CondOctrTitres;
import com.example.stationdechange.entity.CondOctrTitresSave;
import com.example.stationdechange.repository.Aperak0Repository;
import com.example.stationdechange.repository.Aperak0SaveRepository;
import com.example.stationdechange.repository.TitresRepository;
import com.example.stationdechange.repository.TitresSaveRepository;
import com.example.stationdechange.repository.ImputationsRepository;
import com.example.stationdechange.repository.ImputationsSaveRepository;
import com.example.stationdechange.repository.Aperak1Repository;
import com.example.stationdechange.repository.Aperak1ArchRepository;
import com.example.stationdechange.repository.PiecesJointesRepository;
import com.example.stationdechange.repository.PiecesJointesSaveRepository;
import com.example.stationdechange.repository.NshRepository;
import com.example.stationdechange.repository.NshSaveRepository;
import com.example.stationdechange.repository.CondOctrTitresRepository;
import com.example.stationdechange.repository.CondOctrTitresSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Date;

@Service
public class DocumentProcessingService {
    
    @Autowired
    private Aperak0Repository aperak0Repository;
    
    @Autowired
    private Aperak0SaveRepository aperak0SaveRepository;
    
    @Autowired
    private TitresRepository titresRepository;
    
    @Autowired
    private TitresSaveRepository titresSaveRepository;
    
    @Autowired
    private ImputationsRepository imputationsRepository;
    
    @Autowired
    private ImputationsSaveRepository imputationsSaveRepository;
    
    @Autowired
    private Aperak1Repository aperak1Repository;
    
    @Autowired
    private Aperak1ArchRepository aperak1ArchRepository;
    
    @Autowired
    private PiecesJointesRepository piecesJointesRepository;
    
    @Autowired
    private PiecesJointesSaveRepository piecesJointesSaveRepository;
    
    @Autowired
    private NshRepository nshRepository;
    
    @Autowired
    private NshSaveRepository nshSaveRepository;
    
    @Autowired
    private CondOctrTitresRepository condOctrTitresRepository;
    
    @Autowired
    private CondOctrTitresSaveRepository condOctrTitresSaveRepository;
    
    /**
     * Process successful APERAK document
     */
    @Transactional
    public void processSuccessfulAperak(String messageId) {
        // Find the APERAK0 with the matching messageId (assuming messageId is stored in a field or can be matched)
        // If not, fallback to the most recent or only APERAK0
        Aperak0 aperak0 = aperak0Repository.findAll().stream()
            .filter(a -> messageId.equals(a.getMessageId())) // If you have a messageId field
            .findFirst()
            .orElse(null);
        if (aperak0 == null) {
            // Fallback: process the first one if messageId is not stored in Aperak0
            aperak0 = aperak0Repository.findAll().stream().findFirst().orElse(null);
        }
        if (aperak0 == null) {
            System.err.println("No APERAK0 found for messageId: " + messageId);
            return;
        }
        try {
            // Save to APERAK0_SAVE
            Aperak0Save aperak0Save = new Aperak0Save();
            aperak0Save.setNumeroDossierTtn(aperak0.getNumeroDossierTtn());
            aperak0Save.setNumeroMessage(aperak0.getNumeroMessage());
            aperak0Save.setCodeTypeDoc(aperak0.getCodeTypeDoc());
            aperak0Save.setEmetteur(aperak0.getEmetteur());
            aperak0Save.setCodeFlux(aperak0.getCodeFlux());
            aperak0Save.setDateTime(aperak0.getDateTime());
            aperak0Save.setIndTransact(aperak0.getIndTransact());
            aperak0Save.setDestinataire(aperak0.getDestinataire());
            aperak0Save.setNumeroDemande(aperak0.getNumeroDemande());
            aperak0Save.setProcessedDate(new Date());
            aperak0Save.setMessageId(messageId);
            aperak0Save.setDateReception(new Date());
            if (aperak0Save.getNumeroMessage() == null && aperak0.getNumeroMessage() == null) {
                aperak0Save.setNumeroMessage(aperak0.getNumeroDossierTtn());
            }
            aperak0SaveRepository.save(aperak0Save);

            // Archive all related Aperak1 rows
            java.util.List<Aperak1> aperak1List = aperak1Repository.findAll();
            for (Aperak1 aperak1 : aperak1List) {
                if (aperak0.getNumeroDossierTtn().equals(aperak1.getNumeroDossierTtn())) {
                    try {
                        Aperak1Arch arch = new Aperak1Arch();
                        arch.setNumeroDossierTtn(aperak1.getNumeroDossierTtn());
                        arch.setNumeroMessage(aperak1.getNumeroMessage());
                        arch.setCodeErreur(aperak1.getCodeErreur());
                        arch.setLibelleErreur(aperak1.getLibelleErreur());
                        arch.setRefErreur(aperak1.getRefErreur());
                        arch.setIndTransact(aperak1.getIndTransact());
                        arch.setNcol(aperak1.getNcol());
                        arch.setProcessedDate(new Date());
                        arch.setMessageId(messageId);
                        aperak1ArchRepository.save(arch);
                        aperak1Repository.delete(aperak1);
                    } catch (Exception e) {
                        System.err.println("Failed to archive Aperak1: " + aperak1 + ", error: " + e.getMessage());
                    }
                }
            }

            // Archive all matching Titres
            java.util.List<Titres> titresList = titresRepository.findAll();
            for (Titres titres : titresList) {
                if (titres.getNumDossTtn() != null && titres.getNumDossTtn().equals(aperak0.getNumeroDossierTtn()) &&
                    "APERAK".equalsIgnoreCase(titres.getTypemsg())) {
                    TitresSave titresSave = new TitresSave();
                    titresSave.setIdSeq(titres.getIdSeq());
                    titresSave.setProcessedDate(new Date());
                    titresSave.setMessageId(messageId);
                    titresSave.setNumDossTtn(titres.getNumDossTtn());
                    titresSave.setNumdom(titres.getNumdom());
                    titresSave.setNattit(titres.getNattit());
                    titresSave.setCoddevCnt(titres.getCoddevCnt());
                    titresSave.setCoddevRegl(titres.getCoddevRegl());
                    titresSave.setCodreg(titres.getCodreg());
                    titresSave.setCodliv(titres.getCodliv());
                    titresSave.setCoddel(titres.getCoddel());
                    titresSave.setCodregstat(titres.getCodregstat());
                    titresSave.setDatdom(titres.getDatdom());
                    titresSave.setNumdep(titres.getNumdep());
                    titresSave.setNumcnt(titres.getNumcnt());
                    titresSave.setDatcnt(titres.getDatcnt());
                    titresSave.setMntptfndev(titres.getMntptfndev());
                    titresSave.setMntfobdev(titres.getMntfobdev());
                    titresSave.setLibfrs(titres.getLibfrs());
                    titresSave.setAdrfrs(titres.getAdrfrs());
                    titresSave.setMntptfntnd(titres.getMntptfntnd());
                    titresSave.setMntfobtnd(titres.getMntfobtnd());
                    titresSave.setDatdepaut(titres.getDatdepaut());
                    titresSave.setDatenrgmin(titres.getDatenrgmin());
                    titresSave.setNumenrgmin(titres.getNumenrgmin());
                    titresSave.setDatfintit(titres.getDatfintit());
                    titresSave.setCodugdom(titres.getCodugdom());
                    titresSave.setNumdepext(titres.getNumdepext());
                    titresSave.setRaisoc(titres.getRaisoc());
                    titresSave.setAdrclt(titres.getAdrclt());
                    titresSave.setCoddou(titres.getCoddou());
                    titresSave.setCodugdep(titres.getCodugdep());
                    titresSave.setCodburdou(titres.getCodburdou());
                    titresSave.setNumdecldou(titres.getNumdecldou());
                    titresSave.setDatdecldou(titres.getDatdecldou());
                    titresSave.setCodpayOrg(titres.getCodpayOrg());
                    titresSave.setCodpayAchDestdeb(titres.getCodpayAchDestdeb());
                    titresSave.setCodpayProvDestfin(titres.getCodpayProvDestfin());
                    titresSave.setNumcpt(titres.getNumcpt());
                    titresSave.setCodpayTier(titres.getCodpayTier());
                    titresSave.setNbrrbq(titres.getNbrrbq());
                    titresSave.setCours(titres.getCours());
                    titresSave.setNumDemandeTtn(titres.getNumDemandeTtn());
                    titresSave.setTypemsg(titres.getTypemsg());
                    titresSave.setTypedoc(titres.getTypedoc());
                    titresSave.setNumMessage(titres.getNumMessage());
                    titresSave.setEtat(titres.getEtat());
                    titresSave.setEmetteur(titres.getEmetteur());
                    titresSave.setDestinataire(titres.getDestinataire());
                    titresSave.setCleAuth(titres.getCleAuth());
                    titresSave.setDateReception(new Date());
                    titresSave.setSeqReception(titres.getSeqReception());
                    titresSave.setCodeErreur(titres.getCodeErreur());
                    titresSave.setCodeEtat(titres.getCodeEtat());
                    titresSave.setIndTransact(titres.getIndTransact());
                    titresSave.setNumDossTtnOrig(titres.getNumDossTtnOrig());
                    titresSave.setNumDemandeTtnOrig(titres.getNumDemandeTtnOrig());
                    titresSave.setNumMessageOrig(titres.getNumMessageOrig());
                    titresSave.setAperakEnvoye(titres.getAperakEnvoye());
                    titresSaveRepository.save(titresSave);
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(titresSave);
                        System.out.println("Archived TitresSave JSON for validation:\n" + json);
                    } catch (Exception e) {
                        System.err.println("Failed to print TitresSave JSON: " + e.getMessage());
                    }
                }
            }
            // Delete all APERAK Titres in one query
            titresRepository.deleteByNumDossTtnAndTypemsg(aperak0.getNumeroDossierTtn(), "APERAK");

            // Delete from APERAK0
            aperak0Repository.delete(aperak0);

            System.out.println("Successfully processed APERAK0: " + aperak0.getNumeroDossierTtn());

        } catch (Exception e) {
            System.err.println("Failed to process APERAK0: " + aperak0.getNumeroDossierTtn() + ", error: " + e.getMessage());
        }
    }
    
    /**
     * Process successful SANCRT document
     */
    @Transactional
    public void processSuccessfulSancrt(String messageId) {
        // Process ALL Titres documents, not just the first one
        java.util.List<Titres> allTitres = titresRepository.findAll();
        
        for (Titres titres : allTitres) {
            try {
                // Save to TITRES_SAVE
                TitresSave titresSave = new TitresSave();
                titresSave.setIdSeq(titres.getIdSeq());
                titresSave.setProcessedDate(new Date());
                titresSave.setMessageId(messageId);
                titresSave.setNumDossTtn(titres.getNumDossTtn());
                titresSave.setNumdom(titres.getNumdom());
                titresSave.setNattit(titres.getNattit());
                titresSave.setCoddevCnt(titres.getCoddevCnt());
                titresSave.setCoddevRegl(titres.getCoddevRegl());
                titresSave.setCodreg(titres.getCodreg());
                titresSave.setCodliv(titres.getCodliv());
                titresSave.setCoddel(titres.getCoddel());
                titresSave.setCodregstat(titres.getCodregstat());
                titresSave.setDatdom(titres.getDatdom());
                titresSave.setNumdep(titres.getNumdep());
                titresSave.setNumcnt(titres.getNumcnt());
                titresSave.setDatcnt(titres.getDatcnt());
                titresSave.setMntptfndev(titres.getMntptfndev());
                titresSave.setMntfobdev(titres.getMntfobdev());
                titresSave.setLibfrs(titres.getLibfrs());
                titresSave.setAdrfrs(titres.getAdrfrs());
                titresSave.setMntptfntnd(titres.getMntptfntnd());
                titresSave.setMntfobtnd(titres.getMntfobtnd());
                titresSave.setDatdepaut(titres.getDatdepaut());
                titresSave.setDatenrgmin(titres.getDatenrgmin());
                titresSave.setNumenrgmin(titres.getNumenrgmin());
                titresSave.setDatfintit(titres.getDatfintit());
                titresSave.setCodugdom(titres.getCodugdom());
                titresSave.setNumdepext(titres.getNumdepext());
                titresSave.setRaisoc(titres.getRaisoc());
                titresSave.setAdrclt(titres.getAdrclt());
                titresSave.setCoddou(titres.getCoddou());
                titresSave.setCodugdep(titres.getCodugdep());
                titresSave.setCodburdou(titres.getCodburdou());
                titresSave.setNumdecldou(titres.getNumdecldou());
                titresSave.setDatdecldou(titres.getDatdecldou());
                titresSave.setCodpayOrg(titres.getCodpayOrg());
                titresSave.setCodpayAchDestdeb(titres.getCodpayAchDestdeb());
                titresSave.setCodpayProvDestfin(titres.getCodpayProvDestfin());
                titresSave.setNumcpt(titres.getNumcpt());
                titresSave.setCodpayTier(titres.getCodpayTier());
                titresSave.setNbrrbq(titres.getNbrrbq());
                titresSave.setCours(titres.getCours());
                titresSave.setNumDemandeTtn(titres.getNumDemandeTtn());
                titresSave.setTypemsg(titres.getTypemsg());
                titresSave.setTypedoc(titres.getTypedoc());
                titresSave.setNumMessage(titres.getNumMessage());
                titresSave.setEtat(titres.getEtat());
                titresSave.setEmetteur(titres.getEmetteur());
                titresSave.setDestinataire(titres.getDestinataire());
                titresSave.setCleAuth(titres.getCleAuth());
                titresSave.setDateReception(new Date());
                titresSave.setSeqReception(titres.getSeqReception());
                titresSave.setCodeErreur(titres.getCodeErreur());
                titresSave.setCodeEtat(titres.getCodeEtat());
                titresSave.setIndTransact(titres.getIndTransact());
                titresSave.setNumDossTtnOrig(titres.getNumDossTtnOrig());
                titresSave.setNumDemandeTtnOrig(titres.getNumDemandeTtnOrig());
                titresSave.setNumMessageOrig(titres.getNumMessageOrig());
                titresSave.setAperakEnvoye(titres.getAperakEnvoye());
                titresSaveRepository.save(titresSave);
                
                // Archive related PiecesJointes (by numDoc)
                java.util.List<PiecesJointes> piecesList = piecesJointesRepository.findAll();
                for (PiecesJointes pj : piecesList) {
                    if (titres.getNumDossTtn() != null && titres.getNumDossTtn().equals(pj.getNumDoc())) {
                        PiecesJointesSave save = new PiecesJointesSave();
                        save.setTypeDocPj(pj.getTypeDocPj());
                        save.setNumDoc(pj.getNumDoc());
                        save.setDateDoc(pj.getDateDoc());
                        save.setRefBaseImage(pj.getRefBaseImage());
                        save.setRefFichierJoint(pj.getRefFichierJoint());
                        save.setIdSeq(pj.getIdSeq());
                        save.setProcessedDate(new Date());
                        save.setMessageId(messageId);
                        piecesJointesSaveRepository.save(save);
                        piecesJointesRepository.delete(pj);
                    }
                }
                
                // Archive related NSH (by idSeq)
                java.util.List<Nsh> nshList = nshRepository.findAll();
                for (Nsh nsh : nshList) {
                    if (titres.getIdSeq() != null && titres.getIdSeq().equals(nsh.getIdSeq())) {
                        NshSave nshSave = new NshSave();
                        nshSave.setIdSeq(nsh.getIdSeq());
                        nshSave.setCodnsh(nsh.getCodnsh());
                        nshSave.setLibpdt(nsh.getLibpdt());
                        nshSave.setNbrunt(nsh.getNbrunt());
                        nshSave.setCoduntmes(nsh.getCoduntmes());
                        nshSave.setMntpft(nsh.getMntpft());
                        nshSave.setCodpayorg(nsh.getCodpayorg());
                        nshSave.setStatusMc(nsh.getStatusMc());
                        nshSave.setStatusOt(nsh.getStatusOt());
                        nshSave.setStatusBqe(nsh.getStatusBqe());
                        nshSave.setProcessedDate(new Date());
                        nshSave.setMessageId(messageId);
                        nshSave.setNumrbq(nsh.getNumrbq() != null ? nsh.getNumrbq() : java.math.BigDecimal.ZERO);
                        nshSaveRepository.save(nshSave);
                        nshRepository.delete(nsh);
                    }
                }

                // Archive related CondOctrTitres (by idSeq)
                java.util.List<CondOctrTitres> condList = condOctrTitresRepository.findAll();
                for (CondOctrTitres cond : condList) {
                    if (titres.getIdSeq() != null && titres.getIdSeq().equals(cond.getIdSeq())) {
                        CondOctrTitresSave condSave = new CondOctrTitresSave();
                        condSave.setIdSeq(cond.getIdSeq());
                        condSave.setCodeReserve(cond.getCodeReserve());
                        condSave.setLibelReserve(cond.getLibelReserve());
                        condSave.setCodeOrganisme(cond.getCodeOrganisme());
                        condSave.setIdCot(cond.getIdCot());
                        condSave.setProcessedDate(new Date());
                        condSave.setMessageId(messageId);
                        condOctrTitresSaveRepository.save(condSave);
                        condOctrTitresRepository.delete(cond);
                    }
                }
                
                // Delete from TITRES
                titresRepository.delete(titres);
                
                System.out.println("Successfully processed SANCRT: " + titres.getNumDossTtn());
                
            } catch (Exception e) {
                System.err.println("Failed to process SANCRT: " + titres.getNumDossTtn() + ", error: " + e.getMessage());
                // Continue with the next one
            }
        }
    }
    
    /**
     * Process successful CUSRES document
     */
    @Transactional
    public void processSuccessfulCusres(String messageId) {
        // Process ALL Imputations documents, not just the first one
        java.util.List<Imputations> allImputations = imputationsRepository.findAll();
        for (Imputations imputations : allImputations) {
            try {
                // Save to IMPUTATIONS_SAVE
                ImputationsSave imputationsSave = new ImputationsSave();
                imputationsSave.setIdImp(imputations.getIdImp());
                imputationsSave.setProcessedDate(new Date());
                imputationsSave.setMessageId(messageId);
                imputationsSave.setNumDossTtn(imputations.getNumDossTtn());
                imputationsSave.setNumdom(imputations.getNumdom());
                imputationsSave.setCoddev(imputations.getCoddev());
                imputationsSave.setDatimp(imputations.getDatimp());
                imputationsSave.setMntimp(imputations.getMntimp());
                imputationsSave.setQtecompl(imputations.getQtecompl());
                imputationsSave.setTypedecl(imputations.getTypedecl());
                imputationsSave.setNumdecl(imputations.getNumdecl());
                imputationsSave.setBurimp(imputations.getBurimp());
                imputationsSave.setInspimp(imputations.getInspimp());
                imputationsSave.setCodugdom(imputations.getCodugdom());
                imputationsSave.setAnneedom(imputations.getAnneedom());
                imputationsSave.setCoursDevImp(imputations.getCoursDevImp());
                imputationsSave.setMntimpDev(imputations.getMntimpDev());
                imputationsSave.setModliv(imputations.getModliv());
                imputationsSave.setNumart(imputations.getNumart());
                imputationsSave.setNgp(imputations.getNgp());
                imputationsSave.setCodtit(imputations.getCodtit());
                imputationsSave.setModreg(imputations.getModreg());
                imputationsSave.setRegstat(imputations.getRegstat());
                imputationsSaveRepository.save(imputationsSave);

                // Save related Titres to TITRES_SAVE (if found)
                Titres titres = titresRepository.findByNumDossTtnAndNumdom(imputations.getNumDossTtn(), imputations.getNumdom());
                if (titres != null) {
                    TitresSave titresSave = new TitresSave();
                    titresSave.setIdSeq(titres.getIdSeq());
                    titresSave.setProcessedDate(new Date());
                    titresSave.setMessageId(messageId);
                    titresSave.setNumDossTtn(titres.getNumDossTtn());
                    titresSave.setNumdom(titres.getNumdom());
                    titresSave.setNattit(titres.getNattit());
                    titresSave.setCoddevCnt(titres.getCoddevCnt());
                    titresSave.setCoddevRegl(titres.getCoddevRegl());
                    titresSave.setCodreg(titres.getCodreg());
                    titresSave.setCodliv(titres.getCodliv());
                    titresSave.setCoddel(titres.getCoddel());
                    titresSave.setCodregstat(titres.getCodregstat());
                    titresSave.setDatdom(titres.getDatdom());
                    titresSave.setNumdep(titres.getNumdep());
                    titresSave.setNumcnt(titres.getNumcnt());
                    titresSave.setDatcnt(titres.getDatcnt());
                    titresSave.setMntptfndev(titres.getMntptfndev());
                    titresSave.setMntfobdev(titres.getMntfobdev());
                    titresSave.setLibfrs(titres.getLibfrs());
                    titresSave.setAdrfrs(titres.getAdrfrs());
                    titresSave.setMntptfntnd(titres.getMntptfntnd());
                    titresSave.setMntfobtnd(titres.getMntfobtnd());
                    titresSave.setDatdepaut(titres.getDatdepaut());
                    titresSave.setDatenrgmin(titres.getDatenrgmin());
                    titresSave.setNumenrgmin(titres.getNumenrgmin());
                    titresSave.setDatfintit(titres.getDatfintit());
                    titresSave.setCodugdom(titres.getCodugdom());
                    titresSave.setNumdepext(titres.getNumdepext());
                    titresSave.setRaisoc(titres.getRaisoc());
                    titresSave.setAdrclt(titres.getAdrclt());
                    titresSave.setCoddou(titres.getCoddou());
                    titresSave.setCodugdep(titres.getCodugdep());
                    titresSave.setCodburdou(titres.getCodburdou());
                    titresSave.setNumdecldou(titres.getNumdecldou());
                    titresSave.setDatdecldou(titres.getDatdecldou());
                    titresSave.setCodpayOrg(titres.getCodpayOrg());
                    titresSave.setCodpayAchDestdeb(titres.getCodpayAchDestdeb());
                    titresSave.setCodpayProvDestfin(titres.getCodpayProvDestfin());
                    titresSave.setNumcpt(titres.getNumcpt());
                    titresSave.setCodpayTier(titres.getCodpayTier());
                    titresSave.setNbrrbq(titres.getNbrrbq());
                    titresSave.setCours(titres.getCours());
                    titresSave.setNumDemandeTtn(titres.getNumDemandeTtn());
                    titresSave.setTypemsg(titres.getTypemsg());
                    titresSave.setTypedoc(titres.getTypedoc());
                    titresSave.setNumMessage(titres.getNumMessage());
                    titresSave.setEtat(titres.getEtat());
                    titresSave.setEmetteur(titres.getEmetteur());
                    titresSave.setDestinataire(titres.getDestinataire());
                    titresSave.setCleAuth(titres.getCleAuth());
                    titresSave.setDateReception(new Date());
                    titresSave.setSeqReception(titres.getSeqReception());
                    titresSave.setCodeErreur(titres.getCodeErreur());
                    titresSave.setCodeEtat(titres.getCodeEtat());
                    titresSave.setIndTransact(titres.getIndTransact());
                    titresSave.setNumDossTtnOrig(titres.getNumDossTtnOrig());
                    titresSave.setNumDemandeTtnOrig(titres.getNumDemandeTtnOrig());
                    titresSave.setNumMessageOrig(titres.getNumMessageOrig());
                    titresSave.setAperakEnvoye(titres.getAperakEnvoye());
                    titresSaveRepository.save(titresSave);
                    // Also delete the Titres after archiving
                    titresRepository.delete(titres);
                }

                // Archive related PiecesJointes (by numDoc)
                java.util.List<PiecesJointes> piecesList = piecesJointesRepository.findAll();
                for (PiecesJointes pj : piecesList) {
                    if (imputations.getNumDossTtn() != null && imputations.getNumDossTtn().equals(pj.getNumDoc())) {
                        PiecesJointesSave save = new PiecesJointesSave();
                        save.setTypeDocPj(pj.getTypeDocPj());
                        save.setNumDoc(pj.getNumDoc());
                        save.setDateDoc(pj.getDateDoc());
                        save.setRefBaseImage(pj.getRefBaseImage());
                        save.setRefFichierJoint(pj.getRefFichierJoint());
                        save.setIdSeq(pj.getIdSeq());
                        save.setProcessedDate(new Date());
                        save.setMessageId(messageId);
                        piecesJointesSaveRepository.save(save);
                        piecesJointesRepository.delete(pj);
                    }
                }

                // Delete from IMPUTATIONS
                imputationsRepository.delete(imputations);

                // Delete from IMPUTATIONS_SAVE and TITRES_SAVE by messageId
                ImputationsSave savedImp = imputationsSaveRepository.findByMessageId(messageId);
                if (savedImp != null) {
                    imputationsSaveRepository.deleteById(savedImp.getId());
                }
                // Delete TitresSave by numDossTtn and numdom (shared data)
                long deletedCount = titresSaveRepository.deleteByNumDossTtnAndNumdom(imputations.getNumDossTtn(), imputations.getNumdom());
                System.out.println("Deleted TitresSave for numDossTtn=" + imputations.getNumDossTtn() + ", numdom=" + imputations.getNumdom() + ": " + deletedCount + " record(s)");

                System.out.println("Successfully processed CUSRES: " + imputations.getNumDossTtn());

            } catch (Exception e) {
                System.err.println("Failed to process CUSRES: " + imputations.getNumDossTtn() + ", error: " + e.getMessage());
                // Continue with the next one
            }
        }
    }
    
    /**
     * Process one successful APERAK document
     */
    @Transactional
    public void processOneSuccessfulAperak(Aperak0 aperak0, String messageId) {
        try {
            // Save to APERAK0_SAVE
            Aperak0Save aperak0Save = new Aperak0Save();
            aperak0Save.setNumeroDossierTtn(aperak0.getNumeroDossierTtn());
            aperak0Save.setNumeroMessage(aperak0.getNumeroMessage());
            aperak0Save.setCodeTypeDoc(aperak0.getCodeTypeDoc());
            aperak0Save.setEmetteur(aperak0.getEmetteur());
            aperak0Save.setCodeFlux(aperak0.getCodeFlux());
            aperak0Save.setDateTime(aperak0.getDateTime());
            aperak0Save.setIndTransact(aperak0.getIndTransact());
            aperak0Save.setDestinataire(aperak0.getDestinataire());
            aperak0Save.setNumeroDemande(aperak0.getNumeroDemande());
            aperak0Save.setProcessedDate(new Date());
            aperak0Save.setMessageId(messageId);
            aperak0Save.setDateReception(new Date());
            
            aperak0SaveRepository.save(aperak0Save);
            
            // Find all related Aperak1 rows
            java.util.List<Aperak1> relatedAperak1List = new java.util.ArrayList<>();
            java.util.List<Aperak1> aperak1List = aperak1Repository.findAll();
            for (Aperak1 aperak1 : aperak1List) {
                if (aperak0.getNumeroDossierTtn().equals(aperak1.getNumeroDossierTtn())) {
                    relatedAperak1List.add(aperak1);
                }
            }
            
            if (relatedAperak1List.isEmpty()) {
                // No related Aperak1 rows: just delete Aperak0
                aperak0Repository.delete(aperak0);
                System.out.println("Successfully processed APERAK0 (no related APERAK1): " + aperak0.getNumeroDossierTtn());
            } else {
                // Archive and delete all related Aperak1 rows
                for (Aperak1 aperak1 : relatedAperak1List) {
                    try {
                        Aperak1Arch arch = new Aperak1Arch();
                        arch.setNumeroDossierTtn(aperak1.getNumeroDossierTtn());
                        arch.setNumeroMessage(aperak1.getNumeroMessage());
                        arch.setCodeErreur(aperak1.getCodeErreur());
                        arch.setLibelleErreur(aperak1.getLibelleErreur());
                        arch.setRefErreur(aperak1.getRefErreur());
                        arch.setIndTransact(aperak1.getIndTransact());
                        arch.setNcol(aperak1.getNcol());
                        arch.setProcessedDate(new Date());
                        arch.setMessageId(messageId);
                        aperak1ArchRepository.save(arch);
                        aperak1Repository.delete(aperak1);
                    } catch (Exception e) {
                        // Log the error and continue with the next one
                        System.err.println("Failed to archive Aperak1: " + aperak1 + ", error: " + e.getMessage());
                    }
                }
                // Delete from APERAK0
                aperak0Repository.delete(aperak0);
                System.out.println("Successfully processed APERAK0 (with related APERAK1): " + aperak0.getNumeroDossierTtn());
            }
        } catch (Exception e) {
            System.err.println("Failed to process APERAK0: " + aperak0.getNumeroDossierTtn() + ", error: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Process one successful CUSRES document
     */
    @Transactional
    public void processOneSuccessfulCusres(Imputations imputations, String messageId) {
        try {
            // Save to IMPUTATIONS_SAVE
            ImputationsSave imputationsSave = new ImputationsSave();
            imputationsSave.setIdImp(imputations.getIdImp());
            imputationsSave.setProcessedDate(new Date());
            imputationsSave.setMessageId(messageId);
            imputationsSave.setNumDossTtn(imputations.getNumDossTtn());
            imputationsSave.setNumdom(imputations.getNumdom());
            imputationsSave.setCoddev(imputations.getCoddev());
            imputationsSave.setDatimp(imputations.getDatimp());
            imputationsSave.setMntimp(imputations.getMntimp());
            imputationsSave.setQtecompl(imputations.getQtecompl());
            imputationsSave.setTypedecl(imputations.getTypedecl());
            imputationsSave.setNumdecl(imputations.getNumdecl());
            imputationsSave.setBurimp(imputations.getBurimp());
            imputationsSave.setInspimp(imputations.getInspimp());
            imputationsSave.setCodugdom(imputations.getCodugdom());
            imputationsSave.setAnneedom(imputations.getAnneedom());
            imputationsSave.setCoursDevImp(imputations.getCoursDevImp());
            imputationsSave.setMntimpDev(imputations.getMntimpDev());
            imputationsSave.setModliv(imputations.getModliv());
            imputationsSave.setNumart(imputations.getNumart());
            imputationsSave.setNgp(imputations.getNgp());
            imputationsSave.setCodtit(imputations.getCodtit());
            imputationsSave.setModreg(imputations.getModreg());
            imputationsSave.setRegstat(imputations.getRegstat());
            imputationsSaveRepository.save(imputationsSave);
            
            // Archive related PiecesJointes (by numDoc)
            java.util.List<PiecesJointes> piecesList = piecesJointesRepository.findAll();
            for (PiecesJointes pj : piecesList) {
                if (imputations.getNumDossTtn() != null && imputations.getNumDossTtn().equals(pj.getNumDoc())) {
                    PiecesJointesSave save = new PiecesJointesSave();
                    save.setTypeDocPj(pj.getTypeDocPj());
                    save.setNumDoc(pj.getNumDoc());
                    save.setDateDoc(pj.getDateDoc());
                    save.setRefBaseImage(pj.getRefBaseImage());
                    save.setRefFichierJoint(pj.getRefFichierJoint());
                    save.setIdSeq(pj.getIdSeq());
                    save.setProcessedDate(new Date());
                    save.setMessageId(messageId);
                    piecesJointesSaveRepository.save(save);
                    piecesJointesRepository.delete(pj);
                }
            }
            
            // Delete from IMPUTATIONS
            imputationsRepository.delete(imputations);
            
            System.out.println("Successfully processed CUSRES: " + imputations.getNumDossTtn());
            
        } catch (Exception e) {
            System.err.println("Failed to process CUSRES: " + imputations.getNumDossTtn() + ", error: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Process one successful SANCRT document
     */
    @Transactional
    public void processOneSuccessfulSancrt(Titres titres, String messageId) {
        try {
            // Save to TITRES_SAVE
            TitresSave titresSave = new TitresSave();
            titresSave.setIdSeq(titres.getIdSeq());
            titresSave.setProcessedDate(new Date());
            titresSave.setMessageId(messageId);
            titresSave.setNumDossTtn(titres.getNumDossTtn());
            titresSave.setNumdom(titres.getNumdom());
            titresSave.setNattit(titres.getNattit());
            titresSave.setCoddevCnt(titres.getCoddevCnt());
            titresSave.setCoddevRegl(titres.getCoddevRegl());
            titresSave.setCodreg(titres.getCodreg());
            titresSave.setCodliv(titres.getCodliv());
            titresSave.setCoddel(titres.getCoddel());
            titresSave.setCodregstat(titres.getCodregstat());
            titresSave.setDatdom(titres.getDatdom());
            titresSave.setNumdep(titres.getNumdep());
            titresSave.setNumcnt(titres.getNumcnt());
            titresSave.setDatcnt(titres.getDatcnt());
            titresSave.setMntptfndev(titres.getMntptfndev());
            titresSave.setMntfobdev(titres.getMntfobdev());
            titresSave.setLibfrs(titres.getLibfrs());
            titresSave.setAdrfrs(titres.getAdrfrs());
            titresSave.setMntptfntnd(titres.getMntptfntnd());
            titresSave.setMntfobtnd(titres.getMntfobtnd());
            titresSave.setDatdepaut(titres.getDatdepaut());
            titresSave.setDatenrgmin(titres.getDatenrgmin());
            titresSave.setNumenrgmin(titres.getNumenrgmin());
            titresSave.setDatfintit(titres.getDatfintit());
            titresSave.setCodugdom(titres.getCodugdom());
            titresSave.setNumdepext(titres.getNumdepext());
            titresSave.setRaisoc(titres.getRaisoc());
            titresSave.setAdrclt(titres.getAdrclt());
            titresSave.setCoddou(titres.getCoddou());
            titresSave.setCodugdep(titres.getCodugdep());
            titresSave.setCodburdou(titres.getCodburdou());
            titresSave.setNumdecldou(titres.getNumdecldou());
            titresSave.setDatdecldou(titres.getDatdecldou());
            titresSave.setCodpayOrg(titres.getCodpayOrg());
            titresSave.setCodpayAchDestdeb(titres.getCodpayAchDestdeb());
            titresSave.setCodpayProvDestfin(titres.getCodpayProvDestfin());
            titresSave.setNumcpt(titres.getNumcpt());
            titresSave.setCodpayTier(titres.getCodpayTier());
            titresSave.setNbrrbq(titres.getNbrrbq());
            titresSave.setCours(titres.getCours());
            titresSave.setNumDemandeTtn(titres.getNumDemandeTtn());
            titresSave.setTypemsg(titres.getTypemsg());
            titresSave.setTypedoc(titres.getTypedoc());
            titresSave.setNumMessage(titres.getNumMessage());
            titresSave.setEtat(titres.getEtat());
            titresSave.setEmetteur(titres.getEmetteur());
            titresSave.setDestinataire(titres.getDestinataire());
            titresSave.setCleAuth(titres.getCleAuth());
            titresSave.setDateReception(new Date());
            titresSave.setSeqReception(titres.getSeqReception());
            titresSave.setCodeErreur(titres.getCodeErreur());
            titresSave.setCodeEtat(titres.getCodeEtat());
            titresSave.setIndTransact(titres.getIndTransact());
            titresSave.setNumDossTtnOrig(titres.getNumDossTtnOrig());
            titresSave.setNumDemandeTtnOrig(titres.getNumDemandeTtnOrig());
            titresSave.setNumMessageOrig(titres.getNumMessageOrig());
            titresSave.setAperakEnvoye(titres.getAperakEnvoye());
            titresSaveRepository.save(titresSave);
            
            // Archive related PiecesJointes (by numDoc)
            java.util.List<PiecesJointes> piecesList = piecesJointesRepository.findAll();
            for (PiecesJointes pj : piecesList) {
                if (titres.getNumDossTtn() != null && titres.getNumDossTtn().equals(pj.getNumDoc())) {
                    PiecesJointesSave save = new PiecesJointesSave();
                    save.setTypeDocPj(pj.getTypeDocPj());
                    save.setNumDoc(pj.getNumDoc());
                    save.setDateDoc(pj.getDateDoc());
                    save.setRefBaseImage(pj.getRefBaseImage());
                    save.setRefFichierJoint(pj.getRefFichierJoint());
                    save.setIdSeq(pj.getIdSeq());
                    save.setProcessedDate(new Date());
                    save.setMessageId(messageId);
                    piecesJointesSaveRepository.save(save);
                    piecesJointesRepository.delete(pj);
                }
            }
            
            // Archive related NSH (by idSeq)
            java.util.List<Nsh> nshList = nshRepository.findAll();
            for (Nsh nsh : nshList) {
                if (titres.getIdSeq() != null && titres.getIdSeq().equals(nsh.getIdSeq())) {
                    NshSave nshSave = new NshSave();
                    nshSave.setIdSeq(nsh.getIdSeq());
                    nshSave.setCodnsh(nsh.getCodnsh());
                    nshSave.setLibpdt(nsh.getLibpdt());
                    nshSave.setNbrunt(nsh.getNbrunt());
                    nshSave.setCoduntmes(nsh.getCoduntmes());
                    nshSave.setMntpft(nsh.getMntpft());
                    nshSave.setCodpayorg(nsh.getCodpayorg());
                    nshSave.setStatusMc(nsh.getStatusMc());
                    nshSave.setStatusOt(nsh.getStatusOt());
                    nshSave.setStatusBqe(nsh.getStatusBqe());
                    nshSave.setProcessedDate(new Date());
                    nshSave.setMessageId(messageId);
                    nshSave.setNumrbq(nsh.getNumrbq() != null ? nsh.getNumrbq() : java.math.BigDecimal.ZERO);
                    nshSaveRepository.save(nshSave);
                    nshRepository.delete(nsh);
                }
            }

            // Archive related CondOctrTitres (by idSeq)
            java.util.List<CondOctrTitres> condList = condOctrTitresRepository.findAll();
            for (CondOctrTitres cond : condList) {
                if (titres.getIdSeq() != null && titres.getIdSeq().equals(cond.getIdSeq())) {
                    CondOctrTitresSave condSave = new CondOctrTitresSave();
                    condSave.setIdSeq(cond.getIdSeq());
                    condSave.setCodeReserve(cond.getCodeReserve());
                    condSave.setLibelReserve(cond.getLibelReserve());
                    condSave.setCodeOrganisme(cond.getCodeOrganisme());
                    condSave.setIdCot(cond.getIdCot());
                    condSave.setProcessedDate(new Date());
                    condSave.setMessageId(messageId);
                    condOctrTitresSaveRepository.save(condSave);
                    condOctrTitresRepository.delete(cond);
                }
            }
            
            // Delete from TITRES
            titresRepository.delete(titres);
            
            System.out.println("Successfully processed SANCRT: " + titres.getNumDossTtn());
            
        } catch (Exception e) {
            System.err.println("Failed to process SANCRT: " + titres.getNumDossTtn() + ", error: " + e.getMessage());
            throw e;
        }
    }
    
    // Getter methods for repositories
    public Aperak0Repository getAperak0Repository() {
        return aperak0Repository;
    }
    
    public ImputationsRepository getImputationsRepository() {
        return imputationsRepository;
    }
    
    public TitresRepository getTitresRepository() {
        return titresRepository;
    }
} 