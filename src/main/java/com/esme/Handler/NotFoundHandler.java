package com.esme.Handler;

import com.esme.httpserver.BusinessLogic;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

public class NotFoundHandler extends BusinessLogic{
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 404 Not Found");
        response.setContentType("Content-Type: text/plain");
        response.setMessage("404 Not Found");
    }
}
