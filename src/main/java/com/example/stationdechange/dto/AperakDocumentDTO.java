package com.example.stationdechange.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AperakDocumentDTO {
    private String typeDocument;
    private Date sendDate;
    private String otherMeta;
    private Body body;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private EnteteFlux enteteFlux;
        private DetailAperak detailAperak;
        private java.util.List<PieceJointe> pieceJointes;
        private java.util.List<AperakErreur> aperakErreurs;



        @JsonIgnoreProperties(ignoreUnknown = true)
        public EnteteFlux getEnteteFlux() { return enteteFlux; }
        public void setEnteteFlux(EnteteFlux enteteFlux) { this.enteteFlux = enteteFlux; }
        public DetailAperak getDetailAperak() { return detailAperak; }
        public void setDetailAperak(DetailAperak detailAperak) { this.detailAperak = detailAperak; }
        public java.util.List<PieceJointe> getPieceJointes() { return pieceJointes; }
        public void setPieceJointes(java.util.List<PieceJointe> pieceJointes) { this.pieceJointes = pieceJointes; }
        public java.util.List<AperakErreur> getAperakErreurs() { return aperakErreurs; }
        public void setAperakErreurs(java.util.List<AperakErreur> aperakErreurs) { this.aperakErreurs = aperakErreurs; }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EnteteFlux {
        private Long id;
        private String sensFlux;
        private java.util.Date dateInsertion;
        private java.util.Date dateReception;
        private Long idMail;
        private String typeMessage;
        private String typeDocument;
        private String numeroTtn;
        private String numeroDemande;
        private Integer codeTitre;
        private Integer numDom;
        private java.util.Date dateDom;
        private Integer numDepot;
        private java.util.Date dateDepot;
        private Integer numDeclaration;
        private java.util.Date dateDeclaration;
        private String destinataire;
        private String emetteur;
        private String codeCloture;
        private String status;
        private java.util.Date datePec;
        public boolean fullyMapped;
        
        // getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getSensFlux() { return sensFlux; }
        public void setSensFlux(String sensFlux) { this.sensFlux = sensFlux; }
        public java.util.Date getDateInsertion() { return dateInsertion; }
        public void setDateInsertion(java.util.Date dateInsertion) { this.dateInsertion = dateInsertion; }
        public java.util.Date getDateReception() { return dateReception; }
        public void setDateReception(java.util.Date dateReception) { this.dateReception = dateReception; }
        public Long getIdMail() { return idMail; }
        public void setIdMail(Long idMail) { this.idMail = idMail; }
        public String getTypeMessage() { return typeMessage; }
        public void setTypeMessage(String typeMessage) { this.typeMessage = typeMessage; }
        public String getTypeDocument() { return typeDocument; }
        public void setTypeDocument(String typeDocument) { this.typeDocument = typeDocument; }
        public String getNumeroTtn() { return numeroTtn; }
        public void setNumeroTtn(String numeroTtn) { this.numeroTtn = numeroTtn; }
        public String getNumeroDemande() { return numeroDemande; }
        public void setNumeroDemande(String numeroDemande) { this.numeroDemande = numeroDemande; }
        public Integer getCodeTitre() { return codeTitre; }
        public void setCodeTitre(Integer codeTitre) { this.codeTitre = codeTitre; }
        public Integer getNumDom() { return numDom; }
        public void setNumDom(Integer numDom) { this.numDom = numDom; }
        public java.util.Date getDateDom() { return dateDom; }
        public void setDateDom(java.util.Date dateDom) { this.dateDom = dateDom; }
        public Integer getNumDepot() { return numDepot; }
        public void setNumDepot(Integer numDepot) { this.numDepot = numDepot; }
        public java.util.Date getDateDepot() { return dateDepot; }
        public void setDateDepot(java.util.Date dateDepot) { this.dateDepot = dateDepot; }
        public Integer getNumDeclaration() { return numDeclaration; }
        public void setNumDeclaration(Integer numDeclaration) { this.numDeclaration = numDeclaration; }
        public java.util.Date getDateDeclaration() { return dateDeclaration; }
        public void setDateDeclaration(java.util.Date dateDeclaration) { this.dateDeclaration = dateDeclaration; }
        public String getDestinataire() { return destinataire; }
        public void setDestinataire(String destinataire) { this.destinataire = destinataire; }
        public String getEmetteur() { return emetteur; }
        public void setEmetteur(String emetteur) { this.emetteur = emetteur; }
        public String getCodeCloture() { return codeCloture; }
        public void setCodeCloture(String codeCloture) { this.codeCloture = codeCloture; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public java.util.Date getDatePec() { return datePec; }
        public void setDatePec(java.util.Date datePec) { this.datePec = datePec; }
    }
    
    public static class DetailAperak {
        private DetailAperakId id;
        private String typeMessage;
        private String typeDocument;
        private String etat;
        private String refTtnNumMessage;
        private String refTtnNumDossier;
        private String refTtnNumDemande;
        private String routageEmetteur;
        private String routageDestinataire;
        private Date dateEmission;
        private String numeroMessageOrigine;
        private String nomCodeMessageOrigine;
        private Date dateEmissionMessageOrigine;
        // getters and setters
        public DetailAperakId getId() { return id; }
        public void setId(DetailAperakId id) { this.id = id; }
        public String getTypeMessage() { return typeMessage; }
        public void setTypeMessage(String typeMessage) { this.typeMessage = typeMessage; }
        public String getTypeDocument() { return typeDocument; }
        public void setTypeDocument(String typeDocument) { this.typeDocument = typeDocument; }
        public String getEtat() { return etat; }
        public void setEtat(String etat) { this.etat = etat; }
        public String getRefTtnNumMessage() { return refTtnNumMessage; }
        public void setRefTtnNumMessage(String refTtnNumMessage) { this.refTtnNumMessage = refTtnNumMessage; }
        public String getRefTtnNumDossier() { return refTtnNumDossier; }
        public void setRefTtnNumDossier(String refTtnNumDossier) { this.refTtnNumDossier = refTtnNumDossier; }
        public String getRefTtnNumDemande() { return refTtnNumDemande; }
        public void setRefTtnNumDemande(String refTtnNumDemande) { this.refTtnNumDemande = refTtnNumDemande; }
        public String getRoutageEmetteur() { return routageEmetteur; }
        public void setRoutageEmetteur(String routageEmetteur) { this.routageEmetteur = routageEmetteur; }
        public String getRoutageDestinataire() { return routageDestinataire; }
        public void setRoutageDestinataire(String routageDestinataire) { this.routageDestinataire = routageDestinataire; }
        public Date getDateEmission() { return dateEmission; }
        public void setDateEmission(Date dateEmission) { this.dateEmission = dateEmission; }
        public String getNumeroMessageOrigine() { return numeroMessageOrigine; }
        public void setNumeroMessageOrigine(String numeroMessageOrigine) { this.numeroMessageOrigine = numeroMessageOrigine; }
        public String getNomCodeMessageOrigine() { return nomCodeMessageOrigine; }
        public void setNomCodeMessageOrigine(String nomCodeMessageOrigine) { this.nomCodeMessageOrigine = nomCodeMessageOrigine; }
        public Date getDateEmissionMessageOrigine() { return dateEmissionMessageOrigine; }
        public void setDateEmissionMessageOrigine(Date dateEmissionMessageOrigine) { this.dateEmissionMessageOrigine = dateEmissionMessageOrigine; }
    }
    public static class DetailAperakId {
        private Long idFlux;
        private Long idLigne;
        public Long getIdFlux() { return idFlux; }
        public void setIdFlux(Long idFlux) { this.idFlux = idFlux; }
        public Long getIdLigne() { return idLigne; }
        public void setIdLigne(Long idLigne) { this.idLigne = idLigne; }
    }
    public static class PieceJointe {
        private PieceJointeId id;
        private String typeDocument;
        private String numDocument;
        private java.util.Date dateDocument;
        private String refFichierJoint;
        private String refBaseImage;
        private String path;
        public PieceJointeId getId() { return id; }
        public void setId(PieceJointeId id) { this.id = id; }
        public String getTypeDocument() { return typeDocument; }
        public void setTypeDocument(String typeDocument) { this.typeDocument = typeDocument; }
        public String getNumDocument() { return numDocument; }
        public void setNumDocument(String numDocument) { this.numDocument = numDocument; }
        public java.util.Date getDateDocument() { return dateDocument; }
        public void setDateDocument(java.util.Date dateDocument) { this.dateDocument = dateDocument; }
        public String getRefFichierJoint() { return refFichierJoint; }
        public void setRefFichierJoint(String refFichierJoint) { this.refFichierJoint = refFichierJoint; }
        public String getRefBaseImage() { return refBaseImage; }
        public void setRefBaseImage(String refBaseImage) { this.refBaseImage = refBaseImage; }
        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }
    }
    public static class PieceJointeId {
        private Long idFlux;
        private Long numeroPieceJointe;
        public Long getIdFlux() { return idFlux; }
        public void setIdFlux(Long idFlux) { this.idFlux = idFlux; }
        public Long getNumeroPieceJointe() { return numeroPieceJointe; }
        public void setNumeroPieceJointe(Long numeroPieceJointe) { this.numeroPieceJointe = numeroPieceJointe; }
    }
    public static class AperakErreur {
        private AperakErreurId id;
        private Integer idErreur;
        private String codeErreur;
        private String libelleErreur;
        private String referenceDonnee;
        private Integer numeroOccurrenceDonnee;
        public AperakErreurId getId() { return id; }
        public void setId(AperakErreurId id) { this.id = id; }
        public Integer getIdErreur() { return idErreur; }
        public void setIdErreur(Integer idErreur) { this.idErreur = idErreur; }
        public String getCodeErreur() { return codeErreur; }
        public void setCodeErreur(String codeErreur) { this.codeErreur = codeErreur; }
        public String getLibelleErreur() { return libelleErreur; }
        public void setLibelleErreur(String libelleErreur) { this.libelleErreur = libelleErreur; }
        public String getReferenceDonnee() { return referenceDonnee; }
        public void setReferenceDonnee(String referenceDonnee) { this.referenceDonnee = referenceDonnee; }
        public Integer getNumeroOccurrenceDonnee() { return numeroOccurrenceDonnee; }
        public void setNumeroOccurrenceDonnee(Integer numeroOccurrenceDonnee) { this.numeroOccurrenceDonnee = numeroOccurrenceDonnee; }
    }
    public static class AperakErreurId {
        private Long idFlux;
        private Long idLigne;
        public Long getIdFlux() { return idFlux; }
        public void setIdFlux(Long idFlux) { this.idFlux = idFlux; }
        public Long getIdLigne() { return idLigne; }
        public void setIdLigne(Long idLigne) { this.idLigne = idLigne; }
    }
    public String getTypeDocument() { return typeDocument; }
    public void setTypeDocument(String typeDocument) { this.typeDocument = typeDocument; }
    public java.util.Date getSendDate() { return sendDate; }
    public void setSendDate(java.util.Date sendDate) { this.sendDate = sendDate; }
    public String getOtherMeta() { return otherMeta; }
    public void setOtherMeta(String otherMeta) { this.otherMeta = otherMeta; }
    public Body getBody() { return body; }
    public void setBody(Body body) { this.body = body; }
}
