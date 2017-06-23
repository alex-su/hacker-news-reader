package com.alexsukharev.hackernewsreader.network;

import com.alexsukharev.hackernewsreader.model.Item;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsApi {

    @GET("topstories.json")
    Flowable<List<Long>> getTopStories();

    /*
     getStory() and getComment() use the same API endpoint, but return slightly different results for stories and comments.
     It makes sense to separate them for testing purposes.
      */

    @GET("item/{id}.json")
    Flowable<Item> getStory(@Path("id") final long id);

    @GET("item/{id}.json")
    Flowable<Item> getComment(@Path("id") final long id);

}
