package com.alexsukharev.hackernewsreader;

import android.app.Application;

import com.alexsukharev.hackernewsreader.di.Components;

public class App extends Application {

    public void onCreate() {
        super.onCreate();
        Components.init(this);
    }

}
