package com.geekskool.leger.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense implements Parcelable{

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

    private Expense(Parcel in){
        this.id = in.readString();
        this.description = in.readString();
        this.amount = in.readFloat();
        this.date = in.readString();
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.state = in.readParcelable(State.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
        dest.writeFloat(amount);
        dest.writeString(date);
        dest.writeParcelable(category,flags);
        dest.writeParcelable(state,flags);
    }

    @Override
    public String toString() {
        return "{ id : '" + id + '\'' +
                ", description : '" + description + '\'' +
                ", amount: " + amount +
                ", time : '" + date + '\'' +
                ", " + category +
                ", " + state +  "},";
    }

    public static final Parcelable.Creator<Expense> CREATOR = new Parcelable.Creator<Expense>() {

        @Override
        public Expense createFromParcel(Parcel source) {
            return new Expense(source);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;

        Expense expense = (Expense) o;

        return id != null ? id.equals(expense.id) : expense.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
