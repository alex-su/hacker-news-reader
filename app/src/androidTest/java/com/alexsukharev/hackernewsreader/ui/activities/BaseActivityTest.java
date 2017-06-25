package com.alexsukharev.hackernewsreader.ui.activities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.alexsukharev.hackernewsreader.di.Components;
import com.alexsukharev.hackernewsreader.di.components.DaggerNetworkComponent;
import com.alexsukharev.hackernewsreader.di.components.DaggerRepositoryComponent;
import com.alexsukharev.hackernewsreader.di.modules.FakeNetworkModule;
import com.alexsukharev.hackernewsreader.di.modules.NetworkModule;

import org.junit.Before;
import org.junit.Rule;

abstract class BaseActivityTest<T extends Activity> {

    @Rule
    public final IntentsTestRule<T> mActivityTestRule = new IntentsTestRule<>(getActivityClass(), false, false);

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
                        .build());
        mActivityTestRule.launchActivity(getActivityStartIntent());
    }

    Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    Intent getActivityStartIntent() {
        return new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), getActivityClass());
    }

    abstract Class<T> getActivityClass();
}
