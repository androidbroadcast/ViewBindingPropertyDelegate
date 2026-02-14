package dev.androidbroadcast.vbpd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.viewbinding.ViewBinding
import dev.androidbroadcast.vbpd.InflateViewBinding.Full
import dev.androidbroadcast.vbpd.InflateViewBinding.Merge
import java.lang.reflect.Method

private sealed interface ViewBindingCacheImpl {
    fun <T : ViewBinding> getInflateWithLayoutInflater(viewBindingClass: Class<T>): InflateViewBinding<T>

    fun <T : ViewBinding> getBind(viewBindingClass: Class<T>): BindViewBinding<T>

    fun clear() {}

    class Default : ViewBindingCacheImpl {
        private val inflateCache = mutableMapOf<Class<out ViewBinding>, InflateViewBinding<*>>()
        private val bindCache = mutableMapOf<Class<out ViewBinding>, BindViewBinding<*>>()

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewBinding> getInflateWithLayoutInflater(viewBindingClass: Class<T>): InflateViewBinding<T> =
            inflateCache.getOrPut(viewBindingClass) {
                InflateViewBinding(viewBindingClass)
            } as InflateViewBinding<T>

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewBinding> getBind(viewBindingClass: Class<T>): BindViewBinding<T> =
            bindCache.getOrPut(viewBindingClass) {
                BindViewBinding(viewBindingClass)
            } as BindViewBinding<T>

        /**
         * Reset all cached data
         */
        override fun clear() {
            inflateCache.clear()
            bindCache.clear()
        }
    }

    data object Noop : ViewBindingCacheImpl {
        override fun <T : ViewBinding> getInflateWithLayoutInflater(viewBindingClass: Class<T>): InflateViewBinding<T> =
            InflateViewBinding(viewBindingClass)

        override fun <T : ViewBinding> getBind(viewBindingClass: Class<T>): BindViewBinding<T> = BindViewBinding(viewBindingClass)
    }
}

/**
 * Cache for ViewBinding.inflate(LayoutInflater, ViewGroup, Boolean) and ViewBinding.bind(View)
 */
public object ViewBindingCache {
    private var impl: ViewBindingCacheImpl = ViewBindingCacheImpl.Noop

    @RestrictTo(LIBRARY)
    @PublishedApi
    internal fun <T : ViewBinding> getInflateWithLayoutInflater(viewBindingClass: Class<T>): InflateViewBinding<T> =
        impl.getInflateWithLayoutInflater(viewBindingClass)

    @RestrictTo(LIBRARY)
    internal fun <T : ViewBinding> getBind(viewBindingClass: Class<T>): BindViewBinding<T> = impl.getBind(viewBindingClass)

    /**
     * Clear all cached data
     */
    public fun clear() {
        impl.clear()
    }

    /**
     * Enable or disable caching accessing ViewBinding factories methods. Enabled by default.
     */
    public fun setEnabled(enabled: Boolean) {
        impl = if (enabled) ViewBindingCacheImpl.Default() else ViewBindingCacheImpl.Noop
    }
}

/**
 * Wrapper of ViewBinding.inflate(LayoutInflater, ViewGroup, Boolean)
 */
@RestrictTo(LIBRARY)
internal fun <VB : ViewBinding> InflateViewBinding(viewBindingClass: Class<VB>): InflateViewBinding<VB> {
    // Depending on XML layout for ViewBinding inflate function with attaching to parent can exist or not
    try {
        return viewBindingClass
            .getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java,
            ).let(::Full)
    } catch (e: NoSuchMethodException) {
        return viewBindingClass
            .getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
            ).let(::Merge)
    }
}

/**
 * Wrapper of ViewBinding.inflate(LayoutInflater, ViewGroup, Boolean)
 */
@RestrictTo(LIBRARY)
@PublishedApi
internal sealed interface InflateViewBinding<out VB : ViewBinding> {
    fun inflate(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean,
    ): VB

    @RestrictTo(LIBRARY)
    class Full<out VB : ViewBinding>(
        private val inflateViewBinding: Method,
    ) : InflateViewBinding<VB> {
        @Suppress("UNCHECKED_CAST")
        override fun inflate(
            layoutInflater: LayoutInflater,
            parent: ViewGroup?,
            attachToParent: Boolean,
        ): VB = inflateViewBinding(null, layoutInflater, parent, attachToParent) as VB
    }

    @RestrictTo(LIBRARY)
    class Merge<out VB : ViewBinding>(
        private val inflateViewBinding: Method,
    ) : InflateViewBinding<VB> {
        @Suppress("UNCHECKED_CAST")
        override fun inflate(
            layoutInflater: LayoutInflater,
            parent: ViewGroup?,
            attachToParent: Boolean,
        ): VB {
            require(attachToParent) {
                "${InflateViewBinding::class.java.simpleName} " +
                    "supports inflate only with attachToParent=true"
            }
            return inflateViewBinding(null, layoutInflater, parent) as VB
        }
    }
}

/**
 * Wrapper of ViewBinding.bind(View)
 */
@RestrictTo(LIBRARY)
internal class BindViewBinding<out VB : ViewBinding>(
    viewBindingClass: Class<VB>,
) {
    private val bindViewBinding by lazy { viewBindingClass.getMethod("bind", View::class.java) }

    @Suppress("UNCHECKED_CAST")
    fun bind(view: View): VB = bindViewBinding(null, view) as VB
}
