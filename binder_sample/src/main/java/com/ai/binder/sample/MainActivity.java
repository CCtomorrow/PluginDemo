package com.ai.binder.sample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getResources();
        String name = resources.getString(R.string.app_name);
        try {
            getAssets().open("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LayoutInflater layoutInflater = getLayoutInflater();
        // layoutInflater.setFactory();
        // layoutInflater.setFactory2();
        // getSystemService("");
        bindService(new Intent(), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, 0);
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }

    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
    }
}