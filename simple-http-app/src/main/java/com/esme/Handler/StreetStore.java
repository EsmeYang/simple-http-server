package com.esme.Handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class StreetStore implements Store<Street,String> {
    Map<String, Street> streetMap = new ConcurrentHashMap<>(); // Ensure thread safety
    @Override
    public Street save(Street entity) {
        // Implement the logic to save the street entity to the database or in-memory store
        if(streetMap.containsKey(entity.getId())) {
            throw new DuplicateIdException("Street with ID " + entity.getId() + " already exists");
        } else {
            // If the street does not exist, add a new street
            streetMap.put(entity.getId(), entity);
            return entity;
        }
    }

    @Override
    public Street findById(String id) {
        // Implement the logic to find an street by ID from the database or in-memory store
        return streetMap.get(id);
    }

    @Override
    public List<Street> findAll() {
        // Implement the logic to retrieve all streets from the database or in-memory store
        return streetMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(String id) {
        // Implement the logic to delete an street by ID from the database or in-memory store
        return streetMap.remove(id) != null;
    }
    
}
