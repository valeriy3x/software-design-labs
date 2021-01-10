package by.bsuir.tabatatimer.views.adapters

import androidx.recyclerview.widget.DiffUtil
import by.bsuir.tabatatimer.data.viewdata.Sequence

class SequencesDiffUtilCallback: DiffUtil.ItemCallback<Sequence>() {
    override fun areItemsTheSame(oldItem: Sequence, newItem: Sequence): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Sequence, newItem: Sequence): Boolean {
        return oldItem.copy(id = newItem.id) == newItem
    }
}