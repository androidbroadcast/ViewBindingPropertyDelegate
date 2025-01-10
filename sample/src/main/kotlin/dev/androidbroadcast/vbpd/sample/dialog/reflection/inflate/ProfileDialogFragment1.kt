package dev.androidbroadcast.vbpd.sample.dialog.reflection.inflate

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import dev.androidbroadcast.vbpd.CreateMethod
import dev.androidbroadcast.vbpd.sample.databinding.FragmentProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileDialogFragment1 : DialogFragment() {

    private val viewBinding: FragmentProfileBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(viewBinding.root)
            .create()
    }
}
