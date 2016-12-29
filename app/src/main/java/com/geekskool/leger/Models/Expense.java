package com.geekskool.leger.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {

    private String id;
    private String description;
    private float amount;
    private String date;
    private Category category;
    private State state;

    public Expense(String id, String description, float amount, String date, Category category, State state) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
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

    public String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, HH:mm aa");
        return formatter.format(getDate());
    }

    public void setDateString(String date) {
        this.date = date;
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

    public Date getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date parsedDate=null;
        try {
            parsedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      return parsedDate;
    }

}
