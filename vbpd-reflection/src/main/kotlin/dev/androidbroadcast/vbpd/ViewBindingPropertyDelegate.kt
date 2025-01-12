@file:JvmName("ViewBindingPropertyDelegateUtilsRef")


package dev.androidbroadcast.vbpd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBindingProperty] that will be initialized lazy and
 * not associated with any host. You need to manually clear reference with
 * [ViewBindingProperty.clear] or not to keep it at all.
 *
 * @param layoutInflater [LayoutInflater] that will be used to inflate [ViewBinding]
 * @param parent [ViewGroup] that will be used as a parent for the [ViewBinding]
 * @param attachToParent does [ViewBinding] should be attached to the [parent]
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
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
