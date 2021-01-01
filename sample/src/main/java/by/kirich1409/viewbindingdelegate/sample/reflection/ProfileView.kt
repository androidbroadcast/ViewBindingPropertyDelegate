@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection

import android.content.Context
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.ViewProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileView(context: Context) : FrameLayout(context) {

    init {
        inflate(context, R.layout.view_profile, this)
    }

    private val viewBinding: ViewProfileBinding by viewBinding(CreateMethod.BIND)
}