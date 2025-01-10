@file:Suppress("unused")

package dev.androidbroadcast.vbpd.sample.view.reflection.inflate

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import dev.androidbroadcast.vbpd.CreateMethod
import dev.androidbroadcast.vbpd.sample.databinding.ViewProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

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
