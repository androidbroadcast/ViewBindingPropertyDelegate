package by.kirich1409.viewbindingdelegate.sample.dialog.reflection.inflate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileDialogFragment2 : DialogFragment() {

    private val viewBindingUsingReflection: FragmentProfileBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return viewBindingUsingReflection.root
    }
}