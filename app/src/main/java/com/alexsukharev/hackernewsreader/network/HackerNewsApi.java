package com.alexsukharev.hackernewsreader.network;

import com.alexsukharev.hackernewsreader.model.Item;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsApi {

    @GET("topstories.json")
    Observable<List<Item>> getTopStories();

    @GET("item/{id}.json")
    Observable<Item> getItem(@Path("id") final long id);

}
