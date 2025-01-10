@file:Suppress("unused")

package dev.androidbroadcast.vbpd

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import dev.androidbroadcast.vbpd.internal.requireViewByIdCompat

@PublishedApi
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ViewGroupViewBindingProperty<in V : ViewGroup, T : ViewBinding>(
    viewBinder: (V) -> T
) : BaseViewBindingProperty<V, T>(viewBinder)

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 */
public inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (ViewGroup) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return when {
        isInEditMode -> EagerViewBindingProperty(vbFactory(this))
        else -> LazyViewBindingProperty { viewGroup -> vbFactory(viewGroup) }
    }
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
@Deprecated("Order of arguments was changed", ReplaceWith("viewBinding(viewBindingRootId, vbFactory)"))
public inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(viewBindingRootId, vbFactory)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
public inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    crossinline vbFactory: (View) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(viewBindingRootId, lifecycleAware = false, vbFactory)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
public inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    lifecycleAware: Boolean,
    crossinline vbFactory: (View) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return when {
        isInEditMode -> EagerViewBindingProperty(vbFactory(this))
        lifecycleAware -> ViewGroupViewBindingProperty { viewGroup -> vbFactory(viewGroup) }
        else -> LazyViewBindingProperty { viewGroup: ViewGroup ->
            vbFactory(viewGroup.requireViewByIdCompat(viewBindingRootId))
        }
    }
}
