@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.noreflection

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.ViewProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileView @JvmOverloads constructor(
    context: Context,
    atts: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : LinearLayout(context, atts, defStyleAttr) {

    init {
        orientation = VERTICAL
        inflate(context, R.layout.view_profile, this)
    }

    private val viewBinding by viewBinding(ViewProfileBinding::bind)

    override fun onFinishInflate() {
        super.onFinishInflate()
        with(viewBinding) {
            name.text = "Name"
            status.text = "Status"
        }
    }
}