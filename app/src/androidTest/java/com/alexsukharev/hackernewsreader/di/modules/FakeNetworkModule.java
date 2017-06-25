package com.alexsukharev.hackernewsreader.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alexsukharev.hackernewsreader.di.fake.FakeHackerNewsApi;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FakeNetworkModule extends NetworkModule {

    private final Context mContext;

    public FakeNetworkModule(@NonNull final Context context) {
        mContext = context;
    }

    @Provides
    @AppScope
    @Override
    protected HackerNewsApi getHackerNewsApi(final Retrofit retrofit) {
        return new FakeHackerNewsApi(mContext, getGson());
    }

}
