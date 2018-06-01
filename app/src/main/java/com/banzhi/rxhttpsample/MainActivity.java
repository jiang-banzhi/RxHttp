package com.banzhi.rxhttpsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.banzhi.rxhttp.RxHttp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxHttp.updateBaseUrl("http://192.168.1.210:9002/");

    }
}
