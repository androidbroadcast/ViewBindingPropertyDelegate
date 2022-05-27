package by.kirich1409.viewbindingdelegate

import android.os.Looper
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Setting for ViewBindingPropertyDelegate library
 */
object ViewBindingPropertyDelegate {

    /**
     * Enable strict checks for how ViewBindingPropertyDelegate is accessed. Throws an [Exception]
     * if a [ViewBinding] is accessed outside of the view lifecycle. As an example, if you try
     * accessing a [Fragment]'s one before [Fragment.onViewCreated] has been called or after
     * [Fragment.onDestroyView] was called, you will get a crash.
     *
     * **By default strict mode is enabled**
     */
    @set:MainThread
    var strictMode = true
        set(value) {
            check(Looper.getMainLooper() == Looper.myLooper())
            field = value
        }
}