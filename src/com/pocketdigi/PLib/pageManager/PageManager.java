package com.pocketdigi.PLib.pageManager;

import android.os.Bundle;
import com.pocketdigi.PLib.core.PFragment;

/**
 * Page管理器
 * Created by fhp on 14-9-2.
 */
public class PageManager {

    private static PageManager instance;
    private PageManager(){};
    public static PageManager getInstance()
    {
        if(instance==null)
            instance=new PageManager();
        return instance;
    }
    public void showPage(Class<? extends Page> pageClass,Bundle args)
    {
        if(pageClass.isAssignableFrom(PFragment.class))
        {
            try {
                PFragment fragment=(PFragment)pageClass.newInstance();
                showPage(fragment,args);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
    public void showPage(Page page,Bundle args)
    {

    }
}