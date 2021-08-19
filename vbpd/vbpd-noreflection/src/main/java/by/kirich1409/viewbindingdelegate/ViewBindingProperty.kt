@file:Suppress("RedundantVisibilityModifier")

package by.kirich1409.viewbindingdelegate

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface ViewBindingProperty<in R : Any, out T : ViewBinding> : ReadOnlyProperty<R, T> {

    @MainThread
    fun clear()
}

@RestrictTo(LIBRARY_GROUP)
public open class LazyViewBindingProperty<in R : Any, out T : ViewBinding>(
    private val onViewDestroyed: (T) -> Unit,
    protected val viewBinder: (R) -> T,
) : ViewBindingProperty<R, T> {

    constructor(viewBinder: (R) -> T) : this({}, viewBinder)

    protected var viewBinding: Any? = null

    @Suppress("UNCHECKED_CAST")
    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        return viewBinding as? T ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    @Suppress("UNCHECKED_CAST")
    @MainThread
    @CallSuper
    public override fun clear() {
        val viewBinding = this.viewBinding as T?
        if (viewBinding != null) {
            onViewDestroyed(viewBinding)
        }
        this.viewBinding = null
    }
}

@RestrictTo(LIBRARY_GROUP)
public open class EagerViewBindingProperty<in R : Any, out T : ViewBinding>(
    private val viewBinding: T
) : ViewBindingProperty<R, T> {

    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        return viewBinding
    }

    @MainThread
    public override fun clear() {
        // Do nothing
    }
}

@RestrictTo(LIBRARY_GROUP)
public abstract class LifecycleViewBindingProperty<in R : Any, out T : ViewBinding>(
    private val viewBinder: (R) -> T,
    private val onViewDestroyed: (T) -> Unit,
) : ViewBindingProperty<R, T> {

    constructor(viewBinder: (R) -> T) : this(viewBinder, {})

    private var viewBinding: T? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        viewBinding?.let { return it }

        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        val currentState = lifecycle.currentState
        if (currentState == Lifecycle.State.DESTROYED && ViewBindingPropertyDelegate.strictMode) {
            error(ERROR_ACCESS_AFTER_DESTROY)
        }

        val viewBinding = viewBinder(thisRef)
        if (currentState == Lifecycle.State.DESTROYED) {
            Log.w(TAG, ERROR_ACCESS_AFTER_DESTROY)
            // We can access to ViewBinding after Fragment.onDestroyView(),
            // but don't save it to prevent memory leak
        } else {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver(this))
            this.viewBinding = viewBinding
        }
        return viewBinding
    }

    @MainThread
    @CallSuper
    public override fun clear() {
        val viewBinding = this.viewBinding
        if (viewBinding != null) {
            onViewDestroyed(viewBinding)
        }
        this.viewBinding = null
    }

    internal fun postClear() {
        if (!mainHandler.post { clear() }) {
            clear()
        }
    }

    private class ClearOnDestroyLifecycleObserver(
        private val property: LifecycleViewBindingProperty<*, *>
    ) : DefaultLifecycleObserver {

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            property.postClear()
        }
    }

    private companion object {

        private val mainHandler = Handler(Looper.getMainLooper())
    }
}

private const val TAG = "ViewBindingProperty"
private const val ERROR_ACCESS_AFTER_DESTROY =
    "Access to viewBinding after Lifecycle is destroyed or hasn't created yet. " +
            "The instance of viewBinding will be not cached."
