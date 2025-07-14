package com.example.stationdechange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tn.smi.authentification.DTO.ReceptionResponse;
import org.springframework.context.annotation.Lazy;
import com.example.stationdechange.kafka.KafkaRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.entity.Imputations;
import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.repository.TitresRepository;
import com.example.stationdechange.repository.ImputationsRepository;
import com.example.stationdechange.repository.Aperak0Repository;
import com.example.stationdechange.dto.AperakDocumentDTO;
import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.entity.Aperak1Id;
import com.example.stationdechange.repository.Aperak1Repository;
import com.example.stationdechange.dto.SancrtDocumentDTO;
import com.example.stationdechange.dto.CusresDocumentDTO;
import com.example.stationdechange.entity.Nsh;
import com.example.stationdechange.repository.NshRepository;
import com.example.stationdechange.repository.PiecesJointesRepository;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.LocalDate;

import java.util.concurrent.CompletableFuture;

import static com.example.stationdechange.service.ScheduledProcessingService.STATUS_MAP;
import static com.example.stationdechange.service.ScheduledProcessingService.buildKey;

@Service
public class KafkaService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    
    @Autowired
    private DocumentProcessingService documentProcessingService;
    
    @Autowired
    @Lazy
    private ScheduledProcessingService scheduledProcessingService;
    
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TitresRepository titresRepository;
    @Autowired
    private ImputationsRepository imputationsRepository;
    @Autowired
    private Aperak0Repository aperak0Repository;
    @Autowired
    private Aperak1Repository aperak1Repository;
    @Autowired
    private NshRepository nshRepository;
    @Autowired
    private PiecesJointesRepository piecesJointesRepository;
    
    @Autowired
    private KafkaTemplate<String, ReceptionResponse> kafkaTemplateReceptionResponse;
    private static final String RESPONSE_TOPIC = "flux-response-out";
    
    /**
     * Listen for responses from flux-response topic
     * If response is successful, process the document (save to _SAVE table and delete from original table)
     * If response is failed, do nothing (keep in original table)
     */
    @KafkaListener(topics = "flux-outbound", groupId = "flux-saving-group")
    public void listen(
        @Payload String payload,
        @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {
        logger.info("Received message from topic 'flux-outbound' with key: {}", key);
        String status = "success";
        String message = "Processed successfully";
        Long idSeq = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(payload);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
            logger.info("Full JSON payload received (pretty):\n{}", prettyJson);
            JsonNode enteteFlux = root.path("body").path("enteteFlux");
            String typeDocument = root.has("typeDocument") ? root.get("typeDocument").asText() : null;
            JsonNode body = root.path("body");
            logger.info("Proceeding to save entities for document type: {}", typeDocument);
            switch (typeDocument != null ? typeDocument.toUpperCase() : "") {
                case "SANCRT": {
                    SancrtDocumentDTO.Body sancrtBody = objectMapper.treeToValue(body, SancrtDocumentDTO.Body.class);
                    Titres titres = new Titres();
                    titres.setNumDossTtn(sancrtBody.enteteFlux.numeroTtn);
                    titres.setNumdom(sancrtBody.enteteFlux.numDom != null ? new java.math.BigDecimal(sancrtBody.enteteFlux.numDom) : null);
                    titres.setTypedoc(sancrtBody.enteteFlux.typeDocument);
                    titres.setTypemsg(sancrtBody.enteteFlux.typeMessage);
                    titres.setDatdom(sancrtBody.enteteFlux.dateDom);
                    titres.setNumDemandeTtn(sancrtBody.enteteFlux.numeroDemande);
                    titres.setNattit(sancrtBody.enteteFlux.codeTitre != null ? String.valueOf(sancrtBody.enteteFlux.codeTitre) : null);
                    titres.setNumdep(sancrtBody.enteteFlux.numDepot != null ? new java.math.BigDecimal(sancrtBody.enteteFlux.numDepot) : null);
                    titres.setDatdepaut(sancrtBody.enteteFlux.dateDepot);
                    titres.setDatdecldou(sancrtBody.enteteFlux.dateDeclaration);
                    titres.setDestinataire(sancrtBody.enteteFlux.destinataire);
                    titres.setEmetteur(sancrtBody.enteteFlux.emetteur);
                    titres.setEtat(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.etat : null);
                    titres.setNumMessage(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.refTtnNumMessage : null);
                    titres.setCleAuth(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.cleAuth : null);
                    titres.setLibfrs(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.exportateurRaisonSociale : null);
                    titres.setAdrfrs(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.exportateurAdresseLine1 : null);
                    titres.setRaisoc(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.importateurRaisonSociale : null);
                    titres.setNumdepext(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.importateurCode : null);
                    titres.setAdrclt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.importateurAdresseLine1 : null);
                    titres.setCodpayOrg(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.paysProvenanceCode : null);
                    titres.setCodpayAchDestdeb(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.paysAchatCode : null);
                    titres.setCodpayProvDestfin(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.paysDestDefCode : null);
                    titres.setCodliv(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.modeLivraisonCode : null);
                    titres.setNumcpt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.domiciliationNumCompte : null);
                    titres.setCodreg(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.reglementMode : null);
                    titres.setCoddel(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.reglementDelai : null);
                    titres.setCoddevRegl(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.reglementDevise : null);
                    titres.setCours(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.coursConversionDevise != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.coursConversionDevise) : null);
                    titres.setMntptfndev(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantPtfnDevise != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantPtfnDevise) : null);
                    titres.setMntfobdev(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantFobDevise != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantFobDevise) : null);
                    titres.setMntptfntnd(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantPtfnDinars != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantPtfnDinars) : null);
                    titres.setMntfobtnd(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantFobDinars != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantFobDinars) : null);
                    titres.setCodregstat(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.regimeStatCode : null);
                    titres.setNumcnt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.contratNum : null);
                    titres.setDatcnt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.contratDate : null);
                    titres.setDatfintit(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.validiteFin : null);
                    titres.setNumdep(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.numeroDepot != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.numeroDepot) : null);
                    titres.setNumdecldou(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.numeroDeclarationImputation : null);
                    titres.setCodburdou(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.codeBureauDouaneImputation : null);
                    if (sancrtBody.detailSancrt != null) {
                        titres.setNumMessage(sancrtBody.detailSancrt.refTtnNumMessage);
                        titres.setEtat(sancrtBody.detailSancrt.etat);
                    } else {
                        titres.setNumMessage(null);
                        titres.setEtat(null);
                    }
                    titres.setEmetteur(sancrtBody.enteteFlux.emetteur);
                    titres.setDestinataire(sancrtBody.enteteFlux.destinataire);
                    titres.setDatdecldou(sancrtBody.enteteFlux.dateDeclaration);
                    titres.setNumdecldou(
                        sancrtBody.enteteFlux.numDeclaration != null
                            ? sancrtBody.enteteFlux.numDeclaration.toString()
                            : null
                    );
                    // FIX: Assign numCpt as String, not BigDecimal
                    titres.setNumcpt(
                        sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.domiciliationNumCompte : null
                    );
                    
                    titres.setNumDemandeTtnOrig(sancrtBody.enteteFlux.numeroDemande);
                    titres.setNumDemandeTtnOrig(sancrtBody.enteteFlux.numeroDemande);
                    Titres savedTitres = titresRepository.save(titres);
                    idSeq = savedTitres.getIdSeq();
                    // Save PiecesJointes
                    if (sancrtBody.pieceJointeList != null) {
                        for (SancrtDocumentDTO.PieceJointe pj : sancrtBody.pieceJointeList) {
                            com.example.stationdechange.entity.PiecesJointes entity = new com.example.stationdechange.entity.PiecesJointes();
                            entity.setTypeDocPj(pj.typeDocument);
                            entity.setNumDoc(pj.numDocument);
                            if (pj.dateDocument != null) entity.setDateDoc(pj.dateDocument.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                            entity.setRefFichierJoint(pj.refFichierJoint);
                            entity.setRefBaseImage(pj.refBaseImage);
                            entity.setIdSeq(pj.id != null ? pj.id.idFlux : null);
                            entity.setCodePiecesJointes(pj.id != null && pj.id.numeroPieceJointe != null ? pj.id.numeroPieceJointe.toString() : null);
                            piecesJointesRepository.save(entity);
                        }
                        logger.info("Saved {} PiecesJointes entities to database for SANCRT.", sancrtBody.pieceJointeList.size());
                    }
                    // Save Nsh (articles)
                    if (sancrtBody.articleList != null) {
                        for (SancrtDocumentDTO.Article art : sancrtBody.articleList) {
                            Nsh nsh = new Nsh();
                            nsh.setIdSeq(art.id != null ? art.id.idFlux : null);
                            nsh.setCodnsh(art.numeroNomenclature);
                            nsh.setMntpft(art.prixFactureNet != null ? new java.math.BigDecimal(art.prixFactureNet) : null);
                            nsh.setNbrunt(art.quantite != null ? new java.math.BigDecimal(art.quantite) : null);
                            nsh.setLibpdt(art.designation);
                            nsh.setCoduntmes(art.uniteMesure);
                            nsh.setCodpayorg(art.paysOrigineCodePays);
                            nsh.setStatusMc(art.statutMinistereCommerceCodeStatut);
                            nsh.setStatusOt(art.statutOrganismeTechniqueCode);
                            nsh.setStatusBqe(art.codeStatutBanque);
                            nshRepository.save(nsh);
                        }
                        logger.info("Saved {} Nsh (article) entities to database for SANCRT.", sancrtBody.articleList.size());
                    }
                    break;
                }
                case "CUSRES": {
                    CusresDocumentDTO.Body cusresBody = objectMapper.treeToValue(body, CusresDocumentDTO.Body.class);
                  
                    // Save Imputations
                    Imputations imputations = new Imputations();
                    imputations.setIdImp(cusresBody.getEnteteFlux().id);
                    imputations.setNumDossTtn(cusresBody.getEnteteFlux().numeroTtn);
                    imputations.setNumdom(cusresBody.getEnteteFlux().numDom != null ? new java.math.BigDecimal(cusresBody.getEnteteFlux().numDom) : null);
                    imputations.setDatimp(cusresBody.getEnteteFlux().dateInsertion);
                    CusresDocumentDTO.DetailCusres detail = cusresBody.getDetailCusres();
                    if (detail != null) {
                        imputations.setAnneedom(detail.enregistrementNumero);
                        imputations.setCodtit(detail.cleAuth);
                        imputations.setCodugdom(detail.statutDocumentEtatLaDeclaration3);
                        imputations.setCoursDevImp(detail.coursConversionDeviseFacturation != null ? new java.math.BigDecimal(detail.coursConversionDeviseFacturation) : null);
                        imputations.setMntimp(detail.valeurDouaneTotaleDinards != null ? new java.math.BigDecimal(detail.valeurDouaneTotaleDinards) : null);
                        imputations.setMntimpDev(detail.montantDevisePtfn != null ? new java.math.BigDecimal(detail.montantDevisePtfn) : null);
                        imputations.setModliv(detail.affectationNomInspecteur);
                        imputations.setModreg(null);
                        imputations.setQtecompl(null);
                        if (cusresBody.getArticleCusresList() != null && !cusresBody.getArticleCusresList().isEmpty()) {
                            CusresDocumentDTO.ArticleCusres article = cusresBody.getArticleCusresList().get(0);
                            imputations.setModreg(article.reglementFinancierModeReglement);
                            imputations.setQtecompl(article.reglementFinancierDelai != null ? new java.math.BigDecimal(article.reglementFinancierDelai) : null);
                        }
                        imputations.setRegstat(detail.statutDocumentCodeStatut);
                        imputations.setBurimp(null);
                        imputations.setCoddev(detail.montantDeviseCodeDevise);
                        imputations.setTypedecl(detail.typeDocument);
                        imputations.setNgp(null);
                        imputations.setInspimp(detail.statutDocumentEtatLaDeclaration2);
                    }
                    imputationsRepository.save(imputations);
                    logger.info("Saved Imputations entity to database for CUSRES.");
                    break;
                }
                case "APERAK": {
                    AperakDocumentDTO.Body aperakBody = objectMapper.treeToValue(body, AperakDocumentDTO.Body.class);
                    // Save Aperak0
                    Aperak0 aperak0 = new Aperak0();
                    AperakDocumentDTO.EnteteFlux entete = aperakBody.getEnteteFlux();
                    AperakDocumentDTO.DetailAperak detail = aperakBody.getDetailAperak();
                    aperak0.setNumeroDossierTtn(entete != null ? entete.getNumeroTtn() : null);
                    aperak0.setNumeroMessage(detail != null ? detail.getRefTtnNumMessage() : null);
                    aperak0.setCodeTypeDoc(entete != null ? entete.getTypeDocument() : null);
                    aperak0.setEmetteur(entete != null ? entete.getEmetteur() : null);
                    aperak0.setCodeFlux(entete != null ? entete.getTypeMessage() : null);
                    aperak0.setDateTime(entete != null ? entete.getDateInsertion() : null);
                    aperak0.setIndTransact(entete != null ? entete.getCodeCloture() : null);
                    aperak0.setDestinataire(entete != null ? entete.getDestinataire() : null);
                    aperak0.setNumeroDemande(entete != null ? entete.getNumeroDemande() : null);
                    aperak0.setMessageId(null);
                    aperak0Repository.save(aperak0);
                    logger.info("Saved APERAK0 entity to database.");
                    // Save Aperak1 for each error
                    if (aperakBody.getAperakErreurs() != null) {
                        for (AperakDocumentDTO.AperakErreur err : aperakBody.getAperakErreurs()) {
                            Aperak1 aperak1 = new Aperak1();
                            Aperak1Id aperak1Id = new Aperak1Id();
                            aperak1Id.setNumeroDossierTtn(entete != null ? entete.getNumeroTtn() : null);
                            aperak1Id.setNumeroMessage(detail != null ? detail.getRefTtnNumMessage() : null);
                            aperak1.setId(aperak1Id);
                            aperak1.setCodeErreur(err.getCodeErreur());
                            aperak1.setLibelleErreur(err.getLibelleErreur());
                            aperak1.setRefErreur(err.getReferenceDonnee());
                            aperak1.setIndTransact(entete != null ? entete.getCodeCloture() : null);
                            aperak1.setNcol(err.getNumeroOccurrenceDonnee() != null ? err.getNumeroOccurrenceDonnee().longValue() : null);
                            aperak1Repository.save(aperak1);
                        }
                        logger.info("Saved {} APERAK1 error entities to database.", aperakBody.getAperakErreurs().size());
                    }
                    // Do NOT save Titres for APERAK
                    break;
                }
                default:
                    logger.warn("Unknown typeDocument: {}. Message not saved.", typeDocument);
            }
        } catch (Exception e) {
            status = "failed";
            message = e.getMessage();
            logger.error("Error processing response", e);
        } finally {
            ReceptionResponse response = new ReceptionResponse(status, idSeq, message, java.time.LocalDate.now().atStartOfDay());
            System.out.println("Kafka response: " + response);
            kafkaTemplateReceptionResponse.send(RESPONSE_TOPIC, key, response);
            logger.info("Sent response to topic '{}': {}", RESPONSE_TOPIC, response);
        }
    }
    
    /**
     * Determine document type from Kafka key or message
     */
    private String determineDocumentType(String key, String message) {
        if (key != null) {
            if (key.startsWith("APERAK_")) return "APERAK";
            if (key.startsWith("SANCRT_")) return "SANCRT";
            if (key.startsWith("CUSRES_")) return "CUSRES";
        }
        
        if (message != null) {
            if (message.toUpperCase().contains("APERAK")) return "APERAK";
            if (message.toUpperCase().contains("SANCRT")) return "SANCRT";
            if (message.toUpperCase().contains("CUSRES")) return "CUSRES";
        }
        
        return "UNKNOWN";
    }

    @KafkaListener(topics = "flux-outbound", groupId = "flux-saving-group")
    public void listenAndSave(@Payload String payload, @Header(value = "messageId", required = false) String messageId) {
        logger.info("Received message from topic 'flux-outbound' with messageId: {}", messageId);
        logger.info("Full JSON payload received: {}", payload);
        try {
            KafkaRequestModel request = objectMapper.readValue(payload, KafkaRequestModel.class);
            String type = request.getTypeDocument();
            JsonNode body = request.getBody();
            if (type != null && !type.isEmpty()) {
                logger.info("\u2705 Status found: '{}'. Proceeding to save entities for document type: {}", type, type);
                switch (type.toUpperCase()) {
                    case "SANCRT": {
                        SancrtDocumentDTO.Body sancrtBody = objectMapper.treeToValue(body, SancrtDocumentDTO.Body.class);
                        // Save Titres
                        Titres titres = new Titres();
                        titres.setNumDossTtn(sancrtBody.enteteFlux.numeroTtn);
                        titres.setNumdom(sancrtBody.enteteFlux.numDom != null ? new java.math.BigDecimal(sancrtBody.enteteFlux.numDom) : null);
                        titres.setTypedoc(sancrtBody.enteteFlux.typeDocument);
                        titres.setTypemsg(sancrtBody.enteteFlux.typeMessage);
                        titres.setDatdom(sancrtBody.enteteFlux.dateDom);
                        titres.setNumDemandeTtn(sancrtBody.enteteFlux.numeroDemande);
                        titres.setNattit(sancrtBody.enteteFlux.codeTitre != null ? String.valueOf(sancrtBody.enteteFlux.codeTitre) : null);
                        titres.setNumdep(sancrtBody.enteteFlux.numDepot != null ? new java.math.BigDecimal(sancrtBody.enteteFlux.numDepot) : null);
                        titres.setDatdepaut(sancrtBody.enteteFlux.dateDepot);
                        titres.setDatdecldou(sancrtBody.enteteFlux.dateDeclaration);
                        titres.setDestinataire(sancrtBody.enteteFlux.destinataire);
                        titres.setEmetteur(sancrtBody.enteteFlux.emetteur);
                        titres.setEtat(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.etat : null);
                        titres.setNumMessage(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.refTtnNumMessage : null);
                        titres.setCleAuth(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.cleAuth : null);
                        titres.setLibfrs(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.exportateurRaisonSociale : null);
                        titres.setAdrfrs(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.exportateurAdresseLine1 : null);
                        titres.setRaisoc(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.importateurRaisonSociale : null);
                        titres.setNumdepext(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.importateurCode : null);
                        titres.setAdrclt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.importateurAdresseLine1 : null);
                        titres.setCodpayOrg(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.paysProvenanceCode : null);
                        titres.setCodpayAchDestdeb(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.paysAchatCode : null);
                        titres.setCodpayProvDestfin(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.paysDestDefCode : null);
                        titres.setCodliv(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.modeLivraisonCode : null);
                        titres.setNumcpt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.domiciliationNumCompte : null);
                        titres.setCodreg(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.reglementMode : null);
                        titres.setCoddel(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.reglementDelai : null);
                        titres.setCoddevRegl(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.reglementDevise : null);
                        titres.setCours(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.coursConversionDevise != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.coursConversionDevise) : null);
                        titres.setMntptfndev(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantPtfnDevise != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantPtfnDevise) : null);
                        titres.setMntfobdev(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantFobDevise != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantFobDevise) : null);
                        titres.setMntptfntnd(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantPtfnDinars != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantPtfnDinars) : null);
                        titres.setMntfobtnd(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.montantFobDinars != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.montantFobDinars) : null);
                        titres.setCodregstat(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.regimeStatCode : null);
                        titres.setNumcnt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.contratNum : null);
                        titres.setDatcnt(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.contratDate : null);
                        titres.setDatfintit(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.validiteFin : null);
                        titres.setNumdep(sancrtBody.detailSancrt != null && sancrtBody.detailSancrt.numeroDepot != null ? new java.math.BigDecimal(sancrtBody.detailSancrt.numeroDepot) : null);
                        titres.setNumdecldou(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.numeroDeclarationImputation : null);
                        titres.setCodburdou(sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.codeBureauDouaneImputation : null);
                        if (sancrtBody.detailSancrt != null) {
                            titres.setNumMessage(sancrtBody.detailSancrt.refTtnNumMessage);
                            titres.setEtat(sancrtBody.detailSancrt.etat);
                        } else {
                            titres.setNumMessage(null);
                            titres.setEtat(null);
                        }
                        titres.setEmetteur(sancrtBody.enteteFlux.emetteur);
                        titres.setDestinataire(sancrtBody.enteteFlux.destinataire);
                        titres.setDatdecldou(sancrtBody.enteteFlux.dateDeclaration);
                        titres.setNumdecldou(
                            sancrtBody.enteteFlux.numDeclaration != null
                                ? sancrtBody.enteteFlux.numDeclaration.toString()
                                : null
                        );
                        // FIX: Assign numCpt as String, not BigDecimal
                        titres.setNumcpt(
                            sancrtBody.detailSancrt != null ? sancrtBody.detailSancrt.domiciliationNumCompte : null
                        );
                        titresRepository.save(titres);
                        logger.info("Saved TITRES entity to database for SANCRT.");
                        // Save PiecesJointes
                        if (sancrtBody.pieceJointeList != null) {
                            for (SancrtDocumentDTO.PieceJointe pj : sancrtBody.pieceJointeList) {
                                com.example.stationdechange.entity.PiecesJointes entity = new com.example.stationdechange.entity.PiecesJointes();
                                entity.setTypeDocPj(pj.typeDocument);
                                entity.setNumDoc(pj.numDocument);
                                if (pj.dateDocument != null) entity.setDateDoc(pj.dateDocument.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                                entity.setRefFichierJoint(pj.refFichierJoint);
                                entity.setRefBaseImage(pj.refBaseImage);
                                entity.setIdSeq(pj.id != null ? pj.id.idFlux : null);
                                entity.setCodePiecesJointes(pj.id != null && pj.id.numeroPieceJointe != null ? pj.id.numeroPieceJointe.toString() : null);
                                piecesJointesRepository.save(entity);
                            }
                            logger.info("Saved {} PiecesJointes entities to database for SANCRT.", sancrtBody.pieceJointeList.size());
                        }
                        // Save Nsh (articles)
                        if (sancrtBody.articleList != null) {
                            for (SancrtDocumentDTO.Article art : sancrtBody.articleList) {
                                Nsh nsh = new Nsh();
                                nsh.setIdSeq(art.id != null ? art.id.idFlux : null);
                                nsh.setCodnsh(art.numeroNomenclature);
                                nsh.setMntpft(art.prixFactureNet != null ? new java.math.BigDecimal(art.prixFactureNet) : null);
                                nsh.setNbrunt(art.quantite != null ? new java.math.BigDecimal(art.quantite) : null);
                                nsh.setLibpdt(art.designation);
                                nsh.setCoduntmes(art.uniteMesure);
                                nsh.setCodpayorg(art.paysOrigineCodePays);
                                nsh.setStatusMc(art.statutMinistereCommerceCodeStatut);
                                nsh.setStatusOt(art.statutOrganismeTechniqueCode);
                                nsh.setStatusBqe(art.codeStatutBanque);
                                nshRepository.save(nsh);
                            }
                            logger.info("Saved {} Nsh (article) entities to database for SANCRT.", sancrtBody.articleList.size());
                        }
                        break;
                    }
                    case "CUSRES": {
                        CusresDocumentDTO.Body cusresBody = objectMapper.treeToValue(body, CusresDocumentDTO.Body.class);
                        // Save Imputations
                        Imputations imputations = new Imputations();
                        imputations.setIdImp(cusresBody.getEnteteFlux().id);
                        imputations.setNumDossTtn(cusresBody.getEnteteFlux().numeroTtn);
                        imputations.setNumdom(cusresBody.getEnteteFlux().numDom != null ? new java.math.BigDecimal(cusresBody.getEnteteFlux().numDom) : null);
                        imputations.setDatimp(cusresBody.getEnteteFlux().dateInsertion);
                        CusresDocumentDTO.DetailCusres detail = cusresBody.getDetailCusres();
                        if (detail != null) {
                            imputations.setAnneedom(detail.enregistrementNumero);
                            imputations.setCodtit(detail.cleAuth);
                            imputations.setCodugdom(detail.statutDocumentEtatLaDeclaration3);
                            imputations.setCoursDevImp(detail.coursConversionDeviseFacturation != null ? new java.math.BigDecimal(detail.coursConversionDeviseFacturation) : null);
                            imputations.setMntimp(detail.valeurDouaneTotaleDinards != null ? new java.math.BigDecimal(detail.valeurDouaneTotaleDinards) : null);
                            imputations.setMntimpDev(detail.montantDevisePtfn != null ? new java.math.BigDecimal(detail.montantDevisePtfn) : null);
                            imputations.setModliv(null);
                            imputations.setModreg(null);
                            imputations.setQtecompl(null);
                            if (cusresBody.getArticleCusresList() != null && !cusresBody.getArticleCusresList().isEmpty()) {
                                CusresDocumentDTO.ArticleCusres article = cusresBody.getArticleCusresList().get(0);
                                imputations.setModreg(article.reglementFinancierModeReglement);
                                imputations.setQtecompl(article.reglementFinancierDelai != null ? new java.math.BigDecimal(article.reglementFinancierDelai) : null);
                            }
                            imputations.setRegstat(detail.statutDocumentCodeStatut);
                            imputations.setBurimp(detail.statutDocumentEtatLaDeclaration1);
                            imputations.setCoddev(detail.montantDeviseCodeDevise);
                            imputations.setTypedecl(detail.typeDocument);
                            imputations.setNgp(null);
                            imputations.setInspimp(detail.statutDocumentEtatLaDeclaration2);
                        }
                        imputationsRepository.save(imputations);
                        logger.info("Saved Imputations entity to database for CUSRES.");
                        // Save Titres
                       
                        logger.info("Saved TITRES entity to database for CUSRES.");
                        break;
                    }
                    case "APERAK":
                        AperakDocumentDTO.Body aperakBody = objectMapper.treeToValue(body, AperakDocumentDTO.Body.class);
                        // Save Aperak0
                        Aperak0 aperak0 = new Aperak0();
                        AperakDocumentDTO.EnteteFlux entete = aperakBody.getEnteteFlux();
                        AperakDocumentDTO.DetailAperak detail = aperakBody.getDetailAperak();
                        aperak0.setNumeroDossierTtn(entete != null ? entete.getNumeroTtn() : null);
                        aperak0.setNumeroMessage(detail != null ? detail.getRefTtnNumMessage() : null);
                        aperak0.setCodeTypeDoc(entete != null ? entete.getTypeDocument() : null);
                        aperak0.setEmetteur(entete != null ? entete.getEmetteur() : null);
                        aperak0.setCodeFlux(entete != null ? entete.getTypeMessage() : null);
                        aperak0.setDateTime(entete != null ? entete.getDateInsertion() : null);
                        aperak0.setIndTransact(entete != null ? entete.getCodeCloture() : null);
                        aperak0.setDestinataire(entete != null ? entete.getDestinataire() : null);
                        aperak0.setNumeroDemande(entete != null ? entete.getNumeroDemande() : null);
                        aperak0.setMessageId(null);
                        aperak0Repository.save(aperak0);
                        logger.info("Saved APERAK0 entity to database.");
                        // Save Aperak1 for each error
                        if (aperakBody.getAperakErreurs() != null) {
                            for (AperakDocumentDTO.AperakErreur err : aperakBody.getAperakErreurs()) {
                                Aperak1 aperak1 = new Aperak1();
                                Aperak1Id aperak1Id = new Aperak1Id();
                                aperak1Id.setNumeroDossierTtn(entete != null ? entete.getNumeroTtn() : null);
                                aperak1Id.setNumeroMessage(detail != null ? detail.getRefTtnNumMessage() : null);
                                aperak1.setId(aperak1Id);
                                aperak1.setCodeErreur(err.getCodeErreur());
                                aperak1.setLibelleErreur(err.getLibelleErreur());
                                aperak1.setRefErreur(err.getReferenceDonnee());
                                aperak1.setIndTransact(entete != null ? entete.getCodeCloture() : null);
                                aperak1.setNcol(err.getNumeroOccurrenceDonnee() != null ? err.getNumeroOccurrenceDonnee().longValue() : null);
                                aperak1Repository.save(aperak1);
                            }
                            logger.info("Saved {} APERAK1 error entities to database.", aperakBody.getAperakErreurs().size());
                        }
                        // Do NOT save Titres for APERAK
                        break;
                    default:
                        logger.warn("Unknown typeDocument: {}. Message not saved.", type);
                }
            } else {
                logger.error("\u274C No typeDocument found in body. Message not saved.");
            }
        } catch (Exception e) {
            logger.error("Error processing message from topic 'flux-outbound' with messageId: {}", messageId, e);
        }
    }
}



