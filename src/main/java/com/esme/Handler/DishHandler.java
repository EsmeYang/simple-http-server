package com.esme.Handler;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

@WebHandler("/dish")
public class DishHandler{
    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage("[{\"id\":1,\"name\":\"pizza\",\"price\":28.00},{\"id\":2,\"name\":\"hamburger\",\"price\":15.00}]");
    }
    @ServiceMethod(method = HttpMethod.POST)
    public Dish addDish(Dish dish){
        System.out.println("Received dish: " + dish.getName() + ", " + dish.getPrice());
        return dish;
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public Dish deleteDish(Dish dish) {
        System.out.println("Deleting dish: " + dish.getId());
        return dish;
    }
}