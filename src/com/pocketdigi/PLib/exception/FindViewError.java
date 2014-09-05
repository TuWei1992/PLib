package com.pocketdigi.PLib.exception;

import java.lang.reflect.Field;

/**
 * FindView异常
 * Created by fhp on 14-9-5.
 */
public class FindViewError extends Error{

    public FindViewError(Field field)
    {
        super("findView for"+field.getName()+" error,can't find the resource");
    }
}