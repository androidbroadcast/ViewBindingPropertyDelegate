package dev.androidbroadcast.vbpd.sample.dialog.reflection

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.FragmentProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileDialogFragment1 : DialogFragment() {

    private val viewBinding: FragmentProfileBinding by viewBinding(R.id.container)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.fragment_profile)
            .create()
    }
}
