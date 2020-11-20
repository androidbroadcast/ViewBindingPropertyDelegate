@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

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
