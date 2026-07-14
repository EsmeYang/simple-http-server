package com.esme.Handler;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebHandler("/orders")
public class OrdersHandler{
    @ServiceMethod
    //包装成orders对象，添加或者删除的时候不用request和response，直接传入orders对象。
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: text/plain");
        response.setMessage("Orders list here");
    }
    @ServiceMethod(method = HttpMethod.POST)
    public void addOrder(HttpRequest request, HttpResponse response) throws JsonMappingException, JsonProcessingException {
        String body = request.getBody();
        // 用 Jackson 转成 Order 对象
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(body, Order.class);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"Order added: " + order.getDishName() + " x" + order.getQuantity() + "\"}");
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public void deleteOrder(HttpRequest request, HttpResponse response) throws JsonMappingException, JsonProcessingException {
        String body = request.getBody();
        // 用 Jackson 转成 Order 对象
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(body, Order.class);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("{\"message\": \"Order deleted Id: " + order.getId() + "\"}");
    }
}
