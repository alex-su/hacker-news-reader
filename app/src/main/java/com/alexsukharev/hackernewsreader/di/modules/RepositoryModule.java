package com.alexsukharev.hackernewsreader.di.modules;

import com.alexsukharev.hackernewsreader.database.AppDatabase;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;
import com.alexsukharev.hackernewsreader.repository.ItemsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @AppScope
    ItemsRepository providesItemsRepository(final AppDatabase database, final HackerNewsApi api) {
        return new ItemsRepository(database, api);
    }
}