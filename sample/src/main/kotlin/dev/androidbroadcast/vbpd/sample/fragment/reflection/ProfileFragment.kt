@file:Suppress("unused")

package dev.androidbroadcast.vbpd.sample.fragment.reflection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.FragmentProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBindingUsingReflection: FragmentProfileBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBindingUsingReflection) {
            button.setOnClickListener {
                // TODO Handle on click
            }
        }
    }
}
