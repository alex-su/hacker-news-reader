package com.alexsukharev.hackernewsreader.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.alexsukharev.hackernewsreader.database.dao.ItemDao;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.util.ItemTypeConverters;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
@TypeConverters(ItemTypeConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();

}
