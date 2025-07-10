package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "PIECES_JOINTES")
public class PiecesJointes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TYPE_DOC_PJ", length = 35)
    private String typeDocPj;

    @Column(name = "NUM_DOC", length = 35)
    private String numDoc;

    @Column(name = "DATE_DOC")
    private LocalDate dateDoc;

    @Column(name = "REF_BASE_IMAGE", length = 35)
    private String refBaseImage;

    @Column(name = "REF_FICHIER_JOINT", length = 45)
    private String refFichierJoint;

    @Column(name = "ID_SEQ")
    private Long idSeq;

    @Column(name = "CODE_PIECES_JOINTES", length = 35)
    private String codePiecesJointes;

    public String getTypeDocPj() {
        return typeDocPj;
    }

    public void setTypeDocPj(String typeDocPj) {
        this.typeDocPj = typeDocPj;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public LocalDate getDateDoc() {
        return dateDoc;
    }

    public void setDateDoc(LocalDate dateDoc) {
        this.dateDoc = dateDoc;
    }

    public String getRefBaseImage() {
        return refBaseImage;
    }

    public void setRefBaseImage(String refBaseImage) {
        this.refBaseImage = refBaseImage;
    }

    public String getRefFichierJoint() {
        return refFichierJoint;
    }

    public void setRefFichierJoint(String refFichierJoint) {
        this.refFichierJoint = refFichierJoint;
    }

    public Long getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(Long idSeq) {
        this.idSeq = idSeq;
    }

    public String getCodePiecesJointes() {
        return codePiecesJointes;
    }

    public void setCodePiecesJointes(String codePiecesJointes) {
        this.codePiecesJointes = codePiecesJointes;
    }

    
}
