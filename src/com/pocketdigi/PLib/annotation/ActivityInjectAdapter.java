package com.pocketdigi.PLib.annotation;

import android.app.Activity;

/**
 * Created by fhp on 14-9-5.
 */
public class ActivityInjectAdapter extends InjectAdapter{
    @Override
    public Object findFieldValue(Object obj, int resId) {
        return ((Activity)obj).findViewById(resId);
    }

    @Override
    public void inflatLayout(Object obj, int layoutId) {
        ((Activity)obj).setContentView(layoutId);
    }
}