package com.modakdev.models.nonentities.response.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ModakFlixBaseResponse {

    protected final String txnId;
    protected String txnDate;
    protected String txnTime;
    protected HttpStatus status;
    protected String message = "";

    public ModakFlixBaseResponse() {
        this.txnId = UUID.randomUUID().toString();
        this.txnDate = String.valueOf(java.time.LocalDate.now());
        this.txnTime = String.valueOf(java.time.LocalTime.now());
    }

    public ModakFlixBaseResponse(HttpStatus status, String message) {
        this.txnId = UUID.randomUUID().toString();;
        this.txnDate = String.valueOf(java.time.LocalDate.now());
        this.txnTime = String.valueOf(java.time.LocalTime.now());
        this.status = status;
        this.message = message;
    }

    public String getTxnId() {
        return txnId;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}"; // Fallback in case of an exception
        }
    }
}
