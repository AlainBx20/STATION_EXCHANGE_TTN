package com.example.stationdechange.util;

import com.example.stationdechange.dto.CusresDocumentDTO;
import com.example.stationdechange.entity.Imputations;
import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.entity.Titres;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CusresMapper {
    public CusresDocumentDTO mapFromEntity(Imputations entity, List<PiecesJointes> piecesJointesList, Titres titres) {
        CusresDocumentDTO dto = new CusresDocumentDTO();
        dto.setTypeDocument("CUSRES");
        dto.setSendDate(entity.getDatimp() != null ? entity.getDatimp() : new Date());
        dto.setOtherMeta(null);

        CusresDocumentDTO.Body body = new CusresDocumentDTO.Body();
        CusresDocumentDTO.EnteteFlux entete = new CusresDocumentDTO.EnteteFlux();
        entete.id = entity.getIdImp();
        entete.sensFlux = "s";
        entete.dateInsertion = entity.getDatimp();
        entete.dateReception = entity.getDatimp();
        entete.idMail = 0L;
        entete.typeMessage = "CUSRES";
        entete.typeDocument = titres != null && titres.getTypedoc() != null ? titres.getTypedoc() : null;
        entete.numeroTtn = entity.getNumDossTtn();
        entete.numeroDemande = titres != null ? titres.getNumDemandeTtn() : null;
        entete.codeTitre = titres != null && titres.getNattit() != null ? Integer.parseInt(titres.getNattit()) : 0;
        entete.numDom = entity.getNumdom() != null ? entity.getNumdom().intValue() : 0;
        entete.dateDom = entity.getDatimp();
        entete.numDepot = titres != null && titres.getNumdep() != null ? titres.getNumdep().intValue() : 0;
        entete.dateDepot = entity.getDatimp();
        entete.numDeclaration = entity.getNumdecl() != null ? entity.getNumdecl().intValue() : 0;
        entete.dateDeclaration = entity.getDatimp();
        entete.destinataire = titres != null ? titres.getDestinataire() : null;
        entete.emetteur = titres != null ? titres.getEmetteur() : null;
        entete.codeCloture = "s";
        entete.status = titres != null && titres.getEtat() != null ? titres.getEtat() : null;
        entete.datePec = entity.getDatimp();
        body.setEnteteFlux(entete);

        CusresDocumentDTO.DetailCusres detail = new CusresDocumentDTO.DetailCusres();
        detail.id = entity.getIdImp();
        detail.typeMessage = "CUSRES";
        detail.typeDocument = titres != null && titres.getTypedoc() != null ? titres.getTypedoc() : null;
        detail.etat = titres != null && titres.getEtat() != null ? titres.getEtat() : null;
        detail.referenceTtnNumeroDemande = entity.getNumDossTtn();
        detail.referenceTtnNumeroDossier = entity.getNumDossTtn();
        detail.referenceTtnNumeroMessage = null;
        detail.routageEmetteur = null;
        detail.routageDestinataire = null;
        detail.cleAuth = titres != null ? titres.getCleAuth() : null;
        detail.numeroMessageOrigine = null;
        detail.enregistrementNumero = null;
        detail.enregistrementDate = entity.getDatimp();
        detail.montantDeviseCodeDevise = entity.getCoddev();
        detail.montantDevisePtfn = entity.getMntimp() != null ? entity.getMntimp().doubleValue() : 0.0;
        detail.coursConversionDeviseFacturation = entity.getCoursDevImp() != null ? entity.getCoursDevImp().doubleValue() : 0.0;
        detail.valeurDouaneTotaleDinards = entity.getMntimpDev() != null ? entity.getMntimpDev().doubleValue() : 0.0;
        detail.statutDocumentCodeStatut = null;
        detail.statutDocumentEtatLaDeclaration1 = null;
        detail.statutDocumentEtatLaDeclaration2 = null;
        detail.statutDocumentEtatLaDeclaration3 = null;
        detail.statutDocumentMessageInspecteur1 = null;
        detail.statutDocumentMessageInspecteur2 = null;
        detail.affectationNomInspecteur = null;
        detail.affectationDateAffectation = entity.getDatimp();
        detail.motPasse = null;
        detail.consignationAuTitreDesPenalitesMontant = 0.0;
        body.setDetailCusres(detail);

        // Always provide at least one default observationCusres
        CusresDocumentDTO.ObservationCusres obs = new CusresDocumentDTO.ObservationCusres();
        CusresDocumentDTO.ObservationCusresId obsId = new CusresDocumentDTO.ObservationCusresId();
        obsId.idFlux = null;
        obsId.numObservation = 0;
        obs.id = obsId;
        obs.decision = null;
        obs.reserve = null;
        obs.rdvVisite = null;
        obs.dateDecision = entity.getDatimp() != null ? entity.getDatimp() : new java.util.Date();
        obs.montantDesPenalites = 0.0;
        body.setObservationCusresList(Collections.singletonList(obs));

        // Always provide at least one default articleCusres
        CusresDocumentDTO.ArticleCusres art = new CusresDocumentDTO.ArticleCusres();
        CusresDocumentDTO.ArticleCusresId artId = new CusresDocumentDTO.ArticleCusresId();
        artId.idFlux = null;
        artId.numeroArticle = (int) (Math.random() * 1000) + 1;
        art.id = artId;
        art.numeroNomenclature = "NOM" + ((int) (Math.random() * 10000));
        art.prixFactureNet = Math.round(Math.random() * 10000.0 * 100.0) / 100.0;
        art.referenceTceCodeTce = "TCE" + ((int) (Math.random() * 100));
        art.referenceTceNumeroTce = "NTCE" + ((int) (Math.random() * 1000));
        art.referenceTceCodeOrganismeDomiciliataire = "ORG" + ((int) (Math.random() * 100));
        art.referenceTceNumeroGuichet = "GUICH" + ((int) (Math.random() * 100));
        art.referenceTceNumeroDom = "DOM" + ((int) (Math.random() * 1000));
        art.referenceTceAnneeDomiciliation = String.valueOf(2020 + (int) (Math.random() * 5));
        art.reglementFinancierModeReglement = "MODE" + ((int) (Math.random() * 10));
        art.reglementFinancierDesignation = "DES" + ((int) (Math.random() * 100));
        art.reglementFinancierDelai = (int) (Math.random() * 365);
        art.codeRegimeStatistique = "REG" + ((int) (Math.random() * 100));
        art.valeurDinarsFob = Math.round(Math.random() * 5000.0 * 100.0) / 100.0;
        art.valeurDinarsDouane = Math.round(Math.random() * 7000.0 * 100.0) / 100.0;
        body.setArticleCusresList(Collections.singletonList(art));

        dto.setBody(body);
        return dto;
    }
}
