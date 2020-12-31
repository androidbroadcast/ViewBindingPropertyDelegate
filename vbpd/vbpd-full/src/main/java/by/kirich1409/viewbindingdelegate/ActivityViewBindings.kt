@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionActivityViewBindings")

package by.kirich1409.viewbindingdelegate

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.DefaultActivityViewBingingRootProvider

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
    val activityViewBinder = ActivityViewBinder(viewBindingClass) {
        ActivityCompat.requireViewById(this, viewBindingRootId)
    }
    return viewBinding(activityViewBinder::bind)
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
    CreateMethod.INFLATE -> viewBinding(ActivityInflateViewBinder(viewBindingClass)::bind)
    CreateMethod.BIND -> viewBinding(ActivityViewBinder(viewBindingClass)::bind)
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class ActivityViewBinder<T : ViewBinding>(
    private val viewBindingClass: Class<T>,
    private val viewProvider: (Activity) -> View = DefaultActivityViewBingingRootProvider::findRootView
) {

    /**
     * Cache static method `ViewBinding.bind(View)`
     */
    private val bindViewMethod by lazy(LazyThreadSafetyMode.NONE) {
        viewBindingClass.getMethod("bind", View::class.java)
    }

    /**
     * Create new [ViewBinding] instance
     */
    @Suppress("UNCHECKED_CAST")
    fun bind(activity: Activity): T {
        val view = viewProvider(activity)
        return bindViewMethod(null, view) as T
    }
}


@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class ActivityInflateViewBinder<T : ViewBinding>(
    private val viewBindingClass: Class<T>,
) {

    /**
     * Cache static method `ViewBinding.inflate(LayoutInflater)`
     */
    private val bindViewMethod by lazy(LazyThreadSafetyMode.NONE) {
        viewBindingClass.getMethod("inflate", LayoutInflater::class.java)
    }

    /**
     * Create new [ViewBinding] instance
     */
    @Suppress("UNCHECKED_CAST")
    fun bind(activity: Activity): T {
        return bindViewMethod(null, activity.layoutInflater) as T
    }
}
