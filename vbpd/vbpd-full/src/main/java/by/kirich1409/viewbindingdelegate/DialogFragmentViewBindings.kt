@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionDialogFragmentViewBindings")

package by.kirich1409.viewbindingdelegate

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.internal.ViewBindingCache

/**
 * Create new [ViewBinding] associated with the [DialogFragment]'s view
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewBindingRootId Id of the root view from your custom view
 */
@JvmName("viewBindingDialogFragment")
public inline fun <reified T : ViewBinding> DialogFragment.dialogViewBinding(
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<DialogFragment, T> {
    return dialogViewBinding(T::class.java, viewBindingRootId)
}

/**
 * Create new [ViewBinding] associated with the [DialogFragment]'s view
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param viewBindingRootId Id of the root view from your custom view
 */
@JvmName("viewBindingDialogFragment")
public fun <T : ViewBinding> DialogFragment.dialogViewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<DialogFragment, T> {
    return dialogViewBinding {
        ViewBindingCache.getBind(viewBindingClass).bind(this.getRootView(viewBindingRootId))
    }
}

@RestrictTo(LIBRARY)
private fun DialogFragment.getRootView(viewBindingRootId: Int): View {
    val dialog = checkNotNull(dialog) { "Dialog hasn't been created yet" }
    val window = checkNotNull(dialog.window) { "Dialog has no window" }
    return with(window.decorView) { if (viewBindingRootId != 0) findViewById(viewBindingRootId) else this }
}
