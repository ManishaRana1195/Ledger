package com.geekskool.leger.Models;

public class Expense {

    private long id;
    private String description;
    private int amount;
    private String time;
    private Category category;
    private State state;

    public Expense(long id, String description, int amount, String time, Category category, State state) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.time = time;
        this.category = category;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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
