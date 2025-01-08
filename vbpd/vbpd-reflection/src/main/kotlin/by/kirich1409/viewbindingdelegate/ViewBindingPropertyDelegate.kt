@file:Suppress("unused")
@file:JvmName("ViewBindingPropertyDelegateUtilsRef")


package by.kirich1409.viewbindingdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * not associated with any host. You need to manually clear reference with
 * [ViewBindingProperty.clear] or not to keep it at all.
 */
inline fun <R : Any, reified VB : ViewBinding> viewBindingLazy(
    layoutInflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false,
): LazyViewBindingProperty<R, VB> {
    return LazyViewBindingProperty {
        ViewBindingCache.getInflateWithLayoutInflater(VB::class.java)
            .inflate(layoutInflater, parent, attachToParent)
    }
}
