package com.uniandes.vinilosapplication.ui.activities


import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.uniandes.vinilosapplication.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashscreenActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashscreenActivity::class.java)

    @Test
    fun splashscreenActivityTest() {
        val button = onView(
            allOf(
                withId(R.id.bt_start), withText("INICIAR"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))
    }
}
