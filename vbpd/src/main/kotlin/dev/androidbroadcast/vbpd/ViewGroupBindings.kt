package dev.androidbroadcast.vbpd

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import dev.androidbroadcast.vbpd.internal.requireViewByIdCompat

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 */
public inline fun <VG : ViewGroup, T : ViewBinding> VG.viewBinding(crossinline vbFactory: (VG) -> T): ViewBindingProperty<VG, T> =
    when {
        isInEditMode -> EagerViewBindingProperty(vbFactory(this))
        else -> LazyViewBindingProperty { viewGroup -> vbFactory(viewGroup) }
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
): ViewBindingProperty<ViewGroup, T> = viewBinding(viewBindingRootId, vbFactory)

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
public inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    crossinline vbFactory: (View) -> T,
): ViewBindingProperty<ViewGroup, T> =
    when {
        isInEditMode -> EagerViewBindingProperty(requireViewByIdCompat(viewBindingRootId))
        else ->
            LazyViewBindingProperty { viewGroup: ViewGroup ->
                vbFactory(viewGroup.requireViewByIdCompat(viewBindingRootId))
            }
    }
