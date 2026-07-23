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

@WebHandler("/user")
public class UserHandler {
    private static final UserStore userStore = new UserStore();
    private static final Logger logger = LogManager.getLogger(UserHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) throws JsonProcessingException {
        List<User> users = userStore.findAll();
        String json = mapper.writeValueAsString(users);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage(json);
    }

    @ServiceMethod(method = HttpMethod.POST)
    public User addUser(User user){
        logger.info("Received user: {}, {}", user.getName(), user.getEmail());
        return userStore.save(user);
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public User deleteUser(User user) {
        logger.info("Deleting user: {}, {}", user.getId(), user.getName());
        boolean deleted = userStore.deleteById(user.getId());
        if (deleted) {
            return user;
        } else {
            throw new RuntimeException("User not found for deletion: " + user.getId());
        }
    }
    @ServiceMethod(method = HttpMethod.GET, path = "{id}")
    public User getUserById(int id) {
        return userStore.findById(id);
    }
}
