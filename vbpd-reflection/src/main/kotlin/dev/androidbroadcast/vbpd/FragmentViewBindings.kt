@file:JvmName("ReflectionFragmentViewBindings")

package dev.androidbroadcast.vbpd

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.androidbroadcast.vbpd.internal.findRootView
import dev.androidbroadcast.vbpd.internal.requireViewByIdCompat

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> Fragment.viewBinding(
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<Fragment, T> {
    return viewBinding(T::class.java, viewBindingRootId)
}

/**
 * Create new [ViewBinding] associated with the [DialogFragment]
 *
 * @param viewBindingClass Class of expected [ViewBinding]
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
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

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param viewBindingClass Class of expected [ViewBinding]
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
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
 * @param createMethod Method that will be used to create [ViewBinding] instance
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
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
 * @param createMethod Method that will be used to create [ViewBinding] instance
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<Fragment, T> = when (createMethod) {
    CreateMethod.BIND -> fragmentViewBinding {
        ViewBindingCache.getBind(viewBindingClass).bind(requireView())
    }

    CreateMethod.INFLATE -> {
        fragmentViewBinding(
            viewBinder = {
                ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass)
                    .inflate(layoutInflater, null, false)
            }
        )
    }
}
