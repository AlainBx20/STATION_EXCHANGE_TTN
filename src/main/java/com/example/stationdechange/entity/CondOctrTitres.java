package com.example.stationdechange.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COND_OCTR_TITRES")
public class CondOctrTitres implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_SEQ")
    private Long idSeq;

    @Column(name = "CODE_RESERVE", length = 70)
    private String codeReserve;

    @Column(name = "LIBEL_RESERVE", length = 350)
    private String libelReserve;

    @Column(name = "CODE_ORGANISME", length = 2)
    private String codeOrganisme;

    @Column(name = "ID_COT")
    private Long idCot;

    public Long getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(Long idSeq) {
        this.idSeq = idSeq;
    }

    public String getCodeReserve() {
        return codeReserve;
    }

    public void setCodeReserve(String codeReserve) {
        this.codeReserve = codeReserve;
    }

    public String getLibelReserve() {
        return libelReserve;
    }

    public void setLibelReserve(String libelReserve) {
        this.libelReserve = libelReserve;
    }

    public String getCodeOrganisme() {
        return codeOrganisme;
    }

    public void setCodeOrganisme(String codeOrganisme) {
        this.codeOrganisme = codeOrganisme;
    }

    public Long getIdCot() {
        return idCot;
    }

    public void setIdCot(Long idCot) {
        this.idCot = idCot;
    }

    
}
