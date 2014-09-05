package com.pocketdigi.PLib.annotation;

/**
 * 适配器
 * Created by fhp on 14-9-5.
 */
public abstract class InjectAdapter {
    public abstract Object findFieldValue(Object obj, int resId);
    public abstract void inflatLayout(Object obj, int layoutId);
}
