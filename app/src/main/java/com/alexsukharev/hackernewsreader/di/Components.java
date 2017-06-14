package com.alexsukharev.hackernewsreader.di;

import android.app.Application;
import android.support.annotation.NonNull;

import com.alexsukharev.hackernewsreader.di.components.ApplicationComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerApplicationComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerDatabaseComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerRepositoryComponent;
import com.alexsukharev.hackernewsreader.di.components.DatabaseComponent;
import com.alexsukharev.hackernewsreader.di.components.RepositoryComponent;
import com.alexsukharev.hackernewsreader.di.modules.ApplicationModule;

public class Components {

    private static Application sApplication;

    private static ApplicationComponent mApplicationComponent;

    private static DatabaseComponent mDatabaseComponent;

    private static RepositoryComponent mRepositoryComponent;

    public static void init(@NonNull final Application application) {
        sApplication = application;
    }

    public static ApplicationComponent getApplicationComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(sApplication))
                    .build();
        }
        return mApplicationComponent;
    }

    public static DatabaseComponent getDatabaseComponent() {
        if (mDatabaseComponent == null) {
            mDatabaseComponent = DaggerDatabaseComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        return mDatabaseComponent;
    }

    public static RepositoryComponent getRepositoryComponent() {
        if (mRepositoryComponent == null) {
            mRepositoryComponent = DaggerRepositoryComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        return mRepositoryComponent;
    }

}
