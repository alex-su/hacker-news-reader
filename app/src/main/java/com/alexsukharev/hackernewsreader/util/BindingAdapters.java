package com.alexsukharev.hackernewsreader.util;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.alexsukharev.hackernewsreader.R;

import java.util.Date;

public class BindingAdapters {

    @BindingAdapter("bind:time")
    public static void setTime(TextView view, Date time) {
        final long diff = System.currentTimeMillis() / 1000 - time.getTime();
        String text;
        if (diff > 60 * 60 * 24) {
            text = view.getContext().getString(R.string.format_days, diff / (60 * 60 * 24));
        } else if (diff > 60 * 60) {
            text = view.getContext().getString(R.string.format_hours, diff / (60 * 60));
        } else if (diff > 60) {
            text = view.getContext().getString(R.string.format_minutes, diff / 60);
        } else {
            text = view.getContext().getString(R.string.format_seconds, diff);
        }
        view.setText(text);
    }

}
