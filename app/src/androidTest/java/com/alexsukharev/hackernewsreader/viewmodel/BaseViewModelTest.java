package com.alexsukharev.hackernewsreader.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.alexsukharev.hackernewsreader.di.Components;
import com.alexsukharev.hackernewsreader.di.components.DaggerNetworkComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerRepositoryComponent;
import com.alexsukharev.hackernewsreader.di.modules.FakeNetworkModule;
import com.alexsukharev.hackernewsreader.di.modules.InMemoryDatabaseModule;
import com.alexsukharev.hackernewsreader.di.modules.NetworkModule;

import org.junit.Before;

abstract class BaseViewModelTest<T extends ViewModel> {

    T mViewModel;

    @Before
    public void setUp() {
        // Setting up fake components to help with testing
        final NetworkModule fakeNetworkModule = new FakeNetworkModule(getContext());
        Components.init((Application) getContext().getApplicationContext());
        Components.setNetworkComponent(
                DaggerNetworkComponent
                        .builder()
                        .networkModule(fakeNetworkModule)
                        .build());
        Components.setRepositoryComponent(
                DaggerRepositoryComponent
                        .builder()
                        .applicationComponent(Components.getApplicationComponent())
                        .networkModule(fakeNetworkModule)
                        .databaseModule(new InMemoryDatabaseModule())
                        .build());
        mViewModel = createViewModel();
    }

    Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    abstract T createViewModel();

}
