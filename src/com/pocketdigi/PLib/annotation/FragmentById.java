package com.pocketdigi.PLib.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fhp on 14-9-5.
 */
@Target(ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface FragmentById {
    @IdRes int value();
}
