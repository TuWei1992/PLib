package com.pocketdigi.PLib.annotation;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inflat Layout for Activity or Fragment
 * Created by fhp on 14-9-5.
 */
@Target(ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Inflat{
    @LayoutRes int value();
}
