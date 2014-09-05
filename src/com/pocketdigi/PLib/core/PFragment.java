package com.pocketdigi.PLib.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pocketdigi.PLib.annotation.InjectAdapter;
import com.pocketdigi.PLib.annotation.ViewInjector;

/**
 * 支持Injector
 * onCreateView返回的View必须缓存,再次调用不要重新inflat，View注入需要
 * Created by fhp on 14-9-1.
 */
public abstract class PFragment extends Fragment{
    /**用于解析注解时保存Layout的id**/
    public int mLayoutId=0;
    View rootView;
    /**返回onCreateView缓存的rootView**/
    public final View getRootView(){
        return rootView;
    }
    InjectAdapter injectAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectAdapter=ViewInjector.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null)
        {
            rootView=inflater.inflate(mLayoutId,container,false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewInjector.findViewById(this,injectAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ViewGroup)rootView.getParent()).removeView(rootView);
    }
}