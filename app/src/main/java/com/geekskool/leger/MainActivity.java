package com.geekskool.leger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.geekskool.leger.Models.Expense;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchExpenses().execute();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

    }

    private void setUpTablayoutView() {
        int[] tabIcons = {R.drawable.ic_fraud, R.drawable.ic_unverified, R.drawable.ic_verified};
        for (int i = 0; i < tabIcons.length; i++)
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
    }

    private void setupViewPager(ArrayList<Expense> expenseList) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ExpenseListFragment.EXPENSES,expenseList);

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

    public class FetchExpenses extends AsyncTask<Void, Void, ArrayList<Expense>> {
        private static final String BLOB_URL = "https://jsonblob.com/api/jsonBlob/2f0cc9ad-cbf2-11e6-b16a-61c5489cb3d0";
        private static final String EXPENSE = "expenses";
        private HttpsURLConnection connection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Expense> doInBackground(Void... params) {
            String result = "";
            try {
                URL url = new URL(BLOB_URL);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream stream = connection.getInputStream();
                if (stream != null)
                    result = readStream(stream);

                return getExpenseList(result);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;
        }

        private ArrayList<Expense> getExpenseList(String result) throws JSONException {
            ArrayList<Expense> expenseList = new ArrayList<Expense>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray(EXPENSE);
            for (int i = 0; i < jsonArray.length(); i++)
                expenseList.add(ExpenseUtil.getExpenseObject(jsonArray.getJSONObject(i)));

            return expenseList;
        }

        private String readStream(InputStream stream) throws IOException {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            StringBuilder input = new StringBuilder();
            while ((line = br.readLine()) != null)
                input.append(line);

            return input.toString();
        }

        @Override
        protected void onPostExecute(ArrayList<Expense> expenses) {
            super.onPostExecute(expenses);
            if (!expenses.isEmpty()) {
                tabLayout.setupWithViewPager(viewPager);
                setupViewPager(ExpenseUtil.sortByDate(expenses));
                setUpTablayoutView();
            }
        }
    }

}
