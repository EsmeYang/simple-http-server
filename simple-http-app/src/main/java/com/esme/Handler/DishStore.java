package com.esme.Handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DishStore implements Store<Dish, Integer> {
    Map<Integer, Dish> dishMap = new ConcurrentHashMap<>(); // Ensure thread safety
    @Override
    public Dish save(Dish entity) {
        if(dishMap.containsKey(entity.getId())) {
            throw new DuplicateIdException("Dish with ID " + entity.getId() + " already exists");
        } else {
            // If the dish does not exist, add a new dish
            dishMap.put(entity.getId(), entity);
            return entity;
        }
    }

    @Override
    public Dish findById(Integer id) {
        // Implement the logic to find a dish by ID from the database or in-memory store
        return dishMap.get(id); // Return the found dish or null if not found
    }

    @Override
    public List<Dish> findAll() {
        // Implement the logic to retrieve all dishes from the database or in-memory store
        return dishMap.values().stream().collect(Collectors.toList()); // Return a list of dishes
    }

    @Override
    public boolean deleteById(Integer id) {
        // Implement the logic to delete a dish by ID from the database or in-memory store
        return dishMap.remove(id) != null; // Return true if deletion was successful, false otherwise
    }
    
}
