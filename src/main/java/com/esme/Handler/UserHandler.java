package com.esme.Handler;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

@WebHandler("/user")
public class UserHandler {
//不要每次都传入request和response，直接传入User对象
//重复部分抽象成方法
//log4j

    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"id\":1,\"name\":\"Esme\",\"email\":\"esme@example.com\"}");
    }

    @ServiceMethod(method = HttpMethod.POST)
    public User addUser(User user){
        System.out.println("Received user: " + user.getName() + ", " + user.getEmail());
        return user;
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public User deleteUser(User user) {
        System.out.println("Deleting user: " + user.getId());
        return user;
    }
    
}
