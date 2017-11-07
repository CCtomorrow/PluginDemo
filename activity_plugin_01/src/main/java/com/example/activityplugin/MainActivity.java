package com.example.activityplugin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.text);
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
        textView.setText("language:" + language + "\n" + "country:" + country);
        Intent intent = new Intent(Intent.ACTION_VIEW);
    }

    public void launchSampleAct(View view) {
        Intent intent = new Intent(this, PluginSampleActivity.class);
        startActivity(intent);
    }
}
