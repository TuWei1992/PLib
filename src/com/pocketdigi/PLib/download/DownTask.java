package com.pocketdigi.PLib.download;

/**
 * Created by fhp on 14-9-16.
 */
public class DownTask {
    public static final int STATE_DOWNING=1;
    public static final int STATE_WAITING=2;
    public static final int STATE_FAIL=3;
    public static final int STATE_SUCCESS=4;
    public static final int STATE_CANCELED=5;
    /**自动生成的ID**/
    int id;
    long fileSize;
    long downloadedSize;
    String url;
    String savePath;
    int state;
    /**是否阻塞，如果阻塞，共享线程池，需要等之前的任务执行完**/
    boolean isBlock=true;

    boolean cancel;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void cancel() {
        this.cancel = true;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isBlock() {
        return isBlock;
    }
    /**
     * 是否阻塞，如果阻塞，共享线程池，需要等之前的任务执行完
     * 默认阻塞，建议仅当下载升级APK之类的需要独立其他下载任务的时候设置为不阻塞
     **/
    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    @Override
    public boolean equals(Object o) {
        if(o==this)
            return true;
        if(o instanceof DownTask)
        {
            DownTask other=(DownTask)o;
            if(other.getId()==this.getId())
                return true;
            if(other.getUrl().equals(this.getUrl()))
                return true;
        }
        return false;
    }
}