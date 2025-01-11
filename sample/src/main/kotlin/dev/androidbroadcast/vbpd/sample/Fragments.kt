package dev.androidbroadcast.vbpd.sample

import android.os.Parcelable
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty

internal inline fun <reified T : Parcelable> parcelableArgument(name: String): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T> {

        private var value: T? = null

        override fun getValue(thisRef: Fragment, property: kotlin.reflect.KProperty<*>): T {
            return value ?: requireNotNull(
                BundleCompat.getParcelable(
                    thisRef.requireArguments(),
                    name,
                    T::class.java
                )
            ) {
                "Argument $name is missing"
            }.also {
                value = it
            }
        }
    }
}
