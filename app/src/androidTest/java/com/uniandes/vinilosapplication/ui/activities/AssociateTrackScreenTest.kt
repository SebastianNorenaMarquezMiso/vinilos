package com.uniandes.vinilosapplication.ui.activities


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.uniandes.vinilosapplication.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AssociateTrackScreenTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashscreenActivity::class.java)

    @Test
    fun associateTrackScreenTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.bt_start), withText("Start"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.layout_splashScreen),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        Thread.sleep(2000)

        val recyclerView = onView(
            allOf(
                withId(R.id.fragments_rv),
                childAtPosition(
                    withClassName(`is`("android.widget.ScrollView")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(2000)

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.albumDetailAssociateTrackButton), withText("Add Tracks"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        appCompatButton2.perform(scrollTo(), click())

        Thread.sleep(2000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.associateTrackName),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText.perform(scrollTo(), replaceText("Fiesta"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.associateTrackDuration),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    1
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText("2:09"), closeSoftKeyboard())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.associateTrackButton), withText("Add"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    2
                )
            )
        )
        appCompatButton3.perform(scrollTo(), click())

        Thread.sleep(2000)

        val textView = onView(
            allOf(
                withId(R.id.albumname), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
