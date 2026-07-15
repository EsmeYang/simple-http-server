package com.esme.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HandlerFactory {
    private static final Map<String, Object> handlers = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        Reflections rf = new Reflections("com.esme.Handler");
        Set<Class<?>> handlerClasses = rf.getTypesAnnotatedWith(WebHandler.class);

        for(Class<?> clazz: handlerClasses){
            try {
                WebHandler annotation = clazz.getAnnotation(WebHandler.class);
                String path = annotation.value();
                handlers.put(path, clazz.getDeclaredConstructor().newInstance());
            } catch (Exception ex) {
                throw new RuntimeException("Failed to register handler: " + clazz.getName(), ex);
            }
        }
    }

    public static Object getHandler(String key) {
        return handlers.getOrDefault(key, new NotFoundHandler());
    }

    public static void invoke(HttpRequest request, HttpResponse response) throws JsonMappingException, JsonProcessingException {
        try {
            Object handler = getHandler(request.getPath());
            
            for (Method method : handler.getClass().getDeclaredMethods()) {
                ServiceMethod annotation = method.getAnnotation(ServiceMethod.class);
                if (annotation != null && annotation.method().name().equals(request.getMethod())) {
                    //把request通过映射成对象传入方法中
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (paramTypes.length > 0 && paramTypes[0] == HttpRequest.class) {
                        method.invoke(handler, request, response);
                    } else {
                        String body = request.getBody();
                        Object requestObj = mapper.readValue(body, paramTypes[0]);
                        Object result = method.invoke(handler, requestObj);  // 拿到返回值
                        String json = mapper.writeValueAsString(result);      // 序列化成 JSON
                        response.setStatus("HTTP/1.1 200 OK");
                        response.setContentType("Content-Type: application/json");
                        response.setMessage(json);
                    }
                    return;
                }
            }
            response.setStatus("HTTP/1.1 405 Method Not Allowed");
            response.setContentType("Content-Type: text/plain");
            response.setMessage("405 Method Not Allowed");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke handler method", e);
        }
    }
}
