package com.yong.plugin;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yong.plugin.dyhook.BaseActivity;
import com.yong.plugin.dyhook.HookHelper;

public class MainActivity extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            HookHelper.hookContextStartActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void contextStartAct(View view) {
        Application application = getApplication();
        Intent intent = new Intent(this, LaunchedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }

    public void activityStartAct(View view) {
        Intent intent = new Intent(this, LaunchedActivity.class);
        startActivity(intent);
    }

}
