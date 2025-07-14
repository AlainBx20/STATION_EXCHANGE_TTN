package tn.smi.authentification.DTO;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ReceptionResponse {
    private String status;
    private Long idSeq;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    public ReceptionResponse(String status, Long idSeq, String message, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
