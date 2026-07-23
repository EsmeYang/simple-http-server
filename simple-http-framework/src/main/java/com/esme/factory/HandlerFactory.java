package com.esme.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.esme.annotation.ServiceMethod;
import com.esme.annotation.WebHandler;
import com.esme.httpserver.HttpRequest;
import com.esme.httpserver.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//了解synchronized和信号量（互斥体）的原理
//对象里面的多态

public class HandlerFactory {
    private static final Map<String, Object> handlers = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(HandlerFactory.class);
    public static void init(String packageName) {
        Reflections rf = new Reflections(packageName);
        Set<Class<?>> handlerClasses = rf.getTypesAnnotatedWith(WebHandler.class);

        for(Class<?> clazz: handlerClasses){
            try {
                WebHandler annotation = clazz.getAnnotation(WebHandler.class);
                String path = annotation.value();// /user
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
            String[] pathSegments = request.getPath().split("/");
            logger.info("Path segments: {}", (Object) pathSegments);
            Object handler = handlers.get("/" + pathSegments[1]);
            for (Method method : handler.getClass().getDeclaredMethods()) {
                ServiceMethod annotation = method.getAnnotation(ServiceMethod.class);
                if (annotation != null && annotation.method().name().equals(request.getMethod())) {
                    //把request通过映射成对象传入方法中
                    String path = annotation.path();
                    Class<?>[] paramTypes = method.getParameterTypes();
                    if (!path.isEmpty()) {
                        if (pathSegments.length > 2 && !pathSegments[2].isEmpty()) {
                            String JsonValue;
                            if(paramTypes[0] == String.class) {
                                JsonValue = "\"" + pathSegments[2] + "\""; // Wrap the string in quotes to make it valid JSON
                            } else {
                                JsonValue = pathSegments[2];
                            }
                            Object requestObj = mapper.readValue(JsonValue, paramTypes[0]);
                            Object result = method.invoke(handler,requestObj);
                            String json = mapper.writeValueAsString(result);
                            response.setStatus("HTTP/1.1 200 OK");
                            response.setContentType("Content-Type: application/json");
                            response.setMessage(json);
                            return;
                        } else {
                            continue; // Skip this method if the path doesn't match
                        }
                    }
                    if (paramTypes.length > 0 && paramTypes[0] == HttpRequest.class) {
                        method.invoke(handler, request, response);
                    } else {                                                            //方法里传入的是int/String/...类型
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
