package com.alexsukharev.hackernewsreader.di.components;

import com.alexsukharev.hackernewsreader.database.AppDatabase;
import com.alexsukharev.hackernewsreader.di.modules.DatabaseModule;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;

import dagger.Component;

@AppScope
@Component(modules = DatabaseModule.class, dependencies = ApplicationComponent.class)
public interface DatabaseComponent {

    AppDatabase getDatabase();

}