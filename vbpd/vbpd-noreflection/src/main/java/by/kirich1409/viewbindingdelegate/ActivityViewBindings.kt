@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ActivityViewBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.emptyVbCallback
import by.kirich1409.viewbindingdelegate.internal.findRootView
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat

@RestrictTo(LIBRARY)
private class ActivityViewBindingProperty<in A : ComponentActivity, out T : ViewBinding>(
    onViewDestroyed: (T) -> Unit,
    viewBinder: (A) -> T
) : LifecycleViewBindingProperty<A, T>(viewBinder, onViewDestroyed) {

    override fun getLifecycleOwner(thisRef: A): LifecycleOwner {
        return thisRef
    }

    override fun isViewInitialized(thisRef: A): Boolean {
        return getLifecycleOwner(thisRef).lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivity")
public fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
    viewBinder: (A) -> T
): ViewBindingProperty<A, T> {
    return viewBinding(emptyVbCallback(), viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivityWithCallbacks")
public fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
    onViewDestroyed: (T) -> Unit = {},
    viewBinder: (A) -> T
): ViewBindingProperty<A, T> {
    return ActivityViewBindingProperty(onViewDestroyed, viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivity")
public inline fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View = ::findRootView
): ViewBindingProperty<A, T> {
    return viewBinding(emptyVbCallback(), vbFactory, viewProvider)
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivityWithCallbacks")
public inline fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
    noinline onViewDestroyed: (T) -> Unit = {},
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View = ::findRootView
): ViewBindingProperty<A, T> {
    return viewBinding(onViewDestroyed) { activity -> vbFactory(viewProvider(activity)) }
}

/**
 * Create new [ViewBinding] associated with the [Activity][this] and allow customize how
 * a [View] will be bounded to the view binding.
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("unused")
@JvmName("viewBindingActivity")
public inline fun <T : ViewBinding> ComponentActivity.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(emptyVbCallback(), vbFactory, viewBindingRootId)
}

/**
 * Create new [ViewBinding] associated with the [Activity][this] and allow customize how
 * a [View] will be bounded to the view binding.
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("unused")
@JvmName("viewBindingActivity")
public inline fun <T : ViewBinding> ComponentActivity.viewBinding(
    noinline onViewDestroyed: (T) -> Unit = {},
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(onViewDestroyed) { activity ->
        vbFactory(activity.requireViewByIdCompat(viewBindingRootId))
    }
}
