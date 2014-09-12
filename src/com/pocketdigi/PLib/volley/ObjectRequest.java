package com.pocketdigi.PLib.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;

/**
 * 结果直接转成对象的Request
 * Created by fhp on 14-9-12.
 */
public class ObjectRequest<T> extends Request<T> {
    ResponseListener<T> mListener;
    public ObjectRequest(int method, String url,ResponseListener listener) {
        super(method, url, null);
        this.mListener=listener;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Gson gson=new Gson();
            Class<T> tclass=(Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()
            T resultList=gson.fromJson(jsonString,tclass);
            return Response.success(resultList,HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onSuccess(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        mListener.onDeliverError(error);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        mListener.onNetWorkError(volleyError);
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void cancel() {
        super.cancel();
        mListener=null;
    }
}