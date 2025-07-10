package com.example.stationdechange.util;

import com.example.stationdechange.dto.AperakDocumentDTO;
import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.entity.PiecesJointes;
import org.springframework.stereotype.Component;
import com.example.stationdechange.entity.Titres;
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

    public AperakDocumentDTO mapFromEntity(Aperak0 entity, List<Aperak1> aperak1List, List<PiecesJointes> piecesJointesList, Titres titres) {
        AperakDocumentDTO dto = new AperakDocumentDTO();
        Date mainDate = toUtilDate(entity.getDateTime());
        dto.setTypeDocument("APERAK");
        dto.setSendDate(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        dto.setOtherMeta(null);

        AperakDocumentDTO.Body body = new AperakDocumentDTO.Body();
        AperakDocumentDTO.EnteteFlux entete = new AperakDocumentDTO.EnteteFlux();
        entete.setId(0L);
        entete.setSensFlux("s");
        entete.setDateInsertion(
            titres != null && titres.getDatdom() != null
                ? java.sql.Date.valueOf(DATE_FORMAT.format(titres.getDatdom()))
                : java.sql.Date.valueOf(DATE_FORMAT.format(mainDate))
        );
        entete.setDateReception(
            titres != null && titres.getDateReception() != null
                ? java.sql.Date.valueOf(DATE_FORMAT.format(titres.getDateReception()))
                : java.sql.Date.valueOf(DATE_FORMAT.format(mainDate))
        );
        entete.setIdMail(0L);
        entete.setTypeMessage("APERAK");
        entete.setTypeDocument(entity.getCodeTypeDoc() != null ? entity.getCodeTypeDoc() : "Z19");
        entete.setNumeroTtn(entity.getNumeroDossierTtn());
        entete.setNumeroDemande(entity.getNumeroDemande());
        entete.setCodeTitre(titres.getNattit() != null ? Integer.parseInt(titres.getNattit()) : 0);
        entete.setNumDom(titres.getNumdom() != null ? titres.getNumdom().intValue() : 0);
        entete.setDateDom(titres.getDatdom() != null ? java.sql.Date.valueOf(DATE_FORMAT.format(titres.getDatdom())) : java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        entete.setNumDepot(titres.getNumdep() != null ? titres.getNumdep().intValue() : 0);
        entete.setDateDepot(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        entete.setNumDeclaration(0);
        entete.setDateDeclaration(
            titres != null && titres.getDatdecldou() != null
                ? java.sql.Date.valueOf(DATE_FORMAT.format(titres.getDatdecldou()))
                : java.sql.Date.valueOf(DATE_FORMAT.format(mainDate))
        );
        entete.setDestinataire(entity.getDestinataire());
        entete.setEmetteur(entity.getEmetteur());
        entete.setCodeCloture("s");
        entete.setStatus(titres.getEtat() != null ? titres.getEtat() : "0");
        entete.setDatePec(
            titres != null && titres.getDatdom() != null
                ? java.sql.Date.valueOf(DATE_FORMAT.format(titres.getDatdom()))
                : java.sql.Date.valueOf(DATE_FORMAT.format(mainDate))
        );
        body.setEnteteFlux(entete);

        AperakDocumentDTO.DetailAperak detailAperak = new AperakDocumentDTO.DetailAperak();
        AperakDocumentDTO.DetailAperakId detailAperakId = new AperakDocumentDTO.DetailAperakId();
        detailAperakId.setIdFlux(null);
        detailAperakId.setIdLigne(0L);
        detailAperak.setId(detailAperakId);
        detailAperak.setTypeMessage("APERAK");
        detailAperak.setTypeDocument(entity.getCodeTypeDoc() != null ? entity.getCodeTypeDoc() : "AAA");
        detailAperak.setEtat(titres.getEtat() != null ? titres.getEtat() : "0");
        detailAperak.setRefTtnNumMessage(entity.getNumeroMessage());
        detailAperak.setRefTtnNumDossier(entity.getNumeroDossierTtn());
        detailAperak.setRefTtnNumDemande(entity.getNumeroDemande());
        detailAperak.setRoutageEmetteur(entity.getEmetteur());
        detailAperak.setRoutageDestinataire(entity.getDestinataire());
        detailAperak.setDateEmission(java.sql.Date.valueOf(DATE_FORMAT.format(mainDate)));
        detailAperak.setNumeroMessageOrigine(entity.getMessageId());
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
                err.setCodeErreur(a1.getCodeErreur());
                err.setLibelleErreur(a1.getLibelleErreur());
                err.setReferenceDonnee(a1.getRefErreur());
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
                dtoPj.setTypeDocument(pj.getTypeDocPj());
                dtoPj.setNumDocument(pj.getNumDoc());
                Date docDate = toUtilDate(pj.getDateDoc());
                dtoPj.setDateDocument(java.sql.Date.valueOf(DATE_FORMAT.format(docDate)));
                dtoPj.setRefFichierJoint(pj.getRefFichierJoint());
                dtoPj.setRefBaseImage(pj.getRefBaseImage());
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
