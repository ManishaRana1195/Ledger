package com.geekskool.leger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.geekskool.leger.Models.Expense;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXPENSES = "expenses";
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        ArrayList<Expense> expenses = intent.getParcelableArrayListExtra(EXPENSES);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(ExpenseUtil.sortByDate(expenses));
        setUpTablayoutView();

    }

    private void setUpTablayoutView() {
        int[] tabIcons = {R.drawable.ic_fraud, R.drawable.ic_unverified, R.drawable.ic_verified};
        for (int i = 0; i < tabIcons.length; i++)
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
    }

    private void setupViewPager(ArrayList<Expense> expenseList) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXPENSES,expenseList);

        ExpenseListFragment fragment1 = new ExpenseListFragment();
        ExpenseListFragment fragment2 = new ExpenseListFragment();
        ExpenseListFragment fragment3 = new ExpenseListFragment();

        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);

        adapter.addFragment(fragment1, "");
        adapter.addFragment(fragment2, "");
        adapter.addFragment(fragment3, "");
        viewPager.setAdapter(adapter);
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
