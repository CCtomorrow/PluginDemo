package com.example.activityplugin;

import android.content.Context;
import android.util.Log;

import com.qiyo.plugin.lib.manager.HookManager;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/10/27 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class Application extends android.app.Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        long start = System.currentTimeMillis();
        HookManager.getManager(this).init();
        Log.i("qy", "hook use time:" + (System.currentTimeMillis() - start));
    }
}
