@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBindingUsingReflection: FragmentProfileBinding by viewBinding()

    private val viewBindingWithoutReflection by viewBinding(FragmentProfileBinding::bind)

    override fun onDestroyView() {
        super.onDestroyView()
        viewBindingUsingReflection.container.visibility
    }
}
