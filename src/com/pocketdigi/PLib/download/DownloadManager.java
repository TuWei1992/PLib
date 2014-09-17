package com.pocketdigi.PLib.download;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 下载管理器，需要使用Service来统一管理
 * 不会存储下载状态，暂停需要重新addTask
 * 请自行注册监听器保存状态
 * 使用方法，DownloadManager.getInstance().addTask(task),在下载状态和进度改变时，DownloadListener会接收到回调，但没改变不会发通知
 * 所以，需要调用者在Service中，getTaskList(),addListener(),再根据listener的回调，修改下载数据库
 * Created by fhp on 14-9-16.
 */
public class DownloadManager implements DownloadListener{
    private static DownloadManager instance;
    BlockingQueue<Runnable> mDownWorkQueue = new LinkedBlockingQueue<Runnable>();
    int corePoolSize=1;
    int maximumPoolSize=2;
    AtomicInteger atomicInteger=new AtomicInteger();
    ExecutorService executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS, mDownWorkQueue);
    HashSet<DownloadListener> listeners=new HashSet<DownloadListener>();
    ArrayList<DownTask> taskList=new ArrayList<DownTask>();
    private int timeout=5000;

    public static DownloadManager getInstance()
    {
        if(instance!=null)
            instance=new DownloadManager();
        return instance;
    }

    private DownloadManager()
    {
    }

    public DownTask addTask(DownTask task)
    {
        task.setState(DownTask.STATE_WAITING);
        task.setId(atomicInteger.incrementAndGet());
        DownRunnable runnable=new DownRunnable(task,this);
        if(task.isBlock())
        {
            executor.submit(runnable);
        }else{
            new Thread(runnable).start();
        }
        taskList.add(task);
        return task;
    }

    public ArrayList<DownTask> getTaskList() {
        return taskList;
    }

    /**
     * 注册监听器
     * @param listener
     */
    public void addListener(DownloadListener listener)
    {
        listeners.add(listener);
    }

    /**
     * 移除监听器
     * @param listener
     */
    public void removeListener(DownloadListener listener)
    {
        listeners.remove(listener);
    }

    public void removeAllListener()
    {
        listeners.clear();
    }

    @Override
    public void onStart(DownTask task) {
        for(DownloadListener listener:listeners)
        {
            listener.onStart(task);
        }
    }

    @Override
    public void onProgressChanged(DownTask task) {
        for(DownloadListener listener:listeners)
        {
            listener.onProgressChanged(task);
        }
    }

    @Override
    public void onFail(DownTask task) {
        taskList.remove(task);
        for(DownloadListener listener:listeners)
        {
            listener.onFail(task);
        }
    }

    @Override
    public void onComplete(DownTask task) {
        taskList.remove(task);
        for(DownloadListener listener:listeners)
        {
            listener.onComplete(task);
        }
    }

    @Override
    public void onCancel(DownTask task) {
        taskList.remove(task);
        for(DownloadListener listener:listeners)
        {
           listener.onCancel(task);
        }
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void cancelAllTasks()
    {
        for(DownTask task:taskList)
        {
            task.cancel();
        }
    }

    public void destory()
    {
        cancelAllTasks();
        executor.shutdownNow();
        taskList.clear();
        removeAllListener();
        instance=null;

    }
}