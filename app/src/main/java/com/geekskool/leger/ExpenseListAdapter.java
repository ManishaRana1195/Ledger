package com.geekskool.leger;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekskool.leger.Models.Expense;

import java.util.List;

/**
 * Created by manisharana on 28/12/16.
 */
public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder> {

    private final List<Expense> expenses;
    private Context context;

    public ExpenseListAdapter(Context context, List<Expense> expenses) {
        this.expenses = expenses;
        this.context = context;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_expense_view, parent, false);
        return new ExpenseViewHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.categoryView.setImageDrawable(ContextCompat.getDrawable(context, expense.getCategory().getResourceId()));
        holder.timeView.setText(expense.getDateString());
        holder.amountView.setText(String.valueOf(expense.getAmount()));
        holder.descView.setText(expense.getDescription());
        switch (expense.getState().getStateOptions().ordinal()) {
            case 0:
                holder.unverifiedStateButton.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.verifiedStateButton.setVisibility(View.VISIBLE);
                holder.fraudStateButton.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.unverifiedStateButton.setVisibility(View.VISIBLE);
                break;
            default:
                holder.verifiedStateButton.setVisibility(View.GONE);
                holder.unverifiedStateButton.setVisibility(View.GONE);
                holder.fraudStateButton.setVisibility(View.GONE);
        }
        setOnClickListeners(holder);
    }

    public void updateList(List<Expense> newList) {
        expenses.clear();
        expenses.addAll(newList);
        this.notifyDataSetChanged();
    }

    private void setOnClickListeners(ExpenseViewHolder holder) {
        holder.fraudStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.verifiedStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.unverifiedStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses == null ? 0 : expenses.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        private final ImageView categoryView;
        private final TextView descView;
        private final TextView amountView;
        private final TextView timeView;
        private final ImageButton fraudStateButton;
        private final ImageButton verifiedStateButton;
        private final ImageButton unverifiedStateButton;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            categoryView = (ImageView) itemView.findViewById(R.id.expense_category);
            descView = (TextView) itemView.findViewById(R.id.expense_desc);
            amountView = (TextView) itemView.findViewById(R.id.expense_amount);
            timeView = (TextView) itemView.findViewById(R.id.expense_time);
            fraudStateButton = (ImageButton) itemView.findViewById(R.id.expense_fraud_state);
            verifiedStateButton = (ImageButton) itemView.findViewById(R.id.expense_verified_state);
            unverifiedStateButton = (ImageButton) itemView.findViewById(R.id.expense_unverified_state);
        }
    }
}
