package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ActivityViewBinder
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat

@PublishedApi
internal class ActivityViewBindingProperty<A : ComponentActivity, T : ViewBinding>(
    viewBinder: (A) -> T
) : ViewBindingProperty<A, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: A) = thisRef
}

/**
 * Create new [ViewBinding] associated with the [Activity][this] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivity")
fun <A : ComponentActivity, T : ViewBinding> A.viewBinding(viewBinder: (A) -> T): ViewBindingProperty<A, T> {
    return ActivityViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Activity][this]
 *
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
inline fun <A : ComponentActivity, reified T : ViewBinding> A.viewBinding(
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<A, T> {
    val activityViewBinder =
        ActivityViewBinder(T::class.java) { it.requireViewByIdCompat(viewBindingRootId) }
    return viewBinding(activityViewBinder::bind)
}

/**
 * Create new [ViewBinding] associated with the [Activity][this] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivity")
inline fun <A : ComponentActivity, T : ViewBinding> A.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (A) -> View
): ViewBindingProperty<A, T> {
    return viewBinding { activity -> vbFactory(viewProvider(activity)) }
}

/**
 * Create new [ViewBinding] associated with the [Activity][this] and allow customize how
 * a [View] will be bounded to the view binding.
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingActivity")
inline fun <A : ComponentActivity, T : ViewBinding> A.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<A, T> {
    return viewBinding { activity -> vbFactory(activity.findViewById(viewBindingRootId)) }
}
