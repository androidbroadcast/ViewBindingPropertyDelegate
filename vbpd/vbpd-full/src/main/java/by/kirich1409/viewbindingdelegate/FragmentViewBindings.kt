@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionFragmentViewBindings")

package by.kirich1409.viewbindingdelegate

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> Fragment.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND
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
    createMethod: CreateMethod = CreateMethod.BIND
): ViewBindingProperty<Fragment, T> = when (createMethod) {
    CreateMethod.BIND ->  viewBinding{
        ViewBindingCache.getBind(viewBindingClass).bind(requireView())
    }
    CreateMethod.INFLATE ->  viewBinding{
        ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass).inflate(layoutInflater)
    }
}
