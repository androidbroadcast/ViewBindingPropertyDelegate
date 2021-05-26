@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("FragmentViewBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.getRootView
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat
import kotlin.reflect.KProperty

private class DialogFragmentViewBindingProperty<in F : DialogFragment, out T : ViewBinding>(
    viewBinder: (F) -> T
) : LifecycleViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        if (thisRef.showsDialog) {
            return thisRef
        } else {
            try {
                return thisRef.viewLifecycleOwner
            } catch (ignored: IllegalStateException) {
                error("Fragment doesn't have view associated with it or the view has been destroyed")
            }
        }
    }
}

private class FragmentViewBindingProperty<in F : Fragment, out T : ViewBinding>(
    viewBinder: (F) -> T
) : LifecycleViewBindingProperty<F, T>(viewBinder) {

    private var fragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks? = null

    override fun getValue(thisRef: F, property: KProperty<*>): T {
        registerFragmentLifecycleCallbacks(thisRef)
        return super.getValue(thisRef, property)
    }

    private fun registerFragmentLifecycleCallbacks(fragment: Fragment) {
        if (fragmentLifecycleCallbacks != null) {
            return
        }

        fragmentLifecycleCallbacks = ClearOnDestroy().also { callbacks ->
            fragment.parentFragmentManager
                .registerFragmentLifecycleCallbacks(callbacks, false)
        }
    }

    override fun clear() {
        super.clear()
        fragmentLifecycleCallbacks = null
    }

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        try {
            return thisRef.viewLifecycleOwner
        } catch (ignored: IllegalStateException) {
            error("Fragment doesn't have view associated with it or the view has been destroyed")
        }
    }

    private inner class ClearOnDestroy : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            // Fix for destroying view for case with issue of navigation
            postClear()
        }
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 */
@Suppress("UNCHECKED_CAST")
@JvmName("viewBindingFragment")
public fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
    viewBinder: (F) -> T
): ViewBindingProperty<F, T> {
    return when (this) {
        is DialogFragment -> DialogFragmentViewBindingProperty(viewBinder) as ViewBindingProperty<F, T>
        else -> FragmentViewBindingProperty(viewBinder)
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the Fragment. By default call [Fragment.requireView]
 */
@JvmName("viewBindingFragment")
public inline fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (F) -> View = Fragment::requireView
): ViewBindingProperty<F, T> {
    return viewBinding { fragment: F -> vbFactory(viewProvider(fragment)) }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("UNCHECKED_CAST")
@JvmName("viewBindingFragment")
public inline fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<F, T> {
    return when (this) {
        is DialogFragment -> {
            viewBinding<DialogFragment, T>(vbFactory) { fragment ->
                fragment.getRootView(viewBindingRootId)
            } as ViewBindingProperty<F, T>
        }
        else -> {
            viewBinding(vbFactory) { fragment: F ->
                fragment.requireView().requireViewByIdCompat(viewBindingRootId)
            }
        }
    }
}
