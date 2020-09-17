@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.sample.databinding.ActivityProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileActivity : AppCompatActivity(R.layout.activity_profile) {

    private val viewBindingUsingReflection: ActivityProfileBinding by viewBinding(R.id.container)

    private val viewBindingWithoutReflection by viewBinding(ActivityProfileBinding::bind, R.id.container)
}
