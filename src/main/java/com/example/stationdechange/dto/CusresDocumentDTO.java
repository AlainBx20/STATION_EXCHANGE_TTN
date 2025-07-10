package com.example.stationdechange.dto;

import java.util.Date;
import java.util.List;

public class CusresDocumentDTO {
    private String typeDocument;
    private Date sendDate;
    private String otherMeta;
    private Body body;

    public static class Body {
        private EnteteFlux enteteFlux;
        private DetailCusres detailCusres;
        private List<ObservationCusres> observationCusresList;
        private List<ArticleCusres> articleCusresList;
       
        // getters and setters
        public EnteteFlux getEnteteFlux() { return enteteFlux; }
        public void setEnteteFlux(EnteteFlux enteteFlux) { this.enteteFlux = enteteFlux; }
        public DetailCusres getDetailCusres() { return detailCusres; }
        public void setDetailCusres(DetailCusres detailCusres) { this.detailCusres = detailCusres; }
        public List<ObservationCusres> getObservationCusresList() { return observationCusresList; }
        public void setObservationCusresList(List<ObservationCusres> observationCusresList) { this.observationCusresList = observationCusresList; }
        public List<ArticleCusres> getArticleCusresList() { return articleCusresList; }
        public void setArticleCusresList(List<ArticleCusres> articleCusresList) { this.articleCusresList = articleCusresList; }
       
    }

    public static class EnteteFlux {
        public Long id;
        public String sensFlux;
        public Date dateInsertion;
        public Date dateReception;
        public Long idMail;
        public String typeMessage;
        public String typeDocument;
        public String numeroTtn;
        public String numeroDemande;
        public Integer codeTitre;
        public Integer numDom;
        public Date dateDom;
        public Integer numDepot;
        public Date dateDepot;
        public Integer numDeclaration;
        public Date dateDeclaration;
        public String destinataire;
        public String emetteur;
        public String codeCloture;
        public String status;
        public Date datePec;
        // getters and setters
    }

    public static class DetailCusres {
        public Long id;
        public String typeMessage;
        public String typeDocument;
        public String etat;
        public String referenceTtnNumeroDemande;
        public String referenceTtnNumeroDossier;
        public String referenceTtnNumeroMessage;
        public String routageEmetteur;
        public String routageDestinataire;
        public String cleAuth;
        public String numeroMessageOrigine;
        public String enregistrementNumero;
        public Date enregistrementDate;
        public String montantDeviseCodeDevise;
        public Double montantDevisePtfn;
        public Double coursConversionDeviseFacturation;
        public Double valeurDouaneTotaleDinards;
        public String statutDocumentCodeStatut;
        public String statutDocumentEtatLaDeclaration1;
        public String statutDocumentEtatLaDeclaration2;
        public String statutDocumentEtatLaDeclaration3;
        public String statutDocumentMessageInspecteur1;
        public String statutDocumentMessageInspecteur2;
        public String affectationNomInspecteur;
        public Date affectationDateAffectation;
        public String motPasse;
        public Double consignationAuTitreDesPenalitesMontant;
        // getters and setters
    }

    public static class ObservationCusres {
        public ObservationCusresId id;
        public String decision;
        public String reserve;
        public String rdvVisite;
        public Date dateDecision;
        public Double montantDesPenalites;
        // getters and setters
    }
    public static class ObservationCusresId {
        public Long idFlux;
        public Integer numObservation;
        // getters and setters
    }
    public static class ArticleCusres {
        public ArticleCusresId id;
        public String numeroNomenclature;
        public Double prixFactureNet;
        public String referenceTceCodeTce;
        public String referenceTceNumeroTce;
        public String referenceTceCodeOrganismeDomiciliataire;
        public String referenceTceNumeroGuichet;
        public String referenceTceNumeroDom;
        public String referenceTceAnneeDomiciliation;
        public String reglementFinancierModeReglement;
        public String reglementFinancierDesignation;
        public Integer reglementFinancierDelai;
        public String codeRegimeStatistique;
        public Double valeurDinarsFob;
        public Double valeurDinarsDouane;
        // getters and setters
    }
    public static class ArticleCusresId {
        public Long idFlux;
        public Integer numeroArticle;
        // getters and setters
    }
  
    // getters and setters for main class
    public String getTypeDocument() { return typeDocument; }
    public void setTypeDocument(String typeDocument) { this.typeDocument = typeDocument; }
    public Date getSendDate() { return sendDate; }
    public void setSendDate(Date sendDate) { this.sendDate = sendDate; }
    public String getOtherMeta() { return otherMeta; }
    public void setOtherMeta(String otherMeta) { this.otherMeta = otherMeta; }
    public Body getBody() { return body; }
    public void setBody(Body body) { this.body = body; }
}
