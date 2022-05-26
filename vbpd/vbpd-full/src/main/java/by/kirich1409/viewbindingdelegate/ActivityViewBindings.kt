@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionActivityViewBindings")

package by.kirich1409.viewbindingdelegate

import android.app.Activity
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache
import by.kirich1409.viewbindingdelegate.internal.emptyVbCallback
import by.kirich1409.viewbindingdelegate.internal.findRootView

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
@JvmName("viewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    @IdRes viewBindingRootId: Int,
    noinline onViewDestroyed: (T) -> Unit = emptyVbCallback(),
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(T::class.java, viewBindingRootId, onViewDestroyed)
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
@JvmName("viewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int,
    onViewDestroyed: (T) -> Unit = emptyVbCallback(),
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(onViewDestroyed) { activity ->
        val rootView = ActivityCompat.requireViewById<View>(activity, viewBindingRootId)
        ViewBindingCache.getBind(viewBindingClass).bind(rootView)
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param rootViewProvider Provider of a root view from the [Activity][this] for [ViewBinding]
 */
@JvmName("viewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    rootViewProvider: (ComponentActivity) -> View,
    onViewDestroyed: (T) -> Unit = emptyVbCallback(),
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(onViewDestroyed) { activity ->
        ViewBindingCache.getBind(viewBindingClass).bind(rootViewProvider(activity))
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity].
 * You need to set [ViewBinding.getRoot] as a content view using [Activity.setContentView].
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("inflateViewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND,
    noinline onViewDestroyed: (T) -> Unit = emptyVbCallback(),
) = viewBinding(T::class.java, createMethod, onViewDestroyed)

@JvmName("inflateViewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND,
    onViewDestroyed: (T) -> Unit = emptyVbCallback(),
): ViewBindingProperty<ComponentActivity, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding(viewBindingClass, ::findRootView, onViewDestroyed)
    CreateMethod.INFLATE -> {
        activityViewBinding(onViewDestroyed, viewNeedsInitialization = false) {
            ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass)
                .inflate(layoutInflater, null, false)
        }
    }
}
