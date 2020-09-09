package by.kirich1409.viewbindingdelegate.internal

import android.os.Looper
import androidx.annotation.RestrictTo

@get:RestrictTo(RestrictTo.Scope.LIBRARY)
internal inline val isMainThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun checkIsMainThread() = check(isMainThread)