package by.kirich1409.viewbindingdelegate

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.core.app.ComponentActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ActivityViewBinder
import by.kirich1409.viewbindingdelegate.internal.DialogFragmentViewBinder
import by.kirich1409.viewbindingdelegate.internal.FragmentViewBinder
import by.kirich1409.viewbindingdelegate.internal.checkIsMainThread
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class ViewBindingProperty<in R : Any, T : ViewBinding>(
    private val viewBinder: (R) -> T
) : ReadOnlyProperty<R, T> {

    internal var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()

    private var thisRef: R? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        check(thisRef !== this.thisRef) {
            "Instance of ViewBindingProperty can't be shared between different properties"
        }

        checkIsMainThread()
        viewBinding?.let { return it }

        getLifecycleOwner(thisRef).lifecycle.addObserver(lifecycleObserver)
        return viewBinder(thisRef).also { viewBinding = it }
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            mainHandler.post {
                viewBinding = null
            }
        }
    }
}
