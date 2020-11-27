@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

fun <V : ViewHolder, T : ViewBinding> ViewHolder.viewBinding(
    viewBinder: (V) -> T
): ViewHolderBindingProperty<V, T> {
    return ViewHolderBindingProperty(viewBinder)
}

inline fun <V : ViewHolder, T : ViewBinding> ViewHolder.viewBinding(
    crossinline vbFactory: (View) -> T,
    crossinline viewProvider: (V) -> View = ViewHolder::itemView
): ViewHolderBindingProperty<V, T> {
    return ViewHolderBindingProperty { viewHolder: V -> viewProvider(viewHolder).let(vbFactory) }
}

inline fun <V : ViewHolder, T : ViewBinding> ViewHolder.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int
): ViewHolderBindingProperty<V, T> {
    return ViewHolderBindingProperty { viewHolder: V ->
        ViewCompat.requireViewById<View>(viewHolder.itemView, viewBindingRootId).let(vbFactory)
    }
}