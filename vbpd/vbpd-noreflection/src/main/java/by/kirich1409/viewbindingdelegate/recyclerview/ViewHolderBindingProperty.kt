package by.kirich1409.viewbindingdelegate.recyclerview

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
class ViewHolderBindingProperty<in VH : ViewHolder, T : ViewBinding>
@PublishedApi internal constructor(
    private val viewBinder: (VH) -> T
) : ReadOnlyProperty<VH, T> {

    private var viewBinding: T? = null

    override fun getValue(thisRef: VH, property: KProperty<*>): T {
        return viewBinding ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    fun clear() {
        viewBinding = null
    }
}