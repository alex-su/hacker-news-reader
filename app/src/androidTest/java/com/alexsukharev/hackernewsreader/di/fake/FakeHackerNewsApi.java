package com.alexsukharev.hackernewsreader.di.fake;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;
import com.alexsukharev.hackernewsreader.util.ListOfLong;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Fake API that doesn't execute network calls and parses json files instead
 */
public class FakeHackerNewsApi implements HackerNewsApi {

    private final Context mContext;

    private final Gson mGson;

    public FakeHackerNewsApi(@NonNull final Context context, @NonNull final Gson gson) {
        mContext = context;
        mGson = gson;
    }

    @Override
    public Flowable<List<Long>> getTopStories() {
        return getFlowable(getObjectFromResourceFile("top_stories.json", ListOfLong.class, new ArrayList<>()));
    }

    @Override
    public Flowable<Item> getStory(final long id) {
        final Item item = getObjectFromResourceFile("story_details.json", Item.class, new Item(id));
        item.setId(id);
        return getFlowable(item);
    }

    @Override
    public Flowable<Item> getComment(final long id) {
        final Item item = getObjectFromResourceFile("comment_details.json", Item.class, new Item(id));
        item.setId(id);
        return getFlowable(item);
    }

    private <T> T getObjectFromResourceFile(@NonNull final String fileName, @NonNull final Class<? extends T> resultClass, @NonNull final T fallback) {
        try {
            final InputStream resourceStream = mContext.getClassLoader().getResourceAsStream(fileName);
            return mGson.fromJson(new JsonReader(new InputStreamReader(resourceStream, "UTF-8")), resultClass);
        } catch (IOException e) {
            e.printStackTrace();
            return fallback;
        }
    }

    private <T> Flowable<T> getFlowable(@NonNull final T object) {
        return Flowable.just(object).observeOn(Schedulers.io());
    }

}
