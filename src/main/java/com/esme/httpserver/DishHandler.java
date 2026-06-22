package com.esme.httpserver;

public class DishHandler extends BusinessLogic{
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("[{\"id\":1,\"name\":\"pizza\",\"price\":28.00},{\"id\":2,\"name\":\"hamburger\",\"price\":15.00}]");
    }
    
}