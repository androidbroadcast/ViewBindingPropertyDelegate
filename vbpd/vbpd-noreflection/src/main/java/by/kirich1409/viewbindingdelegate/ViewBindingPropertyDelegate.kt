package by.kirich1409.viewbindingdelegate

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.checkMainThread

/**
 * Setting for ViewBindingPropertyDelegate library
 */
object ViewBindingPropertyDelegate {

    /**
     * Enable strict checks of how ViewBindingPropertyDelegate is accessed. Will throw an [Exception]
     * when try to access a [ViewBinding] outside view lifecycle. As an example, when you will try
     * access to [Fragment]'s before [Fragment.onViewCreated] will be called of after
     * [Fragment.onDestroyView] you will get crash.
     *
     * **By default strict mode is enabled**
     */
    @set:MainThread
    var strictMode = true
        set(value) {
            checkMainThread()
            field = value
        }
}