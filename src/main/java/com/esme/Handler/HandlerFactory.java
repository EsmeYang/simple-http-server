package com.esme.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import com.esme.httpserver.BusinessLogic;

public class HandlerFactory {
    private static final Map<String, BusinessLogic> handlers = new HashMap<>();
    static {
        Reflections rf = new Reflections("com.esme.Handler");
        Set<Class<?>> handlerClasses = rf.getTypesAnnotatedWith(WebHandler.class);
        for(Class<?> clazz: handlerClasses){
            try {
                WebHandler annotation = clazz.getAnnotation(WebHandler.class);
                String path = annotation.value();
                handlers.put(path, (BusinessLogic) clazz.getDeclaredConstructor().newInstance());
            } catch (Exception ex) {
                throw new RuntimeException("Failed to register handler: " + clazz.getName(), ex);
            }
        }
    }

    public static BusinessLogic getHandler(String key) {
        return handlers.getOrDefault(key, new NotFoundHandler());
    }
}
