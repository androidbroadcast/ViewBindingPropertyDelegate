package by.kirich1409.viewbindingdelegate.recyclerview

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
class ViewHolderBindingProperty<in V : ViewHolder, T : ViewBinding>
@PublishedApi internal constructor(
    private val viewBinder: (V) -> T
) : ReadOnlyProperty<V, T> {

    private var viewBinding: T? = null

    override fun getValue(thisRef: V, property: KProperty<*>): T {
        return viewBinding ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    fun clear() {
        viewBinding = null
    }
}