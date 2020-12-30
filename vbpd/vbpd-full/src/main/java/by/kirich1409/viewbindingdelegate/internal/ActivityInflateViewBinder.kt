package by.kirich1409.viewbindingdelegate.internal

import android.app.Activity
import android.view.LayoutInflater
import androidx.annotation.RestrictTo
import androidx.viewbinding.ViewBinding

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal class ActivityInflateViewBinder<T : ViewBinding>(
    private val viewBindingClass: Class<T>,
) {

    /**
     * Cache static method `ViewBinding.inflate(LayoutInflater)`
     */
    private val bindViewMethod by lazy(LazyThreadSafetyMode.NONE) {
        viewBindingClass.getMethod("inflate", LayoutInflater::class.java)
    }

    /**
     * Create new [ViewBinding] instance
     */
    @Suppress("UNCHECKED_CAST")
    fun bind(activity: Activity): T {
        return bindViewMethod(null, activity.layoutInflater) as T
    }
}