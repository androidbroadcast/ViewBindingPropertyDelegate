package dev.androidbroadcast.vbpd.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.androidbroadcast.vbpd.sample.databinding.FragmentPersonDetailBinding
import dev.androidbroadcast.vbpd.viewBinding

class PersonFragment : Fragment(R.layout.fragment_person_detail) {

    private val viewBinding by viewBinding(FragmentPersonDetailBinding::bind)
    private val person: Person by parcelableArgument(ARG_PERSON)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            firstName.text = person.name
            lastName.text = person.surname
            email.text = person.email
        }
    }

    companion object {

        private const val ARG_PERSON = "PERSON"

        fun arguments(person: Person): Bundle {
            return Bundle(1).apply {
                putParcelable(ARG_PERSON, person)
            }
        }
    }
}
