package com.example.stationdechange.dto;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SancrtDocumentDTO {
    private String typeDocument;
    private LocalDateTime sendDate;
    private String otherMeta;
    private Body body;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        public EnteteFlux enteteFlux;
        public DetailSancrt detailSancrt;
        public List<Article> articleList;
        public List<PieceJointe> pieceJointeList;
        public List<Observation> observationList;

        @JsonIgnoreProperties(ignoreUnknown = true)
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
            public boolean fullyMapped;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DetailSancrt {
        public Long id;
        public String typeMessage;
        public String typeDocument;
        public String etat;
        public String refTtnNumMessage;
        public String refTtnNumDossier;
        public String refTtnNumDemande;
        public String refTtnUtisateur;
        public String routageEmetteur;
        public String routageDestinataire;
        public String cleAuth;
        public String exportateurRaisonSociale;
        public String exportateurCode;
        public String exportateurNomPrenom;
        public String exportateurAdresseLine1;
        public String exportateurAdresseLine2;
        public String exportateurAdresseLine3;
        public String importateurRaisonSociale;
        public String importateurCode;
        public String importateurNomPrenom;
        public String importateurAdresseLine1;
        public String importateurAdresseLine2;
        public String importateurAdresseLine3;
        public String codeNatureAutorisation;
        public String natureAutorisationDesignation;
        public String paysProvenanceCode;
        public String paysProvenanceNom;
        public String paysAchatCode;
        public String paysAchatNom;
        public String paysPremiereDestCode;
        public String paysPremiereDestNom;
        public String paysDestDefCode;
        public String paysDestDefNom;
        public String modeLivraisonCode;
        public String modeLivraisonDesignation;
        public String domiciliationCodeOrganisme;
        public String domiciliationNumero;
        public String domiciliationDesignation;
        public String domiciliationNumCompte;
        public Date domiciliationDate;
        public String reglementMode;
        public String reglementDesignation;
        public String reglementDelai;
        public String reglementDevise;
        public Double coursConversionDevise;
        public String montantDeviseCode;
        public Double montantPtfnDevise;
        public Double montantFobDevise;
        public Double montantPtfnDinars;
        public Double montantFobDinars;
        public String regimeStatCode;
        public String contratNum;
        public Date contratDate;
        public String enregistrementNum;
        public Date enregistrementDate;
        public Date validiteDebut;
        public Date validiteFin;
        public String minCommerceOrganisme;
        public String minCommerceSignataire;
        public Date minCommerceDate;
        public Date organismeDomiciliataireDate;
        public String organismeDomiciliataireNom;
        public String organismeDomiciliataireNomSignataire;
        public String declarantSignataire;
        public String declarantOrganisme;
        public Date declarantDate;
        public String codeOrganiseDeposit;
        public String numeroDepot;
        public String designationDepot;
        public Date dateDepot;
        public String numeroDeclarationImputation;
        public String codeBureauDouaneImputation;
        public Date dateImputation;
        public String nomOrganismeBanqueCentrale;
        public String nomSignataireBanqueCentrale;
        public Date dateBanqueCentrale;
        public String nomOrganismeOrganismeTechnique;
        public String nomSignataireOrganismeTechnique;
        public Date dateOrganismeTechnique;
        public String codeStatutOrganismeTechnique;
        public String libelleStatutOrganismeTechnique;
        public String codeStatutBanque;
        public String libelleStatutBanque;
        public String codeStatutMinistereCommerce;
        public String libelleStatutMinistereCommerce;
    }

    public static class Article {
        public ArticleId id;
        public String numeroNomenclature;
        public String paysOrigineCodePays;
        public String paysOrigineNomPays;
        public Double prixFactureNet;
        public Double quantite;
        public String statutMinistereCommerceCodeStatut;
        public String statutMinistereCommerceLibelleStatut;
        public String statutOrganismeTechniqueCode;
        public String statutOrganismeTechniqueLibelle;
        public String uniteMesure;
        public String codePaysExportation;
        public String nomPaysExportation;
        public String codeStatutBanque;
        public String libelleStatutBanque;
        public String designation;
        public static class ArticleId {
            public String numeroArticle;
            public Long idFlux;
        }
    }

    public static class PieceJointe {
        public PieceJointeId id;
        public String typeDocument;
        public String numDocument;
        public Date dateDocument;
        public String refFichierJoint;
        public String refBaseImage;
        public String path;
        
        public static class PieceJointeId {
            public Long idFlux;
            public Long numeroPieceJointe;
        }
    }

    public static class Observation {
        public Long id;
        public String codeObservation;
        public String typeObservation;
        public String libelle;
        public String typeParent;
        public Long parentId;
        public Long fluxId;
    }

    // Getters and setters for main fields
    public String getTypeDocument() { return typeDocument; }
    public void setTypeDocument(String typeDocument) { this.typeDocument = typeDocument; }
    public LocalDateTime getSendDate() { return sendDate; }
    public void setSendDate(LocalDateTime sendDate) { this.sendDate = sendDate; }
    public String getOtherMeta() { return otherMeta; }
    public void setOtherMeta(String otherMeta) { this.otherMeta = otherMeta; }
    public Body getBody() { return body; }
    public void setBody(Body body) { this.body = body; }
}
