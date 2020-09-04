package by.kirich1409.viewbindingdelegate

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.core.app.ComponentActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ActivityViewBinder
import by.kirich1409.viewbindingdelegate.internal.DialogFragmentViewBinder
import by.kirich1409.viewbindingdelegate.internal.FragmentViewBinder
import by.kirich1409.viewbindingdelegate.internal.ReflectingFragmentViewBinder
import by.kirich1409.viewbindingdelegate.internal.checkIsMainThread
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class ViewBindingProperty<in R, T : ViewBinding>(
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
internal class ActivityViewBindingProperty<A : ComponentActivity, T : ViewBinding>(
    viewBinder: (A) -> T
) : ViewBindingProperty<A, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: A) = thisRef
}

@PublishedApi
internal class FragmentViewBindingProperty<F : Fragment, T : ViewBinding>(
    viewBinder: (F) -> T
) : ViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F) = thisRef.viewLifecycleOwner
}

@PublishedApi
internal class DialogFragmentViewBindingProperty<F : DialogFragment, T : ViewBinding>(
    viewBinder: (F) -> T
) : ViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        return if (thisRef.view == null) thisRef.viewLifecycleOwner else thisRef
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity][this]
 *
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("unused")
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
@Suppress("unused")
@JvmName("viewBindingActivity")
fun <A : ComponentActivity, T : ViewBinding> A.viewBinding(viewBinder: (A) -> T): ViewBindingProperty<A, T> {
    return ActivityViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Fragment][this]
 */
@Suppress("unused")
@JvmName("viewBindingFragment")
inline fun <F : Fragment, reified T : ViewBinding> F.viewBinding(): ViewBindingProperty<Fragment, T> {
    return viewBinding(ReflectingFragmentViewBinder(T::class.java)::bind)
}

/**
 * Create new [ViewBinding] associated with the [Fragment][this]
 */
@Suppress("unused")
@JvmName("viewBindingFragment")
fun <F : Fragment, T : ViewBinding> F.viewBinding(viewBinder: (View) -> T, viewFinder: (F) -> View = Fragment::requireView): ViewBindingProperty<F, T> {
    return viewBinding(FragmentViewBinder(viewBinder, viewFinder)::bind)
}

/**
 * Create new [ViewBinding] associated with the [Fragment][this]
 */
@Suppress("unused")
@JvmName("viewBindingFragment")
fun <F : Fragment, T : ViewBinding> F.viewBinding(viewBinder: (F) -> T): ViewBindingProperty<F, T> {
    return FragmentViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [DialogFragment][this]'s view
 *
 * @param viewBindingRootId Id of the root view from your custom view
 */
@Suppress("unused")
@JvmName("viewBindingDialogFragment")
inline fun <reified T : ViewBinding> DialogFragment.dialogViewBinding(
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<DialogFragment, T> {
    return dialogViewBinding(DialogFragmentViewBinder(T::class.java, viewBindingRootId)::bind)
}


/**
 * Create new [ViewBinding] associated with the [DialogFragment][this]
 */
@Suppress("unused")
@JvmName("viewBindingDialogFragment")
fun <F : DialogFragment, T : ViewBinding> F.dialogViewBinding(viewBinder: (F) -> T): ViewBindingProperty<F, T> {
    return DialogFragmentViewBindingProperty(viewBinder)
}
