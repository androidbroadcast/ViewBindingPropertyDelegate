package by.kirich1409.viewbindingdelegate.sample.dialog.reflection.inflate

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileDialogFragment1 : DialogFragment() {

    private val viewBinding: FragmentProfileBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(viewBinding.root)
            .create()
    }
}