@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionLazyAnyBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [Any]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewProvider Provide a [View] from the [Any].
 */
@JvmName("viewBindingLazyAny")
public inline fun <reified T : ViewBinding> Any.viewBindingLazy(crossinline viewProvider: (Any) -> View) =
    viewBindingLazy(T::class.java) { viewProvider(it) }

/**
 * Create new [ViewBinding] associated with the [Any]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param viewProvider Provide a [View] from the [Any].
 */
@JvmName("viewBindingLazyAny")
public fun <T : ViewBinding> Any.viewBindingLazy(
    viewBindingClass: Class<T>,
    viewProvider: (Any) -> View
): ViewBindingProperty<RecyclerView.ViewHolder, T> {
    return viewBindingLazy<Any, T> { ViewBindingCache.getBind(viewBindingClass).bind(viewProvider(it)) }
}
