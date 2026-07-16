package com.esme.Handler;
import com.esme.annotation.HttpMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esme.annotation.ServiceMethod;
import com.esme.annotation.WebHandler;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

@WebHandler("/user")
public class UserHandler {
//不要每次都传入request和response，直接传入User对象
//重复部分抽象成方法
//log4j
    private static final Logger logger = LogManager.getLogger(UserHandler.class);
    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"id\":1,\"name\":\"Esme\",\"email\":\"esme@example.com\"}");
    }

    @ServiceMethod(method = HttpMethod.POST)
    public User addUser(User user){
        logger.info("Received user: {}, {}", user.getName(), user.getEmail());
        return user;
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public User deleteUser(User user) {
        logger.info("Deleting user: {}, {}", user.getId(), user.getName());
        return user;
    }
    
}
