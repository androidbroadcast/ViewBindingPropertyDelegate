package dev.androidbroadcast.vbpd.sample.dialog.reflection.inflate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dev.androidbroadcast.vbpd.CreateMethod
import dev.androidbroadcast.vbpd.sample.databinding.FragmentProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileDialogFragment2 : DialogFragment() {

    private val viewBindingUsingReflection: FragmentProfileBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return viewBindingUsingReflection.root
    }
}
