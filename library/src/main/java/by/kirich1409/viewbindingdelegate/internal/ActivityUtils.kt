package by.kirich1409.viewbindingdelegate.internal

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat

@RestrictTo(RestrictTo.Scope.LIBRARY)
@PublishedApi
internal fun <V : View> Activity.requireViewByIdCompat(@IdRes viewId: Int): V {
    return ActivityCompat.requireViewById(this, viewId)
}
