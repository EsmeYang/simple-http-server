package com.esme.Handler;

public class Order {
    int id;
    String dishName;
    int quantity;
    public int getId() {
        return id;
    }
    public String getDishName() {
        return dishName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Order() {
    }
    public Order(int id, String dishName, int quantity) {
        this.id = id;
        this.dishName = dishName;
        this.quantity = quantity;
    }
}
