package com.yong.plugin.dyhook;

import android.app.Instrumentation;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Field;

/**
 * <b>Project:</b> com.yong.plugin.dyhook <br>
 * <b>Create Date:</b> 2016/9/19 <br>
 * <b>Author:</b> Devin <br>
 * <b>Description:</b>  <br>
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        try {
            Class<?> activityClass = Class.forName("android.app.Activity");
            // 拿到原始的 mInstrumentation字段
            Field mInstrumentationField = activityClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            // 拿到Instrumentation对象从currentActivityThread里面
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(this);
            // 新建增加版本Instrumentation实例
            EvilInstrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
            // 设置成增强版本的Instrumentation
            mInstrumentationField.set(this, evilInstrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
