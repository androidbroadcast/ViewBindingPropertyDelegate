@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionViewHolderBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class ViewHolderBinder<T : ViewBinding>(private val viewBindingClass: Class<T>) {

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
    fun bind(viewHolder: ViewHolder): T {
        return bindViewMethod(null, viewHolder.itemView) as T
    }
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewHolder.viewBinding(
): ViewBindingProperty<ViewHolder, T> {
    return viewBinding(ViewHolderBinder(T::class.java)::bind)
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> ViewHolder.viewBinding(
    viewBindingClass: Class<T>,
): ViewBindingProperty<ViewHolder, T> {
    return viewBinding(ViewHolderBinder(viewBindingClass)::bind)
}
