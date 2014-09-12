package com.pocketdigi.PLib.volley;

import com.android.volley.VolleyError;

/**
 * Created by fhp on 14-9-12.
 */
public interface ResponseListener<T> {
    public void onSuccess(T response);
    public void onDeliverError(VolleyError error);
    public void onNetWorkError(VolleyError error);
}