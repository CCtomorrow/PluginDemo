package com.example.activityplugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PluginSampleActivity extends AppCompatActivity {

    private TextView mTextView;

    private boolean First = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_sample);
        mTextView = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        First = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!First) {
            mTextView.setText("onStart");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!First) {
            mTextView.setText("onResume");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!First) {
            mTextView.setText("onPause");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!First) {
            mTextView.setText("onStop");
        }
    }
}
