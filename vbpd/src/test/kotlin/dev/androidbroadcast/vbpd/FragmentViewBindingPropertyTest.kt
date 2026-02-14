package dev.androidbroadcast.vbpd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class FragmentViewBindingPropertyTest {

    class TestFragment : Fragment() {
        val binding by viewBinding { fragment: TestFragment ->
            mockk<ViewBinding> {
                every { root } returns fragment.requireView()
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return FrameLayout(requireContext())
        }
    }

    @Test
    fun `binding is accessible after onViewCreated`() {
        val scenario = launchFragmentInContainer<TestFragment>()
        scenario.onFragment { fragment ->
            assertNotNull(fragment.binding)
        }
    }

    @Test
    fun `binding survives configuration change lifecycle`() {
        val scenario = launchFragmentInContainer<TestFragment>()
        scenario.onFragment { fragment ->
            assertNotNull(fragment.binding)
        }
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }
}
