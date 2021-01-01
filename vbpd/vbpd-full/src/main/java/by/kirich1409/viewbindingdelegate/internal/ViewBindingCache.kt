@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.internal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method

object ViewBindingCache {

    private val inflateCache = mutableMapOf<Class<out ViewBinding>, InflateViewBinding<ViewBinding>>()
    private val bindCache = mutableMapOf<Class<out ViewBinding>, BindViewBinding<ViewBinding>>()

    @Suppress("UNCHECKED_CAST")
    @RestrictTo(LIBRARY)
    internal fun <T : ViewBinding> getInflateWithLayoutInflater(viewBindingClass: Class<T>): InflateViewBinding<T> {
        return inflateCache.getOrPut(viewBindingClass) { InflateViewBinding(viewBindingClass) } as InflateViewBinding<T>
    }

    @Suppress("UNCHECKED_CAST")
    @RestrictTo(LIBRARY)
    internal fun <T : ViewBinding> getBind(viewBindingClass: Class<T>): BindViewBinding<T> {
        return bindCache.getOrPut(viewBindingClass) { BindViewBinding(viewBindingClass) } as BindViewBinding<T>
    }

    /**
     * Reset all cached data
     */
    fun clear() {
        inflateCache.clear()
        bindCache.clear()
    }
}

/**
 * Wrapper of ViewBinding.inflate(LayoutInflater, ViewGroup, Boolean)
 */
@RestrictTo(LIBRARY)
internal abstract class InflateViewBinding<out VB : ViewBinding>(
    private val inflateViewBinding: Method
) {

    @Suppress("UNCHECKED_CAST")
    abstract fun inflate(layoutInflater: LayoutInflater): VB

    @Suppress("UNCHECKED_CAST")
    abstract fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB
}

@RestrictTo(LIBRARY)
@Suppress("FunctionName")
internal fun <VB : ViewBinding> InflateViewBinding(viewBindingClass: Class<VB>): InflateViewBinding<VB> {
    try {
        val method = viewBindingClass.getMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
        )
        return FullInflateViewBinding(method)
    } catch (e: NoSuchMethodException) {
        val method = viewBindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java)
        return MergeInflateViewBinding(method)
    }
}

@RestrictTo(LIBRARY)
internal class FullInflateViewBinding<out VB : ViewBinding>(
    private val inflateViewBinding: Method
) : InflateViewBinding<VB>(inflateViewBinding) {

    @Suppress("UNCHECKED_CAST")
    override fun inflate(layoutInflater: LayoutInflater): VB {
        return inflateViewBinding(null, layoutInflater, null, false) as VB
    }

    @Suppress("UNCHECKED_CAST")
    override fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB {
        return inflateViewBinding(null, layoutInflater, parent, attachToParent) as VB
    }
}


@RestrictTo(LIBRARY)
internal class MergeInflateViewBinding<out VB : ViewBinding>(
    private val inflateViewBinding: Method
) : InflateViewBinding<VB>(inflateViewBinding) {

    @Suppress("UNCHECKED_CAST")
    override fun inflate(layoutInflater: LayoutInflater): VB =
        error("Specify parent to inflate ${InflateViewBinding::class.java.simpleName}")

    @Suppress("UNCHECKED_CAST")
    override fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB {
        require(attachToParent) {
            "${InflateViewBinding::class.java.simpleName} supports inflate only with attachToParent=true"
        }
        return inflateViewBinding(null, layoutInflater, parent) as VB
    }
}

/**
 * Wrapper of ViewBinding.bind(View)
 */
@RestrictTo(LIBRARY)
internal class BindViewBinding<out VB : ViewBinding>(viewBindingClass: Class<VB>) {

    private val bindViewBinding = viewBindingClass.getMethod("bind", View::class.java)

    @Suppress("UNCHECKED_CAST")
    fun bind(view: View): VB {
        return bindViewBinding(null, view) as VB
    }
}
