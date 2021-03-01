@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionViewGroupBindings")

package by.kirich1409.viewbindingdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param createMethod Way of how create [ViewBinding]
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewGroup.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND,
    lifecycleAware: Boolean = false,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(T::class.java, createMethod, lifecycleAware)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param createMethod Way of how create [ViewBinding]
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
@JvmName("viewBindingFragment")
@JvmOverloads
public fun <T : ViewBinding> ViewGroup.viewBinding(
    viewBindingClass: Class<T>,
    createMethod: CreateMethod = CreateMethod.BIND,
    lifecycleAware: Boolean = false,
): ViewBindingProperty<ViewGroup, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding(lifecycleAware) { viewGroup ->
        ViewBindingCache.getBind(viewBindingClass).bind(viewGroup)
    }
    CreateMethod.INFLATE -> viewBinding(viewBindingClass, attachToRoot = true)
}

/**
 * Inflate new [ViewBinding] with the [ViewGroup][this] as parent
 *
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
@JvmName("viewBindingFragment")
@JvmOverloads
public inline fun <reified T : ViewBinding> ViewGroup.viewBinding(
    attachToRoot: Boolean,
    lifecycleAware: Boolean = false,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(T::class.java, attachToRoot, lifecycleAware)
}

/**
 * Inflate new [ViewBinding] with the [ViewGroup][this] as parent
 *
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
@JvmName("viewBindingFragment")
@JvmOverloads
public fun <T : ViewBinding> ViewGroup.viewBinding(
    viewBindingClass: Class<T>,
    attachToRoot: Boolean,
    lifecycleAware: Boolean = false,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(lifecycleAware) { viewGroup ->
        ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass)
            .inflate(LayoutInflater.from(context), viewGroup, attachToRoot)
    }
}
