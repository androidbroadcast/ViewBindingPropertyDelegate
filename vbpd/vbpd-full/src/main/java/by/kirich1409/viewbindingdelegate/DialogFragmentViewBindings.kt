@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionDialogFragmentViewBindings")

package by.kirich1409.viewbindingdelegate

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

/**
 * Create new [ViewBinding] associated with the [DialogFragment]'s view
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewBindingRootId Id of the root view of your custom view
 */
@JvmName("viewBindingDialogFragment")
@Deprecated(
    "Use viewBinding delegate",
    ReplaceWith("viewBinding(viewBindingRootId)", "by.kirich1409.viewbindingdelegate.viewBinding")
)
public inline fun <reified T : ViewBinding> DialogFragment.dialogViewBinding(
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<DialogFragment, T> {
    return viewBinding(viewBindingRootId)
}

@JvmName("viewBindingDialogFragment")
@Deprecated(
    "Use viewBinding delegate",
    ReplaceWith("viewBinding(viewBindingClass, viewBindingRootId)", "by.kirich1409.viewbindingdelegate.viewBinding")
)
public fun <T : ViewBinding> DialogFragment.dialogViewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<DialogFragment, T> {
    return viewBinding(viewBindingClass, viewBindingRootId)
}
