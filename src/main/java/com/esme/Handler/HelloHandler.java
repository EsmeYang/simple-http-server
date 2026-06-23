package com.esme.Handler;

import com.esme.httpserver.BusinessLogic;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.esme.Handler.WebHandler;
@WebHandler("/hello")
public class HelloHandler extends BusinessLogic{

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: text/plain");
        response.setMessage("Hello Servlet");
    }

}