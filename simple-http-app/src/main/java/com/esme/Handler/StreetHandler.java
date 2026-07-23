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
@WebHandler("/street")
public class StreetHandler {
    
    private static final StreetStore streetStore = new StreetStore();
    private static final Logger logger = LogManager.getLogger(StreetHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    @ServiceMethod
    public void service(HttpRequest request, HttpResponse response) throws JsonProcessingException {
        List<Street> streets = streetStore.findAll();
        String json = mapper.writeValueAsString(streets);
        response.setStatus("HTTP/1.1 200 OK");
        response.setContentType("Content-Type: application/json");
        response.setMessage(json);
    }
    @ServiceMethod(method = HttpMethod.POST)
    public Street addStreet(Street street){
        logger.info("Received street: {}, {}", street.getId(), street.getAddress());
        return streetStore.save(street);
    }

    @ServiceMethod(method = HttpMethod.DELETE)
    public Street deleteStreet(Street street) {
        logger.info("Deleting street: {}, {}", street.getId(), street.getAddress());
        boolean deleted = streetStore.deleteById(street.getId());
        if (deleted) {
            return street;
        } else {
            throw new RuntimeException("Street not found for deletion: " + street.getId());
        }
    }

    @ServiceMethod(method = HttpMethod.GET, path = "{id}")
    public Street getStreetById(String id) {
        return streetStore.findById(id);
    }
}