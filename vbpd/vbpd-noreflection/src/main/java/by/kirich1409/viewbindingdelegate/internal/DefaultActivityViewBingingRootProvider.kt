package by.kirich1409.viewbindingdelegate.internal

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object DefaultActivityViewBingingRootProvider {

    fun findRootView(activity: Activity): View {
        val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
        checkNotNull(contentView) { "Activity has no content view" }
        return when (contentView.childCount) {
            0 -> error("Content view has no children. Provide root view explicitly")
            1 -> contentView.getChildAt(0)
            else -> error("More than one child view found in Activity content view")
        }
    }
}