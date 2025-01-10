package dev.androidbroadcast.vbpd.sample.dialog.noreflection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.FragmentProfileBinding
import dev.androidbroadcast.vbpd.sample.fragment.noreflection.ProfileFragment
import dev.androidbroadcast.vbpd.viewBinding

class ProfileDialogFragment2 : DialogFragment() {

    // Creating it the default way would also work for this case
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
