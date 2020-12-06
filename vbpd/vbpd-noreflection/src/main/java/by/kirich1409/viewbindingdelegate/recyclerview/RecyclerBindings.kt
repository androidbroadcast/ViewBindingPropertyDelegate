@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 */
fun <VH : ViewHolder, T : ViewBinding> VH.viewBinding(
    viewBinder: (VH) -> T
): ViewHolderBindingProperty<VH, T> {
    return ViewHolderBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [ViewHolder]. By default call [ViewHolder.itemView]
 */
inline fun <VH : ViewHolder, T : ViewBinding> VH.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (VH) -> View = ViewHolder::itemView
): ViewHolderBindingProperty<VH, T> {
    return ViewHolderBindingProperty { viewHolder: VH -> viewProvider(viewHolder).let(vbFactory) }
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
inline fun <VH : ViewHolder, T : ViewBinding> VH.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewHolderBindingProperty<VH, T> {
    return ViewHolderBindingProperty { viewHolder: VH ->
        ViewCompat.requireViewById<View>(viewHolder.itemView, viewBindingRootId).let(vbFactory)
    }
}