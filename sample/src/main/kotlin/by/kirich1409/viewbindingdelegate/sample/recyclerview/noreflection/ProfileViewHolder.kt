@file:Suppress("CanBeParameter", "unused")

package by.kirich1409.viewbindingdelegate.sample.recyclerview.noreflection

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.sample.databinding.ItemProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val viewBinding: ItemProfileBinding by viewBinding()
}