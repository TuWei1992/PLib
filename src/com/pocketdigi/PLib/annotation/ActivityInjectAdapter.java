package com.pocketdigi.PLib.annotation;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import com.pocketdigi.PLib.core.PActivity;
import com.pocketdigi.PLib.core.PFragmentActivity;

/**
 * Created by fhp on 14-9-5.
 */
public class ActivityInjectAdapter extends InjectAdapter {
    @Override
    public Object findViewValue(Object obj, int resId) {
        return ((Activity) obj).findViewById(resId);
    }

    @Override
    public Object findFragmentValue(Object obj, int resId) {
        if (PFragmentActivity.class.isAssignableFrom(obj.getClass())) {
            return ((FragmentActivity) obj).getSupportFragmentManager().findFragmentById(resId);
        }
        if (PActivity.class.isAssignableFrom(obj.getClass()))
        {
            if(Build.VERSION.SDK_INT>=11)
            return ((PActivity) obj).getFragmentManager().findFragmentById(resId);
        }

        return null;

    }

    @Override
    public void inflatLayout(Object obj, int layoutId) {
        ((Activity) obj).setContentView(layoutId);
    }
}