@file:Suppress("unused")

package dev.androidbroadcast.vbpd.sample.view.noreflection

import android.content.Context
import android.widget.LinearLayout
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.ViewProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileView(context: Context) : LinearLayout(context) {

    init {
        inflate(context, R.layout.view_profile, this)
    }

    private val viewBinding by viewBinding(ViewProfileBinding::bind)
}
