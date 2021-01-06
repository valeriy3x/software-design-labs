package by.bsuir.tabatatimer.views.adapters

import android.view.*
import android.widget.PopupMenu
import android.widget.RelativeLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.databinding.FragmentHomeBinding
import by.bsuir.tabatatimer.databinding.ItemSequenceBinding
import kotlinx.android.synthetic.main.item_sequence.view.*

class SequencesAdapter :
    ListAdapter<Sequence, SequencesAdapter.SequencesViewHolder>(SequencesDiffUtilCallback()) {

    var listener: PopUpMenuManager<Sequence>? = null
    var playListener: ImageButtonManager<Sequence>? = null


    class SequencesViewHolder(private val binding: ItemSequenceBinding, private val listener: PopUpMenuManager<Sequence>?,
    private val playListener: ImageButtonManager<Sequence>?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sequence: Sequence) {
            binding.sequence = sequence
            sequence.color?.let { binding.cardItemLayout.setBackgroundColor(it) }
            binding.executePendingBindings()
            binding.buttonDropdownMenu.setOnClickListener {
                listener?.showPopUp(it, sequence)
            }
            binding.buttonPlay.setOnClickListener{
                playListener?.play(sequence)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SequencesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSequenceBinding.inflate(layoutInflater, parent, false)

        return SequencesViewHolder(itemBinding, listener, playListener)
    }

    override fun onBindViewHolder(holder: SequencesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}