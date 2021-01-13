@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (ViewGroup) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return LazyViewBindingProperty { vbFactory(it) }
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<ViewGroup, T> {
    return LazyViewBindingProperty { viewGroup: ViewGroup ->
        vbFactory(viewGroup.requireViewByIdCompat(viewBindingRootId))
    }
}
