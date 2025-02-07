package dev.androidbroadcast.vbpd.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import dev.androidbroadcast.vbpd.sample.databinding.FragmentPersonListBinding
import dev.androidbroadcast.vbpd.sample.databinding.ItemPersonBinding
import dev.androidbroadcast.vbpd.viewBinding

class PersonListFragment : Fragment(R.layout.fragment_person_list) {

    // Without reflection
    private val viewBinding by viewBinding(FragmentPersonListBinding::bind)

    // or with reflection
    // private val viewBinding: FragmentPersonListBinding by viewBinding()

    private var personAdapter: PersonAdapter? = null

    private val onPersonClickListener: OnPersonClickListener
        get() = requireActivity() as OnPersonClickListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personAdapter = PersonAdapter(
            persons = personsStub,
            onItemClick = { person -> onPersonClickListener.onPersonClick(person) }
        ).also { this.personAdapter = it }
        viewBinding.persons.adapter = personAdapter

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.persons) { personsView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            personsView.updatePadding(bottom = insets.bottom + personsView.paddingTop)
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        personAdapter = null
    }

    interface OnPersonClickListener {

        fun onPersonClick(person: Person)
    }
}

private class PersonAdapter(
    private val persons: List<Person>,
    private val onItemClick: (Person) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder.inflate(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(persons[position])
    }

    override fun getItemCount(): Int = persons.size

    class PersonViewHolder(
        private val viewBinding: ItemPersonBinding,
        private val onClick: (Person) -> Unit,
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(person: Person) {
            with(viewBinding) {
                fullname.text = fullname.context.getString(R.string.fullname_format, person.name, person.surname)
                root.setOnClickListener { onClick(person) }
            }
        }

        companion object {

            fun inflate(
                parent: ViewGroup,
                onClick: (Person) -> Unit,
            ): PersonViewHolder {
                val binding = ItemPersonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return PersonViewHolder(binding, onClick)
            }
        }
    }
}
