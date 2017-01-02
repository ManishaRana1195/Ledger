package com.geekskool.leger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

import javax.net.ssl.HttpsURLConnection;

public class SplashScreen extends AppCompatActivity {

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        rootView = findViewById(android.R.id.content);
        new FetchExpenses().execute();
    }

    private Intent goToNext(ArrayList<Expense> expenses) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(ExpenseUtil.EXPENSES,expenses);
        return intent;
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
            if (isValid(expenses)) {
                Intent intent = goToNext(expenses);
                startActivity(intent);
                finish();
            }else {
                Snackbar.make(rootView,R.string.enable_to_fetch,Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FetchExpenses().execute();
                    }
                }).show();
            }
        }

        private boolean isValid(ArrayList<Expense> expenses) {
            return expenses != null && !expenses.isEmpty();
        }

    }


}