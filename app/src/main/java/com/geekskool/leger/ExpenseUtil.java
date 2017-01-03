package com.geekskool.leger;

import android.support.annotation.NonNull;

import com.geekskool.leger.Models.Category;
import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.Models.FraudState;
import com.geekskool.leger.Models.State;
import com.geekskool.leger.Models.UnverifiedState;
import com.geekskool.leger.Models.VerifiedState;

import org.json.JSONArray;
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
    public static final String BROADCAST = "received";


    public static Expense getExpenseObject(JSONObject jsonObject) {
        Expense expense;
        try {
            String category = jsonObject.getString("category");
            String id = jsonObject.getString("id");
            String description = jsonObject.getString("description");
            float amount = (float) jsonObject.getDouble("amount");
            String time = jsonObject.getString("time");
            String state = jsonObject.getString("state");

            expense = new Expense(id, description, amount, time, getCategory(category), getState(state));

        } catch (JSONException e) {
            e.printStackTrace();
            expense = null;
        }
        return expense;
    }

    @NonNull
    private static Category getCategory(String category) {
        Category categoryObject;
        switch (category) {
            case RECHARGE:
                categoryObject = new Category(category, R.drawable.ic_recharge);
                break;
            case TAXI:
                categoryObject = new Category(category, R.drawable.ic_taxi);
                break;
            default:
                categoryObject = new Category("general", R.drawable.ic_general);
        }
        return categoryObject;
    }

    private static State getState(String input) {
        State state;
        switch (input) {
            case "unverified":
                state = new UnverifiedState();
                break;
            case "verified":
                state = new VerifiedState();
                break;
            case "fraud":
                state = new FraudState();
                break;
            default:
                state = new UnverifiedState();
        }
        return state;
    }


    public static ArrayList<Expense> sortByDate(ArrayList<Expense> expenses) {

        Comparator<Expense> comparator = new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        };

        Collections.sort(expenses, comparator);
        return expenses;
    }

    public static boolean isValid(ArrayList<Expense> expenses) {
        return expenses != null && !expenses.isEmpty();
    }

    public static String getListString(ArrayList<Expense> expenses) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            for(Expense expense:expenses){
                array.put(new JSONObject(expense.toString()));
            }
            jsonObject.put("expenses", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static ArrayList<Expense> getExpenseList(String result) throws JSONException {
        ArrayList<Expense> expenseList = new ArrayList<Expense>();
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray(EXPENSES);
        for (int i = 0; i < jsonArray.length(); i++)
            expenseList.add(ExpenseUtil.getExpenseObject(jsonArray.getJSONObject(i)));

        return expenseList;
    }

}
