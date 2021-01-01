@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionActivityViewBindings")

package by.kirich1409.viewbindingdelegate

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.DefaultActivityViewBingingRootProvider
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(T::class.java, viewBindingRootId)
}


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
    return viewBinding {
        val rootView = ActivityCompat.requireViewById<View>(this, viewBindingRootId)
        ViewBindingCache.getBind(viewBindingClass).bind(rootView)
    }
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
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(T::class.java, createMethod)
}

@JvmName("inflateViewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND
): ViewBindingProperty<ComponentActivity, T> = when (createMethod) {
    CreateMethod.INFLATE -> viewBinding {
        ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass).inflate(layoutInflater)
    }
    CreateMethod.BIND -> viewBinding {
        val rootView = DefaultActivityViewBingingRootProvider.findRootView(this)
        ViewBindingCache.getBind(viewBindingClass).bind(rootView)
    }
}
