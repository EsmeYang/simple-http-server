package com.esme.httpserver;

public abstract class BusinessLogic {
    public abstract void service(HttpRequest request, HttpResponse response);
}
