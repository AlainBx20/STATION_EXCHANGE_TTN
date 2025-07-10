package tn.smi.authentification.DTO;

import java.util.Date;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ReceptionResponse {
    private String status;
    private Long idSeq;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    public ReceptionResponse(String status, Long idSeq, String message, LocalDate date) {
        this.status = status;
        this.idSeq = idSeq;
        this.message = message;
        this.date = date;
    }

    public ReceptionResponse() {
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(Long idSeq) {
        this.idSeq = idSeq;
    }
}
