package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.RestrictTo
import androidx.viewbinding.ViewBinding

/**
 * Create instance of [ViewBinding] from a [View]
 */
interface ViewBinder<T : ViewBinding> {

    fun bind(view: View): T
}

@PublishedApi
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal inline fun <T : ViewBinding> viewBinder(crossinline bindView: (View) -> T): ViewBinder<T> {
    return object : ViewBinder<T> {

        override fun bind(view: View) = bindView(view)
    }
}