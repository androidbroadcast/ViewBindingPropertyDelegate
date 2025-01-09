package by.kirich1409.viewbindingdelegate.internal.core

import android.os.Looper
import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkMainThread() {
    check(Looper.getMainLooper() === Looper.myLooper()) {
        "The method must be called on the main thread"
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkMainThread(reason: String) {
    check(Looper.getMainLooper() === Looper.myLooper()) {
        "The method must be called on the main thread. Reason: $reason."
    }
}
