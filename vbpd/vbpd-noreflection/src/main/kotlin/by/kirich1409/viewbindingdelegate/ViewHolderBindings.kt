@file:Suppress("unused")
@file:JvmName("ViewHolderBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.requireViewByIdCompat

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 */
@Suppress("UnusedReceiverParameter")
fun <VH : ViewHolder, T : ViewBinding> ViewHolder.viewBinding(viewBinder: (VH) -> T): ViewBindingProperty<VH, T> {
    return LazyViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [ViewHolder]. By default call [ViewHolder.itemView]
 */
@Suppress("UnusedReceiverParameter")
inline fun <VH : ViewHolder, T : ViewBinding> ViewHolder.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (VH) -> View = ViewHolder::itemView,
): ViewBindingProperty<VH, T> {
    return LazyViewBindingProperty { viewHolder: VH -> viewProvider(viewHolder).let(vbFactory) }
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param vbFactory Function that creates a new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as a root for the view binding
 */
@Suppress("UnusedReceiverParameter")
inline fun <VH : ViewHolder, T : ViewBinding> ViewHolder.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<VH, T> {
    return LazyViewBindingProperty { viewHolder: VH ->
        vbFactory(viewHolder.itemView.requireViewByIdCompat(viewBindingRootId))
    }
}
