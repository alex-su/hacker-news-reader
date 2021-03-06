package com.alexsukharev.hackernewsreader.di.components;

import com.alexsukharev.hackernewsreader.di.modules.DatabaseModule;
import com.alexsukharev.hackernewsreader.di.modules.NetworkModule;
import com.alexsukharev.hackernewsreader.di.modules.RepositoryModule;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.viewmodel.MainViewModel;
import com.alexsukharev.hackernewsreader.viewmodel.StoryCommentsViewModel;

import dagger.Component;

@AppScope
@Component(dependencies = ApplicationComponent.class, modules = {RepositoryModule.class, NetworkModule.class, DatabaseModule.class})
public interface RepositoryComponent {

    void inject(MainViewModel viewModel);

    void inject(StoryCommentsViewModel viewModel);

}