@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("FragmentViewBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.emptyVbCallback
import by.kirich1409.viewbindingdelegate.internal.getRootView
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat
import kotlin.reflect.KProperty

private class DialogFragmentViewBindingProperty<in F : DialogFragment, out T : ViewBinding>(
    private val viewNeedInitialization: Boolean,
    viewBinder: (F) -> T,
    onViewDestroyed: (T) -> Unit,
) : LifecycleViewBindingProperty<F, T>(viewBinder, onViewDestroyed) {

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

    override fun isViewInitialized(thisRef: F): Boolean {
        if (!viewNeedInitialization) {
            return true
        }

        if (thisRef.showsDialog) {
            return thisRef.dialog != null
        } else {
            return thisRef.view != null
        }
    }
}

private class FragmentViewBindingProperty<in F : Fragment, out T : ViewBinding>(
    private val viewNeedInitialization: Boolean,
    viewBinder: (F) -> T,
    onViewDestroyed: (T) -> Unit,
) : LifecycleViewBindingProperty<F, T>(viewBinder, onViewDestroyed) {

    private var fragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks? = null
    private var fragmentManager: FragmentManager? = null

    override fun getValue(thisRef: F, property: KProperty<*>): T {
        val viewBinding = super.getValue(thisRef, property)
        registerFragmentLifecycleCallbacksIfNeeded(thisRef)
        return viewBinding
    }

    private fun registerFragmentLifecycleCallbacksIfNeeded(fragment: Fragment) {
        if (fragmentLifecycleCallbacks != null) {
            return
        }

        val fragmentManager = fragment.parentFragmentManager.also { fm ->
            this.fragmentManager = fm
        }
        fragmentLifecycleCallbacks = ClearOnDestroy().also { callbacks ->
            fragmentManager.registerFragmentLifecycleCallbacks(callbacks, false)
        }
    }

    override fun isViewInitialized(thisRef: F): Boolean {
        if (!viewNeedInitialization) return true

        if (thisRef !is DialogFragment) {
            return thisRef.view != null
        } else {
            return super.isViewInitialized(thisRef)
        }
    }

    override fun clear() {
        super.clear()
        fragmentManager?.also { fragmentManager ->
            fragmentLifecycleCallbacks?.let(fragmentManager::unregisterFragmentLifecycleCallbacks)
            this.fragmentManager = null
        }
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
    viewBinder: (F) -> T,
): ViewBindingProperty<F, T> {
    return viewBinding(viewBinder, emptyVbCallback())
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 */
@Suppress("UNCHECKED_CAST")
@JvmName("viewBindingFragmentWithCallbacks")
public fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
    viewBinder: (F) -> T,
    onViewDestroyed: (T) -> Unit = {},
): ViewBindingProperty<F, T> {
    return when (this) {
        is DialogFragment -> dialogFragmentViewBinding(onViewDestroyed, viewBinder)
        else -> fragmentViewBinding(onViewDestroyed, viewBinder)
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
    crossinline viewProvider: (F) -> View = Fragment::requireView,
): ViewBindingProperty<F, T> {
    return viewBinding(vbFactory, viewProvider, emptyVbCallback())
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the Fragment. By default call [Fragment.requireView]
 */
@JvmName("viewBindingFragmentWithCallbacks")
public inline fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (F) -> View = Fragment::requireView,
    noinline onViewDestroyed: (T) -> Unit = {},
): ViewBindingProperty<F, T> {
    return viewBinding({ fragment: F -> vbFactory(viewProvider(fragment)) }, onViewDestroyed)
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
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<F, T> {
    return viewBinding(vbFactory, viewBindingRootId, emptyVbCallback())
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("UNCHECKED_CAST")
@JvmName("viewBindingFragmentWithCallbacks")
public inline fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int,
    noinline onViewDestroyed: (T) -> Unit,
): ViewBindingProperty<F, T> {
    return when (this) {
        is DialogFragment -> {
            viewBinding<DialogFragment, T>(vbFactory, { fragment ->
                fragment.getRootView(viewBindingRootId)
            }, onViewDestroyed) as ViewBindingProperty<F, T>
        }
        else -> {
            viewBinding(vbFactory, { fragment: F ->
                fragment.requireView().requireViewByIdCompat(viewBindingRootId)
            }, onViewDestroyed)
        }
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun <F : Fragment, T : ViewBinding> fragmentViewBinding(
    onViewDestroyed: (T) -> Unit,
    viewBinder: (F) -> T,
    viewNeedInitialization: Boolean = true
): ViewBindingProperty<F, T> {
    return FragmentViewBindingProperty(viewNeedInitialization, viewBinder, onViewDestroyed)
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun <F : Fragment, T : ViewBinding> dialogFragmentViewBinding(
    onViewDestroyed: (T) -> Unit,
    viewBinder: (F) -> T,
    viewNeedInitialization: Boolean = true
): ViewBindingProperty<F, T> {
    return DialogFragmentViewBindingProperty(
        viewNeedInitialization,
        viewBinder,
        onViewDestroyed
    ) as ViewBindingProperty<F, T>
}
