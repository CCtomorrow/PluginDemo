package com.example.activityplugin.manager;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Handler;

import com.example.activityplugin.hcallback.PluginHandlerH;
import com.example.activityplugin.instrumentation.PluginInstrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/10/26 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 在{@link android.app.Application#attachBaseContext(Context)}里面调用的 <br>
 */
public class HookManager {

    private static volatile HookManager sManager;

    private HookManager() {
    }

    public static HookManager getManager() {
        if (sManager == null) {
            synchronized (HookManager.class) {
                if (sManager == null) {
                    sManager = new HookManager();
                }
            }
        }
        return sManager;
    }

    /**
     * {@link android.app.ActivityThread} Class
     */
    private Class<?> mActivityThreadClass = null;
    /**
     * {@link android.app.ActivityThread}对象
     */
    private Object mActivityThread = null;
    /**
     * {@link Instrumentation}对象
     */
    private Instrumentation mInstrumentation = null;
    /**
     * {@link Instrumentation} Field
     */
    private Field mInstrumentationField = null;
    /**
     * {@link android.app.ActivityThread#mH}对象
     */
    private Handler mH = null;

    /**
     * 获取ActivityThread Class对象
     *
     * @return
     */
    public Class<?> getActivityThreadClass() {
        if (mActivityThreadClass != null) {
            return mActivityThreadClass;
        }
        try {
            mActivityThreadClass = Class.forName("android.app.ActivityThread");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mActivityThreadClass;
    }

    /**
     * 获取ActivityThread
     *
     * @return
     */
    public Object getActivityThread() {
        if (mActivityThread != null) {
            return mActivityThread;
        }
        Class<?> activityThreadClass = getActivityThreadClass();
        if (activityThreadClass != null) {
            try {
                // 通过反射调用 ActivityThread 的静态方法, 获取 currentActivityThread
                Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
                currentActivityThreadMethod.setAccessible(true);
                mActivityThread = currentActivityThreadMethod.invoke(null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mActivityThread;
    }

    /**
     * 获取 Instrumentation 的 Field
     *
     * @return
     */
    public Field getInstrumentationField() {
        if (mInstrumentationField != null) {
            return mInstrumentationField;
        }
        Class<?> activityThreadClass = getActivityThreadClass();
        if (activityThreadClass != null) {
            // 拿到原始的 mInstrumentation字段
            if (mInstrumentationField == null) {
                try {
                    mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return mInstrumentationField;
    }

    /**
     * 获取Instrumentation
     *
     * @return
     */
    public Instrumentation getInstrumentation() {
        if (mInstrumentation != null) {
            return mInstrumentation;
        }
        Class<?> activityThreadClass = getActivityThreadClass();
        if (activityThreadClass != null) {
            try {
                if (getInstrumentationField() != null) {
                    // 拿到原始的 mInstrumentation 字段
                    getInstrumentationField().setAccessible(true);
                    if (getActivityThread() != null) {
                        mInstrumentation = (Instrumentation) getInstrumentationField().get(getActivityThread());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mInstrumentation;
    }

    /**
     * 获取ActivityThread里面的Handler对象mH
     *
     * @return
     */
    public Handler getActivityThreadH() {
        if (mH != null) {
            return mH;
        }
        Class<?> activityThreadClass = getActivityThreadClass();
        if (activityThreadClass != null) {
            // 获取 currentActivityThread 这个示例中的 mH
            try {
                Field handlerField = activityThreadClass.getDeclaredField("mH");
                handlerField.setAccessible(true);
                Object currentActivityThread = getActivityThread();
                if (currentActivityThread != null) {
                    mH = (Handler) handlerField.get(currentActivityThread);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mH;
    }


    /*------------------------------下面是一系列的Replace方法------------------------------*/

    public void init() {
        replaceInstrumentation();
        replaceHandlerH();
    }

    /**
     * 替换{@link android.app.ActivityThread#mInstrumentation}对象为指定对象
     */
    public void replaceInstrumentation() {
        if (getInstrumentation() != null && !(getInstrumentation() instanceof PluginInstrumentation)) {
            PluginInstrumentation instrumentation = new PluginInstrumentation(getInstrumentation());
            if (getInstrumentationField() != null && getActivityThread() != null) {
                // 拿到原始的 mInstrumentation 字段
                getInstrumentationField().setAccessible(true);
                try {
                    getInstrumentationField().set(getActivityThread(), instrumentation);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 为{@link android.app.ActivityThread#mH}对象添加回调，即是修改
     * {@link Handler#mCallback}
     */
    public void replaceHandlerH() {
        // 修改 mH 中的 callback 字段
        try {
            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            if (getActivityThreadH() != null) {
                callbackField.set(getActivityThreadH(), new PluginHandlerH());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
