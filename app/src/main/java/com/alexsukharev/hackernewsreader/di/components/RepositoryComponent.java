package com.alexsukharev.hackernewsreader.di.components;

import com.alexsukharev.hackernewsreader.di.modules.DatabaseModule;
import com.alexsukharev.hackernewsreader.di.modules.NetworkModule;
import com.alexsukharev.hackernewsreader.di.modules.RepositoryModule;
import com.alexsukharev.hackernewsreader.di.scopes.AppScope;
import com.alexsukharev.hackernewsreader.repository.ItemsRepository;
import com.alexsukharev.hackernewsreader.viewmodel.MainViewModel;

import dagger.Component;

@AppScope
@Component(dependencies = ApplicationComponent.class, modules = {RepositoryModule.class, NetworkModule.class, DatabaseModule.class})
public interface RepositoryComponent {

    void inject(MainViewModel viewModel);

    ItemsRepository getItemsRepository();

}