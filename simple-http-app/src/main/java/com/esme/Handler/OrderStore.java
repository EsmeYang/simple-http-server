package com.esme.Handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OrderStore implements Store<Order, Integer> {
    Map<Integer, Order> orderMap = new ConcurrentHashMap<>(); // Ensure thread safety
    @Override
    public Order save(Order entity) {
        // Implement the logic to save the order entity to the database or in-memory store
        if(orderMap.containsKey(entity.getId())) {
            throw new DuplicateIdException("Order with ID " + entity.getId() + " already exists");
        } else {
            // If the order does not exist, add a new order
            orderMap.put(entity.getId(), entity);
            return entity;
        }
    }

    @Override
    public Order findById(Integer id) {
        // Implement the logic to find an order by ID from the database or in-memory store
        return orderMap.get(id);
    }

    @Override
    public List<Order> findAll() {
        // Implement the logic to retrieve all orders from the database or in-memory store
        return orderMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Integer id) {
        // Implement the logic to delete an order by ID from the database or in-memory store
        return orderMap.remove(id) != null;
    }
    
}
