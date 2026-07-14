package com.esme.Handler;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void addUser(HttpRequest request, HttpResponse response) throws JsonMappingException, JsonProcessingException {
        String body = request.getBody();
        // 用 Jackson 转成 User 对象
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(body, User.class);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"User added: " + user.getId() + " - " + user.getName() + "\"}");
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public void deleteUser(HttpRequest request, HttpResponse response) throws JsonMappingException, JsonProcessingException {
        String body = request.getBody();
        // 用 Jackson 转成 User 对象
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(body, User.class);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"User deleted: " + user.getId() + " - " + user.getName() + "\"}");
    }
    
}
