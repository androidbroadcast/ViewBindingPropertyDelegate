package by.kirich1409.viewbindingdelegate.internal

import android.view.View
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class ReflectingFragmentViewBinder<T : ViewBinding>(private val viewBindingClass: Class<T>) {

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


@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class FragmentViewBinder<F: Fragment, T : ViewBinding>(
    private val viewBinder: (View) -> T,
    private val viewFinder: (F) -> View
) {

    /**
     * Create new [ViewBinding] instance
     */
    fun bind(fragment: F): T {
        return viewBinder(viewFinder(fragment))
    }
}
