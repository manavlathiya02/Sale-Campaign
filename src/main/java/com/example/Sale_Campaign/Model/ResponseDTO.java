package com.example.Sale_Campaign.Model;
import org.springframework.http.HttpStatus;

import java.util.List;


public class ResponseDTO <T>{

    private T body;

    private String Message;

    private HttpStatus status;


    public ResponseDTO(T body, HttpStatus status, String message) {
        this.body = body;
        this.Message = message;
        this.status = status;
    }



    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }


}
