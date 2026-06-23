package com.esme.Handler;

import com.esme.httpserver.BusinessLogic;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.esme.Handler.WebHandler;
@WebHandler("/user")
public class UserHandler extends BusinessLogic {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"id\":1,\"name\":\"Esme\",\"email\":\"esme@example.com\"}");
    }
}
