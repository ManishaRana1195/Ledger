package com.geekskool.leger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.Models.StateOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        expenseList = intent.getParcelableArrayListExtra(ExpenseUtil.EXPENSES);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager();
        setUpTablayoutView();
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        if (tab != null)
            tab.select();

    }

    private void setUpTablayoutView() {
        int[] tabIcons = {R.drawable.ic_fraud, R.drawable.ic_unverified, R.drawable.ic_verified};
        for (int i = 0; i < tabIcons.length; i++)
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
    }

    private void setupViewPager() {
        ExpenseUtil.sortByDate(expenseList);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        HashMap<String, ArrayList<Expense>> map = categorizeExpenses();
        ExpenseListFragment fraudFragment = getFragment(map.get(StateOptions.fraud.name()));
        ExpenseListFragment unverifiedFragment = getFragment(map.get(StateOptions.unverified.name()));
        ExpenseListFragment verifiedFragment = getFragment(map.get(StateOptions.verified.name()));

        adapter.addFragment(fraudFragment, "");
        adapter.addFragment(unverifiedFragment, "");
        adapter.addFragment(verifiedFragment, "");
        viewPager.setAdapter(adapter);
    }

    private ExpenseListFragment getFragment(ArrayList<Expense> expenses) {
        ExpenseListFragment fragment = new ExpenseListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ExpenseUtil.EXPENSES,expenses);
        fragment.setArguments(bundle);
        return fragment;
    }

    private HashMap<String, ArrayList<Expense>> categorizeExpenses(){
        final HashMap<String, ArrayList<Expense>> stateBasedMap = new HashMap<>();
        for(Expense expense:expenseList){
            String stateName = expense.getState().getStateOptions().name();
            ArrayList<Expense> expenses = stateBasedMap.get(stateName);
            if(expenses == null)
                expenses = new ArrayList<>();
            expenses.add(expense);
            stateBasedMap.put(stateName,expenses);
        }
        return stateBasedMap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    public void viewDashboard(MenuItem item) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putParcelableArrayListExtra(ExpenseUtil.EXPENSES,expenseList);
        startActivity(intent);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }




}
