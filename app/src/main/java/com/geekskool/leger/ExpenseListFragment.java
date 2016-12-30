package com.geekskool.leger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekskool.leger.Models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Expense> expenses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_expenses, container, false);
        ArrayList<Expense> expenseList = getArguments().getParcelableArrayList(MainActivity.EXPENSES);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_expense_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ExpenseListAdapter(getActivity(),expenseList));
        return root;
    }

}
