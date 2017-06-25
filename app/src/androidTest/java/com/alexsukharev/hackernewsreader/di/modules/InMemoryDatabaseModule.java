package com.alexsukharev.hackernewsreader.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.alexsukharev.hackernewsreader.database.AppDatabase;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class InMemoryDatabaseModule extends DatabaseModule {

    @Provides
    @AppScope
    @Override
    AppDatabase providesDatabase(final Application application) {
        return Room.inMemoryDatabaseBuilder(application, AppDatabase.class).build();
    }
}