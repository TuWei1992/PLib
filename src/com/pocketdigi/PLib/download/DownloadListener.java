package com.pocketdigi.PLib.download;

/**
 * Created by fhp on 14-9-17.
 */
public interface DownloadListener {
    /**下载开始**/
    public void onStart(DownTask task);
    /**进度改变**/
    public void onProgressChanged(DownTask task);
    /**下载失败**/
    public void onFail(DownTask task);
    /**下载完成**/
    public void onComplete(DownTask task);
    /**任务取消**/
    public void onCancel(DownTask task);
}