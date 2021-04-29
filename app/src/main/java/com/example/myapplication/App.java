package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.share.Share;

public class App extends Application {

    public static App instance;
    public static Share share;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        share = new Share(this);
    }
}
