package com.alexsukharev.hackernewsreader.util;

import android.arch.persistence.room.TypeConverter;

import com.alexsukharev.hackernewsreader.di.Components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemTypeConverters {

    @TypeConverter
    public static List<Long> listFromString(String value) {
        return value == null ? null : Components.getNetworkComponent().getGson().fromJson(value, ListOfLong.class);
    }

    @TypeConverter
    public static String listToString(List<Long> list) {
        return list == null ? null : Components.getNetworkComponent().getGson().toJson(list);
    }

    @TypeConverter
    public static Date dateFromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    private static class ListOfLong extends ArrayList<Long> {
    }

}
