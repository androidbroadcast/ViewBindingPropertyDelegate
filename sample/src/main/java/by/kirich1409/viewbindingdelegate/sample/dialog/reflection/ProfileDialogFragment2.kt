package by.kirich1409.viewbindingdelegate.sample.dialog.reflection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.dialogViewBinding
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding

class ProfileDialogFragment2 : DialogFragment() {

    private val viewBindingUsingReflection: FragmentProfileBinding by dialogViewBinding(R.id.container)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBindingUsingReflection) {
            button.setOnClickListener {
                // TODO Handle on click
            }
        }
    }
}