@file:Suppress("unused")

package by.kirich1409.viewbindingdelegate.sample.reflection

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.ActivityProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileActivity : AppCompatActivity(R.layout.activity_profile) {

    private val viewBinding: ActivityProfileBinding by viewBinding(R.id.container)
}
