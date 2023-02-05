package by.kirich1409.viewbindingdelegate.sample.activity.reflection.inflate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.sample.databinding.ActivityProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class ProfileActivity : AppCompatActivity() {

    private val viewBinding: ActivityProfileBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}