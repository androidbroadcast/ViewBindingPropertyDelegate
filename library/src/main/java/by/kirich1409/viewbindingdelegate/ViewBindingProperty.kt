package by.kirich1409.viewbindingdelegate

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ActivityViewBinder
import by.kirich1409.viewbindingdelegate.internal.FragmentViewBinder
import by.kirich1409.viewbindingdelegate.internal.checkIsMainThread
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@PublishedApi
internal abstract class ViewBindingProperty<R, T : ViewBinding>(
    private val viewBinder: (R) -> T
) : ReadOnlyProperty<R, T> {

    internal var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        checkIsMainThread()
        viewBinding?.let { return it }

        getLifecycleOwner(thisRef).lifecycle.addObserver(lifecycleObserver)
        return viewBinder(thisRef).also { viewBinding = it }
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            mainHandler.post {
                viewBinding = null
            }
        }
    }
}

@PublishedApi
internal class ActivityViewBindingProperty<T : ViewBinding>(
    viewBinder: (Activity) -> T
) : ViewBindingProperty<ComponentActivity, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: ComponentActivity) = thisRef
}

@PublishedApi
internal class FragmentViewBindingProperty<T : ViewBinding>(
    viewBinder: (Fragment) -> T
) : ViewBindingProperty<Fragment, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: Fragment) = thisRef.viewLifecycleOwner
}

/**
 * Create new [ViewBinding] associated with the [Activity][this]
 *
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("unused")
inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    @IdRes viewBindingRootId: Int
): ReadOnlyProperty<ComponentActivity, T> {
    val activityViewBinder =
        ActivityViewBinder(T::class.java) { it.requireViewByIdCompat(viewBindingRootId) }
    return ActivityViewBindingProperty(activityViewBinder::bind)
}

/**
 * Create new [ViewBinding] associated with the [Activity][this] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@Suppress("unused")
fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBinder: (Activity) -> T
): ReadOnlyProperty<ComponentActivity, T> {
    return ActivityViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Fragment][this]
 */
@Suppress("unused")
inline fun <reified T : ViewBinding> Fragment.viewBinding(
    noinline viewBinder: (Fragment) -> T = FragmentViewBinder(T::class.java)::bind
): ReadOnlyProperty<Fragment, T> {
    return FragmentViewBindingProperty(viewBinder)
}
