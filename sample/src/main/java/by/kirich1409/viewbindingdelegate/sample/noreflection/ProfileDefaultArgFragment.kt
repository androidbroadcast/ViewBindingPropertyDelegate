package by.kirich1409.viewbindingdelegate.sample.noreflection

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileDefaultArgFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            button.setOnClickListener {
                // TODO Handle on click
            }
            defaultArgsButton.setOnClickListener {
                startActivity(Intent(context, ProfileDefaultArgActivity::class.java))
            }
        }
    }
}