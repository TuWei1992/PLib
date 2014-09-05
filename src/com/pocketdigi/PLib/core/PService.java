package com.pocketdigi.PLib.core;

import android.app.Service;

/**
 * Service基类
 * Created by fhp on 14-9-5.
 */
public abstract class PService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        PApplication.getInstance().serviceCreate(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PApplication.getInstance().serviceDestory(this);
    }
}