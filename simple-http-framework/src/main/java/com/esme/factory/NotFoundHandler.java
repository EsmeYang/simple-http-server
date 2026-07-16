package com.esme.factory;

import com.esme.annotation.ServiceMethod;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

public class NotFoundHandler{
    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 404 Not Found");
        response.setContentType("Content-Type: text/plain");
        response.setMessage("404 Not Found");
    }
}
