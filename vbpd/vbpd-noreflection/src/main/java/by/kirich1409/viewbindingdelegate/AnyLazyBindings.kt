@file:Suppress("unused")
@file:JvmName("viewBindingLazyAny")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBinding] associated with the [Any]
 */
@JvmName("viewBindingLazyAny")
fun <A : Any, T : ViewBinding> A.viewBindingLazy(viewBinder: (A) -> T): ViewBindingProperty<A, T> {
    return LazyViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Any]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [Any].
 */
@JvmName("viewBindingLazyAny")
inline fun <A : Any, T : ViewBinding> A.viewBindingLazy(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View
): ViewBindingProperty<A, T> {
    return viewBindingLazy { any: A -> vbFactory(viewProvider(any)) }
}

/**
 * Create new [ViewBinding] associated with the [Any]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [Any].
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingLazyAny")
inline fun <A : Any, T : ViewBinding> A.viewBindingLazy(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<A, T> {
    return viewBindingLazy { any: A ->
        vbFactory(viewProvider(any).findViewById(viewBindingRootId))
    }
}
