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

    @Query("SELECT * FROM item WHERE type = 'story' AND title IS NOT NULL COLLATE NOCASE ORDER BY sort_order")
    LiveData<List<Item>> getStoriesLiveData();

    @Query("SELECT * FROM item WHERE type = 'story' AND sort_order > :fromOrder COLLATE NOCASE ORDER BY sort_order LIMIT :limit")
    Flowable<List<Item>> getStoriesFlowable(final int fromOrder, final int limit);

    @Query("SELECT * FROM item WHERE parent = :storyId")
    LiveData<List<Item>> getCommentsLiveData(final long storyId);

    @Query("SELECT * FROM item WHERE parent = :storyId")
    Flowable<List<Item>> getCommentsFlowable(final long storyId);

    @Query("SELECT * FROM item WHERE id = :id")
    LiveData<Item> getItemLiveData(long id);

    @Query("SELECT * FROM item WHERE id = :id")
    Flowable<Item> getItemFlowable(long id);

    @Query("SELECT * FROM item WHERE id = :id")
    Item getItem(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItems(List<Item> items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(Item items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItems(List<Item> items);

    @Query("DELETE FROM item")
    void deleteAll();

}
