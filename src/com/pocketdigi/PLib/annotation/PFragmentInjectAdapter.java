package com.pocketdigi.PLib.annotation;

import com.pocketdigi.PLib.core.PFragment;
import com.pocketdigi.PLib.exception.InflatError;

import java.lang.reflect.Field;

/**
 * Created by fhp on 14-9-5.
 */
public class PFragmentInjectAdapter extends InjectAdapter{
    @Override
    public Object findViewValue(Object obj, int resId) {
        return ((PFragment)obj).getRootView().findViewById(resId);
    }

    @Override
    public Object findFragmentValue(Object obj, int resId) {
        return ((PFragment)obj).getFragmentManager().findFragmentById(resId);
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