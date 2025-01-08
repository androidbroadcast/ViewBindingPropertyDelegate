@file:Suppress("RedundantVisibilityModifier")

package by.kirich1409.viewbindingdelegate

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.core.checkMainThread
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Base ViewBindingProperty interface that provides access to operations in the property delegate.
 */
interface ViewBindingProperty<in R : Any, out T : ViewBinding> : ReadOnlyProperty<R, T> {

    @MainThread
    fun clear()
}

public open class LazyViewBindingProperty<in R : Any, out T : ViewBinding>(
    protected val viewBinder: (R) -> T,
) : ViewBindingProperty<R, T> {

    protected var viewBinding: Any? = null

    @Suppress("UNCHECKED_CAST")
    @MainThread
    public override fun getValue(
        thisRef: R,
        property: KProperty<*>,
    ): T {
        return viewBinding as? T ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    @MainThread
    @CallSuper
    public override fun clear() {
        this.viewBinding = null
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public open class EagerViewBindingProperty<in R : Any, out T : ViewBinding>(
    private val viewBinding: T,
) : ViewBindingProperty<R, T> {

    @MainThread
    public override fun getValue(
        thisRef: R,
        property: KProperty<*>,
    ): T = viewBinding

    @MainThread
    public override fun clear() {
        // Do nothing
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public abstract class BaseViewBindingProperty<in R : Any, T : ViewBinding>(
    private val viewBinder: (R) -> T,
) : ViewBindingProperty<R, T> {

    protected var viewBinding: T? = null

    @MainThread
    public override fun getValue(
        thisRef: R,
        property: KProperty<*>,
    ): T {
        checkMainThread("Access to ViewBinding from non UI (Main) thread forbidden")
        viewBinding?.let { return@getValue it }
        this.viewBinding = null
        return viewBinder(thisRef).also { this.viewBinding = it }
    }

    @MainThread
    @CallSuper
    public override fun clear() {
        checkMainThread()
        this.viewBinding = null
    }
}
