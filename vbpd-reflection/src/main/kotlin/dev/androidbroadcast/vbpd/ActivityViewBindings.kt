@file:JvmName("ReflectionActivityViewBindings")

package  dev.androidbroadcast.vbpd

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import  dev.androidbroadcast.vbpd.internal.findRootView

/**
 * Create new [ViewBinding] associated with the [Activity]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
@JvmName("viewBindingActivity")
public inline fun <reified T : ViewBinding> Activity.viewBinding(
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<Activity, T> {
    return viewBinding(T::class.java, viewBindingRootId)
}

/**
 * Create new [ViewBinding] associated with the [Activity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
@JvmName("viewBindingActivity")
public fun <T : ViewBinding> Activity.viewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<Activity, T> {
    return viewBinding(
        viewBinder = { activity ->
            val rootView = ActivityCompat.requireViewById<View>(activity, viewBindingRootId)
            ViewBindingCache.getBind(viewBindingClass).bind(rootView)
        }
    )
}

/**
 * Create new [ViewBinding] associated with the [Activity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param rootViewProvider Provider of a root view from the [Activity][this] for [ViewBinding]
 */
@JvmName("viewBindingActivity")
public fun <A : Activity, T : ViewBinding> Activity.viewBinding(
    viewBindingClass: Class<T>,
    rootViewProvider: (A) -> View,
): ViewBindingProperty<A, T> {
    return viewBinding(
        viewBinder = { activity ->
            ViewBindingCache.getBind(viewBindingClass).bind(rootViewProvider(activity))
        }
    )
}

/**
 * Create new [ViewBinding] associated with the [Activity].
 * You need to set [ViewBinding.getRoot] as a content view using [Activity.setContentView].
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("inflateViewBindingActivity")
public inline fun <reified T : ViewBinding> Activity.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<Activity, T> {
    return viewBinding(T::class.java, createMethod)
}

@JvmName("inflateViewBindingActivity")
public fun <T : ViewBinding> Activity.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<Activity, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding(viewBindingClass, ::findRootView)
    CreateMethod.INFLATE -> {
        ActivityViewBindingProperty {
            ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass)
                .inflate(layoutInflater, null, false)
        }
    }
}
