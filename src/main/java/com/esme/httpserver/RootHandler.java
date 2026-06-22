package com.esme.httpserver;

public class RootHandler extends BusinessLogic{

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: text/plain");
        response.setMessage("Hello World");
    }
    
}
