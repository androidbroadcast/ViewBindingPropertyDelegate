@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.internal

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.viewbinding.ViewBinding

object ViewBindingCache {

    private val inflateCache = mutableMapOf<Class<out ViewBinding>, InflateViewBinding<ViewBinding>>()
    private val bindCache = mutableMapOf<Class<out ViewBinding>, BindViewBinding<ViewBinding>>()

    @Suppress("UNCHECKED_CAST")
    @RestrictTo(LIBRARY)
    internal fun <T : ViewBinding> getInflateWithLayoutInflater(viewBindingClass: Class<T>): InflateViewBinding<T> {
        return inflateCache.getOrPut(viewBindingClass) {
            InflateViewBinding(viewBindingClass)
        } as InflateViewBinding<T>
    }

    @Suppress("UNCHECKED_CAST")
    @RestrictTo(LIBRARY)
    internal fun <T : ViewBinding> getBind(viewBindingClass: Class<T>): BindViewBinding<T> {
        return bindCache.getOrPut(viewBindingClass) { BindViewBinding(viewBindingClass) } as BindViewBinding<T>
    }

    /**
     * Reset all cached data
     */
    fun clear() {
        inflateCache.clear()
        bindCache.clear()
    }
}

/**
 * Wrapper of ViewBinding.inflate(LayoutInflater)
 */
@RestrictTo(LIBRARY)
internal class InflateViewBinding<out VB : ViewBinding>(viewBindingClass: Class<VB>) {

    private val inflateViewBinding = viewBindingClass.getMethod("inflate", LayoutInflater::class.java)

    @Suppress("UNCHECKED_CAST")
    fun inflate(layoutInflater: LayoutInflater): VB {
        return inflateViewBinding(null, layoutInflater) as VB
    }
}

/**
 * Wrapper of ViewBinding.bind(View)
 */
@RestrictTo(LIBRARY)
internal class BindViewBinding<out VB : ViewBinding>(viewBindingClass: Class<VB>) {

    private val bindViewBinding = viewBindingClass.getMethod("bind", View::class.java)

    @Suppress("UNCHECKED_CAST")
    fun bind(view: View): VB {
        return bindViewBinding(null, view) as VB
    }
}