package dev.androidbroadcast.vbpd.sample.activity.reflection.inflate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.androidbroadcast.vbpd.CreateMethod
import dev.androidbroadcast.vbpd.sample.databinding.ActivityProfileBinding
import dev.androidbroadcast.vbpd.viewBinding

class ProfileActivity : AppCompatActivity() {

    private val viewBinding: ActivityProfileBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}
