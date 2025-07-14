package com.example.stationdechange.util;

import com.example.stationdechange.dto.SancrtDocumentDTO;
import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.entity.Nsh;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Collections;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class SancrtMapper {
    public SancrtDocumentDTO mapFromEntity(Titres titres, List<PiecesJointes> piecesJointesList, List<Nsh> nshList, List<SancrtDocumentDTO.Observation> observationList) {
        SancrtDocumentDTO dto = new SancrtDocumentDTO();
        dto.setTypeDocument("SANCRT");
        dto.setSendDate(titres.getDatdom() == null ? null : titres.getDatdom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        dto.setOtherMeta("");

        SancrtDocumentDTO.Body body = new SancrtDocumentDTO.Body();

        // Map enteteFlux
        SancrtDocumentDTO.Body.EnteteFlux entete = new SancrtDocumentDTO.Body.EnteteFlux();
        entete.id = titres.getIdSeq();
        entete.sensFlux = "s";
        entete.dateInsertion = titres.getDatdom();
        entete.dateReception = titres.getDateReception();
        entete.idMail = 0L;
        entete.typeMessage = "SANCRT";
        entete.typeDocument = titres.getTypedoc();
        entete.numeroTtn = titres.getNumDossTtn();
        entete.numeroDemande = titres.getNumDemandeTtn();
        entete.codeTitre = titres.getNattit() != null ? Integer.parseInt(titres.getNattit()) : 0;
        entete.numDom = titres.getNumdom() != null ? titres.getNumdom().intValue() : 0;
        entete.dateDom = titres.getDatdom();
        entete.numDepot = titres.getNumdep() != null ? titres.getNumdep().intValue() : 0;
        entete.dateDepot = titres.getDatdepaut();
        entete.numDeclaration = titres.getNumdecldou() != null ? 0 : 0; // Map as needed
        entete.dateDeclaration = titres.getDatdecldou();
        entete.destinataire = titres.getDestinataire();
        entete.emetteur = titres.getEmetteur();
        entete.codeCloture = "s";
        entete.status = titres.getEtat();
        entete.datePec = titres.getDatdom();
        body.enteteFlux = entete;

        // Map detailSancrt
        SancrtDocumentDTO.DetailSancrt detail = new SancrtDocumentDTO.DetailSancrt();
        detail.id = titres.getIdSeq();
        detail.typeMessage = titres.getTypemsg();
        detail.typeDocument = titres.getTypedoc();
        detail.etat = titres.getEtat();
        detail.refTtnNumMessage = titres.getNumMessage();
        detail.refTtnNumDossier = titres.getNumDossTtn();
        detail.refTtnNumDemande = titres.getNumDemandeTtn();
        detail.refTtnUtisateur = null;
        detail.routageEmetteur = titres.getEmetteur();
        detail.routageDestinataire = titres.getDestinataire();
        detail.cleAuth = titres.getCleAuth();
        detail.exportateurRaisonSociale = titres.getLibfrs();
        detail.exportateurCode = null;
        detail.exportateurNomPrenom = null;
        detail.exportateurAdresseLine1 = titres.getAdrfrs();
        detail.exportateurAdresseLine2 = null;
        detail.exportateurAdresseLine3 = null;
        detail.importateurRaisonSociale = titres.getRaisoc();
        detail.importateurCode = titres.getNumdepext();
        detail.importateurNomPrenom = null;
        detail.importateurAdresseLine1 = titres.getAdrclt();
        detail.importateurAdresseLine2 = null;
        detail.importateurAdresseLine3 = null;
        detail.codeNatureAutorisation = titres.getNattit();
        detail.natureAutorisationDesignation = null;
        detail.paysProvenanceCode = titres.getCodpayOrg();
        detail.paysProvenanceNom = null;
        detail.paysAchatCode = titres.getCodpayAchDestdeb();
        detail.paysAchatNom = null;
        detail.paysPremiereDestCode = titres.getCodpayAchDestdeb();
        detail.paysPremiereDestNom = null;
        detail.paysDestDefCode = titres.getCodpayProvDestfin();
        detail.paysDestDefNom = null;
        detail.modeLivraisonCode = titres.getCodliv();
        detail.modeLivraisonDesignation = null;
        detail.domiciliationCodeOrganisme = titres.getCodugdom();
        detail.domiciliationNumero = titres.getNumcpt();
        detail.domiciliationDesignation = null;
        detail.domiciliationNumCompte = titres.getNumcpt();
        detail.domiciliationDate = titres.getDatdom();
        detail.reglementMode = titres.getCodreg();
        detail.reglementDesignation = null;
        detail.reglementDelai = titres.getCoddel();
        detail.reglementDevise = titres.getCoddevRegl();
        detail.coursConversionDevise = titres.getCours() != null ? titres.getCours().doubleValue() : null;
        detail.montantDeviseCode = titres.getCoddevCnt();
        detail.montantPtfnDevise = titres.getMntptfndev() != null ? titres.getMntptfndev().doubleValue() : null;
        detail.montantFobDevise = titres.getMntfobdev() != null ? titres.getMntfobdev().doubleValue() : null;
        detail.montantPtfnDinars = titres.getMntptfntnd() != null ? titres.getMntptfntnd().doubleValue() : null;
        detail.montantFobDinars = titres.getMntfobtnd() != null ? titres.getMntfobtnd().doubleValue() : null;
        detail.regimeStatCode = titres.getCodregstat();
        detail.contratNum = titres.getNumcnt();
        detail.contratDate = titres.getDatcnt();
        detail.enregistrementNum = titres.getNumenrgmin();
        detail.enregistrementDate = titres.getDatenrgmin();
        detail.validiteDebut = titres.getDatdom();
        detail.validiteFin = titres.getDatfintit();
        detail.minCommerceOrganisme = null;
        detail.minCommerceSignataire = null;
        detail.minCommerceDate = null;
        detail.organismeDomiciliataireDate = null;
        detail.organismeDomiciliataireNom = null;
        detail.organismeDomiciliataireNomSignataire = null;
        detail.declarantSignataire = null;
        detail.declarantOrganisme = null;
        detail.declarantDate = null;
        detail.codeOrganiseDeposit = null;
        detail.numeroDepot = titres.getNumdep() != null ? titres.getNumdep().toString() : null;
        detail.designationDepot = null;
        detail.dateDepot = titres.getDatdepaut();
        detail.numeroDeclarationImputation = titres.getNumdecldou();
        detail.codeBureauDouaneImputation = titres.getCodburdou();
        detail.dateImputation = titres.getDatdecldou();
        detail.nomOrganismeBanqueCentrale = null;
        detail.nomSignataireBanqueCentrale = null;
        detail.dateBanqueCentrale = null;
        detail.nomOrganismeOrganismeTechnique = null;
        detail.nomSignataireOrganismeTechnique = null;
        detail.dateOrganismeTechnique = null;
        detail.codeStatutOrganismeTechnique = null;
        detail.libelleStatutOrganismeTechnique = null;
        detail.codeStatutBanque = null;
        detail.libelleStatutBanque = null;
        detail.codeStatutMinistereCommerce = null;
        detail.libelleStatutMinistereCommerce = null;
        body.detailSancrt = detail;

        // Map pieceJointeList
        if (piecesJointesList != null && !piecesJointesList.isEmpty()) {
            body.pieceJointeList = piecesJointesList.stream().map(pj -> {
                SancrtDocumentDTO.PieceJointe dtoPj = new SancrtDocumentDTO.PieceJointe();
                SancrtDocumentDTO.PieceJointe.PieceJointeId id = new SancrtDocumentDTO.PieceJointe.PieceJointeId();
                id.idFlux = pj.getIdSeq();
                id.numeroPieceJointe = 0L;
                dtoPj.id = id;
                dtoPj.typeDocument = pj.getTypeDocPj() != null ? pj.getTypeDocPj() : "SAN";
                dtoPj.numDocument = pj.getNumDoc() != null ? pj.getNumDoc() : null;
                if (pj.getDateDoc() != null) {
                    dtoPj.dateDocument = java.sql.Date.valueOf(pj.getDateDoc());
                } else {
                    dtoPj.dateDocument = java.sql.Date.valueOf(java.time.LocalDate.now());
                }
                dtoPj.refFichierJoint = pj.getRefFichierJoint() != null ? pj.getRefFichierJoint() : null;
                dtoPj.refBaseImage = pj.getRefBaseImage() != null ? pj.getRefBaseImage() : null;
                dtoPj.path = "sancrt_path_" + UUID.randomUUID().toString().substring(0, 8);
                return dtoPj;
            }).toList();
        } else {
            SancrtDocumentDTO.PieceJointe dtoPj = new SancrtDocumentDTO.PieceJointe();
            SancrtDocumentDTO.PieceJointe.PieceJointeId id = new SancrtDocumentDTO.PieceJointe.PieceJointeId();
            id.idFlux = 0L;
            id.numeroPieceJointe = 0L;
            dtoPj.id = id;
            dtoPj.typeDocument = "SAN";
            dtoPj.numDocument = null;
            dtoPj.dateDocument = java.sql.Date.valueOf(java.time.LocalDate.now());
            dtoPj.refFichierJoint = null;
            dtoPj.refBaseImage = null;
            dtoPj.path = "sancrt_path_";
            body.pieceJointeList = Collections.singletonList(dtoPj);
        }

        // Map articleList
        if (nshList != null && !nshList.isEmpty()) {
            body.articleList = nshList.stream().map(nsh -> {
                SancrtDocumentDTO.Article dtoArt = new SancrtDocumentDTO.Article();
                SancrtDocumentDTO.Article.ArticleId id = new SancrtDocumentDTO.Article.ArticleId();
                id.numeroArticle = String.valueOf((int) (Math.random() * 1000));
                id.idFlux = nsh.getIdSeq();
                dtoArt.id = id;
                dtoArt.numeroNomenclature = nsh.getCodnsh();
                dtoArt.paysOrigineCodePays = nsh.getCodpayorg();
                dtoArt.paysOrigineNomPays = null;
                dtoArt.prixFactureNet = nsh.getMntpft() != null ? nsh.getMntpft().doubleValue() : 0.0;
                dtoArt.quantite = nsh.getNbrunt() != null ? nsh.getNbrunt().doubleValue() : 0.0;
                dtoArt.statutMinistereCommerceCodeStatut = nsh.getStatusMc();
                dtoArt.statutMinistereCommerceLibelleStatut = null;
                dtoArt.statutOrganismeTechniqueCode = nsh.getStatusOt();
                dtoArt.statutOrganismeTechniqueLibelle = null;
                dtoArt.uniteMesure = nsh.getCoduntmes();
                dtoArt.codePaysExportation = null;
                dtoArt.nomPaysExportation = null;
                dtoArt.codeStatutBanque = nsh.getStatusBqe();
                dtoArt.libelleStatutBanque = null;
                dtoArt.designation = nsh.getLibpdt();
                return dtoArt;
            }).toList();
        } else {
            SancrtDocumentDTO.Article dtoArt = new SancrtDocumentDTO.Article();
            SancrtDocumentDTO.Article.ArticleId id = new SancrtDocumentDTO.Article.ArticleId();
            id.numeroArticle = String.valueOf((int) (Math.random() * 1000));
            id.idFlux = (long) (Math.random() * 10000);
            dtoArt.id = id;
            dtoArt.numeroNomenclature = "NOM" + ((int) (Math.random() * 10000));
            dtoArt.paysOrigineCodePays = "PAY" + ((int) (Math.random() * 100));
            dtoArt.paysOrigineNomPays = "Country" + ((int) (Math.random() * 100));
            dtoArt.prixFactureNet = Math.round(Math.random() * 10000.0 * 100.0) / 100.0;
            dtoArt.quantite = Math.round(Math.random() * 100.0 * 100.0) / 100.0;
            dtoArt.statutMinistereCommerceCodeStatut = "STAT" + ((int) (Math.random() * 100));
            dtoArt.statutMinistereCommerceLibelleStatut = "Libelle" + ((int) (Math.random() * 100));
            dtoArt.statutOrganismeTechniqueCode = "ORGTECH" + ((int) (Math.random() * 100));
            dtoArt.statutOrganismeTechniqueLibelle = "LibOrgTech" + ((int) (Math.random() * 100));
            dtoArt.uniteMesure = "UM" + ((int) (Math.random() * 10));
            dtoArt.codePaysExportation = "EXP" + ((int) (Math.random() * 100));
            dtoArt.nomPaysExportation = "ExpCountry" + ((int) (Math.random() * 100));
            dtoArt.codeStatutBanque = "BANK" + ((int) (Math.random() * 100));
            dtoArt.libelleStatutBanque = "BankLib" + ((int) (Math.random() * 100));
            dtoArt.designation = "Designation" + ((int) (Math.random() * 1000));
            body.articleList = Collections.singletonList(dtoArt);
        }

        // Map observationList
if (observationList != null && !observationList.isEmpty()) {
    body.observationList = observationList;
} else {
    body.observationList = null;
}

        dto.setBody(body);
        return dto;
    }
} 