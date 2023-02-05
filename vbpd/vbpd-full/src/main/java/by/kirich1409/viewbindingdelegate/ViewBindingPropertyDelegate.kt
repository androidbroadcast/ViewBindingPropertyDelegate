@file:Suppress("unused")
@file:JvmName("ViewBindingPropertyDelegateUtilsRef")


package by.kirich1409.viewbindingdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * not associated with any host. You need to manually clear reference with
 * [ViewBindingProperty.clear] or not to keep it at all.
 *
 * @param onViewDestroyed Called when the [ViewBinding] will be destroyed
 */
inline fun <R : Any, reified VB : ViewBinding> viewBindingLazy(
    layoutInflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false,
    noinline onViewDestroyed: (VB) -> Unit = {},
): LazyViewBindingProperty<R, VB> {
    return LazyViewBindingProperty(onViewDestroyed) {
        ViewBindingCache.getInflateWithLayoutInflater(VB::class.java)
            .inflate(layoutInflater, parent, attachToParent)
    }
}

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * associated with [lifecycleOwner].
 *
 * @param lifecycleOwner Owner of associated lifecycle
 * @param onViewDestroyed Called when the [ViewBinding] will be destroyed
 */
inline fun <R : Any, reified VB : ViewBinding> viewBindingWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    layoutInflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false,
    noinline onViewDestroyed: (VB) -> Unit = {},
): LifecycleViewBindingProperty<R, VB> {
    return object : LifecycleViewBindingProperty<R, VB>({
        ViewBindingCache.getInflateWithLayoutInflater(VB::class.java)
            .inflate(layoutInflater, parent, attachToParent)
    }, onViewDestroyed) {

        override fun getLifecycleOwner(thisRef: R): LifecycleOwner = lifecycleOwner
    }
}

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * associated with [lifecycle].
 *
 * @param lifecycle Associated lifecycle
 * @param onViewDestroyed Called when the [ViewBinding] will be destroyed
 */
inline fun <R : Any, reified VB : ViewBinding> viewBindingWithLifecycle(
    lifecycle: Lifecycle,
    layoutInflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false,
    noinline onViewDestroyed: (VB) -> Unit = {},
): LifecycleViewBindingProperty<R, VB> {
    return object : LifecycleViewBindingProperty<R, VB>({
        ViewBindingCache.getInflateWithLayoutInflater(VB::class.java)
            .inflate(layoutInflater, parent, attachToParent)
    }, onViewDestroyed) {

        private val lifecycleOwner = LifecycleOwner { lifecycle }

        override fun getLifecycleOwner(thisRef: R): LifecycleOwner = lifecycleOwner
    }
}