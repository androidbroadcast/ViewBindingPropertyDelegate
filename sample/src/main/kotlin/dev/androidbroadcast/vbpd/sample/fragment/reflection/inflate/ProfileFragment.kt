@file:Suppress("unused")

package dev.androidbroadcast.vbpd.sample.fragment.reflection.inflate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.androidbroadcast.vbpd.CreateMethod
import dev.androidbroadcast.vbpd.sample.databinding.FragmentProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileFragment : Fragment() {

    private val viewBinding: FragmentProfileBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return viewBinding.root
    }
}
