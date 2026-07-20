package com.esme.Handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UserStore implements Store<User> {
    Map<Integer, User> userMap = new ConcurrentHashMap<>();//保证线程安全
    @Override
    public User save(User entity) {
        // Implement the logic to save the user entity to the database or in-memory store
        if(userMap.containsKey(entity.getId())) {
            throw new DuplicateIdException("User with ID " + entity.getId() + " already exists");
        } else {
            // If the user does not exist, add a new user
            userMap.put(entity.getId(), entity);
            return entity;
        }
    }

    @Override
    public User findById(int id) {
        // Implement the logic to find a user by ID from the database or in-memory store
        return userMap.get(id); // Return the found user or null if not found
    }

    @Override
    public List<User> findAll() {
        // Implement the logic to retrieve all users from the database or in-memory store
        return userMap.values().stream().collect(Collectors.toList()); // Return a list of users
    }

    @Override
    public boolean deleteById(int id) {
        // Implement the logic to delete a user by ID from the database or in-memory store
        return userMap.remove(id) != null; // Return true if deletion was successful, false otherwise
    }
    
}
