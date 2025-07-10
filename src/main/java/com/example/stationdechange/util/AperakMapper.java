package com.example.stationdechange.util;

import com.example.stationdechange.dto.AperakDocumentDTO;
import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.entity.PiecesJointes;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Component
public class AperakMapper {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private Date toUtilDate(Object dateObj) {
        if (dateObj == null) return new Date();
        if (dateObj instanceof Date) return (Date) dateObj;
        if (dateObj instanceof java.sql.Timestamp) return new Date(((java.sql.Timestamp) dateObj).getTime());
        if (dateObj instanceof java.sql.Date) return new Date(((java.sql.Date) dateObj).getTime());
        return new Date();
    }

    public AperakDocumentDTO mapFromEntity(Aperak0 entity, List<Aperak1> aperak1List, List<PiecesJointes> piecesJointesList, com.example.stationdechange.entity.Titres titres) {
        AperakDocumentDTO dto = new AperakDocumentDTO();
        Date mainDate = toUtilDate(entity.getDateTime());
        dto.setTypeDocument("APERAK");
        dto.setSendDate(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        dto.setOtherMeta(null);

        AperakDocumentDTO.Body body = new AperakDocumentDTO.Body();
        AperakDocumentDTO.EnteteFlux entete = new AperakDocumentDTO.EnteteFlux();
        entete.setId(0L);
        entete.setSensFlux("O");
        entete.setDateInsertion(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        entete.setDateReception(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        entete.setIdMail(0L);
        entete.setTypeMessage(titres != null && titres.getTypemsg() != null ? titres.getTypemsg() : "APERAK");
        entete.setTypeDocument(titres != null && titres.getTypedoc() != null ? titres.getTypedoc() : (entity.getCodeTypeDoc() != null ? entity.getCodeTypeDoc() : "AAA"));
        entete.setNumeroTtn(entity.getNumeroDossierTtn() != null ? entity.getNumeroDossierTtn() : null);
        entete.setNumeroDemande(entity.getNumeroDemande() != null ? entity.getNumeroDemande() : null);
        entete.setCodeTitre(0);
        entete.setNumDom(titres != null && titres.getNumdom() != null ? titres.getNumdom().intValue() : 0);
        entete.setDateDom(titres != null && titres.getDatdom() != null ? titres.getDatdom() : java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        entete.setNumDepot(0);
        entete.setDateDepot(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        entete.setNumDeclaration(0);
        entete.setDateDeclaration(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        entete.setDestinataire(entity.getDestinataire() != null ? entity.getDestinataire() : null);
        entete.setEmetteur(entity.getEmetteur() != null ? entity.getEmetteur() : null);
        entete.setCodeCloture("s");
        entete.setStatus("s");
        entete.setDatePec(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        body.setEnteteFlux(entete);

        AperakDocumentDTO.DetailAperak detailAperak = new AperakDocumentDTO.DetailAperak();
        AperakDocumentDTO.DetailAperakId detailAperakId = new AperakDocumentDTO.DetailAperakId();
        detailAperakId.setIdFlux(null);
        detailAperakId.setIdLigne(0L);
        detailAperak.setId(detailAperakId);
        detailAperak.setTypeMessage("APERAK");
        detailAperak.setTypeDocument(titres != null && titres.getTypedoc() != null ? titres.getTypedoc() : (entity.getCodeTypeDoc() != null ? entity.getCodeTypeDoc() : "AAA"));
        detailAperak.setEtat("123");
        detailAperak.setRefTtnNumMessage(entity.getNumeroMessage() != null ? entity.getNumeroMessage() : null);
        detailAperak.setRefTtnNumDossier(entity.getNumeroDossierTtn() != null ? entity.getNumeroDossierTtn() : null);
        detailAperak.setRefTtnNumDemande(entity.getNumeroDemande() != null ? entity.getNumeroDemande() : null);
        detailAperak.setRoutageEmetteur(entity.getEmetteur() != null ? entity.getEmetteur() : null);
        detailAperak.setRoutageDestinataire(entity.getDestinataire() != null ? entity.getDestinataire() : null);
        detailAperak.setDateEmission(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        detailAperak.setNumeroMessageOrigine(titres != null && titres.getNumMessageOrig() != null ? titres.getNumMessageOrig() : null);
        detailAperak.setNomCodeMessageOrigine(null);
        detailAperak.setDateEmissionMessageOrigine(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        body.setDetailAperak(detailAperak);

        // Map Aperak1 errors if available
        if (aperak1List != null && !aperak1List.isEmpty()) {
            List<AperakDocumentDTO.AperakErreur> errors = new java.util.ArrayList<>();
            long idLigne = 1;
            for (Aperak1 a1 : aperak1List) {
                AperakDocumentDTO.AperakErreur err = new AperakDocumentDTO.AperakErreur();
                AperakDocumentDTO.AperakErreurId errId = new AperakDocumentDTO.AperakErreurId();
                errId.setIdFlux(null);
                errId.setIdLigne(idLigne++);
                err.setId(errId);
                err.setCodeErreur(a1.getCodeErreur() != null ? a1.getCodeErreur() : null);
                err.setLibelleErreur(a1.getLibelleErreur() != null ? a1.getLibelleErreur() : null);
                err.setReferenceDonnee(a1.getRefErreur() != null ? a1.getRefErreur() : null);
                err.setIdErreur(a1.getNcol() != null ? a1.getNcol().intValue() : 0);
                err.setNumeroOccurrenceDonnee(a1.getNcol() != null ? a1.getNcol().intValue() : 0);
                errors.add(err);
            }
            body.setAperakErreurs(errors);
        } else {
            // Provide at least one error if required
            AperakDocumentDTO.AperakErreur err = new AperakDocumentDTO.AperakErreur();
            AperakDocumentDTO.AperakErreurId errId = new AperakDocumentDTO.AperakErreurId();
            errId.setIdFlux(null);
            errId.setIdLigne(0L);
            err.setId(errId);
            err.setCodeErreur(null);
            err.setLibelleErreur(null);
            err.setReferenceDonnee(null);
            err.setIdErreur(0);
            err.setNumeroOccurrenceDonnee(0);
            body.setAperakErreurs(Collections.singletonList(err));
        }

        // Map PiecesJointes if available
        if (piecesJointesList != null && !piecesJointesList.isEmpty()) {
            List<AperakDocumentDTO.PieceJointe> pjs = piecesJointesList.stream().map(pj -> {
                AperakDocumentDTO.PieceJointe dtoPj = new AperakDocumentDTO.PieceJointe();
                AperakDocumentDTO.PieceJointeId pjId = new AperakDocumentDTO.PieceJointeId();
                pjId.setIdFlux(null);
                pjId.setNumeroPieceJointe(0L);
                dtoPj.setId(pjId);
                dtoPj.setTypeDocument(pj.getTypeDocPj() != null ? pj.getTypeDocPj() : null);
                dtoPj.setNumDocument(pj.getNumDoc() != null ? pj.getNumDoc() : null);
                Date docDate = toUtilDate(pj.getDateDoc());
                dtoPj.setDateDocument(java.sql.Date.valueOf(DATE_FORMAT.format(docDate)));
                dtoPj.setRefFichierJoint(pj.getRefFichierJoint() != null ? pj.getRefFichierJoint() : null);
                dtoPj.setRefBaseImage(pj.getRefBaseImage() != null ? pj.getRefBaseImage() : null);
                dtoPj.setPath("aperak_path_" + UUID.randomUUID().toString().substring(0, 8));
                return dtoPj;
            }).collect(Collectors.toList());
            body.setPieceJointes(pjs);
        } else {
            // Provide at least one pieceJointe if required
            AperakDocumentDTO.PieceJointe dtoPj = new AperakDocumentDTO.PieceJointe();
            AperakDocumentDTO.PieceJointeId pjId = new AperakDocumentDTO.PieceJointeId();
            pjId.setIdFlux(null);
            pjId.setNumeroPieceJointe(0L);
            dtoPj.setId(pjId);
            dtoPj.setTypeDocument("APR");
            dtoPj.setNumDocument(null);
            dtoPj.setDateDocument(java.sql.Date.valueOf(DATE_FORMAT.format(new Date())));
            dtoPj.setRefFichierJoint(null);
            dtoPj.setRefBaseImage(null);
            dtoPj.setPath("apr_res_path_" + UUID.randomUUID().toString().substring(0, 8));
            body.setPieceJointes(Collections.singletonList(dtoPj));
        }

        dto.setBody(body);
        return dto;
    }
}
