package com.yong.plugin;

import android.os.Bundle;

import com.yong.plugin.dyhook.BaseActivity;

public class LaunchedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launched);
    }
}
