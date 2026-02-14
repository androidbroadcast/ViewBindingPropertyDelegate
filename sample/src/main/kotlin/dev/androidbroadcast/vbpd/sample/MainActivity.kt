package dev.androidbroadcast.vbpd.sample

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import dev.androidbroadcast.vbpd.sample.databinding.ActivityMainBinding
import dev.androidbroadcast.vbpd.viewBinding

class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    PersonListFragment.OnPersonClickListener {

    // Without reflection
    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    // Or with reflection
    // private val viewBinding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.WHITE)
        )
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewBinding.appbar)

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.appbar) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onPersonClick(person: Person) {
        supportFragmentManager.beginTransaction()
            .replace<PersonFragment>(
                R.id.fragment_container,
                args = PersonFragment.arguments(person),
                tag = "PersonDetail"
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }
}
