package com.geekskool.leger;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.Models.Result;
import com.geekskool.leger.Models.StateOptions;
import com.geekskool.leger.Sync.SyncAdapter;

import java.util.ArrayList;

import static com.geekskool.leger.Models.StateOptions.fraud;
import static com.geekskool.leger.Models.StateOptions.unverified;
import static com.geekskool.leger.Models.StateOptions.verified;

/**
 * Created by manisharana on 28/12/16.
 */
public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder> {

    private final ArrayList<Expense> expenses;
    private final ArrayList<Expense> completeList;
    private Context context;
    private LinearLayout rootLayout;

    public ExpenseListAdapter(Context context, ArrayList<Expense> expenses, ArrayList<Expense> completeList) {
        this.completeList = completeList;
        this.expenses = expenses;
        this.context = context;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_expense_view, parent, false);
        rootLayout = (LinearLayout) rootView.findViewById(R.id.fragment_layout);
        return new ExpenseViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.categoryView.setImageDrawable(ContextCompat.getDrawable(context, expense.getCategory().getResourceId()));
        holder.timeView.setText(expense.getDateString());
        holder.amountView.setText(String.valueOf(expense.getAmount()));
        holder.descView.setText(expense.getDescription());
        setUpStateView(holder, expense);
        setOnClickListeners(holder,expense,position);
    }

    private void setUpStateView(ExpenseViewHolder holder, Expense expense) {
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
    }


    private void setOnClickListeners(ExpenseViewHolder holder, final Expense expense, final int position) {
        holder.fraudStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateState(expense,fraud,position);
            }
        });
        holder.verifiedStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateState(expense,verified, position);
            }
        });
        holder.unverifiedStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateState(expense,unverified, position);
            }
        });
    }

    private void updateState(Expense expense, StateOptions state, int position) {
        Result result = expense.getState().updateState(state);
        if(result.isSuccess()){
            updateExpenseList(position,expense);
            SyncAdapter.postDataImmediately(context,completeList);
            expenses.remove(position);
            notifyItemRemoved(position);
            Snackbar.make(rootLayout,R.string.successful_update,Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(rootLayout,result.getErrorMsg(),Snackbar.LENGTH_SHORT).show();
        }
    }

    private void updateExpenseList(int position, Expense expense) {
        int index = completeList.indexOf(expense);
        completeList.set(index,expense);
        expenses.set(position,expense);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, expenses.size());
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
