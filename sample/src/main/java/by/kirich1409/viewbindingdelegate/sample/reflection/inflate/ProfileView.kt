@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection.inflate

import android.content.Context
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.databinding.ViewProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileView(context: Context) : FrameLayout(context) {

    private val viewBinding: ViewProfileBinding by viewBinding(CreateMethod.INFLATE)

    override fun onFinishInflate() {
        super.onFinishInflate()
        addView(viewBinding.root)
    }
}