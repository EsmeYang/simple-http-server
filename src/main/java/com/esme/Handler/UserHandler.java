package com.esme.Handler;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
@WebHandler("/user")
public class UserHandler {
//AddUser deleteUser 也用注解的形式完成

    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"id\":1,\"name\":\"Esme\",\"email\":\"esme@example.com\"}");
    }

    @ServiceMethod(method = HttpMethod.POST)
    public void addUser(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"User added\"}");
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public void deleteUser(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"User deleted\"}");
    }
    
}
