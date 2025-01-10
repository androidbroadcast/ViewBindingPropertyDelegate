@file:Suppress("unused")

package dev.androidbroadcast.vbpd.sample.view.reflection

import android.content.Context
import android.widget.FrameLayout
import dev.androidbroadcast.vbpd.CreateMethod
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.ViewProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileView(context: Context) : FrameLayout(context) {

    init {
        inflate(context, R.layout.view_profile, this)
    }

    private val viewBinding: ViewProfileBinding by viewBinding(CreateMethod.BIND)
}
