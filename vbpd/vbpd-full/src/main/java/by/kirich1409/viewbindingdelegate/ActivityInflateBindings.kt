@file:JvmName("ReflectionActivityViewBindings")
@file:JvmMultifileClass
@file:Suppress("RedundantVisibilityModifier", "unused")

package by.kirich1409.viewbindingdelegate

import android.app.Activity
import android.view.LayoutInflater
import androidx.annotation.RestrictTo
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding

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

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]. You need to set [ViewBinding.getRoot] as
 * content view using [Activity.setContentView].
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("inflateViewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(ActivityInflateViewBinder(T::class.java)::bind)
}


/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]. You need to set [ViewBinding.getRoot] as
 * content view using [Activity.setContentView].
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("inflateViewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding(ActivityInflateViewBinder(viewBindingClass)::bind)
}