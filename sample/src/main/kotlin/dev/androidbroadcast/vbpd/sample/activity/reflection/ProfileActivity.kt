package dev.androidbroadcast.vbpd.sample.activity.reflection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.androidbroadcast.vbpd.sample.R
import dev.androidbroadcast.vbpd.sample.databinding.ActivityProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileActivity : AppCompatActivity(R.layout.activity_profile) {

    private val viewBinding: ActivityProfileBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewBinding) {
            profileFragmentContainer
        }
    }
}
