@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection

import android.content.Context
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.ViewProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileView(context: Context) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        inflate(context, R.layout.view_profile, this)
    }

    private val viewBinding: ViewProfileBinding by viewBinding(CreateMethod.BIND)

    override fun onFinishInflate() {
        super.onFinishInflate()
        // ViewBinding views be added only after first call of viewBinding, because layout resource contains <merge> in the root
        with(viewBinding) {
            name.text = "Name"
            status.text = "Status"
        }
    }
}