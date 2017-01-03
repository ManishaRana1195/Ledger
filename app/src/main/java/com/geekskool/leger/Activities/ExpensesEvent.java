package com.geekskool.leger.Activities;

import com.geekskool.leger.Models.Expense;

import java.util.ArrayList;

/**
 * Created by manisharana on 3/1/17.
 */
public class ExpensesEvent {
    private final ArrayList<Expense> expenses;

    public ExpensesEvent(ArrayList<Expense> expenses) {
        this.expenses=expenses;
    }

    public  ArrayList<Expense> getExpenses(){
        return expenses;
    }
}
