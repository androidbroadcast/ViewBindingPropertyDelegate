@file:Suppress("unused")
@file:JvmName("ViewBindingPropertyDelegateUtilsRef")


package dev.androidbroadcast.vbpd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.androidbroadcast.vbpd.internal.ViewBindingCache

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * not associated with any host. You need to manually clear reference with
 * [ViewBindingProperty.clear] or not to keep it at all.
 */
public inline fun <R : Any, reified VB : ViewBinding> viewBindingLazy(
    layoutInflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = parent != null,
): ViewBindingProperty<R, VB> {
    return LazyViewBindingProperty {
        ViewBindingCache.getInflateWithLayoutInflater(VB::class.java)
            .inflate(layoutInflater, parent, attachToParent)
    }
}
