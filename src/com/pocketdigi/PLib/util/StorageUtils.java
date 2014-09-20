package com.pocketdigi.PLib.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 存储util
 * Created by fhp on 14-9-19.
 */
public class StorageUtils {
    /**
     * 获取可用空间大小
     * @return
     */
    public static long getAvailableSize()
    {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long availableBlocks;
        if(Build.VERSION.SDK_INT>=18)
        {
            blockSize=stat.getBlockSizeLong();
            availableBlocks=stat.getAvailableBlocksLong();
        }else{
            blockSize=stat.getBlockSize();
            availableBlocks=stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

}