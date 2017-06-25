package com.alexsukharev.hackernewsreader.ui.activities;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.alexsukharev.hackernewsreader.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.alexsukharev.hackernewsreader.util.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class StoryCommentsActivityTest extends BaseActivityTest<StoryCommentsActivity> {

    /**
     * See https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
     */
    @Test
    public void testCommentsList() {
        // Check that 11 items are displayed on the screen
        onView(withId(R.id.recycler_view)).check((view, noViewFoundException) -> assertThat(((RecyclerView) view).getAdapter().getItemCount(), is(11)));

        // Check if the first item has the correct data
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.comment_text)).check(matches(withText(startsWith("Here is the snapshot from"))));
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.username_text)).check(matches(withText("Skunkleton")));
    }

    @Test
    public void testStoryDetails() {
        onView(withId(R.id.title)).check(matches(withText("Luna – Visual and textual functional programming language")));
        onView(withId(R.id.comments)).check(matches(withText(getContext().getString(R.string.comments, 11))));
        onView(withId(R.id.points)).check(matches(withText(getContext().getString(R.string.points, 85))));
    }

    @Override
    Class<StoryCommentsActivity> getActivityClass() {
        return StoryCommentsActivity.class;
    }

    @Override
    Intent getActivityStartIntent() {
        final Intent intent = super.getActivityStartIntent();
        intent.putExtra(StoryCommentsActivity.EXTRA_STORY_ID, 14612680L);
        return intent;
    }
}