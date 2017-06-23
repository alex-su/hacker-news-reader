package com.alexsukharev.hackernewsreader.di.modules;

import com.alexsukharev.hackernewsreader.database.AppDatabase;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;
import com.alexsukharev.hackernewsreader.repository.CommentsRepository;
import com.alexsukharev.hackernewsreader.repository.StoriesRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @AppScope
    StoriesRepository providesStoriesRepository(final AppDatabase database, final HackerNewsApi api) {
        return new StoriesRepository(database, api);
    }

    @Provides
    @AppScope
    CommentsRepository providesCommentsRepository(final AppDatabase database, final HackerNewsApi api) {
        return new CommentsRepository(database, api);
    }
}