/*
 * Copyright (C) 2017 Beijing Didi Infinity Technology and Development Co.,Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qyai.activityplugin.instrumentation;

import android.content.pm.ActivityInfo;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/11/7 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> sub Activity 的选择，找到对应的sub Activity <br>
 */
class StubActivityChooser {

    /**
     * 数目
     */
    public static final int MAX_COUNT_STANDARD = 1;
    public static final int MAX_COUNT_SINGLETOP = 8;
    public static final int MAX_COUNT_SINGLETASK = 8;
    public static final int MAX_COUNT_SINGLEINSTANCE = 8;

    /**
     * 由于Manifest里面注册的时候是写的
     * <p>
     * <activity
     * android:name=".A$1"
     * android:launchMode="standard"/>
     * <p>
     * 所以需要以Manifest里面的package的名称
     */
    public static final String corePackage = "com.example.activityplugin";

    public static final String STUB_ACTIVITY_STANDARD = "%s.A$%d";
    public static final String STUB_ACTIVITY_SINGLETOP = "%s.B$%d";
    public static final String STUB_ACTIVITY_SINGLETASK = "%s.C$%d";
    public static final String STUB_ACTIVITY_SINGLEINSTANCE = "%s.D$%d";

    public final int usedStandardStubActivity = 1;
    public int usedSingleTopStubActivity = 0;
    public int usedSingleTaskStubActivity = 0;
    public int usedSingleInstanceStubActivity = 0;

    private HashMap<String, String> mCachedStubActivity = new HashMap<>();

    /**
     * 获取对应的stub Activity，这里用了一种很巧妙的处理方式，框架一般认为不存在说超过8个特殊
     * 启动模式的Activity在运行，所以这里使用了%8的方式，8个用完就又从第一个开始
     *
     * @param className  要启动的origin Activity
     * @param launchMode 启动模式
     * @param theme      主题，需要靠这个来判断是不是透明的
     * @return 合适的stub Activity
     */
    public String getStubActivity(String className, int launchMode, Theme theme) {
        String stubActivity = mCachedStubActivity.get(className);
        if (stubActivity != null) {
            return stubActivity;
        }
        TypedArray array = theme.obtainStyledAttributes(new int[]{
                android.R.attr.windowIsTranslucent,
                android.R.attr.windowBackground
        });
        boolean windowIsTranslucent = array.getBoolean(0, false);
        array.recycle();
        Log.i("StubActivityInfo", "getStubActivity, is transparent theme : " + windowIsTranslucent);
        stubActivity = format(STUB_ACTIVITY_STANDARD, corePackage, usedStandardStubActivity);
        switch (launchMode) {
            case ActivityInfo.LAUNCH_MULTIPLE: {
                stubActivity = format(STUB_ACTIVITY_STANDARD, corePackage, usedStandardStubActivity);
                if (windowIsTranslucent) {
                    stubActivity = format(STUB_ACTIVITY_STANDARD, corePackage, 2);
                }
                break;
            }
            case ActivityInfo.LAUNCH_SINGLE_TOP: {
                usedSingleTopStubActivity = usedSingleTopStubActivity % MAX_COUNT_SINGLETOP + 1;
                stubActivity = format(STUB_ACTIVITY_SINGLETOP, corePackage, usedSingleTopStubActivity);
                break;
            }
            case ActivityInfo.LAUNCH_SINGLE_TASK: {
                usedSingleTaskStubActivity = usedSingleTaskStubActivity % MAX_COUNT_SINGLETASK + 1;
                stubActivity = format(STUB_ACTIVITY_SINGLETASK, corePackage, usedSingleTaskStubActivity);
                break;
            }
            case ActivityInfo.LAUNCH_SINGLE_INSTANCE: {
                usedSingleInstanceStubActivity = usedSingleInstanceStubActivity % MAX_COUNT_SINGLEINSTANCE + 1;
                stubActivity = format(STUB_ACTIVITY_SINGLEINSTANCE, corePackage, usedSingleInstanceStubActivity);
                break;
            }
            default:
                break;
        }
        mCachedStubActivity.put(className, stubActivity);
        return stubActivity;
    }

    public String format(String format, Object... objects) {
        return String.format(Locale.getDefault(), format, objects);
    }

}
