@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.sample.databinding.ProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import by.kirich1409.viewbindingdelegate.dialogViewBinding

class ProfileDialogFragment2 : DialogFragment() {

    private val viewBindingUsingReflection: ProfileBinding by dialogViewBinding(R.id.container)

    // Creating via default way will work too for that case
    // private val viewBinding: ProfileBinding by viewBinding()

    private val viewBindingWithoutReflection by viewBinding(ProfileBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile, container, false)
    }
}
