@file:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)

package by.kirich1409.viewbindingdelegate.internal

import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding


@Suppress("NOTHING_TO_INLINE")
@RestrictTo(RestrictTo.Scope.LIBRARY)
inline fun <V : View> View.requireViewByIdCompat(@IdRes id: Int): V {
    return ViewCompat.requireViewById(this, id)
}

@Suppress("NOTHING_TO_INLINE")
@RestrictTo(RestrictTo.Scope.LIBRARY)
inline fun <V : View> Activity.requireViewByIdCompat(@IdRes id: Int): V {
    return ActivityCompat.requireViewById(this, id)
}

/**
 * Utility to find root view for ViewBinding in Activity
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun findRootView(activity: Activity): View {
    val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
    checkNotNull(contentView) { "Activity has no content view" }
    return when (contentView.childCount) {
        1 -> contentView.getChildAt(0)
        0 -> error("Content view has no children. Provide root view explicitly")
        else -> error("More than one child view found in Activity content view")
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun DialogFragment.getRootView(viewBindingRootId: Int): View {
    val dialog = checkNotNull(dialog) {
        "DialogFragment doesn't have dialog. Use viewBinding delegate after onCreateDialog"
    }
    val window = checkNotNull(dialog.window) { "Fragment's Dialog has no window" }
    return with(window.decorView) { if (viewBindingRootId != 0) requireViewByIdCompat(viewBindingRootId) else this }
}

internal val EMPTY_VB_CALLBACK: (ViewBinding) -> Unit = { _ -> }

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun <T : ViewBinding> emptyVbCallback():(T) -> Unit {
    return EMPTY_VB_CALLBACK
}

internal fun checkMainThread() {
    check(Looper.getMainLooper() === Looper.myLooper()) {
        "The method must be called on the main thread"
    }
}

internal fun checkMainThread(reason: String) {
    check(Looper.getMainLooper() === Looper.myLooper()) {
        "The method must be called on the main thread. Reason: $reason."
    }
}