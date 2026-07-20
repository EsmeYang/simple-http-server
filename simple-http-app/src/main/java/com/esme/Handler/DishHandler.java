package com.esme.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esme.annotation.HttpMethod;
import com.esme.annotation.ServiceMethod;
import com.esme.annotation.WebHandler;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
@WebHandler("/dish")
public class DishHandler{
    private static final DishStore dishStore = new DishStore();
    private static final Logger logger = LogManager.getLogger(DishHandler.class);
    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("[{\"id\":1,\"name\":\"pizza\",\"price\":28.00},{\"id\":2,\"name\":\"hamburger\",\"price\":15.00}]");
    }
    @ServiceMethod(method = HttpMethod.POST)
    public Dish addDish(Dish dish){
        logger.info("Received dish: {}, {}", dish.getName(), dish.getPrice());
        return dishStore.save(dish);
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public Dish deleteDish(Dish dish) {
        logger.info("Deleting dish: {}, {}", dish.getId(), dish.getName());
        dishStore.deleteById(dish.getId());
        return dish;
    }
}