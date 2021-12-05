package com.uniandes.vinilosapplication.ui.activities


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
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
class AlbumCreateScreenTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashscreenActivity::class.java)

    @Test
    fun albumCreateScreenTest() {
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

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.newAlbum), withText("New Album"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        Thread.sleep(2000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.albumCreateTitle),
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
        appCompatEditText.perform(scrollTo(), replaceText("Bandera negra"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.albumCreateCover),
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
        appCompatEditText2.perform(scrollTo(), replaceText("https://mariskalrock.com/wp-content/uploads/2021/03/mago-de-oz-bandera-negra-portada.jpg"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.albumCreateDate),
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
        appCompatEditText3.perform(scrollTo(), replaceText("1948-07-16T00:00:00.000Z"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.albumCreateDescription),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    3
                )
            )
        )
        appCompatEditText4.perform(scrollTo(), replaceText("Bandera negra es un álbum de Mägo de Oz. Entre los meses de abril y junio de 2020 compusieron por Zoom cada uno desde su casa y entre julio y septiembre del mismo año grabaron en secreto en los estudios Cube."), closeSoftKeyboard())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.albumCreateGenre),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    4
                )
            )
        )
        appCompatEditText5.perform(scrollTo(), replaceText("Rock"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.albumCreateRecordCompany),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    5
                )
            )
        )
        appCompatEditText6.perform(scrollTo(), replaceText("EMI"), closeSoftKeyboard())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.albumCreateButton), withText("Create Album"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    6
                )
            )
        )
        appCompatButton3.perform(scrollTo(), click())

        Thread.sleep(2000)

        val textView = onView(
            allOf(
                withId(R.id.textView6), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
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
