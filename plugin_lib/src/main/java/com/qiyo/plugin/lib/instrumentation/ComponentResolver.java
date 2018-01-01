package com.qiyo.plugin.lib.instrumentation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.qiyo.plugin.lib.manager.HookManager;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/11/6 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 组件解析 <br>
 */
public class ComponentResolver {

    private static final String TAG = "ComponentResolver";

    Context mContext;
    HookManager mHookManager;
    StubActivityChooser mActivityChooser = new StubActivityChooser();

    public ComponentResolver(HookManager manager) {
        mHookManager = manager;
        mContext = mHookManager.getHostContext();
    }

    /**
     * 隐式Intent转换成显示
     *
     * @param intent
     */
    public void implicitToExplicit(Intent intent) {
        ComponentName component = intent.getComponent();
        String hostPackName = mHookManager.getHostContext().getPackageName();
        if (component == null || component.getPackageName().equals(hostPackName)) {
            // TODO: 2017/11/7 隐式Intent的处理
        }
    }

    /**
     * Intent重新解析，可能需要加上标记，标记是一个需要替换的Activity
     *
     * @param intent
     */
    public void reMakeIntent(Intent intent) {
        if (intent.getComponent() == null) {
            return;
        }
        String targetPackageName = intent.getComponent().getPackageName();
        String targetClassName = intent.getComponent().getClassName();
        // 包名不是宿主的，并且能在插件列表找到对应包名的插件就打上是插件Activity的标记
        // 2017/11/7 插件Activity判断，这里做最简单的判断，如果不是主界面就认为是插件Activity
        if (!targetClassName.contains("MainActivity")) {
            intent.putExtra(Constants.KEY_IS_PLUGIN, true);
            intent.putExtra(Constants.KEY_TARGET_PACKAGE, targetPackageName);
            intent.putExtra(Constants.KEY_TARGET_ACTIVITY, targetClassName);
            dispatchStubActivity(intent);
        }
    }

    /**
     * 找出最合适的可以替换的Activity
     *
     * @param intent
     */
    private void dispatchStubActivity(Intent intent) {
        PackageManager pm = mContext.getPackageManager();
        ComponentName component = intent.getComponent();
        if (component == null) {
            return;
        }
        try {
            String targetClassName = component.getClassName();
            ActivityInfo activityInfo = pm.getActivityInfo(intent.getComponent(), 0);
            int launchMode = activityInfo.launchMode;
            String stubActivity = getStubActivity(targetClassName, launchMode);
            if (!TextUtils.isEmpty(stubActivity)) {
                Log.i(TAG, String.format("dispatchStubActivity,[%s -> %s]", targetClassName, stubActivity));
                intent.setClassName(mContext, stubActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getStubActivity(String targetClassName, int launchMode) {
        return mActivityChooser.getStubActivity(targetClassName, launchMode, mContext.getTheme());
    }

}
