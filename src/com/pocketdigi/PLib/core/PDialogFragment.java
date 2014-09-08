package com.pocketdigi.PLib.core;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pocketdigi.PLib.annotation.InjectAdapter;
import com.pocketdigi.PLib.annotation.ViewInjector;

/**
 * DialogFragment基类
 * Created by fhp on 14-9-5.
 */
public class PDialogFragment extends DialogFragment {
    /**用于解析注解时保存Layout的id**/
    public int mLayoutId=0;
    View rootView;
    /**返回onCreateView缓存的rootView**/
    public final View getRootView(){
        return rootView;
    }
    InjectAdapter injectAdapter;
    boolean isFirstCreate=true;
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
        if(isFirstCreate){
            ViewInjector.findViewById(this,injectAdapter);
            isFirstCreate=false;
        }
        registerListenerOrReceiver();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterListerOrReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ViewGroup)rootView.getParent()).removeView(rootView);
    }

    /**
     * 注册监听器以及接收器(包括Event),在Fragment被隐藏或销毁时，会调用unregisterListerOrReceiver
     */
    protected void registerListenerOrReceiver(){};
    /**解注册监听器及接收器(包括Event)**/
    protected void unregisterListerOrReceiver(){};
}