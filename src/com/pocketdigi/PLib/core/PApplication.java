package com.pocketdigi.PLib.core;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;

/**
 * Application基类
 * Created by fhp on 14-8-28.
 */
public abstract class PApplication extends Application{
    private static PApplication instance;
    private ArrayList<Activity> activities;
    private ArrayList<Service> services;
    public static PApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activities=new ArrayList<Activity>();
        services=new ArrayList<Service>();
        instance=this;
    }

    void activityCreate(Activity activity)
    {
        activities.add(activity);
    }
    void serviceCreate(Service service)
    {
        services.add(service);
    }
    void activityDestory(Activity activity)
    {
        activities.remove(activity);
    }
    void serviceDestory(Service service)
    {
        services.remove(service);
    }

    /**
     * 退出所有Activity
     */
    public void destoryAllActivity()
    {
        for (Activity activity:activities)
        {
            activity.finish();
        }
        activities.clear();
    }

    /**
     * 退出整个应用,会关闭Activity和Service
     */
    public void exit()
    {
        destoryAllActivity();
        destoryAllService();
        System.exit(0);
    }

    public void destoryAllService()
    {
        for (Service service:services)
        {
            service.stopSelf();
        }
        services.clear();
    }

    /**
     * 注册事件接收者，封装是为了以后使用其他事件总线
     * @param obj
     */
    public void registerEventSubscriber(Object obj)
    {
        EventBus.getDefault().register(obj);
    }
    public void unregisterEventSubscriber(Object obj)
    {
        EventBus.getDefault().unregister(obj);
    }
    public void postEvent(PEvent event)
    {
        EventBus.getDefault().post(event);
    }


}