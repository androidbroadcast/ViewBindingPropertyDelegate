@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.databinding.ProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileFragment2 : ComplexFragment(R.layout.profile) {

    private val viewBinding by viewBinding(ProfileBinding::bind, ComplexFragment::requireInternalView)
}

abstract class ComplexFragment(
    @LayoutRes private val internalLayoutId: Int
): Fragment() {
    
    private var internalView: View? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val complexView = inflater.inflate(R.layout.complex, container, false) as ViewGroup
        internalView = inflater.inflate(internalLayoutId, complexView, false)
        complexView.findViewById<ViewGroup>(R.id.container)?.addView(internalView)
        return complexView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        internalView = null
    }

    fun requireInternalView(): View = internalView ?: throw IllegalStateException("internalView is null")
}