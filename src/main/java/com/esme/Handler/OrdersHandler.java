package com.esme.Handler;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

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
    public Order addOrder(Order order){
        System.out.println("Received order: " + order.getDishName() + ", " + order.getQuantity());
        return order;
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public Order deleteOrder(Order order) {
        System.out.println("Deleting order: " + order.getId());
        return order;
    }
}