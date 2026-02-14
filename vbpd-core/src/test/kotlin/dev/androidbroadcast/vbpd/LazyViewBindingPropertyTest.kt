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
class LazyViewBindingPropertyTest {

    private fun createMockBinding(): ViewBinding {
        val view = mockk<View>()
        return mockk<ViewBinding> {
            every { root } returns view
        }
    }

    @Test
    fun `getValue returns binding created by viewBinder`() {
        val expectedBinding = createMockBinding()
        val property = LazyViewBindingProperty<Any, ViewBinding> { expectedBinding }
        val thisRef = Any()

        val result = property.getValue(thisRef, ::result)
        assertEquals(expectedBinding, result)
    }

    @Test
    fun `getValue returns same instance on subsequent calls`() {
        var callCount = 0
        val binding = createMockBinding()
        val property = LazyViewBindingProperty<Any, ViewBinding> {
            callCount++
            binding
        }
        val thisRef = Any()

        val first = property.getValue(thisRef, ::first)
        val second = property.getValue(thisRef, ::second)

        assertEquals(first, second)
        assertEquals(1, callCount, "viewBinder should be called only once")
    }

    @Test
    fun `clear resets cached binding`() {
        var callCount = 0
        val property = LazyViewBindingProperty<Any, ViewBinding> {
            callCount++
            createMockBinding()
        }
        val thisRef = Any()

        property.getValue(thisRef, ::thisRef)
        property.clear()
        property.getValue(thisRef, ::thisRef)

        assertEquals(2, callCount, "viewBinder should be called again after clear()")
    }

    @Test
    fun `viewBinder receives correct thisRef`() {
        var receivedRef: Any? = null
        val property = LazyViewBindingProperty<String, ViewBinding> { ref ->
            receivedRef = ref
            createMockBinding()
        }

        property.getValue("test", ::receivedRef)
        assertEquals("test", receivedRef)
    }
}
