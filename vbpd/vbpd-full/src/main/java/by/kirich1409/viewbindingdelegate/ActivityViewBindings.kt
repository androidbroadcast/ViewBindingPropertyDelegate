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
import by.kirich1409.viewbindingdelegate.internal.findRootView

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(@IdRes viewBindingRootId: Int) =
    viewBinding(T::class.java, viewBindingRootId)

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding { activity ->
        val rootView = ActivityCompat.requireViewById<View>(activity, viewBindingRootId)
        ViewBindingCache.getBind(viewBindingClass).bind(rootView)
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param rootViewProvider Provider of root view for the [ViewBinding] from the [Activity][this]
 */
@JvmName("viewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    rootViewProvider: (ComponentActivity) -> View
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding { activity -> ViewBindingCache.getBind(viewBindingClass).bind(rootViewProvider(activity)) }
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity].
 * You need to set [ViewBinding.getRoot] as content view using [Activity.setContentView].
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("inflateViewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND
) = viewBinding(T::class.java, createMethod)

@JvmName("inflateViewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND
): ViewBindingProperty<ComponentActivity, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding(viewBindingClass, ::findRootView)
    CreateMethod.INFLATE -> viewBinding {
        ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass).inflate(layoutInflater, null, false)
    }
}
