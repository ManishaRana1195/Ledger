package com.geekskool.leger.Models;

public class Expense {

    private String id;
    private String description;
    private float amount;
    private String time;
    private Category category;
    private State state;

    public Expense(String id, String description, float amount, String time, Category category, State state) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.time = time;
        this.category = category;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
