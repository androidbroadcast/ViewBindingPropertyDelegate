package dev.androidbroadcast.vbpd

import org.junit.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class ReflectionActivityViewBindingsTest {
    @Test
    fun `CreateMethod BIND and INFLATE enum values exist`() {
        assertNotNull(CreateMethod.BIND)
        assertNotNull(CreateMethod.INFLATE)
    }

    @Test
    fun `CreateMethod values are distinct`() {
        assertNotEquals(CreateMethod.BIND, CreateMethod.INFLATE)
    }
}
