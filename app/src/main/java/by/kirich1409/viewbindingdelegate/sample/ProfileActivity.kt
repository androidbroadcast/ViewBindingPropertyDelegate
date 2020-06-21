@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.sample.databinding.ProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileActivity : AppCompatActivity(R.layout.profile) {

    private val viewBindingUsingReflection: ProfileBinding by viewBinding(R.id.container)

    private val viewBindingWithoutReflection by viewBinding { activity ->
        ProfileBinding.bind(activity.findViewById(R.id.container))
    }
}
