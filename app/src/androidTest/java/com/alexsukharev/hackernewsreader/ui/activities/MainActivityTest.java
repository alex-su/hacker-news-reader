package com.alexsukharev.hackernewsreader.ui.activities;

import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.alexsukharev.hackernewsreader.R;
import com.alexsukharev.hackernewsreader.repository.StoriesRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.alexsukharev.hackernewsreader.util.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends BaseActivityTest<MainActivity> {

    /**
     * See https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
     */
    @Test
    public void testStoryList() {
        // Check that PAGE_SIZE items are displayed on the screen
        onView(withId(R.id.recycler_view)).check((view, noViewFoundException) -> assertThat(((RecyclerView) view).getAdapter().getItemCount(), is(StoriesRepository.PAGE_SIZE)));

        // Check if the first item has the correct title
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.title)).check(matches(withText("Luna – Visual and textual functional programming language")));
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.comments)).check(matches(withText(getContext().getString(R.string.comments, 11))));
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.points)).check(matches(withText(getContext().getString(R.string.points, 85))));
    }

    @Test
    public void testOpenStory() {
        // Click on the first list item
        onView(withRecyclerView(R.id.recycler_view).atPosition(0)).perform(click());

        // Verify that StoryCommentsActivity has started with the correct data
        intended(allOf(
                hasComponent(hasClassName(StoryCommentsActivity.class.getName())),
                hasExtra(StoryCommentsActivity.EXTRA_STORY_ID, 14612680L)));
    }

    @Override
    Class<MainActivity> getActivityClass() {
        return MainActivity.class;
    }
}