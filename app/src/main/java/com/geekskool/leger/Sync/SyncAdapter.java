package com.geekskool.leger.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.geekskool.leger.ExpenseUtil;
import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.R;

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

/**
 * Created by manisharana on 2/1/17.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String BLOB_URL = "https://jsonblob.com/api/jsonBlob/2f0cc9ad-cbf2-11e6-b16a-61c5489cb3d0";
    private static final String EXPENSE = "expenses";
    private HttpsURLConnection connection;
    public static final int SYNC_INTERVAL = 60 * 5;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private String BROADCAST="received";


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize, false);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(SyncAdapter.class.getName(), "On Perform Sync");
        String result = "";
        try {
            URL url = new URL(BLOB_URL);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream stream = connection.getInputStream();
            if (stream != null)
                result = readStream(stream);
            postExpenses(getExpenseList(result));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

    }

    private void postExpenses(final ArrayList<Expense> expenseList) {
        Intent i = new Intent();
        i.setAction(BROADCAST);
        i.putParcelableArrayListExtra("expenses",expenseList);
        getContext().sendBroadcast(i);
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

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }


}
