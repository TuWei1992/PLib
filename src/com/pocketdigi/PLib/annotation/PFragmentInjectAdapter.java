package com.pocketdigi.PLib.annotation;

import android.support.v4.app.Fragment;
import com.pocketdigi.PLib.core.PDialogFragment;
import com.pocketdigi.PLib.core.PFragment;
import com.pocketdigi.PLib.exception.InflatError;

import java.lang.reflect.Field;

/**
 * Created by fhp on 14-9-5.
 */
public class PFragmentInjectAdapter extends InjectAdapter{
    @Override
    public Object findViewValue(Object obj, int resId) {
        if(PFragment.class.isAssignableFrom(obj.getClass()))
            return ((PFragment)obj).getRootView().findViewById(resId);
        else if(PDialogFragment.class.isAssignableFrom(obj.getClass()))
            return ((PDialogFragment)obj).getRootView().findViewById(resId);
        return null;
    }

    @Override
    public Object findFragmentValue(Object obj, int resId) {
        return ((Fragment)obj).getFragmentManager().findFragmentById(resId);
    }

    @Override
    public void inflatLayout(Object obj, int layoutId) {
        try {
            Field layoutIdField = obj.getClass().getField("mLayoutId");
            layoutIdField.set(obj, layoutId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InflatError("inflat layout for " + obj.getClass().getSimpleName() + " error");
        }
    }
}