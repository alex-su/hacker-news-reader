package com.alexsukharev.hackernewsreader.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

@Entity
public class Item {

    public static final String TYPE_STORY = "story";
    public static final String TYPE_COMMENT = "comment";

    @PrimaryKey
    private long id;

    private String title;

    private String type;

    private String text;

    private String by;

    private String url;

    private int score;

    private long parent;

    private Date time;

    private List<Long> kids;

    @ColumnInfo(name = "sort_order")
    private int sortOrder;

    public Item(final long id) {
        this.id = id;
    }

    @Ignore
    public Item(final long id, @NonNull final String type, final int sortOrder) {
        this.id = id;
        this.type = type;
        this.sortOrder = sortOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getBy() {
        return by;
    }

    public void setBy(final String by) {
        this.by = by;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(final long parent) {
        this.parent = parent;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(final Date time) {
        this.time = time;
    }

    public List<Long> getKids() {
        return kids;
    }

    public void setKids(final List<Long> kids) {
        this.kids = kids;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
