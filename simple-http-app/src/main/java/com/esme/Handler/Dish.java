package com.esme.Handler;

public class Dish {
    int id;
    String name;
    double price;
    
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Dish() {
    }
    public Dish(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
