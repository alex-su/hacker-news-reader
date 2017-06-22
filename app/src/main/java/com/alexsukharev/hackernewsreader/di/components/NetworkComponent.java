package com.alexsukharev.hackernewsreader.di.components;

import com.alexsukharev.hackernewsreader.di.modules.NetworkModule;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;
import com.google.gson.Gson;

import dagger.Component;

@AppScope
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    HackerNewsApi getHackerNewsApi();

    Gson getGson();

}