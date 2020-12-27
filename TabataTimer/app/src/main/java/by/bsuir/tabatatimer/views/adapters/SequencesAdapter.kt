package by.bsuir.tabatatimer.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.databinding.FragmentHomeBinding
import by.bsuir.tabatatimer.databinding.ItemSequenceBinding

class SequencesAdapter :
    ListAdapter<Sequence, SequencesAdapter.SequencesViewHolder>(SequencesDiffUtilCallback()) {
    class SequencesViewHolder(private val binding: ItemSequenceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sequence: Sequence) {
            binding.sequence = sequence
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SequencesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSequenceBinding.inflate(layoutInflater, parent, false)

        return SequencesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SequencesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}