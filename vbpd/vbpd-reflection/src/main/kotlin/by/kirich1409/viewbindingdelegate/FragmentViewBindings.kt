@file:Suppress("RedundantVisibilityModifier", "unused") @file:JvmName("ReflectionFragmentViewBindings")

package by.kirich1409.viewbindingdelegate

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache
import by.kirich1409.viewbindingdelegate.internal.findRootView
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat

@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> Fragment.viewBinding(
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<Fragment, T> {
    return viewBinding(T::class.java, viewBindingRootId)
}

@JvmName("viewBindingFragment")
public fun <T : ViewBinding> DialogFragment.viewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<DialogFragment, T> {
    return viewBinding { dialogFragment: DialogFragment ->
        ViewBindingCache.getBind(viewBindingClass)
            .bind(dialogFragment.findRootView(viewBindingRootId))
    }
}

@JvmName("viewBindingFragment")
public fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<Fragment, T> {
    return viewBinding { _: Fragment ->
        ViewBindingCache.getBind(viewBindingClass)
            .bind(requireView().requireViewByIdCompat(viewBindingRootId))
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> Fragment.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<Fragment, T> {
    return viewBinding(T::class.java, createMethod)
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<Fragment, T> = when (createMethod) {
    CreateMethod.BIND -> fragmentViewBinding{
        ViewBindingCache.getBind(viewBindingClass).bind(requireView())
    }

    CreateMethod.INFLATE -> {
        fragmentViewBinding(
            viewNeedsInitialization = false,
            viewBinder = {
                ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass)
                    .inflate(layoutInflater, null, false)
            }
        )
    }
}
