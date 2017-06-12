package com.alexsukharev.hackernewsreader.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.alexsukharev.hackernewsreader.database.AppDatabase;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static final String DATABASE_NAME = "hacker-news-database";

    @Provides
    @AppScope
    AppDatabase providesDatabase(final Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, DATABASE_NAME).build();
    }
}