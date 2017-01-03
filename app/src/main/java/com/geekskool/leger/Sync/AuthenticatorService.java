package com.geekskool.leger.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by manisharana on 2/1/17.
 */
public class AuthenticatorService extends Service {

    private StubAuthenticator stubAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        stubAuthenticator = new StubAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stubAuthenticator.getIBinder();
    }
}
