@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection.inflate

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.databinding.ViewProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileView @JvmOverloads constructor(
    context: Context,
    atts: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : LinearLayout(context, atts, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    private val viewBinding: ViewProfileBinding by viewBinding(CreateMethod.INFLATE)

    override fun onFinishInflate() {
        super.onFinishInflate()
        // ViewBinding views be added only after first call of viewBinding, because layout resource contains <merge> in the root
        with(viewBinding) {
            name.text = "Name"
            status.text = "Status"
        }
    }
}