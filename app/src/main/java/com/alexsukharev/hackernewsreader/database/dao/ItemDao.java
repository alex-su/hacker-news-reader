package com.alexsukharev.hackernewsreader.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alexsukharev.hackernewsreader.model.Item;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item WHERE type = 'story' COLLATE NOCASE")
    LiveData<List<Item>> getStoriesLiveData();

    @Query("SELECT * FROM item WHERE parent = :storyId")
    LiveData<List<Item>> getCommentsLiveData(final long storyId);

    @Query("SELECT * FROM item WHERE parent = :storyId")
    Flowable<List<Item>> getCommentsFlowable(final long storyId);

    @Query("SELECT * FROM item WHERE id = :id")
    LiveData<Item> getItemLiveData(long id);

    @Query("SELECT * FROM item WHERE id = :id")
    Flowable<Item> getItemFlowable(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItems(List<Item> items);

    @Update
    void updateItem(Item items);

}
