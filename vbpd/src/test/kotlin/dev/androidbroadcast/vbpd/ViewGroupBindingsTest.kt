package dev.androidbroadcast.vbpd

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ViewGroupBindingsTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `viewBinding creates lazy binding for ViewGroup`() {
        val viewGroup = FrameLayout(context)
        val expectedBinding = mockk<ViewBinding> {
            every { root } returns View(context)
        }

        val property = viewGroup.viewBinding { _: FrameLayout -> expectedBinding }
        val result = property.getValue(viewGroup, ::result)

        assertEquals(expectedBinding, result)
    }

    @Test
    fun `viewBinding returns same instance on subsequent calls`() {
        val viewGroup = FrameLayout(context)
        val binding = mockk<ViewBinding> {
            every { root } returns View(context)
        }

        val property = viewGroup.viewBinding { _: FrameLayout -> binding }
        val first = property.getValue(viewGroup, ::first)
        val second = property.getValue(viewGroup, ::second)

        assertEquals(first, second)
    }
}
