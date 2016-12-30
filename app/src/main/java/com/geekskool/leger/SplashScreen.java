package com.geekskool.leger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import javax.net.ssl.HttpsURLConnection;

public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new FetchExpenses().execute();
    }

    private Intent goToNext(ArrayList<Expense> expenses) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(MainActivity.EXPENSES,expenses);
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
            if (!expenses.isEmpty()) {
                Intent intent = goToNext(expenses);
                startActivity(intent);
                finish();
                //create intent else show error
            }
        }

    }


}