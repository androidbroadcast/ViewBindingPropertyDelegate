package by.kirich1409.viewbindingdelegate.sample.dialog.noreflection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.sample.fragment.noreflection.ProfileFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileDialogFragment2 : DialogFragment() {

    // Creating via default way will work too for that case
    // private val viewBinding: ProfileBinding by viewBinding()

    private val viewBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            button.setOnClickListener {
                parentFragment?.parentFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.profile_fragment_container, ProfileFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}