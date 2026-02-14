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
        val controller = Robolectric.buildActivity(TestActivity::class.java)
            .create()
            .start()
            .resume()

        val activity = controller.get()
        assertNotNull(activity.binding)
    }

    @Test
    fun `binding is cleared after onDestroy`() {
        val controller = Robolectric.buildActivity(TestActivity::class.java)
            .create()
            .start()
            .resume()

        val activity = controller.get()
        assertNotNull(activity.binding)

        controller.pause().stop().destroy()
    }
}
