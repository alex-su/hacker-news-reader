package com.alexsukharev.hackernewsreader.di.modules;

import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";

    @Provides
    @AppScope
    HackerNewsApi getHackerNewsApi(final Retrofit retrofit) {
        return retrofit.create(HackerNewsApi.class);
    }

    @Provides
    @AppScope
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
