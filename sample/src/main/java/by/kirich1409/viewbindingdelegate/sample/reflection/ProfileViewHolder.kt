@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.sample.Profile
import by.kirich1409.viewbindingdelegate.sample.databinding.ItemProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewBinding: ItemProfileBinding by viewBinding()

    fun bind(profile: Profile) {
        with(viewBinding) {
            name.text = profile.name
            status.text = profile.status
        }
    }
}