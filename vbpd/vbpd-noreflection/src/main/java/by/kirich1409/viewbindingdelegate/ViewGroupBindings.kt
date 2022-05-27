@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.emptyVbCallback
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat

@PublishedApi
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ViewGroupViewBindingProperty<in V : ViewGroup, out T : ViewBinding>(
    onViewDestroyed: (T) -> Unit,
    viewBinder: (V) -> T
) : LifecycleViewBindingProperty<V, T>(viewBinder, onViewDestroyed) {

    override fun getLifecycleOwner(thisRef: V): LifecycleOwner {
        return checkNotNull(ViewTreeLifecycleOwner.get(thisRef)) {
            "Fragment doesn't have a view associated with it or the view has been destroyed"
        }
    }
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (ViewGroup) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(lifecycleAware = false, vbFactory)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    lifecycleAware: Boolean,
    crossinline vbFactory: (ViewGroup) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(lifecycleAware, vbFactory, emptyVbCallback())
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    lifecycleAware: Boolean,
    crossinline vbFactory: (ViewGroup) -> T,
    noinline onViewDestroyed: (T) -> Unit,
): ViewBindingProperty<ViewGroup, T> {
    return when {
        isInEditMode -> EagerViewBindingProperty(vbFactory(this))
        lifecycleAware -> ViewGroupViewBindingProperty(onViewDestroyed) { viewGroup -> vbFactory(viewGroup) }
        else -> LazyViewBindingProperty(onViewDestroyed) { viewGroup -> vbFactory(viewGroup) }
    }
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
@Deprecated("Order of arguments was changed", ReplaceWith("viewBinding(viewBindingRootId, vbFactory)"))
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(viewBindingRootId, vbFactory, emptyVbCallback())
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    crossinline vbFactory: (View) -> T,
    noinline onViewDestroyed: (T) -> Unit,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(viewBindingRootId, lifecycleAware = false, vbFactory, onViewDestroyed)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    lifecycleAware: Boolean,
    crossinline vbFactory: (View) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(viewBindingRootId, lifecycleAware, vbFactory, emptyVbCallback())
}


/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    lifecycleAware: Boolean,
    crossinline vbFactory: (View) -> T,
    noinline onViewDestroyed: (T) -> Unit,
): ViewBindingProperty<ViewGroup, T> {
    return when {
        isInEditMode -> EagerViewBindingProperty(vbFactory(this))
        lifecycleAware -> ViewGroupViewBindingProperty(onViewDestroyed) { viewGroup -> vbFactory(viewGroup) }
        else -> LazyViewBindingProperty(onViewDestroyed) { viewGroup: ViewGroup ->
            vbFactory(viewGroup.requireViewByIdCompat(viewBindingRootId))
        }
    }
}
