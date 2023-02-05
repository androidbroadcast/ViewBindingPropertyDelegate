package by.kirich1409.viewbindingdelegate.sample.fragment.noreflection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.sample.dialog.noreflection.ProfileDialogFragment2
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding by viewBinding(FragmentProfileBinding::bind,
        onViewDestroyed = { _: FragmentProfileBinding ->
            // reset view
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            button.setOnClickListener {
                if (childFragmentManager.findFragmentByTag("dialog") == null) {
                    ProfileDialogFragment2().show(childFragmentManager, "dialog")
                }
            }
        }
    }
}