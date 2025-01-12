@file:JvmName("ReflectionViewGroupBindings")

package dev.androidbroadcast.vbpd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param createMethod Way of creating [ViewBinding]
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewGroup.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(T::class.java, createMethod)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param createMethod Way of creating [ViewBinding]
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingFragment")
@JvmOverloads
public fun <T : ViewBinding> ViewGroup.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<ViewGroup, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding { viewGroup ->
        ViewBindingCache.getBind(viewBindingClass).bind(viewGroup)
    }

    CreateMethod.INFLATE -> viewBinding(viewBindingClass, attachToRoot = true)
}

/**
 * Inflate new [ViewBinding] with the [ViewGroup][this] as a parent
 *
 * @param T Class of expected [ViewBinding] result class
 * @param attachToRoot Whether the inflated view should be attached to the parent [ViewGroup]
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewGroup.viewBinding(
    attachToRoot: Boolean = false,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(T::class.java, attachToRoot)
}

/**
 * Inflate new [ViewBinding] with the [ViewGroup][this] as a parent
 *
 * @param viewBindingClass Class instance of expected [ViewBinding]
 * @param attachToRoot Whether the inflated view should be attached to the parent [ViewGroup]
 *
 * @return [ViewBindingProperty] that holds [ViewBinding] instance
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> ViewGroup.viewBinding(
    viewBindingClass: Class<T>,
    attachToRoot: Boolean = false,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding { viewGroup ->
        ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass)
            .inflate(LayoutInflater.from(context), viewGroup, attachToRoot)
    }
}
