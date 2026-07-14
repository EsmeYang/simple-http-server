package com.esme.Handler;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebHandler("/dish")
public class DishHandler{
    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("[{\"id\":1,\"name\":\"pizza\",\"price\":28.00},{\"id\":2,\"name\":\"hamburger\",\"price\":15.00}]");
    }
    @ServiceMethod(method = HttpMethod.POST)
    public void addDish(HttpRequest request, HttpResponse response) throws JsonMappingException, JsonProcessingException {
        String body = request.getBody();
        // 用 Jackson 转成 Dish 对象
        ObjectMapper mapper = new ObjectMapper();
        Dish dish = mapper.readValue(body, Dish.class);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"Dish added: " + dish.getName() + " - $" + dish.getPrice() + "\"}");
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public void deleteDish(HttpRequest request, HttpResponse response) throws JsonMappingException, JsonProcessingException {
        String body = request.getBody();
        // 用 Jackson 转成 DIsh 对象
        ObjectMapper mapper = new ObjectMapper();
        Dish dish = mapper.readValue(body, Dish.class);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"Dish deleted: " + dish.getId() + " - " + dish.getName() + "\"}");
    }
}