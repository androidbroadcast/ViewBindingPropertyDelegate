package by.kirich1409.viewbindingdelegate.sample.noreflection

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.ActivityProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileActivity : AppCompatActivity(R.layout.activity_profile) {

    private val viewBinding by viewBinding(ActivityProfileBinding::bind, R.id.container)
}