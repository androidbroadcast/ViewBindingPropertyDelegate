package dev.androidbroadcast.vbpd.internal

import android.os.Looper
import androidx.annotation.RestrictTo

/**
 * Check if the current thread is the main thread. If not, throw an exception.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkMainThread() {
    check(isMainLooper()) {
        "The method must be called on the main thread"
    }
}

/**
 * Check if the current thread is the main thread. If not, throw an exception with the provided [reason].
 *
 * @param reason The reason why the method must be called on the main thread.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkMainThread(reason: String) {
    check(isMainLooper()) {
        "The method must be called on the main thread. Reason: $reason."
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
private fun isMainLooper(): Boolean {
    return Looper.getMainLooper() === Looper.myLooper()
}
