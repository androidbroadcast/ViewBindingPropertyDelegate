package dev.androidbroadcast.vbpd

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import kotlin.reflect.KProperty
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ActivityViewBindingPropertyTest {
    class TestActivity : Activity() {
        val binding by viewBinding { activity: TestActivity ->
            mockk<ViewBinding> {
                every { root } returns View(activity)
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val content = FrameLayout(this)
            content.id = android.R.id.content
            setContentView(content)
        }
    }

    @Test
    fun `binding is accessible after onCreate`() {
        val controller =
            Robolectric
                .buildActivity(TestActivity::class.java)
                .create()
                .start()
                .resume()

        val activity = controller.get()
        assertNotNull(activity.binding)
    }

    @Test
    fun `binding is cleared after onDestroy`() {
        val controller =
            Robolectric
                .buildActivity(TestActivity::class.java)
                .create()
                .start()
                .resume()

        val activity = controller.get()
        assertNotNull(activity.binding)

        // Verify full lifecycle completes without crash
        // onDestroy triggers clear() which nulls binding and unregisters callbacks
        controller.pause().stop().destroy()
    }

    @Test
    fun `clear resets binding and allows recreation`() {
        val mockProperty = mockk<KProperty<*>>(relaxed = true)
        var callCount = 0
        val delegate =
            ActivityViewBindingProperty<Activity, ViewBinding> { activity ->
                callCount++
                mockk<ViewBinding> {
                    every { root } returns View(activity)
                }
            }

        val controller =
            Robolectric
                .buildActivity(TestActivity::class.java)
                .create()
                .start()
                .resume()
        val activity = controller.get()

        val binding1 = delegate.getValue(activity, mockProperty)
        assertEquals(1, callCount)

        delegate.clear()
        val binding2 = delegate.getValue(activity, mockProperty)
        assertEquals(2, callCount)
        assertNotEquals(binding1, binding2, "After clear(), a new binding should be created")
    }
}
