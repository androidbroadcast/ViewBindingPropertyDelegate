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
import by.kirich1409.viewbindingdelegate.internal.core.checkMainThread
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface ViewBindingProperty<in R : Any, out T : ViewBinding> : ReadOnlyProperty<R, T> {

    @MainThread
    fun clear()
}

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
    private val onViewDestroyed: (T) -> Unit = {},
) : ViewBindingProperty<R, T> {

    private var viewBinding: T? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        checkMainThread("access to ViewBinding from non UI (Main) thread")
        viewBinding?.let { return it }

        if (!isViewInitialized(thisRef)) {
            error(viewNotInitializedReadableErrorMessage(thisRef))
        }

        if (ViewBindingPropertyDelegate.strictMode) {
            runStrictModeChecks(thisRef)
        }

        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            this.viewBinding = null
            Log.w(TAG, ERROR_ACCESS_AFTER_DESTROY)
            // We can access ViewBinding after Fragment.onDestroyView() was called,
            // but it isn't saved to prevent memory leaks
            return viewBinder(thisRef)
        } else {
            val viewBinding = viewBinder(thisRef)
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver(this))
            this.viewBinding = viewBinding
            return viewBinding
        }
    }

    private fun runStrictModeChecks(thisRef: R) {
        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            error(ERROR_ACCESS_AFTER_DESTROY)
        }
    }

    /**
     * Check if the host view is ready to create viewBinding
     */
    protected open fun isViewInitialized(thisRef: R): Boolean = true

    protected open fun viewNotInitializedReadableErrorMessage(
        thisRef: R
    ): String = ERROR_VIEW_NOT_INITIALIZED

    @MainThread
    @CallSuper
    public override fun clear() {
        checkMainThread()
        val viewBinding = viewBinding
        this.viewBinding = null
        if (viewBinding != null) {
            onViewDestroyed(viewBinding)
        }
    }

    @RestrictTo(LIBRARY_GROUP)
    protected fun postClear() {
        if (!mainHandler.post { clear() }) {
            clear()
        }
    }

    private class ClearOnDestroyLifecycleObserver(
        private val property: LifecycleViewBindingProperty<*, *>
    ) : DefaultLifecycleObserver {

        /**
         * Problem with desugaring in some AGP versions: https://issuetracker.google.com/issues/194289155#comment21
         * Solution 1 - override ALL DefaultLifecycleObserver's methods -
         * https://stackoverflow.com/questions/59067743/what-is-this-error-and-why-this-doesnt-happen-when-i-dont-use-library-like-a-c/60873211#60873211
         * Solution 2 - replace DefaultLifecycleObserver with LifecycleObserver
         */

        override fun onCreate(owner: LifecycleOwner) {
            // Do nothing
        }

        override fun onStart(owner: LifecycleOwner) {
            // Do nothing
        }

        override fun onResume(owner: LifecycleOwner) {
            // Do nothing
        }

        override fun onPause(owner: LifecycleOwner) {
            // Do nothing
        }

        override fun onStop(owner: LifecycleOwner) {
            // Do nothing
        }

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
private const val ERROR_VIEW_NOT_INITIALIZED =
    "Host view isn't ready. LifecycleViewBindingProperty.isViewInitialized return false"
private const val ERROR_ACCESS_BEFORE_VIEW_READY =
    "Host view isn't ready to create a ViewBinding instance"
private const val ERROR_ACCESS_AFTER_DESTROY =
    "Accessing viewBinding after Lifecycle is destroyed or hasn't been created yet. " +
            "The instance of viewBinding isn't cached."