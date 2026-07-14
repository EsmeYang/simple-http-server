package com.esme.httpserver;

import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private String body;
    private String protocol;
    private Map<String, String> headers;

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getBody() { return body; }
    public String getProtocol() { return protocol; }
    public Map<String, String> getHeaders() { return headers; }
    public void setMethod(String method) { this.method = method; }
    public void setPath(String path) { this.path = path; }
    public void setBody(String body) { this.body = body; }
    public void setProtocol(String protocol) { this.protocol = protocol; }
    public void setHeaders(Map<String, String> headers) { this.headers = headers; }
    
    public HttpRequest(String method, String path, String protocol, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.headers = headers;
    }
}