package com.pocketdigi.PLib.annotation;

/**
 * 适配器
 * Created by fhp on 14-9-5.
 */
public abstract class InjectAdapter {
    public abstract Object findViewValue(Object obj, int resId);
    public abstract Object findFragmentValue(Object obj, int resId);
    public abstract void inflatLayout(Object obj, int layoutId);
}
