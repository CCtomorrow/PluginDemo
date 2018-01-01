package com.qiyo.plugin.lib.instrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.qiyo.plugin.lib.manager.HookManager;

import java.lang.reflect.Method;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/10/25 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Hook替换掉系统的{@link Instrumentation}，一个App里面的Instrumentation对象是同一个 <br>
 */
public class PluginInstrumentation extends Instrumentation {

    private static final String TAG = "PluginInstrumentation";

    private Instrumentation mOrigin;
    private HookManager mHookManager;

    public PluginInstrumentation(Instrumentation origin, HookManager manager) {
        this.mOrigin = origin;
        this.mHookManager = manager;
    }

    /**
     * <p>This method throws {@link android.content.ActivityNotFoundException}
     * if there was no Activity found to run the given Intent.
     *
     * @param who           The Context from which the activity is being started.
     * @param contextThread The main thread of the Context from which the activity
     *                      is being started.
     * @param token         Internal token identifying to the system who is starting
     *                      the activity; may be null.
     * @param target        Which activity is performing the start (and thus receiving
     *                      any result); may be null if this call is not being made
     *                      from an activity.
     * @param intent        The actual Intent to start.
     * @param requestCode   Identifier for this request's result; less than zero
     *                      if the caller is not expecting a result.
     * @param options       Addition options.
     * @return To force the return of a particular result, return an
     * ActivityResult object containing the desired data; otherwise
     * return null.  The default implementation always returns null.
     * @throws android.content.ActivityNotFoundException
     * @see Activity#startActivity(Intent)
     * @see Activity#startActivityForResult(Intent, int)
     * @see Activity#startActivityFromChild
     * <p>
     */
    @Override
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        // 隐式Intent的转换
        mHookManager.getComponentResolver().implicitToExplicit(intent);
        // Intent的重新解析
        if (intent.getComponent() != null) {
            mHookManager.getComponentResolver().reMakeIntent(intent);
        }
        // 由于这个方法是隐藏的,因此需要使用反射调用,首先找到这个方法
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity", Context.class, IBinder.class, IBinder.class,
                    Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mOrigin,
                    who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Perform instantiation of the process's {@link Activity} object.  The
     * default implementation provides the normal system behavior.
     *
     * @param cl        The ClassLoader with which to instantiate the object.
     * @param className The name of the class implementing the Activity
     *                  object.
     * @param intent    The Intent object that specified the activity class being
     *                  instantiated.
     * @return The newly instantiated Activity object.
     */
    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // 2017/10/27 替换className为真正需要启动的class
        String realClass = intent.getStringExtra(Constants.KEY_TARGET_ACTIVITY);
        if (!TextUtils.isEmpty(realClass)) {
            Log.i(TAG, String.format("newActivity[%s : %s]", className, realClass));
            Activity activity = mOrigin.newActivity(cl, realClass, intent);
            activity.setIntent(intent);
            return activity;
        }
        return mOrigin.newActivity(cl, className, intent);
    }

}
