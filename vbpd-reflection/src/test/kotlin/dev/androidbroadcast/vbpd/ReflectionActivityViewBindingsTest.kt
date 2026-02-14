package dev.androidbroadcast.vbpd

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ReflectionActivityViewBindingsTest {
    @Test
    fun `CreateMethod BIND and INFLATE enum values exist`() {
        assertNotNull(CreateMethod.BIND)
        assertNotNull(CreateMethod.INFLATE)
    }

    @Test
    fun `CreateMethod values are distinct`() {
        assert(CreateMethod.BIND != CreateMethod.INFLATE)
    }
}
