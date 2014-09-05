package com.pocketdigi.PLib.core;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity基类
 * Created by fhp on 14-9-2.
 */
public class PActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PApplication.getInstance().activityCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PApplication.getInstance().activityDestory(this);
    }
}