@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.view.noreflection

import android.content.Context
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.ViewProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileView(context: Context) : LinearLayout(context) {

    init {
        inflate(context, R.layout.view_profile, this)
    }

    private val viewBinding by viewBinding(ViewProfileBinding::bind)
}