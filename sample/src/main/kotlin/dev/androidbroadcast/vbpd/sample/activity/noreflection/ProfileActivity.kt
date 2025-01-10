package dev.androidbroadcast.vbpd.sample.activity.noreflection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.ActivityProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileActivity : AppCompatActivity(R.layout.activity_profile) {

    private val viewBinding by viewBinding(ActivityProfileBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewBinding) {
            profileFragmentContainer
        }
    }
}
