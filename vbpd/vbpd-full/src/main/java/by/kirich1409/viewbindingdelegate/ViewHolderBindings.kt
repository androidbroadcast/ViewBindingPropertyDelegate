@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionViewHolderBindings")

package by.kirich1409.viewbindingdelegate

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewHolder.viewBinding(): ViewBindingProperty<ViewHolder, T> {
    return viewBinding(T::class.java)
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> ViewHolder.viewBinding(
    viewBindingClass: Class<T>,
): ViewBindingProperty<ViewHolder, T> {
    return viewBinding { ViewBindingCache.getBind(viewBindingClass).bind(itemView) }
}
