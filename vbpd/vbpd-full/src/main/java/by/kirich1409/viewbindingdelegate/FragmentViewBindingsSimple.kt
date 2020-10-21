@file:Suppress("RedundantVisibilityModifier", "unused")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class FragmentViewBinder<T : ViewBinding>(private val viewBindingClass: Class<T>) {

    /**
     * Cache static method `ViewBinding.bind(View)`
     */
    private val bindViewMethod by lazy(LazyThreadSafetyMode.NONE) {
        viewBindingClass.getMethod("bind", View::class.java)
    }

    /**
     * Create new [ViewBinding] instance
     */
    @Suppress("UNCHECKED_CAST")
    fun bind(fragment: Fragment): T {
        return bindViewMethod(null, fragment.requireView()) as T
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> Fragment.viewBinding(): ViewBindingProperty<Fragment, T> {
    return viewBinding(FragmentViewBinder(T::class.java)::bind)
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> Fragment.viewBinding(viewBindingClass: Class<T>): ViewBindingProperty<Fragment, T> {
    return viewBinding(FragmentViewBinder(viewBindingClass)::bind)
}
