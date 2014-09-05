package com.pocketdigi.PLib.core;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.pocketdigi.PLib.annotation.ViewInjector;

/**
 *
 * Created by fhp on 14-9-1.
 */
public abstract class PFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PApplication.getInstance().activityCreate(this);
        ViewInjector.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PApplication.getInstance().activityDestory(this);
    }

}