package by.kirich1409.viewbindingdelegate.sample.noreflection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.sample.R
import by.kirich1409.viewbindingdelegate.sample.databinding.ActivityProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileDefaultArgActivity : AppCompatActivity(R.layout.activity_profile) {

    private val viewBinding by viewBinding(ActivityProfileBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewBinding) {
            profileFragmentContainer
        }
    }
}