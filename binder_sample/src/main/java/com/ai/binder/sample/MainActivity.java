package com.ai.binder.sample;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView tv1, tv2, tv3;
    private TextView tv4, tv5, tv6;

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
//        bindService(new Intent(), new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        }, 0);

        tv1 = (TextView) findViewById(R.id.c1);
        tv2 = (TextView) findViewById(R.id.c2);
        tv3 = (TextView) findViewById(R.id.c3);

        tv4 = (TextView) findViewById(R.id.c4);
        tv5 = (TextView) findViewById(R.id.c5);
        tv6 = (TextView) findViewById(R.id.c6);

        tv1.setText(tv1.getText() + getClassLoader().toString());
        tv2.setText(tv2.getText() + getClassLoader().getParent().toString());
        ClassLoader c3 = getClassLoader().getParent().getParent();
        if (c3 == null) {
            tv3.setText(tv3.getText() + "null");
        } else {
            tv3.setText(tv3.getText() + c3.toString());
        }

        tv4.setText(tv4.getText() + ClassLoader.getSystemClassLoader().toString());
        ClassLoader c5 = ClassLoader.getSystemClassLoader().getParent();
        if (c5 == null) {
            tv5.setText(tv5.getText() + "null");
        } else {
            tv5.setText(tv5.getText() + c5.toString());
        }

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