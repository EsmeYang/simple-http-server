package com.esme.httpserver;



public class HttpResponse{
    private String message;
    private String contentType;
    private String status;

    public String getMessage() { return message; }
    public String getContentType() { return contentType; }
    public String getStatus() { return status; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    
    public HttpResponse(String message, String contentType, String status) {
        this.message = message;
        this.contentType = contentType;
        this.status = status;
    }

    public HttpResponse(){
    }
}