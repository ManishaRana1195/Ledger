package com.geekskool.leger;

import com.geekskool.leger.Models.Category;
import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.Models.UnverifiedState;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by manisharana on 28/12/16.
 */
public class ExpenseUtil {

    public static final String EXPENSES = "expenses";
    private static final String RECHARGE = "Recharge";
    private static final String TAXI = "Taxi";


    public static Expense getExpenseObject(JSONObject jsonObject) {
        Category categoryObject;
        Expense expense;
        try {
            String category = jsonObject.getString("category");
            String id = jsonObject.getString("id");
            String description = jsonObject.getString("description");
            float amount =(float) jsonObject.getDouble("amount");
            String time = jsonObject.getString("time");
            String state = jsonObject.getString("state");
            switch(category){
                case RECHARGE:
                    categoryObject = new Category(category,R.drawable.ic_recharge);
                    break;
                case TAXI:
                    categoryObject = new Category(category,R.drawable.ic_taxi);
                    break;
                default:
                    categoryObject = new Category("general",R.drawable.ic_general);
            }
            expense = new Expense(id,description,amount,time,categoryObject,new UnverifiedState());  // need to fix

        } catch (JSONException e) {
            e.printStackTrace();
            expense = null;
        }
        return expense;
    }


    public static ArrayList<Expense> sortByDate(ArrayList<Expense> expenses){

        Comparator<Expense> comparator = new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        };

        Collections.sort(expenses,comparator);
        return expenses;
    }

}
