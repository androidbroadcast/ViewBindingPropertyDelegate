@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionViewGroupBindings")

package by.kirich1409.viewbindingdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param createMethod Way of how create [ViewBinding]
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewGroup.viewBinding(createMethod: CreateMethod = CreateMethod.BIND) =
    viewBinding(T::class.java, createMethod)

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param createMethod Way of how create [ViewBinding]
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> ViewGroup.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND,
): ViewBindingProperty<ViewGroup, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding { ViewBindingCache.getBind(viewBindingClass).bind(this) }
    CreateMethod.INFLATE -> viewBinding(viewBindingClass, attachToRoot = true)
}

/**
 * Inflate new [ViewBinding] with the [ViewGroup][this] as parent
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewGroup.viewBinding(attachToRoot: Boolean) =
    viewBinding(T::class.java, attachToRoot)

/**
 * Inflate new [ViewBinding] with the [ViewGroup][this] as parent
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> ViewGroup.viewBinding(
    viewBindingClass: Class<T>,
    attachToRoot: Boolean
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding { viewGroup ->
        ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass)
            .inflate(LayoutInflater.from(context), viewGroup, attachToRoot)
    }
}
