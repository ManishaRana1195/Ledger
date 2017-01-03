package com.geekskool.leger.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by manisharana on 2/1/17.
 */
public class SyncService extends Service {

    private SyncAdapter syncAdapter = null;
    private static final Object lock=new Object();

    public SyncService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (lock) {
            if (syncAdapter == null)
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
