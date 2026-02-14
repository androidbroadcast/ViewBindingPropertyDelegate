package dev.androidbroadcast.vbpd

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ViewBindingCacheTest {
    @Before
    fun setup() {
        ViewBindingCache.clear()
    }

    @After
    fun tearDown() {
        ViewBindingCache.clear()
        ViewBindingCache.setEnabled(false)
    }

    @Test
    fun `cache can be enabled and disabled`() {
        ViewBindingCache.setEnabled(true)
        ViewBindingCache.setEnabled(false)
    }

    @Test
    fun `clear does not crash when cache is empty`() {
        ViewBindingCache.clear()
        ViewBindingCache.setEnabled(true)
        ViewBindingCache.clear()
    }

    @Test
    fun `setEnabled true then false resets cache`() {
        ViewBindingCache.setEnabled(true)
        ViewBindingCache.setEnabled(false)
        ViewBindingCache.clear()
    }
}
