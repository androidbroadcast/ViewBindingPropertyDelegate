@file:Suppress("RedundantVisibilityModifier")

package by.kirich1409.viewbindingdelegate

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


interface ViewBindingProperty<in R : Any, T : ViewBinding> : ReadOnlyProperty<R, T> {

    @MainThread
    fun clear()
}

@RestrictTo(LIBRARY_GROUP)
public open class LazyViewBindingProperty<in R : Any, T : ViewBinding>(
    protected val viewBinder: (R) -> T
) : ViewBindingProperty<R, T> {

    protected var viewBinding: T? = null

    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        return viewBinding ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    @MainThread
    public override fun clear() {
        viewBinding = null
    }
}

@RestrictTo(LIBRARY_GROUP)
public abstract class LifecycleViewBindingProperty<in R : Any, T : ViewBinding>(
    private val viewBinder: (R) -> T
) : ViewBindingProperty<R, T> {

    private var viewBinding: T? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        viewBinding?.let { return it }

        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        val viewBinding = viewBinder(thisRef)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            // We can access to ViewBinding after on destroy, but don't save to prevent memory leak
        } else {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver())
            this.viewBinding = viewBinding
        }
        return viewBinding
    }

    @MainThread
    public override fun clear() {
        mainHandler.post { viewBinding = null }
    }

    private inner class ClearOnDestroyLifecycleObserver : DefaultLifecycleObserver {

        @MainThread
        override fun onDestroy(owner: LifecycleOwner): Unit = clear()
    }

    private companion object {

        private val mainHandler = Handler(Looper.getMainLooper())
    }
}
