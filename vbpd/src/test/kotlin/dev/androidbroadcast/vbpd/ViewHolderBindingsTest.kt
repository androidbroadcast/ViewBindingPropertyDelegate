package dev.androidbroadcast.vbpd

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ViewHolderBindingsTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    class TestViewHolder(view: View) : RecyclerView.ViewHolder(view)

    @Test
    fun `viewBinding creates binding from ViewHolder`() {
        val view = FrameLayout(context)
        val viewHolder = TestViewHolder(view)
        val expectedBinding = mockk<ViewBinding> {
            every { root } returns view
        }

        val property = viewHolder.viewBinding { _: TestViewHolder -> expectedBinding }
        val result = property.getValue(viewHolder, ::result)

        assertEquals(expectedBinding, result)
    }

    @Test
    fun `viewBinding with factory and viewProvider`() {
        val view = FrameLayout(context)
        val viewHolder = TestViewHolder(view)
        val expectedBinding = mockk<ViewBinding> {
            every { root } returns view
        }

        val property = viewHolder.viewBinding(
            vbFactory = { expectedBinding },
            viewProvider = { it.itemView }
        )
        val result = property.getValue(viewHolder, ::result)

        assertEquals(expectedBinding, result)
    }
}
