package com.geekskool.leger.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekskool.leger.Models.Category;
import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by manisharana on 1/1/17.
 */
public class DashboardActivity extends AppCompatActivity {

    private final HashMap<Category, Float> categoryTotalMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        Intent intent = getIntent();
//        LinearLayout root = (LinearLayout) findViewById(R.id.root);
//        ArrayList<Expense> expenses = intent.getParcelableArrayListExtra(ExpenseUtil.EXPENSES);
//        HashMap<Category, Float> map = categoryTotal(expenses);
//        for (Category category : map.keySet()) {
//            LinearLayout layout = getLayout();
//            ImageView imageView = getImageView(category);
//            TextView textView = getTextView(map.get(category));
//            layout.addView(imageView);
//            layout.addView(textView);
//            root.addView(layout);
//        }
    }

    private ImageView getImageView(Category category) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(category.getResourceId());
        iv.setPadding(10, 10, 10, 10);
        iv.setLayoutParams(new LinearLayout.LayoutParams(48,48));
        return iv;
    }

    private LinearLayout getLayout() {
        LinearLayout linearLayout = new LinearLayout(DashboardActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(0);
        linearLayout.setPadding(20,20,20,10);
        return linearLayout;
    }

    private HashMap<Category, Float> categoryTotal(ArrayList<Expense> expenseList){
        for(Expense expense:expenseList){
            Category category = expense.getCategory();
            Float amount = categoryTotalMap.get(category);
            if(amount==null)
                amount = 0f;
            amount += expense.getAmount();
            categoryTotalMap.put(category,amount);
        }
        return categoryTotalMap;
    }


    public TextView getTextView(Float amount) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        tv.setTextSize(getResources().getDimension(R.dimen.sp18));
        String text = getResources().getString(R.string.inr) + "  " + amount;
        tv.setText(text);
        return tv;
    }
}
