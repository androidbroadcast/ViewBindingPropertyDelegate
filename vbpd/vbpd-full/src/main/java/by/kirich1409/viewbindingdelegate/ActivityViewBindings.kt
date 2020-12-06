@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionActivityViewBindings")
@file:JvmMultifileClass

package by.kirich1409.viewbindingdelegate

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class ActivityViewBinder<T : ViewBinding>(
    private val viewBindingClass: Class<T>,
    private val viewProvider: (Activity) -> View
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
