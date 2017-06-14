package com.alexsukharev.hackernewsreader.util;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import java.util.List;

public class ItemTypeConverters {

    @TypeConverter
    public static List<Long> listFromString(String value) {
        return value == null ? null : new Gson().fromJson(value, List.class);
    }

    @TypeConverter
    public static String stringToList(List<Long> list) {
        return list == null ? null : new Gson().toJson(list);
    }

}
