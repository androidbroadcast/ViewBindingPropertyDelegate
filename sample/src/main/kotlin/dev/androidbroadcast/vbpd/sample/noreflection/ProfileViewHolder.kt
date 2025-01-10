@file:Suppress("CanBeParameter", "unused")

package dev.androidbroadcast.vbpd.sample.noreflection

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.androidbroadcast.vbpd.sample.databinding.ItemProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val viewBinding: ItemProfileBinding by viewBinding()
}
