package by.kirich1409.viewbindingdelegate.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentABinding
import by.kirich1409.viewbindingdelegate.sample.databinding.FragmentBBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    fun onFragmentAClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentB())
            .setTransition(TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

    fun onFragmentBClick() {
        supportFragmentManager.popBackStack()
    }
}

class FragmentA : Fragment(R.layout.fragment_a) {

    private val viewBinding: FragmentABinding by viewBinding(FragmentABinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOG_TAG, "FragmentA@${hashCode()} onViewCreated binding = ${viewBinding.javaClass.simpleName}@${viewBinding.hashCode()}")
        viewBinding.root.text = "Fragment A onViewCreated"
        viewBinding.root.setOnClickListener { (context as? MainActivity)?.onFragmentAClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LOG_TAG, "FragmentA@${hashCode()} onDestroyView binding = ${viewBinding.javaClass.simpleName}@${viewBinding.hashCode()}")
    }
}

class FragmentB : Fragment(R.layout.fragment_b) {

    private val viewBinding by viewBinding(FragmentBBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOG_TAG, "FragmentB@${hashCode()} onViewCreated binding = ${viewBinding.javaClass.simpleName}@${viewBinding.hashCode()}")
        viewBinding.root.text = "Fragment B onViewCreated"
        viewBinding.root.setOnClickListener { (context as? MainActivity)?.onFragmentBClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LOG_TAG, "FragmentB@${hashCode()} onDestroyView binding = ${viewBinding.javaClass.simpleName}@${viewBinding.hashCode()}")
    }
}

private const val LOG_TAG = "ViewBindingDelegate"
