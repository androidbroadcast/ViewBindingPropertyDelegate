@file:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)

package dev.androidbroadcast.vbpd.internal

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@RestrictTo(RestrictTo.Scope.LIBRARY)
public fun <V : View> View.requireViewByIdCompat(@IdRes id: Int): V {
    return ViewCompat.requireViewById(this, id)
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
public fun <V : View> Activity.requireViewByIdCompat(@IdRes id: Int): V {
    return ActivityCompat.requireViewById(this, id)
}

/**
 * Utility to find root view for ViewBinding in Activity
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public fun findRootView(activity: Activity): View {
    val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
    checkNotNull(contentView) { "Activity has no content view" }
    return when (contentView.childCount) {
        1 -> contentView.getChildAt(0)
        0 -> error("Content view has no children. Provide a root view explicitly")
        else -> error("More than one child view found in the Activity content view")
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public fun DialogFragment.findRootView(@IdRes viewBindingRootId: Int): View {
    if (showsDialog) {
        val dialog = checkNotNull(dialog) {
            "DialogFragment doesn't have a dialog. Use viewBinding delegate after onCreateDialog"
        }
        val window = checkNotNull(dialog.window) { "Fragment's Dialog has no window" }
        return with(window.decorView) {
            if (viewBindingRootId != 0) requireViewByIdCompat(viewBindingRootId) else this
        }
    } else {
        return requireView().findViewById(viewBindingRootId)
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun <T : Any> weakReference(value: T? = null): ReadWriteProperty<Any, T?> {
    return object : ReadWriteProperty<Any, T?> {

        private var weakRef = WeakReference(value)

        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            return weakRef.get()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            weakRef = WeakReference(value)
        }
    }
}
