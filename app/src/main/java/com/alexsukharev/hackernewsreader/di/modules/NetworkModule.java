package com.alexsukharev.hackernewsreader.di.modules;

import com.alexsukharev.hackernewsreader.BuildConfig;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Date;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    Retrofit getRetrofit(final Gson gson, final OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @AppScope
    OkHttpClient getClient() {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    @Provides
    @AppScope
    Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .create();
    }

}
