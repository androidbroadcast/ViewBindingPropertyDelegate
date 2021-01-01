@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionViewGroupBindings")

package by.kirich1409.viewbindingdelegate

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewGroup.viewBinding(): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(T::class.java)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> ViewGroup.viewBinding(viewBindingClass: Class<T>, ): ViewBindingProperty<ViewGroup, T> {
    return viewBinding { ViewBindingCache.getBind(viewBindingClass).bind(this) }
}
