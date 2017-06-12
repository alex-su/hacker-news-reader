package com.alexsukharev.hackernewsreader.di.components;

import android.app.Application;

import com.alexsukharev.hackernewsreader.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Application getApplication();

}