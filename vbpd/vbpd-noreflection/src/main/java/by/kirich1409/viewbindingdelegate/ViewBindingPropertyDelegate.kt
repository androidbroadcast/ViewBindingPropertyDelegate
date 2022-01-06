@file:Suppress("unused")
@file:JvmName("ViewBindingPropertyDelegateUtils")

package by.kirich1409.viewbindingdelegate

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.checkMainThread

/**
 * Setting for ViewBindingPropertyDelegate library
 */
object ViewBindingPropertyDelegate {

    /**
     * Enable strict checks for how ViewBindingPropertyDelegate is accessed. Throws an [Exception]
     * if a [ViewBinding] is accessed outside of the view lifecycle. As an example, if you try
     * accessing a [Fragment]'s one before [Fragment.onViewCreated] has been called or after
     * [Fragment.onDestroyView] was called, you will get a crash.
     *
     * **By default strict mode is enabled**
     */
    @set:MainThread
    var strictMode = true
        set(value) {
            checkMainThread()
            field = value
        }
}

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * not associated with any host. You need to manually clear reference with
 * [ViewBindingProperty.clear] or not to keep it at all.
 *
 * @param viewBinder Function that create new instance of [ViewBinding]
 * @param onViewDestroyed Called when the [ViewBinding] will be destroyed
 */
fun <R : Any, VB : ViewBinding> viewBindingLazy(
    viewBinder: (R) -> VB,
    onViewDestroyed: (VB) -> Unit = {},
): LazyViewBindingProperty<R, VB> {
    return LazyViewBindingProperty(onViewDestroyed, viewBinder)
}

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * associated with [lifecycleOwner].
 *
 * @param lifecycleOwner Owner of associated lifecycle
 * @param viewBinder Function that create new instance of [ViewBinding]
 * @param onViewDestroyed Called when the [ViewBinding] will be destroyed
 */
fun <R : Any, VB : ViewBinding> viewBindingWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    viewBinder: (R) -> VB,
    onViewDestroyed: (VB) -> Unit = {},
): LifecycleViewBindingProperty<R, VB> {
    return object : LifecycleViewBindingProperty<R, VB>(viewBinder, onViewDestroyed) {

        override fun getLifecycleOwner(thisRef: R): LifecycleOwner = lifecycleOwner
    }
}

/**
 * Create new [ViewBindingPropertyDelegate] that will be initialized lazy and
 * associated with [lifecycle].
 *
 * @param lifecycle Associated lifecycle
 * @param viewBinder Function that create new instance of [ViewBinding]
 * @param onViewDestroyed Called when the [ViewBinding] will be destroyed
 */
fun <R : Any, VB : ViewBinding> viewBindingWithLifecycle(
    lifecycle: Lifecycle,
    viewBinder: (R) -> VB,
    onViewDestroyed: (VB) -> Unit = {},
): LifecycleViewBindingProperty<R, VB> {
    return object : LifecycleViewBindingProperty<R, VB>(viewBinder, onViewDestroyed) {

        private val lifecycleOwner = LifecycleOwner { lifecycle }

        override fun getLifecycleOwner(thisRef: R): LifecycleOwner = lifecycleOwner
    }
}