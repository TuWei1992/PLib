package com.pocketdigi.PLib.download;

import android.os.Handler;
import android.os.Looper;
import com.pocketdigi.PLib.util.FileUtils;
import com.pocketdigi.PLib.util.StorageUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 下载Runnable
 * Created by fhp on 14-9-17.
 */
public class DownRunnable implements Runnable {
    DownTask task;
    DownloadListener listener;
    static Handler handler;
    String tmpFilePath;
    public DownRunnable(DownTask task, DownloadListener listener) {
        this.task = task;
        this.listener = listener;
        tmpFilePath=task.getSavePath()+".tmp";
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        if (task.isCancel()) {
            task.setState(DownTask.STATE_CANCELED);
            listener.onCancel(task);
            return;
        }
        task.setState(DownTask.STATE_DOWNING);

        if (StorageUtils.getAvailableSize() < 50 * 1024 * 1024) {
            taskFailure(DownloadListener.ERROR_CODE_DISK_FULL);
            return;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(task.getUrl());
            fileOutputStream = new FileOutputStream(tmpFilePath);
            connection = openConnection(url);
            long remoteFileSize = connection.getContentLength();
            task.setFileSize(remoteFileSize);
            inputStream = connection.getInputStream();
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            long downloadedSize = 0;
            long t1 = System.currentTimeMillis();
            while ((length = inputStream.read(buffer, 0, bufferSize)) > 0 && !task.isCancel()) {
                downloadedSize += length;
                fileOutputStream.write(buffer, 0, length);
                task.setDownloadedSize(downloadedSize);
                long t2 = System.currentTimeMillis();
                //每秒发送一次进度改变事件
                if (t2 - t1 > 1000) {
                    t1 = t2;
                    taskProgressChanged();
                }

            }
            if (task.isCancel()) {
                taskCancel();
                return;
            } else {
                taskSuccess();
                return;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

            task.setState(DownTask.STATE_FAIL);
            taskFailure(DownloadListener.ERROR_CODE_OTHER);
        } catch (IOException e) {
            e.printStackTrace();
            task.setState(DownTask.STATE_FAIL);
            taskFailure(DownloadListener.ERROR_CODE_IO);
        } finally {
            if (connection != null)
                connection.disconnect();
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    private void taskFailure(final int errorCode) {
        FileUtils.deleteFile(tmpFilePath);
        handler.post(new Runnable() {
            @Override
            public void run() {
                task.setState(DownTask.STATE_FAIL);
                listener.onFail(task,errorCode);
            }
        });
    }

    private void taskSuccess() {
        FileUtils.rename(tmpFilePath,task.getSavePath());
        handler.post(new Runnable() {
            @Override
            public void run() {
                task.setState(DownTask.STATE_SUCCESS);
                listener.onComplete(task);
            }
        });
    }

    private void taskCancel() {
        //删除临时文件
        FileUtils.deleteFile(tmpFilePath);
        handler.post(new Runnable() {
            @Override
            public void run() {
                task.setState(DownTask.STATE_CANCELED);
                listener.onCancel(task);
            }
        });
    }

    private void taskProgressChanged() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onProgressChanged(task);
            }
        });
    }

    private HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }


    private HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection connection = createConnection(url);
        int timeoutMs = DownloadManager.getInstance().getTimeout();
        connection.setConnectTimeout(timeoutMs);
        connection.setReadTimeout(timeoutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        return connection;
    }
}