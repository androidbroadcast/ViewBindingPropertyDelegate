@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.databinding.ProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileFragment1 : Fragment(R.layout.profile) {

    private val viewBindingUsingReflection: ProfileBinding by viewBinding()

    private val viewBindingWithoutReflection1 by viewBinding { fragment ->
        ProfileBinding.bind(fragment.requireView())
    }

    private val viewBindingWithoutReflection2 by viewBinding(ProfileBinding::bind)
}
