package by.kirich1409.viewbindingdelegate.sample

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.databinding.ProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileFragment : Fragment(R.layout.profile) {

    private val viewBinding: ProfileBinding by viewBinding()
}