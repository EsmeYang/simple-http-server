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
@WebHandler("/dish")
public class DishHandler{
    private static final DishStore dishStore = new DishStore();
    private static final Logger logger = LogManager.getLogger(DishHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) throws JsonProcessingException {
        List<Dish> dishes = dishStore.findAll();
        String json = mapper.writeValueAsString(dishes);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage(json);
    }
    @ServiceMethod(method = HttpMethod.POST)
    public Dish addDish(Dish dish){
        logger.info("Received dish: {}, {}", dish.getName(), dish.getPrice());
        return dishStore.save(dish);
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public Dish deleteDish(Dish dish) {
        logger.info("Deleting dish: {}, {}", dish.getId(), dish.getName());
        boolean deleted = dishStore.deleteById(dish.getId());
        if (deleted) {
            return dish;
        } else {
            throw new RuntimeException("Dish not found for deletion: " + dish.getId());
        }
    }
}