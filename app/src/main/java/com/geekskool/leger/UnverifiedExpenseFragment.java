package com.geekskool.leger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekskool.leger.Events.ExpensesEvent;
import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.Models.StateOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class UnverifiedExpenseFragment extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<Expense> expenseList=new ArrayList<>();
    private ArrayList<Expense> completeList=new ArrayList<>();
    private ExpenseListAdapter adapter;
    private EventBus bus = EventBus.getDefault();
    ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_expenses, container, false);
        bus.register(this);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_expense_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExpenseListAdapter(getActivity(), expenseList,completeList);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ExpensesEvent event){
        expenseList= ExpenseUtil.sortByDate(filterExpenses(event.getExpenses()));
        completeList = event.getExpenses();
        Log.i(UnverifiedExpenseFragment.class.getName(),"into fragment");
    }


    @Override
    public void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    private ArrayList<Expense> filterExpenses(ArrayList<Expense> expenses) {
        ArrayList<Expense> filtered = new ArrayList<>();
        for (Expense expense : expenses) {
            String stateName = StateOptions.unverified.name();
            if (stateName.equals(expense.getState().getStateOptions().name()))
                filtered.add(expense);
        }
        return filtered;
    }

}
