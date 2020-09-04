@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.databinding.ProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileFragment : Fragment(R.layout.profile) {

    private val viewBindingUsingReflection: ProfileBinding by viewBinding()

    private val viewBindingWithoutReflection by viewBinding(ProfileBinding::bind)
}
