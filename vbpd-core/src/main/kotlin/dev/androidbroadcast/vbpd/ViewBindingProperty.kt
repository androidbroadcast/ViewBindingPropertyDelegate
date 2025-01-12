package dev.androidbroadcast.vbpd

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.viewbinding.ViewBinding
import dev.androidbroadcast.vbpd.internal.checkMainThread
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Base ViewBindingProperty interface that provides access to operations in the property delegate.
 */
public interface ViewBindingProperty<in R : Any, out T : ViewBinding> : ReadOnlyProperty<R, T> {

    /**
     * Clear all cached data. Will be called when own object destroys view
     */
    @MainThread
    public fun clear() {
        // Do nothing
    }
}

/**
 * Eager implementation of [ViewBindingProperty] that holds [ViewBinding] instance.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public open class EagerViewBindingProperty<in R : Any, out T : ViewBinding>(
    private val viewBinding: T,
) : ViewBindingProperty<R, T> {

    @MainThread
    public override fun getValue(
        thisRef: R,
        property: KProperty<*>,
    ): T = viewBinding
}

/**
 * Lazy implementation of [ViewBindingProperty] that creates [ViewBinding] instance on the first access.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public open class LazyViewBindingProperty<in R : Any, T : ViewBinding>(
    private val viewBinder: (R) -> T,
) : ViewBindingProperty<R, T> {

    private var viewBinding: T? = null

    @MainThread
    public override fun getValue(
        thisRef: R,
        property: KProperty<*>,
    ): T {
        checkMainThread("Access to ViewBinding from non UI (Main) thread forbidden")
        return viewBinding ?: viewBinder(thisRef).also { viewBinding = it }
    }

    @MainThread
    @CallSuper
    public override fun clear() {
        checkMainThread()
        viewBinding = null
    }
}
