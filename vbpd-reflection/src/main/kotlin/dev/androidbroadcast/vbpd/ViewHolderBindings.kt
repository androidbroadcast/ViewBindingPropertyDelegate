@file:JvmName("ReflectionViewHolderBindings")

package dev.androidbroadcast.vbpd

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param T Class of expected [ViewBinding] result class
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingViewHolder")
public inline fun <reified T : ViewBinding> ViewHolder.viewBinding(): ViewBindingProperty<ViewHolder, T> {
    return viewBinding(T::class.java)
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingViewHolder")
public fun <T : ViewBinding> ViewHolder.viewBinding(
    viewBindingClass: Class<T>,
): ViewBindingProperty<ViewHolder, T> {
    return viewBinding { ViewBindingCache.getBind(viewBindingClass).bind(itemView) }
}
