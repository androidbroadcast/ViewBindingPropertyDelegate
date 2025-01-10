package dev.androidbroadcast.vbpd.sample.fragment.noreflection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.FragmentProfileBinding
import dev.androidbroadcast.vbpd.sample.dialog.noreflection.ProfileDialogFragment2
import dev.androidbroadcast.vbpd.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding by viewBinding(FragmentProfileBinding::bind)

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
