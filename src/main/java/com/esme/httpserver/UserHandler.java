package com.esme.httpserver;

public class UserHandler extends BusinessLogic {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"id\":1,\"name\":\"Esme\",\"email\":\"esme@example.com\"}");
    }
}
