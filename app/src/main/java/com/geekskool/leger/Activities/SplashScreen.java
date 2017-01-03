package com.geekskool.leger.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.geekskool.leger.Events.ExpensesEvent;
import com.geekskool.leger.ExpenseUtil;
import com.geekskool.leger.Models.Expense;
import com.geekskool.leger.R;
import com.geekskool.leger.Sync.SyncAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    private View rootView;
    private UpdateReceiver updateReceiver;
    private EventBus bus = EventBus.getDefault();
    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        rootView = findViewById(android.R.id.content);
        updateReceiver = new UpdateReceiver();
        SyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(updateReceiver, new IntentFilter(ExpenseUtil.BROADCAST));
    }

    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.i(SplashScreen.class.getName(),"Got expenses");

            ArrayList<Expense> expenses = intent.getParcelableArrayListExtra("expenses");
            if (ExpenseUtil.isValid(expenses)) {
                bus.postSticky(new ExpensesEvent(expenses));
                getToNext(expenses);
                finish();
            } else {
                Snackbar.make(rootView, R.string.enable_to_fetch, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       SyncAdapter.syncImmediately(context);
                    }
                }).show();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(updateReceiver);
    }

    @NonNull
    private void getToNext(ArrayList<Expense> expenses) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(ExpenseUtil.EXPENSES,expenses);
        startActivity(intent);
    }




}