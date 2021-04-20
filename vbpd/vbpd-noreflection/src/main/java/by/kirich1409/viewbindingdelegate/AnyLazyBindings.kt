@file:Suppress("unused")
@file:JvmName("lazyViewBindingAny")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBinding] associated with the [Any]
 */
@JvmName("lazyViewBindingAny")
fun <A : Any, T : ViewBinding> A.lazyViewBinding(viewBinder: (A) -> T): ViewBindingProperty<A, T> {
    return LazyViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Any]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [Any].
 */
@JvmName("lazyViewBindingAny")
inline fun <A : Any, T : ViewBinding> A.lazyViewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View
): ViewBindingProperty<A, T> {
    return lazyViewBinding { any: A -> vbFactory(viewProvider(any)) }
}

/**
 * Create new [ViewBinding] associated with the [Any]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [Any].
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("lazyViewBindingAny")
inline fun <A : Any, T : ViewBinding> A.lazyViewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<A, T> {
    return lazyViewBinding { any: A ->
        vbFactory(viewProvider(any).findViewById(viewBindingRootId))
    }
}
