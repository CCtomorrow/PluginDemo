package com.yong.plugin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toast(View view) {
        File dexOutputDir = getDir(SDCardUtils.DEX_OUT_DIR, Context.MODE_PRIVATE);
        String dexPath = SDCardUtils.DEX_DIR + "dex_Plugin.dex";
        DexClassLoader loader = new DexClassLoader(dexPath,
                dexOutputDir.getAbsolutePath(),
                null, getClassLoader());
        try {
            Class clz = loader.loadClass("com.yong.plugin.ShowToastImpl");
            IShowToast impl = (IShowToast) clz.newInstance();
            impl.showToast(this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "加载出错", Toast.LENGTH_LONG).show();
        }
    }

}
