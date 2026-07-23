package com.esme.Handler;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esme.annotation.HttpMethod;
import com.esme.annotation.ServiceMethod;
import com.esme.annotation.WebHandler;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebHandler("/orders")
public class OrdersHandler{
    private static final OrderStore orderStore = new OrderStore();
    private static final Logger logger = LogManager.getLogger(OrdersHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    @ServiceMethod
    //包装成orders对象，添加或者删除的时候不用request和response，直接传入orders对象。
    public void service(HttpRequest request, HttpResponse response) throws JsonProcessingException {
        List<Order> orders = orderStore.findAll();
        String json = mapper.writeValueAsString(orders);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage(json);
    }
    @ServiceMethod(method = HttpMethod.POST)
    public Order addOrder(Order order){
        logger.info("Received order: {}, {}", order.getDishName(), order.getQuantity());
        return orderStore.save(order);
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public Order deleteOrder(Order order) {
        logger.info("Deleting order: {}, {}", order.getId(), order.getDishName());
        boolean deleted = orderStore.deleteById(order.getId());
        if (deleted) {
            return order;
        } else {
            throw new RuntimeException("Order not found for deletion: " + order.getId());
        }
    }

    public Order getOrderById(int id) {
        return orderStore.findById(id);
    }
}