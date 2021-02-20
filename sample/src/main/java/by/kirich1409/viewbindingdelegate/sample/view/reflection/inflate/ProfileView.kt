@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.view.reflection.inflate

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.databinding.ViewProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val viewBinding: ViewProfileBinding by viewBinding(CreateMethod.INFLATE)

    init {
        addView(viewBinding.root)
    }
}