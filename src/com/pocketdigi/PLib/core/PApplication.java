package com.pocketdigi.PLib.core;

import android.app.Application;

/**
 * Application基类
 * Created by fhp on 14-8-28.
 */
public class PApplication extends Application{
    private static PApplication instance;
    public static PApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}