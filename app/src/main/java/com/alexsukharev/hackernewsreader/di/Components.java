package com.alexsukharev.hackernewsreader.di;

import android.app.Application;
import android.support.annotation.NonNull;

import com.alexsukharev.hackernewsreader.di.components.ApplicationComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerApplicationComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerNetworkComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerRepositoryComponent;
import com.alexsukharev.hackernewsreader.di.components.NetworkComponent;
import com.alexsukharev.hackernewsreader.di.components.RepositoryComponent;
import com.alexsukharev.hackernewsreader.di.modules.ApplicationModule;

public class Components {

    private static Application sApplication;

    private static ApplicationComponent mApplicationComponent;

    private static NetworkComponent mNetworkComponent;

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

    public static NetworkComponent getNetworkComponent() {
        if (mNetworkComponent == null) {
            mNetworkComponent = DaggerNetworkComponent.builder().build();
        }
        return mNetworkComponent;
    }

    public static RepositoryComponent getRepositoryComponent() {
        if (mRepositoryComponent == null) {
            mRepositoryComponent = DaggerRepositoryComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        return mRepositoryComponent;
    }

    public static void setNetworkComponent(@NonNull final NetworkComponent networkComponent) {
        mNetworkComponent = networkComponent;
    }

    public static void setRepositoryComponent(@NonNull final RepositoryComponent repositoryComponent) {
        mRepositoryComponent = repositoryComponent;
    }

}
