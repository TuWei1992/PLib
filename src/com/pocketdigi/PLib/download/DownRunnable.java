package com.pocketdigi.PLib.download;

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

    public DownRunnable(DownTask task, DownloadListener listener) {
        this.task = task;
        this.listener = listener;

    }

    @Override
    public void run() {
        if (task.isCancel()) {
            task.setState(DownTask.STATE_CANCELED);
            listener.onCancel(task);
            return;
        }
        task.setState(DownTask.STATE_DOWNING);
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream=null;
        try {
            URL url = new URL(task.getUrl());
            fileOutputStream = new FileOutputStream(task.getSavePath());
            connection = openConnection(url);
            long remoteFileSize = connection.getContentLength();
            task.setFileSize(remoteFileSize);
            inputStream = connection.getInputStream();
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            long downloadedSize = 0;
            while ((length = inputStream.read(buffer, 0, bufferSize)) > 0 && !task.isCancel()) {
                downloadedSize += length;
                fileOutputStream.write(buffer, 0, length);
                task.setDownloadedSize(downloadedSize);
            }
            if (task.isCancel()) {
                task.setState(DownTask.STATE_CANCELED);
                listener.onCancel(task);
                return;
            } else {
                task.setState(DownTask.STATE_SUCCESS);
                listener.onCancel(task);
                return;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            task.setState(DownTask.STATE_FAIL);
            listener.onFail(task);
        } catch (IOException e) {
            e.printStackTrace();
            task.setState(DownTask.STATE_FAIL);
            listener.onFail(task);
        } finally {
            if (connection != null)
                connection.disconnect();
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(fileOutputStream!=null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

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