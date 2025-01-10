@file:Suppress("unused")
@file:JvmName("ViewBindingPropertyDelegateUtils")

package by.kirich1409.viewbindingdelegate

import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * not associated with any host. You need to manually clear reference with
 * [ViewBindingProperty.clear] or not to keep it at all.
 *
 * @param viewBinder Function that create new instance of [ViewBinding]
 */
public fun <R : Any, VB : ViewBinding> viewBindingLazy(
    viewBinder: (R) -> VB,
): LazyViewBindingProperty<R, VB> {
    return LazyViewBindingProperty(viewBinder)
}
