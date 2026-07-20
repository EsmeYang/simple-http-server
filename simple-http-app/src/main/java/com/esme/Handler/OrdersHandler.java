package com.esme.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esme.annotation.HttpMethod;
import com.esme.annotation.ServiceMethod;
import com.esme.annotation.WebHandler;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

@WebHandler("/orders")
public class OrdersHandler{
    private static final OrderStore orderStore = new OrderStore();
    private static final Logger logger = LogManager.getLogger(OrdersHandler.class);
    @ServiceMethod
    //包装成orders对象，添加或者删除的时候不用request和response，直接传入orders对象。
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: text/plain");
        response.setMessage("Orders list here");
    }
    @ServiceMethod(method = HttpMethod.POST)
    public Order addOrder(Order order){
        logger.info("Received order: {}, {}", order.getDishName(), order.getQuantity());
        return orderStore.save(order);
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public Order deleteOrder(Order order) {
        logger.info("Deleting order: {}, {}", order.getId(), order.getDishName());
        return order;
    }
}