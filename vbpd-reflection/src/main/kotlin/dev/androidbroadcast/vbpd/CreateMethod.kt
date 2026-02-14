package dev.androidbroadcast.vbpd

import androidx.viewbinding.ViewBinding

/**
 * Method that will be used to create [ViewBinding].
 */
public enum class CreateMethod {
    /**
     * Use `ViewBinding.bind(View)`
     */
    BIND,

    /**
     * Use `ViewBinding.inflate(LayoutInflater, ViewGroup, boolean)`
     */
    INFLATE,
}
