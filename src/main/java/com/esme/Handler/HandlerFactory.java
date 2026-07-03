package com.esme.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;

public class HandlerFactory {
    private static final Map<String, Object> handlers = new HashMap<>();
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

    public static void invoke(HttpRequest request, HttpResponse response) {
        try {
            Object handler = getHandler(request.getPath());
            for (Method method : handler.getClass().getDeclaredMethods()) {
                ServiceMethod annotation = method.getAnnotation(ServiceMethod.class);
                if (annotation != null && annotation.method().name().equals(request.getMethod())) {
                    method.invoke(handler, request, response);
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
