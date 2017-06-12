package com.alexsukharev.hackernewsreader.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.alexsukharev.hackernewsreader.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    LiveData<List<Item>> getAll();

    @Query("SELECT * FROM item WHERE type = 'story' COLLATE NOCASE")
    LiveData<List<Item>> getStories();

    @Query("SELECT * FROM item WHERE type = 'comment' COLLATE NOCASE")
    LiveData<List<Item>> getComments();

}
