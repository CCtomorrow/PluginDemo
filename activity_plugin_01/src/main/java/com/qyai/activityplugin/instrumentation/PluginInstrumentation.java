package com.qyai.activityplugin.instrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/10/25 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Hook替换掉系统的{@link Instrumentation}，一个App里面的Instrumentation对象是同一个 <br>
 */
public class PluginInstrumentation extends Instrumentation {

    private Instrumentation mOrigin;

    private static final String RESOURCES_PACKAGE_NAME = "com.example.activityplugin";

    public PluginInstrumentation(Instrumentation origin) {
        this.mOrigin = origin;
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
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity", Context.class, IBinder.class, IBinder.class,
                    Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            // TODO: 2017/10/27 这里把intent里面的class替换成Manifest里面注册的
            reWarpIntent(who, intent);
            return (ActivityResult) execStartActivity.invoke(mOrigin,
                    who, contextThread, token, target, intent, requestCode, options);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对Intent做一些修改
     *
     * @param context
     * @param intent
     */
    private void reWarpIntent(Context context, Intent intent) {
        // TODO: 2017/10/27 判断
        if (!intent.getComponent().getClassName().contains("MainActivity")) {
            intent.setClassName(context.getPackageName(), RESOURCES_PACKAGE_NAME + ".A$1");
        }
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
        // TODO: 2017/10/27 替换className为真正需要启动的class
        String targetClass = className;
        if (!className.contains("MainActivity")) {
            targetClass = RESOURCES_PACKAGE_NAME + ".PluginSampleActivity";
        }
        return mOrigin.newActivity(cl, targetClass, intent);
    }

}
