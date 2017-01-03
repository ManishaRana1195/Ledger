package com.geekskool.leger.Activities;

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

import com.geekskool.leger.FraudExpenseFragment;
import com.geekskool.leger.R;
import com.geekskool.leger.UnverifiedExpenseFragment;
import com.geekskool.leger.VerifiedExpenseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FraudExpenseFragment(), "");
        adapter.addFragment(new UnverifiedExpenseFragment(), "");
        adapter.addFragment(new VerifiedExpenseFragment(), "");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    public void viewDashboard(MenuItem item) {
        Intent intent = new Intent(this, DashboardActivity.class);
 //       intent.putParcelableArrayListExtra(ExpenseUtil.EXPENSES,expenseList);
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
