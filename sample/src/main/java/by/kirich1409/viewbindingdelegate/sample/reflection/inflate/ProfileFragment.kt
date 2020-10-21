@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection.inflate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileFragment : Fragment() {

    private val viewBinding: FragmentProfileBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return viewBinding.root
    }
}
