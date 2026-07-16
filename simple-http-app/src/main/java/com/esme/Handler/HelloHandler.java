package com.esme.Handler;

import com.esme.annotation.ServiceMethod;
import com.esme.annotation.WebHandler;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

@WebHandler("/hello")
public class HelloHandler{

    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: text/plain");
        response.setMessage("Hello Servlet");
    }

}