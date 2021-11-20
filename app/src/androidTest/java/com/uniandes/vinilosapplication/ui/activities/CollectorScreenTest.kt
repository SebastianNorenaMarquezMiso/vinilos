package com.uniandes.vinilosapplication.ui.activities


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.uniandes.vinilosapplication.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorScreenTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashscreenActivity::class.java)

    @Test
    fun collectorScreenTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.bt_start), withText("Iniciar"),
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

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.collectorFragment), withContentDescription("COLECCIONISTAS"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigatin_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(2000)

        val textView = onView(
            allOf(
                withId(R.id.textView3), withText("manollo@caracol.com.co"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
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
