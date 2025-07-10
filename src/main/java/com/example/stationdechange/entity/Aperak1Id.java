package com.example.stationdechange.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Aperak1Id implements Serializable {
    private String numeroDossierTtn;
    private String numeroMessage;

    public Aperak1Id() {}
    public Aperak1Id(String numeroDossierTtn, String numeroMessage) {
        this.numeroDossierTtn = numeroDossierTtn;
        this.numeroMessage = numeroMessage;
    }
    public String getNumeroDossierTtn() { return numeroDossierTtn; }
    public void setNumeroDossierTtn(String numeroDossierTtn) { this.numeroDossierTtn = numeroDossierTtn; }
    public String getNumeroMessage() { return numeroMessage; }
    public void setNumeroMessage(String numeroMessage) { this.numeroMessage = numeroMessage; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aperak1Id that = (Aperak1Id) o;
        return Objects.equals(numeroDossierTtn, that.numeroDossierTtn) &&
               Objects.equals(numeroMessage, that.numeroMessage);
    }
    @Override
    public int hashCode() {
        return Objects.hash(numeroDossierTtn, numeroMessage);
    }
} 