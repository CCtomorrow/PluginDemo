package com.yong.plugin.dyhook;


import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <b>Project:</b> com.yong.plugin.dyhook <br>
 * <b>Create Date:</b> 2016/9/19 <br>
 * <b>Author:</b> Devin <br>
 * <b>Description:</b> HookHelper <br>
 */
public class HookHelper {

    /**
     * Context的startActivity最终是由ContextImpl实现的，
     * 调用ActivityThread成员的Instrumentation对象的execStartActivity方法
     */
    public static void hookContextStartActivity() throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        // 找到currentActivityThread静态函数
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        //currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数，直接拿到的就是ActivityThread对象
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        // 拿到Instrumentation对象从currentActivityThread里面
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 新建增加版本Instrumentation实例
        EvilInstrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        // 设置成增强版本的Instrumentation
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);
    }

}
