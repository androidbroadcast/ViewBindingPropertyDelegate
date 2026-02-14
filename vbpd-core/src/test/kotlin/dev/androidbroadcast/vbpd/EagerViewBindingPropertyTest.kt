package dev.androidbroadcast.vbpd

import android.view.View
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class EagerViewBindingPropertyTest {

    private fun createMockBinding(): ViewBinding {
        val view = mockk<View>()
        return mockk<ViewBinding> {
            every { root } returns view
        }
    }

    @Test
    fun `getValue returns the provided binding`() {
        val expectedBinding = createMockBinding()
        val property = EagerViewBindingProperty<Any, ViewBinding>(expectedBinding)

        val result = property.getValue(Any(), ::result)
        assertEquals(expectedBinding, result)
    }

    @Test
    fun `getValue always returns same instance`() {
        val binding = createMockBinding()
        val property = EagerViewBindingProperty<Any, ViewBinding>(binding)
        val thisRef = Any()

        val first = property.getValue(thisRef, ::first)
        val second = property.getValue(thisRef, ::second)
        assertEquals(first, second)
    }
}
