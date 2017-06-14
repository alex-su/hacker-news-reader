package com.alexsukharev.hackernewsreader.network;

import com.alexsukharev.hackernewsreader.model.Item;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsApi {

    @GET("topstories.json")
    Flowable<List<Long>> getTopStories();

    @GET("item/{id}.json")
    Flowable<Item> getItem(@Path("id") final long id);

}
